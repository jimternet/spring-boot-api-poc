package com.noofinc.inventory.config;

import java.time.Duration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.wait.strategy.Wait;
import org.testcontainers.utility.DockerImageName;

import com.hazelcast.client.HazelcastClient;
import com.hazelcast.client.config.ClientConfig;
import com.hazelcast.core.HazelcastInstance;

@TestConfiguration
public class TestHazelcastConfig {
    private static final Logger logger = LoggerFactory.getLogger(TestHazelcastConfig.class);

    private static final DockerImageName HAZELCAST_IMAGE = DockerImageName.parse("hazelcast/hazelcast:5.3.6");
    private static final int HAZELCAST_PORT = 5900;
    private static final Duration STARTUP_TIMEOUT = Duration.ofMinutes(2);
    private static GenericContainer<?> container;

    public static class Initializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {
        @Override
        public void initialize(ConfigurableApplicationContext applicationContext) {
            logger.info("Starting Hazelcast container...");
            
            container = new GenericContainer<>(HAZELCAST_IMAGE)
                .withExposedPorts(HAZELCAST_PORT)
                .withStartupTimeout(STARTUP_TIMEOUT)
                .withEnv("JAVA_OPTS", "-Dhazelcast.config=/opt/hazelcast/config/hazelcast.xml")
                .withEnv("HZ_NETWORK_PORT", String.valueOf(HAZELCAST_PORT))
                .withEnv("HZ_NETWORK_PUBLICADDRESS", "localhost:" + HAZELCAST_PORT)
                .withEnv("HZ_CLUSTERNAME", "test-cluster")
                .withEnv("HZ_MEMBERNAME", "test-member")
                .withLogConsumer(outputFrame -> logger.info(outputFrame.getUtf8String()))
                .waitingFor(Wait.forLogMessage(".*Members \\{size:1, ver:1\\}.*", 1)
                    .withStartupTimeout(STARTUP_TIMEOUT));

            try {
                container.start();
                logger.info("Hazelcast container started successfully");
            } catch (Exception e) {
                logger.error("Failed to start Hazelcast container", e);
                throw e;
            }

            String host = container.getHost();
            int port = container.getMappedPort(HAZELCAST_PORT);
            String memberAddress = host + ":" + port;
            logger.info("Hazelcast member address: {}", memberAddress);

            TestPropertyValues.of(
                // Hazelcast properties
                "hz_url=http://" + memberAddress,
                "hz_members=" + memberAddress,
                "hz_username=admin",
                "hz_password=admin",
                "spring.hazelcast.config=classpath:hazelcast-test.xml",
                
                // System properties
                "pop3.port=25",
                "h.url=http://localhost:8080/mancenter-3.3.2",
                
                // Map store configuration
                "hazelcast.map.retailProductLocationMap.map-store.class-name=com.noofinc.inventory.mapstore.RetailProductLocationMapStore"
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

    @Bean
    @Primary
    public HazelcastInstance hazelcastInstance() {
        logger.info("Creating Hazelcast client instance...");
        ClientConfig clientConfig = new ClientConfig();
        clientConfig.setClusterName("test-cluster");
        return HazelcastClient.newHazelcastClient(clientConfig);
    }
} 