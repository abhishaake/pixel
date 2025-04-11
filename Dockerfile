# Build stage
FROM amazoncorretto:21 AS build
WORKDIR /build

# Install Maven
RUN apk add --no-cache maven

# Copy pom.xml first for better layer caching
COPY pom.xml .

# Copy source code
COPY src ./src

# Run Maven build
RUN mvn clean install -DskipTests

# Runtime stage - use smaller JRE image
FROM amazoncorretto:21-alpine

# Set working directory
WORKDIR /app

# Copy only the built JAR from the build stage
COPY --from=build /build/target/*.jar application.jar

# Expose the application port
EXPOSE 8080

# Run the application
ENTRYPOINT ["sh", "-c", "java -jar application.jar"]
