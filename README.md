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



#notes
java -jar target/inventory-0.0.1-SNAPSHOT.jar --hz_url=http://162.222.176.205:8888 -—hz_members=10.240.191.47,10.240.158.89



java -jar target/inventory-0.0.1-SNAPSHOT.jar -—hz_members=10.240.191.47,10.240.158.89


java -jar target/inventory-0.0.1-SNAPSHOT.jar --hz_members=cassandra-fatm:5900,cassandra-fi7s:5900



INSERT INTO inventory(inventory_id, supply, demand) values('1', 0, 0);

INSERT INTO inventory(inventory_id, supply, demand) values('2', 2, 0);
INSERT INTO inventory(inventory_id, supply, demand) values('3', 5, 0);


nohup java -jar target/inventory-0.0.1-SNAPSHOT.jar --hz_members=cassandra-fatm:5900,cassandra-fi7s:5900


java -jar jetty-runner.jar mancenter-3.2.3.war
--hz_url=http://10.240.244.88:8080/

java -jar jetty-runner.jar mancenter-3.2.3.war 
java -jar jetty-runner.jar mancenter-3.2.3.war 


--hz_url=http://10.240.244.88:8080/

CREATE KEYSPACE davros WITH REPLICATION = { 'class' : 'NetworkTopologyStrategy', 'TTC' : 2, 'TTCE' : 2};

CREATE KEYSPACE esv WITH REPLICATION = { 'class' : 'SimpleStrategy', 'replication_factor' : 3 };

java -jar target/inventory-0.0.1-SNAPSHOT.jar --hz_members=cassandra-fatm:5900,cassandra-fi7s:5900 --hz_url=http://10.240.244.88:8080/

java -jar target/inventory-0.0.1-SNAPSHOT.jar --hz_members=cassandra-fatm:5900,cassandra-fi7s:5900 --hz_url=http://162.222.176.205:8080/


CREATE TABLE inventory(
inventory_id text,
supply int,
demand int, PRIMARY KEY(inventory_id));

INSERT INTO inventory(inventory_id, supply, demand) values('1', 0, 0);

INSERT INTO inventory(inventory_id, supply, demand) values('2', 2, 0);
INSERT INTO inventory(inventory_id, supply, demand) values('3', 5, 0);


## start on each node
nohup java -jar target/inventory-0.0.1-SNAPSHOT.jar --hz_members=cassandra-fatm:5900,cassandra-fi7s:5900 --hz_url=http://10.240.244.88:8080/


### ISSUES

com.hazelcast.com.eclipsesource.json.ParseException: Expected value at 1:0


gcloud compute copy-files hazelcast-3.3.3.zip cassandra-uo7k:/home/jimternet --zone "us-central1-a" --project "atp-01"




newman -c update.json -e fi7_env.json -n 10000

newman -c update.json -e fatm_env.json -n 10000



fatm_env.json
fi7_env.json