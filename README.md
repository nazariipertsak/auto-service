# :car: Auto-Service :car:
# Project description
Simple REST API of auto-service for ordering wares and favors that supports CRUD operations. Described by Swagger and tested with test containers and mockito.
# Features
- create and update master
- count master's salary
- create and update favors
- update favor's status
- create and update wares
- create and update car
- create and update car owners
- get car owners orders list
- create and update order
- get order's price
- add favors and wares to order
- update order's status
- ability to apply discount to favors and wares depending on previous orders of car owner
# Project structure
Project uses 3-tier architecture:
1. Data access tier -> handled by Spring Data Jpa;
2. Business logic tier -> handled by Service;
3. Presentation tier -> handled by Controllers and Spring.
# Technologies
- Maven
- JDK 17
- JDBC
- Spring-Boot
- Spring-Core
- Spring-Web
- Hibernate
- Tomcat 9.0.50
- PostgresSQL 15
- Swagger
- Mockito
- Docker
- Test Containers
# Instructions to run my project
1. Clone this repository <br/>
2. Configure connection to your database in
> [Auto Service/src/main/resources/application.properties]

By changing url to your database, username and password to your own. <br/>

3. Build project
```shell
mvn clean package
```

4. Run this with docker. <br/>
```shell
docker compose up
```
5. Go to url "http://localhost:6868/swagger-ui/index.html" and read description of end points.
