# OutScout - Smart Picnic & Camping Planner

A production-ready monorepo for intelligent camping and picnic planning with weather-aware recommendations.

## Architecture

- **Frontend**: React + TypeScript + Vite with TailwindCSS
- **Backend**: Java 17 + Spring Boot 3 (multi-module)
- **Datastores**: MySQL, Redis, Elasticsearch
- **Messaging**: Kafka
- **DevOps**: Docker + docker-compose

## Quick Start

### Prerequisites
- Docker & Docker Compose
- Java 17+ (for local development)
- Node.js 18+ (for local development)

### Local Development

1. **Clone and setup**:
   ```bash
   git clone <repo-url>
   cd OutScout
   ```

2. **Start the full stack**:
   ```bash
   docker compose -f docker/docker-compose.local.yml up --build
   ```

3. **Access the application**:
   - Frontend: http://localhost:5173
   - API Gateway: http://localhost:8080
   - Elasticsearch: http://localhost:9200
   - Kibana: http://localhost:5601

### Seed Data

The application automatically loads demo data on startup:
- 10 parks and campsites
- Sample weather forecasts
- Demo alerts

### Manual Data Seeding

If you need to reload seed data:
```bash
# Stop services
docker compose -f docker/docker-compose.local.yml down

# Remove volumes to reset data
docker volume prune

# Restart with fresh data
docker compose -f docker/docker-compose.local.yml up --build
```

## Development

### Frontend Development
```bash
cd frontend
npm install
npm run dev
```

### Backend Development
```bash
# Build all modules
mvn clean install

# Run individual services
cd backend/api && mvn spring-boot:run
cd backend/ingest && mvn spring-boot:run
cd backend/planner && mvn spring-boot:run
cd backend/notifier && mvn spring-boot:run
```

### Database Migrations
Flyway migrations run automatically on startup. Manual execution:
```bash
cd backend/api
mvn flyway:migrate
```

## API Endpoints

- `GET /api/spots/search` - Search campsites with filters
- `GET /api/spots/{id}` - Get campsite details
- `GET /api/spots/{id}/forecast` - Get weather forecast
- `POST /api/plan` - Request trip planning
- `GET /api/plan/stream/{requestId}` - SSE stream for planning results

## Project Structure

```
├── frontend/          # React + TypeScript + Vite
├── backend/           # Spring Boot multi-module
│   ├── api/          # REST API gateway
│   ├── ingest/       # Data ingestion services
│   ├── planner/      # Weather scoring engine
│   └── notifier/     # SSE notifications
├── docker/           # Docker configurations
├── infra/            # CI/CD and infrastructure
├── seed/             # Demo data files
└── README.md
```

## Environment Variables

Copy `.env.local.template` to `.env.local` and configure:
- Database connections (MySQL, Redis, Elasticsearch)
- Kafka broker settings
- Open-Meteo API configuration

## Contributing

1. Create feature branch
2. Make changes
3. Run tests: `mvn test` (backend) / `npm test` (frontend)
4. Submit pull request

## License

See LICENSE file for details.