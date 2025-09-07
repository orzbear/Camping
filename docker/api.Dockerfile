FROM openjdk:17-jdk-slim

WORKDIR /app

# Copy all Maven files first to resolve dependencies
COPY pom.xml .
COPY api/pom.xml api/
COPY ingest/pom.xml ingest/
COPY planner/pom.xml planner/
COPY notifier/pom.xml notifier/

# Download dependencies
RUN apt-get update && apt-get install -y maven && rm -rf /var/lib/apt/lists/*
RUN mvn dependency:go-offline -B

# Copy source code for the specific service
COPY api/src ./api/src

# Build only the 'api' module
RUN mvn clean package -DskipTests -pl api

# Run the application
EXPOSE 8080
CMD ["java", "-jar", "api/target/api-1.0.0.jar"]