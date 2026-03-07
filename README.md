# Order Service

Order Service is part of an event-driven e-commerce microservices system.

## Responsibilities

- Create orders
- Manage order lifecycle
- Publish order events to Kafka
- Participate in Saga transactions

## Tech Stack

- Java 17
- Spring Boot
- Apache Kafka
- Spring Data JPA
- MySQL
- Docker

## Architecture Role

Order Service acts as the Saga initiator in the distributed transaction workflow.

Flow:

Order Created → Inventory Reserved → Payment Processed → Order Confirmed

## Running the Service

1. Start Kafka
2. Start Config Server
3. Start Eureka Server
4. Run the service
