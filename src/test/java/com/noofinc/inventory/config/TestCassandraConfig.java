package com.noofinc.inventory.config;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.data.cassandra.config.AbstractCassandraConfiguration;
import org.springframework.data.cassandra.config.CqlSessionFactoryBean;
import org.springframework.data.cassandra.config.SchemaAction;
import org.springframework.data.cassandra.core.CassandraAdminTemplate;
import org.springframework.data.cassandra.core.CassandraTemplate;
import org.springframework.data.cassandra.core.convert.CassandraConverter;
import org.springframework.data.cassandra.core.convert.MappingCassandraConverter;
import org.springframework.data.cassandra.core.mapping.CassandraMappingContext;
import org.testcontainers.containers.CassandraContainer;
import org.testcontainers.utility.DockerImageName;

import com.datastax.oss.driver.api.core.CqlSession;
import com.datastax.oss.driver.api.core.CqlSessionBuilder;
import com.datastax.oss.driver.api.core.cql.SimpleStatement;

@TestConfiguration
public class TestCassandraConfig extends AbstractCassandraConfiguration {

    private static final String KEYSPACE_NAME = "inventory_test";
    private static final String LOCAL_DATACENTER = "datacenter1";
    private static final DockerImageName CASSANDRA_IMAGE = DockerImageName.parse("cassandra:4.1.3");
    private static CassandraContainer<?> container;

    public static class Initializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {
        @Override
        public void initialize(ConfigurableApplicationContext applicationContext) {
            container = new CassandraContainer<>(CASSANDRA_IMAGE);
            container.start();

            String host = container.getHost();
            int port = container.getMappedPort(9042);

            // Create keyspace
            try (CqlSession session = CqlSession.builder()
                    .addContactPoint(container.getContactPoint())
                    .withLocalDatacenter(LOCAL_DATACENTER)
                    .build()) {
                
                session.execute(SimpleStatement.builder(
                    "CREATE KEYSPACE IF NOT EXISTS " + KEYSPACE_NAME + 
                    " WITH replication = {'class': 'SimpleStrategy', 'replication_factor': 1}")
                    .build());
            }

            TestPropertyValues.of(
                // Spring Data Cassandra properties
                "spring.data.cassandra.contact-points=" + host,
                "spring.data.cassandra.port=" + port,
                "spring.data.cassandra.keyspace-name=" + KEYSPACE_NAME,
                "spring.data.cassandra.schema-action=CREATE_IF_NOT_EXISTS",
                "spring.data.cassandra.local-datacenter=" + LOCAL_DATACENTER,
                "spring.data.cassandra.basic.load-balancing-policy.local-datacenter=" + LOCAL_DATACENTER,
                
                // Legacy Cassandra properties
                "cassandra_keyspace=" + KEYSPACE_NAME,
                "cassandra_contactpoints=" + host,
                "cassandra_port=" + port,
                "cassandra_local-datacenter=" + LOCAL_DATACENTER
            ).applyTo(applicationContext.getEnvironment());
        }
    }

    public static void cleanup() {
        if (container != null && container.isRunning()) {
            container.stop();
        }
    }

    public static boolean isContainerRunning() {
        return container != null && container.isRunning();
    }

    @Override
    protected String getKeyspaceName() {
        return KEYSPACE_NAME;
    }

    @Override
    protected String getLocalDataCenter() {
        return LOCAL_DATACENTER;
    }

    @Override
    protected int getPort() {
        return container != null ? container.getMappedPort(9042) : 9042;
    }

    @Override
    protected String getContactPoints() {
        return container != null ? container.getHost() : "localhost";
    }

    @Override
    public SchemaAction getSchemaAction() {
        return SchemaAction.CREATE_IF_NOT_EXISTS;
    }

    @Bean
    @Primary
    @Override
    public CqlSessionFactoryBean cassandraSession() {
        CqlSessionFactoryBean session = new CqlSessionFactoryBean();
        session.setContactPoints(getContactPoints());
        session.setKeyspaceName(getKeyspaceName());
        session.setPort(getPort());
        session.setLocalDatacenter(LOCAL_DATACENTER);
        return session;
    }

    @Bean(name = "testCassandraMappingContext")
    @Primary
    public CassandraMappingContext mappingContext() {
        return new CassandraMappingContext();
    }

    @Bean(name = "testCassandraConverter")
    @Primary
    public CassandraConverter converter() {
        return new MappingCassandraConverter(mappingContext());
    }

    @Bean(name = "testCassandraTemplate")
    @Primary
    public CassandraTemplate cassandraTemplate(CqlSession session, CassandraConverter converter) {
        return new CassandraTemplate(session, converter);
    }

    @Bean(name = "testCassandraAdminTemplate")
    @Primary
    public CassandraAdminTemplate cassandraAdminTemplate(CqlSession session, CassandraConverter converter) {
        return new CassandraAdminTemplate(session, converter);
    }
} 