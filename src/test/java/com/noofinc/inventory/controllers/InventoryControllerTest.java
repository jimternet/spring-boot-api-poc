package com.noofinc.inventory.controllers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.annotation.DirtiesContext;

import com.noofinc.inventory.config.TestApplicationConfig;
import com.noofinc.inventory.config.TestCassandraConfig;
import com.noofinc.inventory.config.TestHazelcastConfig;
import com.noofinc.inventory.model.Inventory;

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
public class InventoryControllerTest {
    private static final Logger LOG = LoggerFactory.getLogger(InventoryControllerTest.class);

    @Autowired
    private InventoryController inventoryController;

    private static final String TEST_INVENTORY_ID = "test-inventory-1";
    private static final int INITIAL_SUPPLY = 100;
    private static final int INITIAL_DEMAND = 50;

    @BeforeEach
    public void setup() throws Exception {
        LOG.info("Setting up test data...");
        
        // Create a test inventory item
        Inventory inventory = new Inventory();
        inventory.setInventory_id(TEST_INVENTORY_ID);
        inventory.setSupply(INITIAL_SUPPLY);
        inventory.setDemand(INITIAL_DEMAND);
        
        LOG.info("Creating test inventory: {}", inventory);
        Inventory created = inventoryController.postInventory(inventory);
        LOG.info("Created inventory: {}", created);
        
        // Verify the item was created
        List<Inventory> allItems = inventoryController.getAllInventory();
        LOG.info("Current inventory items: {}", allItems);
        
        // Verify count
        Integer count = inventoryController.getCount();
        LOG.info("Current inventory count: {}", count);
    }

    @AfterAll
    public static void cleanup() {
        TestCassandraConfig.cleanup();
        TestHazelcastConfig.cleanup();
    }

    @Test
    public void testGetInventory() throws Exception {
        LOG.info("Testing getInventory...");
        Inventory inventory = inventoryController.getInventory(TEST_INVENTORY_ID);
        assertNotNull(inventory);
        assertEquals(TEST_INVENTORY_ID, inventory.getInventory_id());
        assertEquals(INITIAL_SUPPLY, inventory.getSupply());
        assertEquals(INITIAL_DEMAND, inventory.getDemand());
        LOG.info("getInventory test passed");
    }

    @Test
    public void testUpdateSupply() throws Exception {
        LOG.info("Testing updateSupply...");
        int newSupply = 200;
        Inventory inventory = inventoryController.updateSupply(TEST_INVENTORY_ID, newSupply);
        assertNotNull(i