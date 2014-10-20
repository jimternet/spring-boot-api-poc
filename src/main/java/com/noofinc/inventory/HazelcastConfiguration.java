package com.noofinc.inventory;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.hazelcast.config.Config;
import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;

@Configuration
public class HazelcastConfiguration {

	@Bean
	public HazelcastInstance hazelcastInstance() {
		Config config = new Config();
		HazelcastInstance h = Hazelcast.newHazelcastInstance(config);
		return h;
	}

}
