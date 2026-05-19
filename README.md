# Event-Driven Patient Management System

## Overview
Production-style backend system built using Spring Boot microservices architecture and event-driven communication.

The system handles patient workflows and billing operations using asynchronous messaging and scalable backend service communication.

---

## Tech Stack

- Java
- Spring Boot
- Apache Kafka
- Redis
- MySQL
- AWS EC2
- Nginx
- Docker
- REST APIs
- gRPC
- Linux

---

## Architecture

Client → Nginx → Patient Service → Kafka Events → Billing Service → MySQL

---

## Features

- Event-driven microservices architecture
- Asynchronous communication using Kafka
- REST API-based backend workflows
- Redis caching support
- gRPC-based inter-service communication
- AWS deployment with Linux server configuration
- Nginx reverse proxy setup
- Scalable backend service design

---

## Microservices

### Patient Service
Handles:
- Patient registration
- Patient workflow management
- API processing

### Billing Service
Handles:
- Billing operations
- Event consumption
- Transaction workflows

---

## Deployment

- Deployed on AWS EC2 Linux instances
- Configured using Nginx reverse proxy
- Managed backend deployment and debugging workflows

---

## Future Improvements

- API Gateway
- Service Discovery
- Kubernetes deployment
- Monitoring & Observability
- CI/CD pipeline
