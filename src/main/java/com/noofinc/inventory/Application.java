package com.noofinc.inventory;

import org.cassandraunit.spring.EmbeddedCassandra;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import com.mangofactory.swagger.plugin.EnableSwagger;

@Configuration
@ComponentScan
@EnableAutoConfiguration
//@EmbeddedCassandra
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
