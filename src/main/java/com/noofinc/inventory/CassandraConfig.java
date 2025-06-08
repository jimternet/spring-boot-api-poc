package com.noofinc.inventory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.cassandra.config.AbstractCassandraConfiguration;
import org.springframework.data.cassandra.config.CqlSessionFactoryBean;
import org.springframework.data.cassandra.core.CassandraAdminTemplate;
import org.springframework.data.cassandra.core.convert.CassandraConverter;
import org.springframework.data.cassandra.core.convert.MappingCassandraConverter;
import org.springframework.data.cassandra.core.mapping.CassandraMappingContext;
import org.springframework.data.cassandra.repository.config.EnableCassandraRepositories;

@Configuration
@EnableCassandraRepositories(basePackages = "com.noofinc.inventory.repositories")
public class CassandraConfig extends AbstractCassandraConfiguration {

	private static final Logger LOG = LoggerFactory.getLogger(CassandraConfig.class);
	private static final String LOCAL_DATACENTER = "datacenter1";

	@Value("${cassandra_keyspace}")
	private String keyspaceName;

	@Value("${cassandra_port}")
	private int port;

	@Value("${cassandra_contactpoints}")
	private String contactPoints;

	@Override
	protected String getKeyspaceName() {
		return keyspaceName;
	}

	@Override
	protected String getContactPoints() {
		return contactPoints;
	}

	@Override
	protected int getPort() {
		return port;
	}

	@Override
	protected String getLocalDataCenter() {
		return LOCAL_DATACENTER;
	}

	@Bean
	@Primary
	@Override
	public CqlSessionFactoryBean cassandraSession() {
		CqlSessionFactoryBean session = new CqlSessionFactoryBean();
		session.setContactPoints(contactPoints);
		session.setKeyspaceName(keyspaceName);
		session.setPort(port);
		session.setLocalDatacenter(LOCAL_DATACENTER);
		return session;
	}

	@Bean
	public CassandraMappingContext mappingContext() {
		LOG.info("Created CassandraMappingContext");
		return new CassandraMappingContext();
	}

	@Bean
	public CassandraConverter converter() {
		LOG.info("Created CassandraConverter");
		return new MappingCassandraConverter(mappingContext());
	}

	@Bean
	public CassandraAdminTemplate cassandraTemplate(CqlSessionFactoryBean session, CassandraConverter converter) throws Exception {
		return new CassandraAdminTemplate(session.getObject(), converter);
	}
}
