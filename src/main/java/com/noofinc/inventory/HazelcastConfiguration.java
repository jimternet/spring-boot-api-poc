package com.noofinc.inventory;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.hazelcast.config.Config;
import com.hazelcast.config.JoinConfig;
import com.hazelcast.config.ManagementCenterConfig;
import com.hazelcast.config.NetworkConfig;
import com.hazelcast.config.TcpIpConfig;
import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.spring.cache.HazelcastCacheManager;

@Configuration
public class HazelcastConfiguration {
	
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

	//http://localhost:8080/mancenter-3.3.2
}
