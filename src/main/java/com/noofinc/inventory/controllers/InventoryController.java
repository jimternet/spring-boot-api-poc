package com.noofinc.inventory.controllers;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.cp.lock.FencedLock;
import com.noofinc.inventory.model.Inventory;
import com.noofinc.inventory.repositories.InventoryRepository;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@Tag(name = "Inventory Management", description = "APIs for managing inventory records")
public class InventoryController {

	private static final Logger LOG = LoggerFactory.getLogger(InventoryController.class);

	private final InventoryRepository inventoryRepo;
	private final Inventory inventory;
	private final HazelcastInstance hazelcastInstance;

	public InventoryController(InventoryRepository inventoryRepo, Inventory inventory, HazelcastInstance hazelcastInstance) {
		this.inventoryRepo = inventoryRepo;
		this.inventory = inventory;
		this.hazelcastInstance = hazelcastInstance;
	}

	@RequestMapping(value = "/inventory/count/", method = RequestMethod.GET)
	@Operation(summary = "Get inventory count", description = "Returns the total count of inventory records")
	public @ResponseBody Integer getCount() {
		return inventoryRepo.getCount(hazelcastInstance);
	}
	
	@RequestMapping(value = "/inventory", method = RequestMethod.GET)
	@Operation(summary = "Get all inventory records", description = "Returns a list of all inventory records")
	public @ResponseBody List<Inventory> getAllInventory() {
		return (List<Inventory>) inventoryRepo.findAllInventory();
	}

	@RequestMapping(value = "/inventory/{inventory_id}", method = RequestMethod.GET)
	@Operation(summary = "Get inventory by ID", description = "Returns a single inventory record by its ID")
	public @ResponseBody Inventory getInventory(@PathVariable("inventory_id") String inventory_id) throws Exception {
		LOG.info("about to search for inventory with key : " + inventory_id);
		Inventory inventory = inventoryRepo.findOneInventory(inventory_id);
		if (inventory == null) {
			throw new Exception("Inventory record not found");
		}
		return inventory;
	}

	@RequestMapping(value = "/inventory/", method = RequestMethod.POST)
	@Operation(summary = "Create inventory record", description = "Creates a new inventory record")
	public @ResponseBody Inventory postInventory(@RequestBody Inventory inventory) throws Exception {
		if (inventory == null) {
			throw new Exception("Inventory cannot be null");
		} 

		inventoryRepo.saveInventory(inventory);
		return inventory;
	}

	@RequestMapping(value = "/inventory/", method = RequestMethod.PUT)
	@Operation(summary = "Update inventory record", description = "Updates an existing inventory record")
	public @ResponseBody Inventory putInventory(@RequestBody Inventory inventory) throws Exception {
		if (inventory == null) {
			throw new Exception("Inventory cannot be null");
		}

		LOG.info("just before lock : " + inventory.toString());

		FencedLock lock = hazelcastInstance.getCPSubsystem().getLock(inventory.getInventory_id());

		lock.lock();
		try {
			if (inventoryRepo.findOneInventory(inventory.getInventory_id()) == null) {
				throw new Exception("Inventory record not found");
			}

			LOG.info("just before save : " + inventory.toString());
			inventoryRepo.saveInventory(inventory);
		} finally {
			LOG.info("unlocking");
			lock.unlock();
		}

		return inventory;
	}

	@RequestMapping(value = "/inventory/{inventory_id}/supply/{supply}", method = RequestMethod.PUT)
	@Operation(summary = "Update inventory supply", description = "Updates the supply value for an inventory record")
	public @ResponseBody Inventory updateSupply(
			@PathVariable("inventory_id") String inventory_id,
			@PathVariable("supply") int supply) throws Exception {
		
		Inventory inventory = inventoryRepo.findOneInventory(inventory_id);
		if (inventory == null) {
			throw new Exception("Inventory record not found");
		}
		
		inventory.setSupply(supply);
		inventoryRepo.saveInventory(inventory);
		return inventory;
	}

	@RequestMapping(value = "/inventory/{inventory_id}/demand/{demand}", method = RequestMethod.PUT)
	@Operation(summary = "Update inventory demand", description = "Updates the demand value for an inventory record")
	public @ResponseBody Inventory updateDemand(
			@PathVariable("inventory_id") String inventory_id,
			@PathVariable("demand") int demand) throws Exception {
		
		Inventory inventory = inventoryRepo.findOneInventory(inventory_id);
		if (inventory == null) {
			throw new Exception("Inventory record not found");
		}
		
		inventory.setDemand(demand);
		inventoryRepo.saveInventory(inventory);
		return inventory;
	}
}
