package com.noofinc.inventory.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.noofinc.inventory.model.Inventory;

@Repository
public interface InventoryRepository extends CrudRepository<Inventory, String> {
	
	

}
