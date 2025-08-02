<h1 align="center">
  E-Bambi
</h1>

## 👥 Test users

* 👨🏻‍💻 **Admin**
  * **Username:** e-bambi-admin
  * **Password:** admin!1Aadmin!1A

* 👨🏻‍💼 **User**
  * **Username:** e-bambi-user
  * **Password:** usera!1Aadmin!1A

## 🕹️ Usage/Examples

1. Authenticate with Keycloak e-bambi client as e-bambi-user

    * **Client ID:** e-bambi
    * **Client Secret:** You can get the client secret using the [Keycloak console.](http://localhost:8081/admin/master/console/#/bambi/clients/f068cb7d-2ea3-4490-bd0a-e2bd096aba16/credentials)

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
    GET http://localhost:8181/api/v1/me/orders?date=30
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

## 📐 Project structure

```
📁 e-bambi/ 
 ┣ 📁 common/      
 ┃  ┗ 📁 common-utils/
 ┣ 📁 deployment/       
 ┃  ┣ 📁 docker-compose/                            # Docker, environment services     
 ┃  ┣ 📁 helm/
 ┃  ┣ 📁 iac/
 ┃  ┣ 📁 k8s/
 ┃  ┣ 📁 keycloak/
 ┃  ┣ 📄 create-debezium-connectors.ps1
 ┃  ┗ 📄 create-debezium-connectors.sh
 ┣ 📁 inventory-service/      
 ┃  ┣ 📁 inventory-application/
 ┃  ┣ 📁 inventory-bootstrap/
 ┃  ┣ 📁 inventory-domain/
 ┃  ┣ 📁 inventory-infrastructure-messaging/
 ┃  ┣ 📁 inventory-infrastructure-persistence/
 ┃  ┣ 📁 inventory-infrastructure-rest/
 ┃  ┣ 📁 inventory-infrastructure-security/
 ┃  ┗ 📄 pom.xml 
 ┣ 📁 order-service/       
 ┣ 📁 payment-service/       
 ┣ 📁 shared-infrastructure/    
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
 ┣ 📁 shared-kernel/    
 ┃  ┣ 📁 shared-kernel-bus/
 ┃  ┣ 📁 shared-kernel-domain/
 ┃  ┣ 📁 shared-kernel-event/
 ┃  ┣ 📁 shared-kernel-saga/
 ┃  ┗ 📄 pom.xml
 ┣ 📁 test/   
 ┃  ┣ 📁 json-files/
 ┃  ┣ 📁 performance/
 ┃  ┣ 📁 postman/
 ┃  ┗ 📁 sql-files/    
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

## 🚀 Run Locally

  * Prerequists:
    * [Git](https://git-scm.com/downloads)
    * [Docker Desktop](https://www.docker.com/products/docker-desktop/) (Windows & macOS) or [Docker Engine](https://docs.docker.com/engine/install/) (Linux)
    * Java 21
    * [Terraform](https://developer.hashicorp.com/terraform/tutorials/aws-get-started/install-cli)

Clone the project

```bash
  git clone https://github.com/ByLuisen/e-bambi.git
  cd e-bambi/deployment/docker-compose
  docker compose -f common.yaml -f auth.yaml -f db.yaml -f queue.yaml up -d
```

```bash
../../mvnw -f ../../pom.xml clean install -Pdev
```

```bash
docker compose -f common.yaml -f init_kafka.yaml up -d
```

```bash
docker compose -f common.yaml -f app.yaml -f swaggerapi.yaml up -d
```

```bash
terraform -chdir="../iac/terraform/environments/dev" init -upgrade
terraform -chdir="../iac/terraform/environments/dev" plan -out="keycloak.plan"
terraform -chdir="../iac/terraform/environments/dev" apply keycloak.plan
```

### On macOS & Linux
```bash
../create-debezium-connectors.sh
```

```bash
echo "127.0.0.1 keycloak.local" | sudo tee -a /etc/hosts
```

### On Windows (PowerShell)
```powershell
Set-ExecutionPolicy ByPass -Scope Process -Force; ../create-debezium-connectors.ps1
```

```powershell
Add-Content -Path "C:\Windows\System32\drivers\etc\hosts" -Value "127.0.0.1 keycloak.local"
```
