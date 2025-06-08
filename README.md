# spring-boot-api-poc

🔒 **Security Updated June 2025** - All dependencies upgraded to current secure versions

## 🚨 Security Updates Applied

This repository has been updated with **major security improvements**:

### Dependency Security Upgrades
- **Spring Boot**: 1.2.2.RELEASE (2015) → 3.3.0 (2024) - **9 years of security fixes**
- **Java**: 1.8 → 17 (LTS with ongoing security support)
- **Hazelcast**: 3.3.3 (2014) → 5.4.0 (2024) - **10 years of security fixes**
- **Swagger**: Legacy mangofactory → springdoc-openapi-starter (actively maintained)
- **Podam**: 4.1.0 → 8.0.1 (security and compatibility fixes)

### Breaking Changes (Migration Required)
⚠️ **This is a major version upgrade that requires code changes:**

1. **Java 17 Required**: Update your runtime to Java 17+
2. **Spring Boot 3.x Changes**: 
   - Package namespace: `javax.*` → `jakarta.*`
   - Configuration property changes
   - Some API method signatures changed
3. **Swagger UI**: Now available at `/swagger-ui.html` (springdoc-openapi)
4. **Hazelcast 5.x**: Configuration format changes required

## Framework and Tools

### Data
* **Cassandra** - Using Spring Boot starter for better integration
* **Hazelcast 5.4.0** - Distributed caching and locking
* **Spring Data Cassandra** - Integrated via Spring Boot starter

### Application
* **Spring Boot 3.3.0** - Modern, secure framework
* **Spring Actuator** - Production-ready monitoring
* **OpenAPI 3** - Modern API documentation (replaces Swagger 2)

## Setup

### Prerequisites
- **Java 17+** (required for Spring Boot 3.x)
- **Maven 3.6+**
- **Cassandra 3.11+ or 4.x**

### Quick Start
1. Clone the project
   ```bash
   git clone https://github.com/jimternet/spring-boot-api-poc.git
   cd spring-boot-api-poc
   ```

2. Create necessary tables in Cassandra:
   ```sql
   CREATE TABLE inventory(
       inventory_id text,
       supply int,
       demand int, 
       PRIMARY KEY(inventory_id)
   );

   INSERT INTO inventory(inventory_id, supply, demand) values('1', 0, 0);
   INSERT INTO inventory(inventory_id, supply, demand) values('2', 2, 0);
   INSERT INTO inventory(inventory_id, supply, demand) values('3', 5, 0);
   ```

3. Build and run:
   ```bash
   mvn clean test spring-boot:run
   ```

4. Access the application:
   - **Application**: http://localhost:8080/
   - **API Documentation**: http://localhost:8080/swagger-ui.html
   - **Health Check**: http://localhost:8080/actuator/health

## Production Deployment

For clustered deployment with Hazelcast:
```bash
java -jar target/inventory-0.0.1-SNAPSHOT.jar \
  --hazelcast.network.join.tcp-ip.members=node1:5701,node2:5701 \
  --server.port=8080
```

## Security Features
- ✅ All dependencies updated to secure versions
- ✅ Java 17 with latest security patches
- ✅ Spring Security auto-configuration available
- ✅ Actuator endpoints secured by default
- ✅ HTTPS ready (configure in application.properties)

## Migration Notes

If migrating from the old version, you'll need to:
1. Update your Java runtime to 17+
2. Update import statements (`javax.*` → `jakarta.*`)
3. Review Hazelcast configuration for 5.x compatibility
4. Update any Spring Boot configuration properties
5. Test API endpoints (some may have changed)

## License
Apache License 2.0
