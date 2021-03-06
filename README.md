# Bonds Broker backend application

This is Bonds Broker backend application of a fictional Bonds4All.com company.

It's a BNP Paribas Cardif Java developer coding test sample application.

## Requirements

Start by reading [the requirements](./REQUIREMENTS.md).

## FSD

The requirements imply the following [Functional Specification Document](./FSD.md).

## Tech stack

- Java 8
- Spring Boot 2.2.2
- REST: Spring Web MVC REST controllers
- ORM: JPA / Hibernate
- Connection pool: Hikari
- DB migrations: Flyway
- DB: H2
- DB tests: [Light Air](http://lightair.sourceforge.net/)


## Commands

### Clean build

To build the application uber-JAR:

    ./mvnw clean install

The uber-JAR is then created at `target/bonds-broker.jar`.

### Run the application

To run the application from sources:

    ./mvnw -Prun

Then press `CTRL-C` to stop the application.

While the app is running, you can:
- Access its homepage at [http://localhost:8080/](http://localhost:8080/)
- Execute individual Integration tests

### Re-create the database

To re-create the database tables after a new DB migration has been introduced:

    ./mvnw -Precreate-db

### Run System / Integration tests

To execute all Integration tests:

    ./mvnw -Pit


## TODO

Lit of issues to resolve: [TODO](./TODO.md).
