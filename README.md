KBazaar - Online Shopping Application (Workshop)

## Pre-requisites
- Java 17
- [Gradle](https://gradle.org/install/)
- [pre-commit](https://pre-commit.com/#installation)
- Docker

## Getting Started
1. Clone this repository
2. cd to `kbazaar` directory and run `make setup` (if not working you can run pre-commit manually)
3. Run `make test` to run unit tests
4. Run `make run` to start the application

## API Documentation
- Swagger UI: [http://localhost:8080/swagger-ui/index.html](http://localhost:8080/swagger-ui/index.html)

## Existing Features
- List all Shopper GET /shoppers
- Get Shopper by username GET /shoppers/{username}
- List all Products GET /products
- Get Product by sku GET /products/{sku}
- List all Promotions GET /promotions
- Get Promotion by code GET /promotions/{code}
