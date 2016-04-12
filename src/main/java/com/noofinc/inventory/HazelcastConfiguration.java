package com.noofinc.inventory;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurer;
import org.springframework.cache.interceptor.CacheErrorHandler;
import org.springframework.cache.interceptor.CacheResolver;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.cache.interceptor.SimpleKeyGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.hazelcast.config.Config;
import com.hazelcast.config.JoinConfig;
import com.hazelcast.config.ManagementCenterConfig;
import com.hazelcast.config.MapConfig;
import com.hazelcast.config.MapStoreConfig;
import com.hazelcast.config.NetworkConfig;
import com.hazelcast.config.TcpIpConfig;
import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.IMap;
import com.hazelcast.spring.cache.HazelcastCacheManager;


@Configuration
public class HazelcastConfiguration implements CachingConfigurer {
	
//	@Value("#{systemProperties['pop3.port'] ?: 25}")
	
//	@Value("#{systemProperties['h.url'] ?: 'http://localhost:8080/mancenter-3.3.2'}")
	
	@Value("${hz_url}")
	private String hazelCastManagementUrl;

	@Value("${hz_members}")
	private String hazelcastMembers;
	
	@Bean
	public Config hazelcastConfig(){
		Config config = new Config();
		
		
		NetworkConfig networkConfig = config.getNetworkConfig();
		networkConfig.setPort(5900);
		networkConfig.setPortAutoIncrement(false);
		JoinConfig join = networkConfig.getJoin();
		TcpIpConfig tcpIpConfig = join.getTcpIpConfig();
		tcpIpConfig.addMember(hazelcastMembers);
		tcpIpConfig.setEnabled(true);

		join.getMulticastConfig().setEnabled(false);

		ManagementCenterConfig managementCenterConfig = new ManagementCenterConfig();
		managementCenterConfig.setEnabled(true);
		managementCenterConfig.setUrl(hazelCastManagementUrl);
		config.setManagementCenterConfig(managementCenterConfig );
		
		MapStoreConfig mapStoreConfig = new MapStoreConfig();
		mapStoreConfig.setClassName("com.noofinc.inventory.mapstore.RetailProductLocationMapStore");
		MapConfig retailProductLocationMapConfig = new MapConfig();
		retailProductLocationMapConfig.setMapStoreConfig(mapStoreConfig );
		retailProductLocationMapConfig.setName("retailProductLocationMap");
		config.getMapConfigs().put("retailProductLocationMap", retailProductLocationMapConfig );
		
		
		return config;
	}

	@Bean
	public HazelcastInstance hazelcastInstance(Config hazelcastConfig) {

		HazelcastInstance h = Hazelcast.newHazelcastInstance(hazelcastConfig);
		return h;
	}
	


	
	@Bean 
	public HazelcastCacheManager cacheManager(HazelcastInstance hazelcastInstance){
		HazelcastCacheManager cacheManager = new HazelcastCacheManager();
		cacheManager.setHazelcastInstance(hazelcastInstance);
		return cacheManager;
		
	}

	@Override
	public CacheManager cacheManager() {
		HazelcastCacheManager cacheManager = new HazelcastCacheManager();
		cacheManager.setHazelcastInstance(hazelcastInstance(hazelcastConfig()));
		return cacheManager;
	}

	@Override
	public KeyGenerator keyGenerator() {
		return new SimpleKeyGenerator();
	}

	@Override
	public CacheResolver cacheResolver() {
		return null;
	}

	@Override
	public CacheErrorHandler errorHandler() {
		// TODO Auto-generated method stub
		return null;
	}

	//http://localhost:8080/mancenter-3.3.2
}
