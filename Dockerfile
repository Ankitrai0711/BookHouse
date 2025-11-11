# Use Java 8 base image (Debian-based)
FROM eclipse-temurin:8-jdk-alpine

WORKDIR /app

# Copy Maven build jar (after running mvn package locally)
COPY target/BookHouse-0.0.1-SNAPSHOT.jar app.jar

# Run the JAR
ENTRYPOINT ["java", "-jar", "app.jar"]