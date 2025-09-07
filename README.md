# OutScout – Smart Picnic & Camping Planner

## 📌 Project Overview
OutScout is a full-stack application that helps users plan outdoor trips (picnics, camping, hiking) by combining **campsite information, weather forecasts, availability data, and park alerts** into one platform.  

It showcases modern **full-stack and distributed system design** with a strong emphasis on scalability, caching, search, and real-time streaming.

---

## 🎯 Key Goals
- Provide **searchable campsite and park information** (filters: amenities, pets, fees, distance).
- Deliver **real-time weather windows** (best time slots scored by rain, wind, UV, temperature).
- Integrate **availability snapshots** and **alerts** (closures, fire bans, hazards).
- Support **live notifications** (new alerts, better weather windows).
- Demonstrate **scalable, cloud-native architecture** using industry-standard tools.

---

## 🏗️ Tech Stack

### Frontend
- **React + TypeScript**
- Vite build system
- TailwindCSS (styling)
- Maps & charts (Leaflet/Recharts)

### Backend
- **Java Spring Boot**
  - REST APIs + WebSocket/SSE
  - Spring Data JPA (MySQL)
  - Spring Security (JWT)
- **Maven** for build/dependency management

### Datastores
- **MySQL (AWS RDS)** → structured data (parks, campsites, forecasts, trips, alerts)
- **Redis (AWS ElastiCache)** → caching, live counters, rate-limiting
- **Elasticsearch (AWS OpenSearch)** → full-text + faceted search

### Messaging / Streaming
- **Apache Kafka (AWS MSK / local dev)**  
  Topics:
  - `forecast_refreshed`
  - `availability_polled`
  - `alert_ingested`
  - `plan_requested` / `plan_scored`

### Containers & Deployment
- **Docker** for containerization
- **ECS Fargate** (Tier A) or **EKS Kubernetes** (Tier B showcase)
- **AWS Cloud**:
  - RDS (MySQL)
  - ElastiCache (Redis)
  - OpenSearch (Elasticsearch)
  - MSK (Kafka) or self-hosted for dev
  - S3 + CloudFront (frontend hosting)
  - ALB + Route 53 + ACM (TLS certs)

### CI/CD
- **GitHub Actions**
  - Build/test backend & frontend
  - Build/push Docker images → ECR
  - Deploy to ECS/EKS
  - Frontend deploy → S3 + CloudFront invalidate
- **Terraform/CDK** for Infrastructure as Code

### Distributed Systems / HA
- Multi-AZ RDS with backups
- Redis replica set with failover
- ECS tasks in 2+ AZs with ALB health checks
- Blue/green deployments

---

## 🔑 Core Features

### 1. Campsite & Park Search
- Search by keywords, filters (amenities, fees, pets allowed, distance).
- Backed by Elasticsearch (fast text + faceted queries).
- Cached queries in Redis for performance.

### 2. Weather Window Planner
- Fetch hourly forecasts (rain, wind, UV, temp).
- Score & recommend best 3-hour windows for trips.
- Real-time updates via SSE/WebSocket.

### 3. Availability & Alerts
- Periodic polling of campsite booking availability.
- Park alerts ingestion (closures, fire bans).
- Stored in MySQL, indexed in Elasticsearch.
- Users can subscribe to spots & get notified.

### 4. Notifications
- Kafka → Notifier service → SSE/push to frontend.
- Example: “North Era Campground is now available for Sat 9 AM.”

### 5. Trip Management
- Users can create/save trips.
- Stored in MySQL.
- Weather updates automatically attached.

---

## 📊 Data Sources (MVP + Future)
- **Weather**: Open-Meteo API (free) / OpenWeatherMap (free tier)
- **Campsites**: Seed dataset (20–30 entries) → later scrapers/APIs
- **Alerts**: Seeded JSON or scrape from official park sites
- **Availability**: Seeded CSV first → later booking API/scraper integration

---

## 📂 Project Structure

/frontend # React + TS app
/backend
/api # REST + SSE endpoints
/ingest # Scrapers/APIs → Kafka
/planner # Weather-window scoring
/notifier # Subscriptions + alerts
/docker # docker-compose for local dev
/infra
/terraform # AWS IaC
/github # CI/CD workflows
/seed # Initial CSV/JSON datasets


---

## 🚀 Development Plan

**Phase 1 – MVP**
- React frontend + Spring Boot monolith
- MySQL schema + Redis caching
- Search with Elasticsearch
- Weather ingestion (Open-Meteo)
- Docker Compose local dev

**Phase 2 – Streaming**
- Introduce Kafka
- Split services (`ingest`, `planner`, `notifier`)
- SSE live updates

**Phase 3 – Availability & Alerts**
- Seeded availability/alerts
- Add subscriptions + notifications
- Enhanced ES search + filters

**Phase 4 – Cloud Deployment**
- Deploy on AWS (ECS Fargate + RDS + ElastiCache + OpenSearch)
- CI/CD pipelines with GitHub Actions
- Multi-AZ + health checks
- Terraform infra as code

---

## 💡 Resume Pitch
> Built **OutScout**, a distributed full-stack application for outdoor trip planning. Designed with **React + Spring Boot**, **MySQL + Redis + Elasticsearch**, and a **Kafka-based ingestion/notification pipeline**. Deployed with **Docker + AWS ECS/RDS/ElastiCache/OpenSearch**, implementing **CI/CD pipelines and HA best practices**. Features include **search, real-time weather planning, availability snapshots, and park alerts**.

---

