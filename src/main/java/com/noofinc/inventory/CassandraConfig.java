package com.noofinc.inventory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cassandra.core.CqlOperations;
import org.springframework.cassandra.core.CqlTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.data.cassandra.config.CassandraClusterFactoryBean;
import org.springframework.data.cassandra.config.CassandraSessionFactoryBean;
import org.springframework.data.cassandra.config.SchemaAction;
import org.springframework.data.cassandra.config.java.AbstractCassandraConfiguration;
import org.springframework.data.cassandra.convert.CassandraConverter;
import org.springframework.data.cassandra.convert.MappingCassandraConverter;
import org.springframework.data.cassandra.mapping.BasicCassandraMappingContext;
import org.springframework.data.cassandra.mapping.CassandraMappingContext;
import org.springframework.data.cassandra.repository.config.EnableCassandraRepositories;

@Configuration
//@PropertySource(value = { "classpath:cassandra.properties" })
@EnableCassandraRepositories("com.noofinc.inventory.repositories")
public class CassandraConfig extends AbstractCassandraConfiguration {
	
	
	private static final Logger LOG = LoggerFactory.getLogger(CassandraConfig.class);

//	@Autowired
//	private Environment env;
	
	@Value("${cassandra_keyspace}")
	private String keyspaceName;
	
	
	@Value("${cassandra_port}")
	private int port;
	
	
	@Value("${cassandra_contactpoints}")
	private String contactPoints;
	

	@Override
	protected String getKeyspaceName() {
		// TODO Auto-generated method stub
		return keyspaceName;
	}

	@Bean
	public CqlOperations CqlTemplate() throws Exception {
		return new CqlTemplate(session().getObject());
	}
	
	
	@Bean
	public CassandraClusterFactoryBean cluster() {

		CassandraClusterFactoryBean cluster = new CassandraClusterFactoryBean();
		cluster.setContactPoints(contactPoints);
		cluster.setPort(port);
//		cluster.setContactPoints(env.getProperty("cassandra.contactpoints"));
//		cluster.setPort(Integer.parseInt(env.getProperty("cassandra.port")));

		LOG.info("Created CassandraClusterFactoryBean");
		return cluster;
	}

	@Bean
	public CassandraMappingContext mappingContext() {
		LOG.info("Created CassandraMappingContext");

		return new BasicCassandraMappingContext();
	}

	@Bean
	public CassandraConverter converter() {
		LOG.info("Created CassandraConverter");

		return new MappingCassandraConverter(mappingContext());
	}

	@Bean
	public CassandraSessionFactoryBean session() throws Exception {

		LOG.info("Creating CassandraSessionFactoryBean");

		
		CassandraSessionFactoryBean session = new CassandraSessionFactoryBean();
		session.setCluster(cluster().getObject());
		session.setKeyspaceName(keyspaceName);
//		session.setKeyspaceName(env.getProperty("cassandra.keyspace"));
		session.setConverter(converter());
		session.setSchemaAction(SchemaAction.CREATE);

		LOG.info("Created CassandraSessionFactoryBean");

		return session;
	}


	



}
