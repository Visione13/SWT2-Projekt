# Stage 1, build the App
FROM maven:3.9.8-eclipse-temurin-22 AS build

# Set the working directory
WORKDIR /app

# Copy Maven project files
COPY pom.xml ./
COPY src ./src

# Build the application
RUN mvn clean package

# Step 2, run the app
FROM eclipse-temurin:latest

# Set the workspace
WORKDIR /app

# Copy the JAR file from the first stage
COPY --from=build /app/target/*.jar /app/application.jar

# Expose the port your application will use
EXPOSE 8080

# Set the entrypoint for the container
ENTRYPOINT ["java", "-Xmx2048M", "-jar", "/app/application.jar"]


