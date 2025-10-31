# My first Spring Boot Project

Hi, I have been studying java for a significant time, and now is the moment to diving deep into Spring Boot.

This project is a simple CRUD, simulating a library, where we can create, find, remove/delete or update some books.

### Technologies used to code this project:
- (including some dependencies of the Spring boot like: Spring Data, Spring Web, Spring Test, Spring DevTools, Spring Validation)


- Lombok


- MapStruct


- H2 database (based on memory), doesn't need to integrate a real database, but just run the application


- Postman (recommended to make easier all requests)

Note: Using H2 database in memory, all the data will be lost if the application stops.
If you want to keep all data even with the application not running, use the H2 based on files, or integrate a real database, create a schema and pass your credentials to application.properties / application.yml.

### Features

- CRUD operations: create, read, update or delete books

- Filtering: search books by author, price or initial letter of the title;

---

When cloning this project, make sure to load all dependencies properly (by reloading all maven projects and typing "mvn clean install" in the terminal).
