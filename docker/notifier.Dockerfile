FROM openjdk:17-jdk-slim

WORKDIR /app

# Copy Maven files
COPY backend/pom.xml .
COPY backend/notifier/pom.xml ./notifier/

# Download dependencies
RUN apt-get update && apt-get install -y maven && rm -rf /var/lib/apt/lists/*
RUN mvn dependency:go-offline -B

# Copy source code
COPY backend/notifier/src ./notifier/src
COPY backend/pom.xml .

# Build the application
RUN mvn clean package -DskipTests -pl notifier

# Run the application
EXPOSE 8083
CMD ["java", "-jar", "notifier/target/notifier-1.0.0.jar"]
