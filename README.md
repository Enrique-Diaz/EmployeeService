# EmployeeService
Employee REST Service

Test project of a µEmployee with the following technologies;
* Java 8
* Spring Boot 2.1.7.RELEASE
* Spring Framework 5.1.9.RELEASE
* Hibernate 5.4.4.Final
* MySql 8.0.17 (Connector)
* JUnit 5.5.1
* Mockito 3.0.0

Service will serve the following petitions;
* Get employees by ID
* Create new employees
* Update existing employees
* Delete employees
* Get all employees

# Things to consider
1. Modify the path for the logger in the src/main/resources/logback.xml
2. Update the src/main/resources/application.properties to connecto to the DB (MySql Ver 14.14 Distrib 5.7.11, for osx10.9 (x86_64))
3. Project has a DDL under resources which will be executed upon project starts (DB need to be running) which has the instructions to DROP `tbl_Employee` if exists, recreate it and fill it with some records to test the service, if you don't want it to be executed take out the following props HibernateConfiguration.java;
  * hibernate.hbm2ddl.auto
  * hibernate.hbm2ddl.import_files
