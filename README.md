spring-boot-api-poc
===================
# frameworks and tools
## Data
* Cassandra - attempted to use embedded - unsucessfully for now
* Hazelcast - using for locking at the moment
* spring-data-cassandra for connections
## Application
* Spring boot

    Skeleton from here : http://start.spring.io/
* Metrics  - using metrics from spring boot

# setup
1. Clone the project
2. use the create table scripts to create necessary tables in C*
3. build the project using maven
4. Run by either using the maven plugin or running the main class in the Application class.
5. Maven = mvn clean test spring-boot:run
6. goto http://localhost:8080/index.html
7. Change the url in the page from "http://petstore.swagger.wordnik.com/api/api-docs" to "http://localhost:8080/api-docs"
8. PROFIT!!



CREATE TABLE inventory(
inventory_id text,
supply int,
demand int, PRIMARY KEY(inventory_id));

INSERT INTO inventory(inventory_id, supply, demand) values('1', 0, 0);

INSERT INTO inventory(inventory_id, supply, demand) values('2', 2, 0);
INSERT INTO inventory(inventory_id, supply, demand) values('3', 5, 0);


## start on each node
nohup java -jar target/inventory-0.0.1-SNAPSHOT.jar --hz_members=cassandra-fatm:5900,cassandra-fi7s:5900 --hz_url=http://10.240.244.88:8080/
