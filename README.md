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
