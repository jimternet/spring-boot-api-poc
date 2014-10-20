package com.noofinc.inventory.repositories;

import static org.junit.Assert.*;

import org.cassandraunit.spring.CassandraDataSet;
import org.cassandraunit.spring.CassandraUnitTestExecutionListener;
import org.cassandraunit.spring.EmbeddedCassandra;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.data.cassandra.core.CassandraOperations;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.noofinc.inventory.Application;


@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
//@TestExecutionListeners({ CassandraUnitTestExecutionListener.class })
//@CassandraDataSet(value = { "cql/inventory1.cql" }, keyspace = "test")
//@EmbeddedCassandra
public class InventoryRepositoryTest {
	
	@Autowired
	InventoryRepository inventoryRepository;
	
	
	@Autowired
	protected CassandraOperations template;
	
	
	@Test
	public void testRepo(){
		long count = inventoryRepository.count();
		assertNotNull(count);
	}
	
	
	
	



}
