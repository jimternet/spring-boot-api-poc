package com.noofinc.inventory.repositories;

import com.noofinc.inventory.model.Inventory;

public interface InventoryRepositoryCustom {

	Inventory findOne(String inventory_id);
	
	Iterable<Inventory> findAll();
	
	Inventory save(Inventory inventory);




}
