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
        assertNotNull(inventory);
        assertEquals(newSupply, inventory.getSupply());
        assertEquals(INITIAL_DEMAND, inventory.getDemand());
        LOG.info("updateSupply test passed");
    }

    @Test
    public void testUpdateDemand() throws Exception {
        LOG.info("Testing updateDemand...");
        int newDemand = 75;
        Inventory inventory = inventoryController.updateDemand(TEST_INVENTORY_ID, newDemand);
        assertNotNull(inventory);
        assertEquals(INITIAL_SUPPLY, inventory.getSupply());
        assertEquals(newDemand, inventory.getDemand());
        LOG.info("updateDemand test passed");
    }

    @Test
    public void testGetNonExistentInventory() {
        LOG.info("Testing getNonExistentInventory...");
        assertThrows(Exception.class, () -> {
            inventoryController.getInventory("non-existent-id");
        });
        LOG.info("getNonExistentInventory test passed");
    }

    @Test
    public void testUpdateNonExistentInventory() {
        LOG.info("Testing updateNonExistentInventory...");
        assertThrows(Exception.class, () -> {
            inventoryController.updateSupply("non-existent-id", 100);
        });
        LOG.info("updateNonExistentInventory test passed");
    }

    @Test
    public void testGetCount() {
        LOG.info("Testing getCount...");
        Integer count = inventoryController.getCount();
        assertNotNull(count);
        assertEquals(1, count, "Count should be 1 after setup");
        LOG.info("getCount test passed with count: {}", count);
    }

    @Test
    public void testConcurrentUpdates() throws Exception {
        LOG.info("Testing concurrent updates...");
        // Create a new inventory item for concurrent testing
        String concurrentId = "concurrent-test";
        Inventory inventory = new Inventory();
        inventory.setInventory_id(concurrentId);
        inventory.setSupply(0);
        inventory.setDemand(0);
        inventoryController.postInventory(inventory);
        LOG.info("Created inventory for concurrent testing: {}", inventory);

        // Create multiple threads to update the same inventory
        int numThreads = 5;
        ExecutorService executor = Executors.newFixedThreadPool(numThreads);
        CountDownLatch latch = new CountDownLatch(numThreads);

        // Each thread will try to update the supply
        for (int i = 0; i < numThreads; i++) {
            final int supply = i + 1;
            executor.submit(() -> {
                try {
                    inventoryController.putInventory(inventory);
                    latch.countDown();
                } catch (Exception e) {
                    LOG.error("Error in concurrent update", e);
                }
            });
        }

        // Wait for all threads to complete
        boolean completed = latch.await(10, TimeUnit.SECONDS);
        assertTrue(completed, "Concurrent updates should complete within timeout");

        // Verify the final state
        Inventory finalInventory = inventoryController.getInventory(concurrentId);
        assertNotNull(finalInventory);
        assertEquals(concurrentId, finalInventory.getInventory_id());
        LOG.info("Concurrent updates test passed");
    }
} 