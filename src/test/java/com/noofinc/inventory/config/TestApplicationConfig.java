package com.noofinc.inventory.config;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.cassandra.CassandraAutoConfiguration;
import org.springframework.boot.autoconfigure.data.cassandra.CassandraDataAutoConfiguration;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.context.annotation.Import;
import org.springframework.data.cassandra.repository.config.EnableCassandraRepositories;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.noofinc.inventory.CassandraConfig;
import com.noofinc.inventory.HazelcastConfiguration;
import com.noofinc.inventory.repositories.InventoryRepository;

@TestConfiguration
@SpringBootApplication(scanBasePackages = "com.noofinc.inventory")
@ImportAutoConfiguration(exclude = {
    CassandraAutoConfiguration.class,
    CassandraDataAutoConfiguration.class
})
@ComponentScan(
    basePackages = "com.noofinc.inventory",
    excludeFilters = {
        @ComponentScan.Filter(
            type = FilterType.ASSIGNABLE_TYPE,
            classes = {
                CassandraConfig.class,
                HazelcastConfiguration.class
            }
        )
    }
)
@EnableCassandraRepositories(
    basePackages = "com.noofinc.inventory.repositories",
    basePackageClasses = InventoryRepository.class,
    excludeFilters = @ComponentScan.Filter(
        type = FilterType.ASSIGNABLE_TYPE,
        classes = CassandraConfig.class
    )
)
@Import({TestCassandraConfig.class, TestHazelcastConfig.class})
public class TestApplicationConfig {
} 