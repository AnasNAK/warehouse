# FX Deal Data Service

A Spring Boot application for importing and validating FX deal data into a PostgreSQL database. Includes validation, deduplication, and no-rollback persistence logic.

## Prerequisites

- Java 21+
- Maven 3.6+
- Docker & Docker Compose
- Make (optional)

## Quick Start

### Using Make (Recommended)

```bash
# Build the application
make build

# Start services
make up

# View logs
make logs

# Stop services
make down

# Clean build files
make clean
```

### Using Docker Compose Directly

```bash
# Build and start services
docker compose up -d

# View logs
docker compose logs -f warehouse-app

# Stop services
docker compose down
```

### Using Maven (Development)

```bash
# Run tests
mvn test

# Build JAR
mvn clean package

# Run locally (requires local PostgreSQL)
mvn spring-boot:run
```

## Services

- **Application**: http://localhost:9090
- **PostgreSQL**: localhost:5433

## Database Configuration

- **Database**: warehouse_db
- **Username**: warehouse_anas
- **Password**: anasnak
- **Port**: 5433 (host) / 5432 (container)

## Features

- **Validation**: Validates FX deal data using Jakarta Validation
- **Deduplication**: Prevents duplicate deals using unique identifiers
- **No-rollback persistence**: Robust error handling without transaction rollbacks
- **Logging**: Comprehensive debug and info logging
- **Health checks**: Built-in health monitoring

## API Endpoints

### FX Deal Management
- `POST /api/deals` - Create/Import FX deal data

### Example Request

```bash
curl -X POST http://localhost:9090/api/deals \
  -H "Content-Type: application/json" \
  -d '{
    "dealUniqueId": "DEAL-001",
    "fromCurrencyIsoCode": "USD",
    "toCurrencyIsoCode": "EUR",
    "dealTimestamp": "2024-01-15T10:30:00",
    "dealAmount": 1000.50
  }'
```

## Logs

View application logs:
```bash
make logs
# or
docker compose logs -f warehouse-app
```

## Development

The application uses:
- Spring Boot 3.5.3
- Java 21
- PostgreSQL 15
- JPA/Hibernate
- Jakarta Validation
- Lombok
- MapStruct
- Docker containers

## Environment Variables

- `APP_DATASOURCE_URL` - Database URL
- `APP_DATASOURCE_USERNAME` - Database username
- `APP_DATASOURCE_PASSWORD` - Database password
- `SPRING_PROFILES_ACTIVE` - Active profile (dev/prod)