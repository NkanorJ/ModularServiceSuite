<<<<<<< HEAD
# sim-practical
=======
# Microservices Architecture Overview #

This project follows a modular microservices design where different services are encapsulated within a single
repository,
ensuring each service has a clear separation of concerns. Each microservice, such as User, Loan, and transaction,
is structured in a similar fashion to maintain consistency and ease of maintenance.
Below is the breakdown of the architecture for the User and Loan services.

## User Microservice Architecture ##

The User microservice handles user management, authentication, and authorization. It is organized as follows:

### App-Service modules

This is the entry point for the microservice where the main application runs.
It houses the core configurations and initialization logic for the user service.

This module serves as the main entry point for the User microservice. It contains the
migration scripts, application properties, and the configuration needed to initialize and run the service. Migration
scripts
ensure that database schemas are updated consistently, while application properties handle environment-specific
configurations.

### Commons Lib Service modules

Contains shared resources, such as utility classes, records, enumerations, exceptions, DTOs (Data Transfer Objects),
responses,
and requests. These components are used across different microservices to maintain consistency and facilitate seamless
data transfer.

### Core Service modules

This module contains the business logic and use cases of the service, along with the interfaces that communicate
with the database. It ensures that the application's core functionality is decoupled from external dependencies.

### Adapter Persistence JPA Service modules

Responsible for managing domain models, entities, and database operations through Spring Data JPA. It handles all
database
interactions and defines the structure of persistent objects.

### Adapter REST Spring Service modules

This module exposes the microservice's API via controllers. The controllers are standalone components
that interact with other services through pipelines, such as Feign clients. The module also contains the configuration
for pipeline.

### Authentication Service modules

Manages user authentication, authorization, and token management. It handles user security and access control, ensuring
that
that only authenticated and authorized users can perform certain actions within the application.

Each service is designed with high modularity, making it possible to scale and maintain the system independently.
This design adheres to Object-Oriented Programming principles such as encapsulation and abstraction,
ensuring that internal logic is hidden and only necessary interfaces are exposed.

## Loan Microservice Architecture ##

The Loan microservice handles loan management and operates with a similar modular structure as the User microservice:

### App-Service modules

Contains the application’s core logic and is responsible for running the loan management application.
Like the User service, it houses the migration scripts, application properties, and configuration details.

### Commons Lib Service modules

Contains common resources that are shared across multiple microservices, such as DTOs, exceptions, and utility classes.

### Core Service modules

The core functionality for managing loans is encapsulated here.
This module houses the business logic and use cases for loan management, ensuring separation of concerns.

### Gateway Service modules

This module facilitates communication between the Loan service and other services, such as the User Authentication and
Authorization (UAA) service. It includes Feign clients that enable user verification and authorization processes.

### Rest Spring Service modules

This module handles the API layer for loan management, exposing the required endpoints to interact with the service.
It communicates with the core service to perform loan-related operations.

## Service Communication

### Feign Client:

Communication between services occurs through Feign clients.
For example, the Loan service uses a Feign client to interact with the User service for user verification during loan
processing.

### Pipeline Communication:

Services communicate asynchronously via pipelines, often utilizing messaging queues or HTTP requests.
This ensures that services remain loosely coupled, fostering a scalable and flexible architecture.

### This design aligns with established principles of modular microservices architecture and domain-driven design (DDD), which have been developed and refined by thought leaders in software architecture over the years.

### Design Philosophy

This architecture adheres to core principles of Object-Oriented Programming:

Encapsulation: Internal service details are hidden, exposing only necessary interfaces for external communication.

Loose Coupling: Services are designed with minimal interdependence, allowing each microservice to scale, evolve, and be
maintained independently.

Modularity: Each service is self-contained, making the system easier to extend and manage in the long term.

By following these principles, the architecture supports flexibility, maintainability, and scalability, making it
well-suited for growing applications.

### Test configuration and tools

* Testing Framework: Use JUnit (typically JUnit) as the primary framework for unit testing or spock test.
* Mocking Framework: Utilize Mockito or similar tools for mocking dependencies.
* Testing the usecase

>>>>>>> 5ffed38 (Initial commit)
