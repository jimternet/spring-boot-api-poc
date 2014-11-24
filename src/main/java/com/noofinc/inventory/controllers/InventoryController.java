package com.noofinc.inventory.controllers;

import java.util.List;
import java.util.concurrent.locks.Lock;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.hazelcast.core.HazelcastInstance;
import com.noofinc.inventory.CassandraConfig;
import com.noofinc.inventory.model.Inventory;
import com.noofinc.inventory.repositories.InventoryRepositoryCustom;
import com.wordnik.swagger.annotations.ApiOperation;

@RestController
public class InventoryController {

	private static final Logger LOG = LoggerFactory
			.getLogger(InventoryController.class);

	@Autowired
	InventoryRepositoryCustom inventoryRepo;

	@Autowired
	Inventory inventory;
	
	@Autowired
	HazelcastInstance hazelcastInstance;



	@RequestMapping(value = "/inventory", method = RequestMethod.GET)
	@ApiOperation(httpMethod = "GET", hidden=false, nickname="Get All",
	value = "get all inventory records"
//	, notes ="This Operation should return all the records that we ahve in the table."
	)
	public @ResponseBody List<Inventory> getAllInventory() {
		
		return (List<Inventory>) inventoryRepo.findAll();
	}

	@RequestMapping(value = "/inventory/{inventory_id}", method = { RequestMethod.GET })
	@ApiOperation(httpMethod = "GET", nickname="Get One", value = "get an inventory record", notes="this returns one inventory record by it's inventory ID" )
	public @ResponseBody Inventory getInventory(@PathVariable("inventory_id") String inventory_id) {
		LOG.info("about to search for inventory with key : " + inventory_id);
		return inventoryRepo.findOne(inventory_id);
	}

	@RequestMapping(value = "/inventory/", method = { RequestMethod.POST })
	@ApiOperation(httpMethod = "POST", value = "Create an inventory record")
	public @ResponseBody Inventory postInventory(
			@RequestBody Inventory inventory) throws Exception {

		if (inventory == null) {
			throw new Exception();
		} 
//		else if (inventoryRepo.findOne(inventory.getInventory_id()) != null) {
//			LOG.error("You can't create this record : " + inventory.getInventory_id()
//					+ " it already exsits");
//			throw new Exception();
//		}

		inventoryRepo.save(inventory);

		return inventory;
	}

	@RequestMapping(value = "/inventory/", method = { RequestMethod.PUT })
	@ApiOperation(httpMethod = "PUT", value = "Update one inventory record")
	public @ResponseBody Inventory putInventory(@RequestBody Inventory inventory)
			throws Exception {

		if (inventory == null) {
			throw new Exception();
		}

		Lock lock = hazelcastInstance.getLock(inventory.getInventory_id());

		lock.lock();
		try {
			System.out.println(inventory);
			if (inventoryRepo.findOne(inventory.getInventory_id()) == null) {
				throw new Exception();
			}

			inventoryRepo.save(inventory);
		} finally {
			LOG.info("unlocking");
			lock.unlock();
		}

		return inventory;
	}

	// Update supply by id
	@RequestMapping(value = "/inventory/{inventory_id}/supply/{supply}", method = { RequestMethod.PUT })
	@ApiOperation(httpMethod = "PUT", value = "Update an inventory records supply")
	public @ResponseBody Inventory updateSupply(@PathVariable("inventory_id") String inventory_id,
			@PathVariable("supply") int supply) throws Exception {
		

		Inventory inventory = inventoryRepo.findOne(inventory_id);
		if (inventoryRepo.findOne(inventory.getInventory_id()) == null) {
			throw new Exception();
		}
		
		inventory.setSupply(supply);
		inventoryRepo.save(inventory);
		return inventory;

	}

	// Update demand by id
	@RequestMapping(value = "/inventory/{inventory_id}/demand/{demand}", method = { RequestMethod.PUT })
	@ApiOperation(httpMethod = "PUT", value = "Update an inventory records demand")
	public @ResponseBody Inventory updateDemand(@PathVariable("inventory_id") String inventory_id,
			@PathVariable("demand") int demand) throws Exception {

		
		
		Inventory inventory = inventoryRepo.findOne(inventory_id);
		if (inventoryRepo.findOne(inventory.getInventory_id()) == null) {
			throw new Exception();
		}
		inventory.setDemand(demand);
		inventoryRepo.save(inventory);
		return inventory;

	}
	
	


}
