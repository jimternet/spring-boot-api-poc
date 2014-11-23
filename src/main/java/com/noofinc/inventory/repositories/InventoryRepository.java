package com.noofinc.inventory.repositories;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.noofinc.inventory.model.Inventory;

@Repository
public interface InventoryRepository extends CrudRepository<Inventory, String> {

	@Override
//	@CachePut(value="book", key="#isbn")
//	@CachePut(value = "inventory", key="#inventory_id")
	
    @Cacheable(value = "inventory")
	Inventory findOne(String inventory_id);
	
	@Override
    @Cacheable(value = "inventories")
//    @Cacheable(value = "inventory")

	Iterable<Inventory> findAll();
	
	
	@Override
//	@CacheEvict(value = "inventories", allEntries=true)
//	@Caching(evict = { @CacheEvict(value = "inventories", allEntries=true), @CacheEvict(value = "inventory", key = "#inventory_id") })
	@Caching(evict = { @CacheEvict(value = "inventories", allEntries=true), @CacheEvict(value = "inventory")})
//	@CacheEvict(value = "inventory", key = "#inventory_id")
	Inventory save(Inventory inventory);

    
    
    

}
