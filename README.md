# E-Commerce Backend

A production-ready RESTful backend for a full-featured e-commerce platform built with **Spring Boot 3**, **MongoDB**, and **JWT authentication**. The API covers everything from user registration to Razorpay payment verification, with a clean layered architecture and role-based access control throughout.

---

## Tech Stack

| Layer | Technology |
|---|---|
| Framework | Spring Boot 3.5.8 |
| Language | Java 25 |
| Database | MongoDB (Spring Data MongoDB) |
| Security | Spring Security + JWT (JJWT 0.11.5) |
| Payment | Razorpay |
| Mapping | MapStruct 1.6.3 |
| Utilities | Lombok |
| Build Tool | Maven |
| Containerization | Docker (multi-stage build) |

---

## Features

- **JWT Authentication** — Stateless login and registration. On login, a signed JWT is issued containing the user's ID and roles. Every protected request passes through a custom `JwtAuthFilter` that validates the token and loads the user into the Spring Security context.
- **Role-Based Access Control** — Two roles: `ROLE_USER` and `ROLE_ADMIN`. Endpoints are protected at both the security filter level and with `@PreAuthorize` annotations, so users can never access admin routes and vice versa.
- **Product Catalog** — Products have a name, description, price, stock count, image URL, and category. The public product listing supports filtering by category, price range, and keyword search — all handled via custom Spring Data MongoDB query methods.
- **Cart Management** — Each user has a persistent cart stored in MongoDB. The cart service handles adding items (auto-creates the cart on first use), updating quantity, removing individual items, clearing the entire cart, and recalculating the total amount on every change.
- **Address Book** — Users can store multiple delivery addresses. Full CRUD is supported — add, list, update, and delete — and each address is scoped to the authenticated user.
- **Checkout Flow** — On checkout, an order is created with status `PENDING_PAYMENT` and immediately sent to Razorpay to create a payment session. The Razorpay order ID, key, amount, and currency are returned to the frontend so it can open the Razorpay payment modal.
- **Payment Verification** — After the user pays, the frontend sends back the Razorpay order ID, payment ID, and HMAC signature. The backend verifies the signature using Razorpay's `Utils.verifySignature`, marks the order as `PAID`, deducts stock for each ordered item, and clears the user's cart. A dummy payment mode is also supported for development/testing.
- **Order Lifecycle** — Orders move through statuses: `PENDING_PAYMENT → PAID → SHIPPED → DELIVERED → CANCELLED`. Admins can update order status; users can view their own order history.
- **Admin Dashboard** — A summary endpoint returns total products, users, orders, and revenue. Revenue is calculated by summing total amounts across all orders with status `PAID`, `SHIPPED`, or `DELIVERED`.
- **Global Exception Handling** — A `@RestControllerAdvice` handles validation errors (`MethodArgumentNotValidException`), bad requests (`IllegalArgumentException`), custom exceptions (`ResourceNotFoundException`, `BadRequestException`), and generic server errors — all returning the same `ApiResponse` structure.
- **Consistent API Responses** — Every endpoint (success or error) returns a unified `ApiResponse<T>` wrapper with `timestamp`, `success`, `status`, `message`, `path`, and `data` fields, making frontend integration predictable.

---

## Architecture

The project follows a clean layered architecture:

```
Controllers  →  Services  →  Repositories  →  MongoDB
                    ↓
                 Mappers (MapStruct)
                    ↓
                  DTOs  ←→  Models
```

- **Controllers** handle HTTP requests and delegate all logic to services.
- **Services** contain the business logic, call repositories, and use mappers for conversions.
- **Repositories** extend `MongoRepository` and include custom query methods for filtering.
- **MapStruct Mappers** handle all conversions between Models and DTOs — no manual field-by-field mapping anywhere. The `@BeanMapping(nullValuePropertyMappingStrategy = IGNORE)` strategy is used for partial updates so only non-null fields are overwritten.
- **DTOs** keep request/response shapes separate from the database models.

---

## Project Structure

```
src/main/java/com/sandeep/E_Commerce_Project/
├── Config/
│   ├── SecurityConfig.java       # Filter chain, auth rules, BCrypt bean
│   ├── CorsConfig.java           # CORS filter restricted to FRONTEND_URL
│   └── MongoConfig.java          # BigDecimal ↔ Decimal128 custom converters
├── Controllers/
│   ├── admin/                    # Product CRUD, order mgmt, user list, dashboard
│   ├── auth/                     # Register, Login
│   ├── cart/                     # Cart CRUD
│   ├── checkout/                 # Checkout + Payment verification
│   ├── address/                  # Address management
│   └── user/                     # Public products, user orders
├── Services/                     # Business logic (mirrors controller structure)
├── Repositories/                 # MongoRepository interfaces with custom queries
├── Models/                       # MongoDB documents (User, Product, Order, Cart, Address, ...)
├── Dtos/                         # Request and response DTOs per domain
├── Mappers/                      # MapStruct interfaces for model ↔ DTO conversion
├── Security/
│   ├── JwtService.java           # Token generation and parsing (HS256)
│   └── JwtAuthFilter.java        # OncePerRequestFilter — validates token per request
└── Exceptions/
    ├── ApiResponse.java           # Unified response wrapper
    ├── GlobalExceptionHandler.java
    ├── ResourceNotFoundException.java
    └── BadRequestException.java
```

---

## API Reference

All responses use the `ApiResponse<T>` wrapper:

```json
{
  "timestamp": "2025-12-18T13:00:00Z",
  "success": true,
  "status": 200,
  "message": "...",
  "path": "/api/...",
  "data": {}
}
```

### Auth — `/api/auth` (Public)

| Method | Endpoint | Description |
|---|---|---|
| POST | `/api/auth/register` | Register a new user |
| POST | `/api/auth/login` | Login — returns JWT token |

### Products — `/api/products` (Public)

| Method | Endpoint | Description |
|---|---|---|
| GET | `/api/products` | List products — supports `?category=`, `?minPrice=`, `?maxPrice=`, `?search=` |
| GET | `/api/products/{id}` | Get a single product by ID |

### Cart — `/api/cart` (User)

| Method | Endpoint | Description |
|---|---|---|
| POST | `/api/cart/add` | Add item (auto-creates cart if not exists) |
| PUT | `/api/cart/update` | Update item quantity (set to 0 to remove) |
| GET | `/api/cart` | View current cart with total amount |
| DELETE | `/api/cart/remove/{productId}` | Remove a specific item |
| DELETE | `/api/cart/clear` | Clear all items from cart |

### Addresses — `/api/addresses` (User)

| Method | Endpoint | Description |
|---|---|---|
| POST | `/api/addresses` | Add a delivery address |
| GET | `/api/addresses` | Get all saved addresses |
| PUT | `/api/addresses/{id}` | Update an address |
| DELETE | `/api/addresses/{id}` | Delete an address |

### Checkout & Payment — (User)

| Method | Endpoint | Description |
|---|---|---|
| POST | `/api/checkout` | Create order + get Razorpay payment details |
| POST | `/api/payment/verify` | Verify Razorpay signature, mark order PAID, deduct stock, clear cart |

### Admin — `/api/admin/**` (Admin only)

| Method | Endpoint | Description |
|---|---|---|
| POST | `/api/admin/products` | Create a product |
| GET | `/api/admin/products` | List all products |
| GET | `/api/admin/products/{id}` | Get product by ID |
| PUT | `/api/admin/products/{id}` | Update product |
| DELETE | `/api/admin/products/{id}` | Delete product |
| GET | `/api/admin/orders` | View all orders |
| PUT | `/api/admin/orders/{id}/status` | Update order status |
| GET | `/api/admin/users` | List all users |
| GET | `/api/admin/dashboard` | Stats: total products, users, orders, revenue |

---

## Security Details

- **Password Hashing** — BCrypt via Spring Security's `PasswordEncoder`.
- **JWT Signing** — Tokens are signed with HMAC-SHA256 using a secret key loaded from the environment. Each token embeds the user's ID, username, and roles, and expires after a configurable duration.
- **JwtAuthFilter** — Extends `OncePerRequestFilter`. On each request it reads the `Authorization: Bearer <token>` header, validates the token, and sets the authentication in the `SecurityContextHolder`. If no valid token is present, the request proceeds unauthenticated (and is rejected at the authorization layer for protected routes).
- **CORS** — Configured via a `CorsFilter` bean. Allowed origin is restricted to `FRONTEND_URL` from the environment. Supports `GET`, `POST`, `PUT`, `DELETE`, `OPTIONS` with credentials.
- **MongoDB Decimal Handling** — `BigDecimal` is not natively supported in MongoDB. A custom `MongoCustomConversions` bean registers bidirectional converters between `BigDecimal` and MongoDB's `Decimal128`, ensuring prices are stored and retrieved accurately.

---

## Getting Started

### Prerequisites

- Java 25
- Maven 3.8+
- MongoDB (local or Atlas)
- Razorpay account (for live payments; dummy mode available without it)

### Environment Variables

```env
SPRING_DATA_MONGODB_URI=mongodb://localhost:27017/ecommerce
PORT=8080
APP_JWT_SECRET=your_jwt_secret_key
APP_JWT_EXPIRATION_MS=86400000
RAZORPAY_KEY_ID=your_razorpay_key_id
RAZORPAY_KEY_SECRET=your_razorpay_key_secret
RAZORPAY_CURRENCY=INR
FRONTEND_URL=http://localhost:3000
```

### Run Locally

```bash
git clone https://github.com/your-username/E_Commerce_Boot_Backend.git
cd E_Commerce_Boot_Backend
./mvnw spring-boot:run
```

The server starts at `http://localhost:8080`.

### Run with Docker

The Dockerfile uses a **multi-stage build** — the first stage compiles and packages the app with the full JDK; the second stage runs the final JAR using a lightweight JRE image, keeping the production image small.

```bash
docker build -t ecommerce-backend .

docker run -p 8080:8080 \
  -e SPRING_DATA_MONGODB_URI=your_mongo_uri \
  -e APP_JWT_SECRET=your_secret \
  -e APP_JWT_EXPIRATION_MS=86400000 \
  -e RAZORPAY_KEY_ID=your_key_id \
  -e RAZORPAY_KEY_SECRET=your_key_secret \
  -e RAZORPAY_CURRENCY=INR \
  -e FRONTEND_URL=http://localhost:3000 \
  ecommerce-backend
```

---

## Author

**Sandeep**  
Feel free to open an issue or reach out for any questions.
