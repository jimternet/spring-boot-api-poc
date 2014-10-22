package com.noofinc.inventory.controllers;

import java.util.concurrent.locks.Lock;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.hazelcast.core.HazelcastInstance;
import com.noofinc.inventory.CassandraConfig;
import com.noofinc.inventory.model.Inventory;
import com.noofinc.inventory.repositories.InventoryRepository;
import com.wordnik.swagger.annotations.ApiOperation;

@RestController
public class InventoryController {

	private static final Logger LOG = LoggerFactory
			.getLogger(InventoryController.class);

	@Autowired
	InventoryRepository inventoryRepo;

	@Autowired
	HazelcastInstance hazelcastInstance;

	@RequestMapping("/")
	public String index() {
		return "Greetings from Spring Boot!";
	}

	@RequestMapping(value = "/inventory", method = RequestMethod.GET)
	@ApiOperation(httpMethod = "GET", value = "get all inventory records")
	public @ResponseBody Iterable<Inventory> getAllInventory() {
		
		return inventoryRepo.findAll();
	}

	@RequestMapping(value = "/inventory/{id}", method = { RequestMethod.GET })
	@ApiOperation(httpMethod = "GET", value = "get an inventory record")
	public @ResponseBody Inventory getInventory(@PathVariable("id") String id) {
		return inventoryRepo.findOne(id);
	}

	@RequestMapping(value = "/inventory/", method = { RequestMethod.POST })
	@ApiOperation(httpMethod = "POST", value = "Create an inventory record")
	public @ResponseBody Inventory postInventory(
			@RequestBody Inventory inventory) throws Exception {

		if (inventory == null) {
			throw new Exception();
		} else if (inventoryRepo.findOne(inventory.getId()) != null) {
			LOG.error("You can't create this record : " + inventory.getId()
					+ " it already exsits");
			throw new Exception();
		}

		inventoryRepo.save(inventory);

		return inventory;
	}

	@RequestMapping(value = "/inventory/", method = { RequestMethod.PUT })
	@ApiOperation(httpMethod = "PUT", value = "Update an inventory record")
	public @ResponseBody Inventory putInventory(@RequestBody Inventory inventory)
			throws Exception {

		if (inventory == null) {
			throw new Exception();
		}

		Lock lock = hazelcastInstance.getLock(inventory.getId());

		lock.lock();
		try {
			System.out.println(inventory);
			if (inventoryRepo.findOne(inventory.getId()) == null) {
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
	@RequestMapping(value = "/inventory/{id}/supply/{supply}", method = { RequestMethod.PUT })
	@ApiOperation(httpMethod = "PUT", value = "Update an inventory records supply")
	public @ResponseBody Inventory updateSupply(@PathVariable("id") String id,
			@PathVariable("supply") int supply) throws Exception {
		

		Inventory inventory = inventoryRepo.findOne(id);
		if (inventoryRepo.findOne(inventory.getId()) == null) {
			throw new Exception();
		}
		
		inventory.setSupply(supply);
		inventoryRepo.save(inventory);
		return inventory;

	}

	// Update demand by id
	@RequestMapping(value = "/inventory/{id}/demand/{demand}", method = { RequestMethod.PUT })
	@ApiOperation(httpMethod = "PUT", value = "Update an inventory records demand")
	public @ResponseBody Inventory updateDemand(@PathVariable("id") String id,
			@PathVariable("demand") int demand) throws Exception {

		
		
		Inventory inventory = inventoryRepo.findOne(id);
		if (inventoryRepo.findOne(inventory.getId()) == null) {
			throw new Exception();
		}
		inventory.setDemand(demand);
		inventoryRepo.save(inventory);
		return inventory;

	}

}
