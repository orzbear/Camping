FROM openjdk:17-jdk-slim

WORKDIR /app

# Copy Maven files
COPY backend/pom.xml .
COPY backend/ingest/pom.xml ./ingest/

# Download dependencies
RUN apt-get update && apt-get install -y maven && rm -rf /var/lib/apt/lists/*
RUN mvn dependency:go-offline -B

# Copy source code
COPY backend/ingest/src ./ingest/src
COPY backend/pom.xml .

# Build the application
RUN mvn clean package -DskipTests -pl ingest

# Run the application
EXPOSE 8081
CMD ["java", "-jar", "ingest/target/ingest-1.0.0.jar"]
