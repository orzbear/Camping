FROM openjdk:17-jdk-slim

WORKDIR /app

# Copy Maven files
COPY backend/pom.xml .
COPY backend/planner/pom.xml ./planner/

# Download dependencies
RUN apt-get update && apt-get install -y maven && rm -rf /var/lib/apt/lists/*
RUN mvn dependency:go-offline -B

# Copy source code
COPY backend/planner/src ./planner/src
COPY backend/pom.xml .

# Build the application
RUN mvn clean package -DskipTests -pl planner

# Run the application
EXPOSE 8082
CMD ["java", "-jar", "planner/target/planner-1.0.0.jar"]
