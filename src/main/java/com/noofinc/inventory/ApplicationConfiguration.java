package com.noofinc.inventory;

import java.io.IOException;

import org.apache.cassandra.exceptions.ConfigurationException;
import org.apache.thrift.transport.TTransportException;
import org.cassandraunit.utils.EmbeddedCassandraServerHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import com.noofinc.inventory.repositories.InventoryRepository;
import com.noofinc.inventory.repositories.RetailProductLocationRepository;


@Configuration
@ComponentScan
@EnableAutoConfiguration
public class ApplicationConfiguration {
	
	
	@Autowired
	InventoryRepository inventoryRepository;
	
	@Autowired
	RetailProductLocationRepository retailProductLocationRepository;
	
}
