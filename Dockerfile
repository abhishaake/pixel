FROM amazoncorretto:21

# Set working directory
WORKDIR application

# Copy application JAR
COPY target/*.jar application.jar

# Expose the application port
EXPOSE 8080

# Run the application correctly
ENTRYPOINT java -jar application.jar
