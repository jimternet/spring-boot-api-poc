package com.noofinc.inventory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import com.mangofactory.swagger.plugin.EnableSwagger;

@Configuration
@ComponentScan
@EnableAutoConfiguration
@EnableCaching
//@EmbeddedCassandra
public class Application {
	
//	@Autowired
//	RetailProductLocationRepository retailProductLocationRepository;

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
        
//        Application app = new Application();
//        app.retailProductLocationRepository.findAll();
    }
}
