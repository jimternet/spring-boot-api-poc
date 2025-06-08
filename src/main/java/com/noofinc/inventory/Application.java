package com.noofinc.inventory;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

@SpringBootConfiguration
@ComponentScan
@EnableAutoConfiguration
@EnableCaching
//@EmbeddedCassandra
public class Application {
	
//	@Autowired
//	RetailProductLocationRepository retailProductLocationRepository;

    @Bean
    public ApplicationContextProvider applicationContextProvider() {
        return new ApplicationContextProvider();
    }

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
        
//        Application app = new Application();
//        app.retailProductLocationRepository.findAll();
    }
}
