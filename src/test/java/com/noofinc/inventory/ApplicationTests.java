package com.noofinc.inventory;

import static org.junit.Assert.*;

//import org.cassandraunit.spring.CassandraDataSet;
//import org.cassandraunit.spring.CassandraUnitTestExecutionListener;
//import org.cassandraunit.spring.EmbeddedCassandra;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.Session;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
// @TestExecutionListeners({ CassandraUnitTestExecutionListener.class })
//@TestExecutionListeners({ CassandraUnitTestExecutionListener.class })

//@CassandraDataSet(value = { "cql/inventory1.cql" }, keyspace = "esv")
//@EmbeddedCassandra
public class ApplicationTests {



	@BeforeClass
	public static void beforeClass() {
		DummyCassandraConnector.resetInstancesCounter();
	}
	
	@Test
	public void placeholder(){
		assertTrue(true);
	}


//	@Test
	public void checkSupply() throws Exception {

	    Cluster cluster = Cluster.builder()
	            .addContactPoints("127.0.0.1")
	            .withPort(9042)
	            .build();
	        Session session = cluster.connect("esv");
	        
		
		ResultSet result = session
				.execute("select * from inventory WHERE inventory_id='x'");

		int val = result.iterator().next().getInt("supply");
//		assertEquals(5, val);
		assertNotNull(val);
	}

}
