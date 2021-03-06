# expense-tracker-app
Restful-APIs for expense tracking application with JWT security.

Springboot project to perform operations for expense tracking application. 
- Followed J2EE architectural design patterns and divided backend into resource, service and repository(dao) layer. 
- Enhanced security by avoiding taking userId from client instead set the attribute userId to HttpServletRequest during authentication for each request.
- Implemented payload validation using spring-boot-starter-validation 
- Made custom DTO for validation, security and avoid circular dependency in DBMS. 
- Secured endpoints with JWT tokens. 
- Custom exception handling. 
- Used JDBC Template to execute native SQL queries on the MySQL database and defined ORM for specific use-cases. 
- Lombok to avoid boilerplate code for setter, getter, constructor and object initialization using Builder. 
- Made API collections and designed scripts in Postman for API testing. 
- Maven used for build automation and Git for source version control.

## Project Directory
![expense-tracker-api-project-directory](https://user-images.githubusercontent.com/44142827/178159807-16c2ecef-928b-4bac-bea5-f58b7ee6b3b7.PNG)

## Database Schema [a relative link](Assets/expense-tracker-app-mysql-db-script.sql)
- MySQL scripts is provided in ASSETS folder for quickly creating the database instance locally. User just need to run the script locally in MySQL console/workbench. Please configure the port as 3306 for database connection or provide the desired port in application.properties file spring.datasource.url peoperty.

![expense-tracker-app-database-schema](https://user-images.githubusercontent.com/44142827/178159836-b0224ba9-4ff7-4306-87c3-b6c7af1ce4af.PNG)

## Postman API Collections [a relative link](Assets/Expense-Tracker-App.postman_collection)
Description
- Provided Postman API collection in ASSETS/ folder for testing the API locally. 
Note - There is no need to manually copy-paste JWT Token with each request for authentication as JS script is added in TESTS of Register/Login API which automatically fetch the TOKEN key in Postman environment and is being dynamically added as parameters in each request header. ## User just need to create an environment in Postman and you are Good to Go !!!

![expense-tracker-app-postman-api-collections](https://user-images.githubusercontent.com/44142827/178159852-dcc80ead-b5bc-44a4-b0b2-152bbe5932bd.PNG)

Technology Stack, Tools and Library

- Java 1.8
- SpringBoot
- JDBC Template
- MySQL
- JWT Authentication
- jBCrypt
- Lombok
- Maven
- STS
- Postman

