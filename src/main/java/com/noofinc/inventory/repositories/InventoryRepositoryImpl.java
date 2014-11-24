package com.noofinc.inventory.repositories;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;

import com.noofinc.inventory.controllers.InventoryController;
import com.noofinc.inventory.model.Inventory;
import com.noofinc.inventory.repositories.InventoryRepositoryCustom;

public class InventoryRepositoryImpl implements InventoryRepositoryCustom {
	
	private static final Logger LOG = LoggerFactory
			.getLogger(InventoryRepositoryImpl.class);
	
	@Autowired
	InventoryRepository repo;

    @Cacheable(value = "inventory", key="#inventory_id")
	public Inventory findOneInventory(String inventory_id){
		LOG.info("going to try to findOneInventory in the IMPL class" );

		return repo.findOne(inventory_id);
	}
	
	@Override
    @Cacheable(value = "inventories")
	public Iterable<Inventory> findAllInventory(){
		LOG.info("going to try to findAllInventory in the IMPL class" );

		return repo.findAll();
	}
	
	
	@Override
	@Caching(evict = { @CacheEvict(value = "inventories", allEntries=true), @CacheEvict(value = "inventory", key = "#inventory.inventory_id") })
//	@Caching(evict = { @CacheEvict(value = "inventories", allEntries=true), @CacheEvict(value = "inventory")})
	public
	Inventory saveInventory(Inventory inventory){
		LOG.info("going to try to save with saveInventory in the IMPL class" );
		return repo.save(inventory);
	}


    
    
    

}
