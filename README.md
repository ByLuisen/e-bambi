<h1 align="center">
  E-Bambi
</h1>

<br>

Proyecto de microservicios para e-commerce (Inventory, Order, Payment) diseñado con Hexagonal Architecture y Domain-Driven Design. Cada microservicio expone su OpenAPI y se despliega con Docker/Helm sobre Kubernetes (Minikube), complementado con Terraform, Skaffold y CI/CD para despliegues reproducibles. Está construido en Java reactivo (Spring WebFlux) con persistencia R2DBC/JOOQ sobre PostgreSQL y seguridad centralizada con Spring Security + Keycloak. Para integración y consistencia eventual usa Kafka junto al patrón Outbox y Debezium; además aplica patrones avanzados (CQRS, Saga) y optimizaciones de rendimiento (control de concurrencia en stock, índices, batch inserts, prevención de N+1 y pruebas de carga con JMeter — 7.000 usuarios) para ser resiliente, escalable y fiable.

## 🔀 Create order flow

## 🔥 Features

- ✅ OpenAPI definitions for each microservice: [Inventory Service](https://github.com/ByLuisen/e-bambi/blob/main/inventory-service/inventory-infrastructure-rest/src/main/resources/static/openapi.yaml), [Order Service](https://github.com/ByLuisen/e-bambi/blob/main/order-service/order-infrastructure-rest/src/main/resources/static/openapi.yaml) and [Payment Service](https://github.com/ByLuisen/e-bambi/blob/main/payment-service/payment-infrastructure-rest/src/main/resources/static/openapi.yaml). 
- ✅ Resilience when publishing events thanks to the Outbox pattern and Debezium.
- ✅ Authentication and authorization with Spring Security and Keycloak: [InventorySecurityFilterChain.java](https://github.com/ByLuisen/e-bambi/blob/main/inventory-service/inventory-infrastructure-security/src/main/java/com/e/bambi/inventory/infrastructure/security/InventorySecurityFilterChain.java), [OrderSecurityFilterChain.java](https://github.com/ByLuisen/e-bambi/blob/main/order-service/order-infrastructure-security/src/main/java/com/e/bambi/order/infrastructure/security/OrderSecurityFilterChain.java) and [PaymentSecurityFilterChain.java](https://github.com/ByLuisen/e-bambi/blob/main/payment-service/payment-infrastructure-security/src/main/java/com/e/bambi/payment/infrastructure/security/PaymentSecurityFilterChain.java).
- ✅ Exception handling: [GlobalExceptionHandler.java](https://github.com/ByLuisen/e-bambi/blob/main/shared-infrastructure/shared-infrastructure-rest/src/main/java/com/e/bambi/shared/infrastructure/rest/exception/handler/GlobalExceptionHandler.java), [InventoryGlobalExceptionHandler.java](https://github.com/ByLuisen/e-bambi/blob/main/inventory-service/inventory-infrastructure-rest/src/main/java/com/e/bambi/inventory/infrastructure/rest/exception/handler/InventoryGlobalExceptionHandler.java), [OrderGlobalExceptionHandler.java](https://github.com/ByLuisen/e-bambi/blob/main/order-service/order-infrastructure-rest/src/main/java/com/e/bambi/order/infrastructure/rest/exception/handler/OrderGlobalExceptionHandler.java) and [PaymentGlobalExceptionHandler.java](https://github.com/ByLuisen/e-bambi/blob/main/payment-service/payment-infrastructure-rest/src/main/java/com/e/bambi/payment/infrastructure/rest/exception/handler/PaymentGlobalExceptionHandler.java).
- ✅ Dynamic filters with JOOQ: [OrderJooqRepository.java](https://github.com/ByLuisen/e-bambi/blob/main/order-service/order-infrastructure-persistence/src/main/java/com/e/bambi/order/infrastructure/persistence/order/repository/jooq/OrderJooqRepository.java).
- ✅ Concurrency control in product stock: [OfferEntity.java](https://github.com/ByLuisen/e-bambi/blob/main/inventory-service/inventory-infrastructure-persistence/src/main/java/com/e/bambi/inventory/infrastructure/persistence/offer/entity/OfferEntity.java).
- ✅ Interact with the application layer through [Command Bus](https://github.com/ByLuisen/e-bambi/blob/main/shared-kernel/shared-kernel-bus/src/main/java/com/e/bambi/shared/kernel/application/CommandBusImpl.java) and [Query Bus](https://github.com/ByLuisen/e-bambi/blob/main/shared-kernel/shared-kernel-bus/src/main/java/com/e/bambi/shared/kernel/application/QueryBusImpl.java)

## 📊 Optimizations

- Concurrency and performance tests with JMeter for 7000 concurrent users using [e-bambi-load-test.jmx](https://github.com/ByLuisen/e-bambi/blob/main/test/performance/e-bambi-load-test.jmx)
- Pagination in the database
- Simple and composite indexes in the database [V2__add_indexes.sql](https://github.com/ByLuisen/e-bambi/blob/main/inventory-service/inventory-infrastructure-persistence/src/main/resources/db/migration/V2__add_indexes.sql)
- Optimized queries in [OrderR2dbcRepository.java](https://github.com/ByLuisen/e-bambi/blob/main/order-service/order-infrastructure-persistence/src/main/java/com/e/bambi/order/infrastructure/persistence/order/repository/r2dbc/OrderR2dbcRepository.java)
- Batch insert when creating an order for the order’s products in [OrderItemR2dbcDatabaseClient.java](https://github.com/ByLuisen/e-bambi/blob/main/order-service/order-infrastructure-persistence/src/main/java/com/e/bambi/order/infrastructure/persistence/order/repository/r2dbc/OrderItemR2dbcDatabaseClient.java)
- Prevent inefficient N + 1 queries in [OrderJooqRepository.java](https://github.com/ByLuisen/e-bambi/blob/main/order-service/order-infrastructure-persistence/src/main/java/com/e/bambi/order/infrastructure/persistence/order/repository/jooq/OrderJooqRepository.java) 

## 👥 Test users

* 👨🏻‍💻 **Admin**
  * **Username:** e-bambi-admin
  * **Password:** admin!1Aadmin!1A

* 👨🏻‍💼 **User**
  * **Username:** e-bambi-user
  * **Password:** usera!1Aadmin!1A

## 🕹️ Usage/Examples

🚨 **Important:** If you want to follow this example, you need to have the project running.  
📝 **Note:** All these steps can be done on the [Order Service OpenAPI definition](http://localhost/?urls.primaryName=order).

1. Authenticate with Keycloak e-bambi client as **e-bambi-user**

    * **Client ID:** e-bambi
    * **Client Secret:** You can get the client secret using the [Keycloak console](http://localhost:8081/admin/master/console/#/bambi/clients): **e-bambi > Credentials**
      * Keycloak credentials:
        * **Username:** admin
        * **Password:** admin

2. Create an order: 

    ```
    POST http://localhost:8181/api/v1/me/orders 
    ```

    * Resquest body: 
    ```
    {
      "paymentMethod": {
        "id": "3fdc42b4-8341-43a4-aa78-c9fa792d4d9e",
        "name": "PayPal"
      },
      "address": {
        "country": "Spain",
        "address": "Calle Gran Vía, 28",
        "city": "Madrid",
        "province": "Madrid",
        "postalCode": "28013",
        "phoneNumber": "+34 912 34 56 78"
      },
      "items": [
        {
          "imageUrl": "https://example.com/images/productos/camiseta-deportiva.jpg",
          "supplier": {
            "id": "0d95b4c4-a138-4e6a-9d86-4c27ea98a83c",
            "name": "Bambi"
          },
          "product": {
            "id": "839546df-e9ab-45c4-b5e0-06f10ca5c3d7",
            "sku": "SAM-001-EL",
            "name": "Samsung Galaxy S23"
          },
          "price": 789.99,
          "quantity": 1,
          "totalPrice": 789.99
        }
      ],
      "totalPrice": 789.99
    }
    ```

3. Track the order status:

    ```
    GET http://localhost:8181/api/v1/me/orders/{orderId}/tracking
    ```

    * Output example:
    ```
    {
      "orderStatus": "CREATED",
      "failureMessages": []
    }
    ```

4. Get the orders of the last 30 days:

    ```
    GET http://localhost:8181/api/v1/me/orders?page=0&date=30
    ```

    * Output example:
    ```
    {
      "data": [
        {
          "id": "7786665c-fd50-4c87-9ae2-9f8b0656bf90",
          "orderStatus": "PENDING",
          "address": {
            "country": "Spain",
            "address": "Calle Gran Vía, 28",
            "city": "Madrid",
            "province": "Madrid",
            "postalCode": "28013",
            "phoneNumber": "+34 912 34 56 78"
          },
          "items": [
            {
              "imageUrl": "https://example.com/images/productos/camiseta-deportiva.jpg",
              "productId": "839546df-e9ab-45c4-b5e0-06f10ca5c3d7",
              "name": "Samsung Galaxy S23"
            }
          ],
          "totalPrice": 789.99,
          "createdAt": "2025-08-02T16:55:56.534Z"
        }
      ],
      "count": 1
    }
    ```

## 📐 Project structure (Monorepo)

```
📁 e-bambi/ 
 ┣ 📁 common/      
 ┃  ┣ 📁 common-utils/                              
 ┃  ┗ 📄 pom.xml 
 ┣ 📁 deployment/       
 ┃  ┣ 📁 docker-compose/                            # Docker, PostgreSQL configurations and Swagger UI theme    
 ┃  ┣ 📁 helm/                                      # Helm charts, values, Helmfile and Skaffold
 ┃  ┣ 📁 iac/                                       # Terraform to create and configure Keycloak
 ┃  ┣ 📁 k8s/                                       # Ingress and TLS manifests for staging and production
 ┃  ┣ 📁 keycloak/                                  # Keycloak Dockerfile and theme
 ┃  ┣ 📄 create-debezium-connectors.ps1             # PowerShell script for create debezium connectors
 ┃  ┗ 📄 create-debezium-connectors.sh              # Shell script for create debezium connectors
 ┣ 📁 inventory-service/      
 ┃  ┣ 📁 inventory-application/                     
 ┃  ┣ 📁 inventory-bootstrap/                       # Submodule that runs the application by calling the other submodules
 ┃  ┣ 📁 inventory-domain/
 ┃  ┣ 📁 inventory-infrastructure-messaging/
 ┃  ┣ 📁 inventory-infrastructure-persistence/
 ┃  ┣ 📁 inventory-infrastructure-rest/
 ┃  ┣ 📁 inventory-infrastructure-security/
 ┃  ┗ 📄 pom.xml 
 ┣ 📁 order-service/       
 ┣ 📁 payment-service/       
 ┣ 📁 shared-infrastructure/                        # Reusable infrastructure configurations
 ┃  ┣ 📁 shared-infrastructure-messaging-kafka/      
 ┃  ┃  ┣ 📁 kafka-config-data/
 ┃  ┃  ┣ 📁 kafka-consumer/
 ┃  ┃  ┣ 📁 kafka-model/
 ┃  ┃  ┣ 📁 kafka-producer/
 ┃  ┃  ┗ 📄 pom.xml
 ┃  ┣ 📁 shared-infrastructure-persistence-flyway/  
 ┃  ┣ 📁 shared-infrastructure-persistence-jooq/
 ┃  ┣ 📁 shared-infrastructure-rest/
 ┃  ┣ 📁 shared-infrastructure-security/   
 ┃  ┗ 📄 pom.xml
 ┣ 📁 shared-kernel/                                # Reusable application / domain objects 
 ┃  ┣ 📁 shared-kernel-bus/                         
 ┃  ┣ 📁 shared-kernel-domain/
 ┃  ┣ 📁 shared-kernel-event/
 ┃  ┣ 📁 shared-kernel-saga/
 ┃  ┗ 📄 pom.xml
 ┣ 📁 test/   
 ┃  ┣ 📁 json-files/                                # Debezium connectors and create order with one / ten items
 ┃  ┣ 📁 performance/                               # JMeter concurrency and performance testing
 ┃  ┣ 📁 postman/                                   # Postman collection and environments
 ┃  ┗ 📁 sql-files/                                 # Mocks and queries
 ┗ 📄 pom.xml
```

## 💻 Tech Stack

### Infrastructure & DevOps
  
  * Kubernetes (Minikube) • Helm • k9s
  * Docker • Terraform • Skaffold (CI/CD)

### Backend & Frameworks

  * Java • Maven
  * Spring WebFlux • Spring Data R2DBC • Spring JOOQ • Spring Security • Flyway • Keycloak
  * PostgreSQL • CloudBeaver

### Messaging & Data Streaming

  * Apache Kafka • Kafka Connect • Debezium • Conduktor

### Testing & API

  * JMeter (performance testing) • OpenAPI (API documentation)

### Architecture & Patterns

  * Clean Code • Hexagonal Architecture • Domain‑Driven Design (DDD)
  * RESTful • CQRS • Outbox Pattern • Saga Pattern
  * Factory • Builder • Repository • Command/Query Bus

## 🚀 Run Locally (PowerShell, macOS & Linux)

  * Prerequists:
    * [Git](https://git-scm.com/downloads)
    * [Docker Desktop](https://www.docker.com/products/docker-desktop/) (Windows & macOS) or [Docker Engine](https://docs.docker.com/engine/install/) (Linux)
    * Java 21
    * [Terraform](https://developer.hashicorp.com/terraform/tutorials/aws-get-started/install-cli)

Clone the project, navigate to the docker-compose directory, and start the basic and essential services (db.yaml) to compile the project.

```bash
git clone https://github.com/ByLuisen/e-bambi.git
cd e-bambi/deployment/docker-compose
docker compose -f common.yaml -f auth.yaml -f db.yaml -f queue.yaml up -d
```

Compile the project by installing dependencies, applying Flyway migrations, generating JOOQ objects, and finally installing the artifacts.

```bash
../../mvnw -f ../../pom.xml clean install -Pdev
```

Create the Kafka topics with 3 partitions and 3 replicas if this is your first time or if you want to reset the Kafka topics.

```bash
docker compose -f common.yaml -f init_kafka.yaml up -d
```

Start the three microservices (inventory, order, payment) along with a service (swaggerapi) that centralizes the OpenAPI documentation, accessible at [localhost](http://localhost).

```bash
docker compose -f common.yaml -f app.yaml -f swaggerapi.yaml up -d
```

Create the space where the application's users will be stored by setting up and configuring the Realm, the password policy, roles (ADMIN and USER), users (e-bambi-admin and e-bambi-user), and the client through which our users will authenticate.

```bash
terraform -chdir="../iac/terraform/environments/dev" init -upgrade
terraform -chdir="../iac/terraform/environments/dev" plan -out="keycloak.plan"
terraform -chdir="../iac/terraform/environments/dev" apply keycloak.plan
```

### On macOS & Linux

Create the Debezium Kafka connectors that will publish the event to the Kafka cluster as soon as it is inserted into the database.

```bash
../create-debezium-connectors.sh
```

Necessary to make our IdP (Keycloak) accessible from the browser, since our microservices run inside Docker.

```bash
echo "127.0.0.1 keycloak.local" | sudo tee -a /etc/hosts
```

### On Windows (PowerShell)

Create the Debezium Kafka connectors that will publish the event to the Kafka cluster as soon as it is inserted into the database.

```powershell
Set-ExecutionPolicy ByPass -Scope Process -Force; ../create-debezium-connectors.ps1
```

Necessary to make our IdP (Keycloak) accessible from the browser, since our microservices run inside Docker.

```powershell
Add-Content -Path "C:\Windows\System32\drivers\etc\hosts" -Value "127.0.0.1 keycloak.local"
```

### Tools Deployed with Docker

* **Keycloak:** Identity and access management, enabling authentication, authorization, and single sign-on (SSO) for applications and services.
  * http://localhost:8081  
  * Credentials:
    * **Username:** admin
    * **Password:** admin
* **Cloudbeaver:** Tool to explore, manage, and query databases 
  * http://localhost:8978  
* **Conduktor:** Visual platform that simplifies the management, monitoring, and usage of Apache Kafka.
  * http://localhost:8080  
* **Swaggerapi:** Serves a Swagger UI interface to centralize OpenAPI definitions.
  * http://localhost 
