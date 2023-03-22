# Getting Started

### Reference Documentation
For further reference, please consider the following sections:

* [Official Apache Maven documentation](https://maven.apache.org/guides/index.html)
* [Spring Boot Maven Plugin Reference Guide](https://docs.spring.io/spring-boot/docs/2.7.9/maven-plugin/reference/html/)
* [Create an OCI image](https://docs.spring.io/spring-boot/docs/2.7.9/maven-plugin/reference/html/#build-image)
* [Spring Data Reactive for Apache Cassandra](https://docs.spring.io/spring-boot/docs/2.7.9/reference/htmlsingle/#data.nosql.cassandra)
* [Spring Reactive Web](https://docs.spring.io/spring-boot/docs/2.7.9/reference/htmlsingle/#web.reactive)

### Guides
The following guides illustrate how to use some features concretely:

* [Spring Data Reactive for Apache Cassandra](https://spring.io/guides/gs/accessing-data-cassandra/)
* [Building a Reactive RESTful Web Service](https://spring.io/guides/gs/reactive-rest-service/)

### Running the application

In order to run the app, please follow the setups steps below:

1. Run cassandra db using a docker image.

```aidl
docker run -p 9042:9042 --rm --name cassandra -d cassandra:4.0.7
```

2. Create the cassandra namespace by executing the following commands

```aidl
docker exec -it cassandra cqlsh
CREATE KEYSPACE container_booking_manager_keyspace WITH replication = {'class' : 'SimpleStrategy', 'replication_factor' : 1};
```

3. Build the application:

```aidl
mvn clean install 
```

5. Run the application 

```aidl
mvn spring-boot:run
```