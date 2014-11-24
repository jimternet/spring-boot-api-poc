package com.noofinc.inventory.repositories.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.data.cassandra.core.CassandraTemplate;
import org.springframework.data.cassandra.repository.query.CassandraEntityInformation;
import org.springframework.data.cassandra.repository.support.SimpleCassandraRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.noofinc.inventory.model.Inventory;
import com.noofinc.inventory.repositories.InventoryRepository;
import com.noofinc.inventory.repositories.InventoryRepositoryCustom;

public class InventoryRepositoryCustomImpl implements InventoryRepositoryCustom {
	
	@Autowired
	InventoryRepository repo;

    @Cacheable(value = "inventory", key="#inventory_id")
	public Inventory findOne(String inventory_id){
		return repo.findOne(inventory_id);
	}
	
	@Override
    @Cacheable(value = "inventories")
	public Iterable<Inventory> findAll(){
		return repo.findAll();
	}
	
	
	@Override
//	@CacheEvict(value = "inventories", allEntries=true)
	@Caching(evict = { @CacheEvict(value = "inventories", allEntries=true), @CacheEvict(value = "inventory", key = "#inventory_id") })
//	@Caching(evict = { @CacheEvict(value = "inventories", allEntries=true), @CacheEvict(value = "inventory")})
	public
	Inventory save(Inventory inventory){
		return repo.save(inventory);
	}





    
    
    

}
