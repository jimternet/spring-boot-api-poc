package com.noofinc.inventory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import com.noofinc.inventory.repositories.InventoryRepositoryCustom;
import com.noofinc.inventory.repositories.RetailProductLocationRepository;


@Configuration
@ComponentScan
@EnableAutoConfiguration
public class ApplicationConfiguration {
	
	
	@Autowired
	InventoryRepositoryCustom inventoryRepository;
	
	@Autowired
	RetailProductLocationRepository retailProductLocationRepository;
	
}
