FROM openjdk:17-jdk-slim

WORKDIR /app

# Copy Maven files
COPY backend/pom.xml .
COPY backend/api/pom.xml ./api/

# Download dependencies
RUN apt-get update && apt-get install -y maven && rm -rf /var/lib/apt/lists/*
RUN mvn dependency:go-offline -B

# Copy source code
COPY backend/api/src ./api/src
COPY backend/pom.xml .

# Build the application
RUN mvn clean package -DskipTests -pl api

# Run the application
EXPOSE 8080
CMD ["java", "-jar", "api/target/api-1.0.0.jar"]
