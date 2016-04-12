package com.noofinc.inventory.repositories;

import com.noofinc.inventory.model.Inventory;

public interface InventoryRepositoryCustom {

	Inventory findOneInventory(String inventory_id);
	
	Iterable<Inventory> findAllInventory();
	
	Inventory saveInventory(Inventory inventory);
	
	Integer getCount();




}
