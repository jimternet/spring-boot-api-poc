package com.noofinc.inventory;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.context.ApplicationContext;

import com.noofinc.inventory.config.TestApplicationConfig;
import com.noofinc.inventory.config.TestCassandraConfig;
import com.noofinc.inventory.config.TestHazelcastConfig;
import com.noofinc.inventory.repositories.InventoryRepository;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(properties = {
    "spring.main.allow-bean-definition-overriding=true",
    "spring.main.allow-circular-references=true"
})
@ContextConfiguration(
    classes = TestApplicationConfig.class,
    initializers = {
        TestCassandraConfig.Initializer.class,
        TestHazelcastConfig.Initializer.class
    }
)
@ActiveProfiles("test")
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class TestContextLoadsTest {

    @Autowired
    private ApplicationContext applicationContext;

    @Autowired
    private InventoryRepository inventoryRepository;

    @AfterAll
    public static void cleanup() {
        // Cleanup test data
        TestCassandraConfig.cleanup();
        TestHazelcastConfig.cleanup();
    }

    @Test
    public void testSpringContextLoadsSuccessfully() {
        // Verify that the application context is not null
        assertNotNull(applicationContext, "Application context should not be null");
        
        // Verify that the test configuration is loaded
        assertTrue(applicationContext.containsBean("testApplicationConfig"), 
            "Test application configuration should be loaded");
        
        // Verify that the repository is properly configured
        assertNotNull(inventoryRepository, "Inventory repository should be autowired");
        
        // Verify that the test beans are properly configured
        assertTrue(applicationContext.containsBean("cassandraTemplate"), 
            "Cassandra template should be configured");
        assertTrue(applicationContext.containsBean("hazelcastInstance"), 
            "Hazelcast instance should be configured");
    }
} 