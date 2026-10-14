# Customer Orders API

A small, layered REST API built with Java 17, Spring Boot, Maven, Spring Data JPA, and an in-memory H2 database. It includes customer and order CRUD operations, paginated customer search, a JPQL join query, request/response logging with Spring AOP, an outbound REST integration, tests, and OpenAPI documentation.

## Requirements

- JDK 17
- Internet access on the first build so Maven can download dependencies

Maven is not required globally; use the included Maven Wrapper.

## Run

From the project root:

```powershell
.\mvnw.cmd spring-boot:run
```

The application starts at `http://localhost:8080`. H2 is in-memory, and sample customers/orders are loaded at startup. Data resets when the application stops.

## Test and package

```powershell
.\mvnw.cmd test
.\mvnw.cmd clean package
```

On macOS/Linux, use `./mvnw` instead.

## Package layout

- `controller` contains the REST endpoints.
- `service` contains business logic and the external API client flow.
- `repository` contains Spring Data persistence interfaces.
- `entity` contains JPA entities.
- `dto` contains request/response models and query projections.
- `common`, `config`, `integration`, and `logging` hold shared concerns and infrastructure.

## API documentation and database console

- Swagger UI: `http://localhost:8080/swagger-ui.html`
- OpenAPI JSON: `http://localhost:8080/v3/api-docs`
- H2 console: `http://localhost:8080/h2-console`
  - JDBC URL: `jdbc:h2:mem:customerorders`
  - Username: `sa`
  - Password: leave blank

## Endpoints

| Method | Endpoint | Purpose |
| --- | --- | --- |
| GET | `/api/customers/search?q=alice&page=0&size=10&sort=name,asc` | Search customer name/email with pagination |
| GET | `/api/customers/{id}` | Get a customer |
| POST | `/api/customers` | Create a customer |
| PUT | `/api/customers/{id}` | Replace a customer |
| DELETE | `/api/customers/{id}` | Delete a customer |
| GET | `/api/orders` | List orders |
| GET | `/api/orders/{id}` | Get an order |
| GET | `/api/orders/by-customer/{customerId}` | Get orders joined with customer details |
| POST | `/api/orders` | Create an order |
| PUT | `/api/orders/{id}` | Replace an order |
| DELETE | `/api/orders/{id}` | Delete an order |
| GET | `/api/external/posts/{id}` | Fetch a post from JSONPlaceholder |

Customer request example:

```json
{
  "name": "Taylor Example",
  "email": "taylor@example.com"
}
```

Order request example:

```json
{
  "orderNumber": "ORD-2001",
  "amount": 79.95,
  "status": "PLACED",
  "customerId": 1
}
```

Search returns a Spring `Page` response containing `content`, `totalElements`, `totalPages`, and page metadata. `size` is limited to 100. Sort accepts a property and direction, for example `name,desc`.

## Postman

Import `postman/Customer-Orders-API.postman_collection.json` into Postman. Its `baseUrl` collection variable defaults to `http://localhost:8080`. Run the requests individually in order where a request uses an ID captured by a preceding create request, or use the seeded IDs for read/search examples.

## Implementation notes

- HTTP request arguments and controller responses are logged by `RequestResponseLoggingAspect`. Treat these logs as potentially sensitive and restrict access/retention in real deployments; avoid logging credentials or regulated data.
- The outbound JSONPlaceholder integration has connect/read timeouts. Its endpoint requires internet access and is not used by the local unit/database tests.
- The H2 database and `create-drop` schema are development/demo choices. Use migrations and a managed database for persistent environments.
- Validation errors return HTTP 400; missing resources return 404; uniqueness/constraint conflicts return 409; upstream failures return 502.
