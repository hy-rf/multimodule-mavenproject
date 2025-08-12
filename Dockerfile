# -------- Build stage --------
FROM maven:3.9.6-eclipse-temurin-21-alpine AS build

WORKDIR /app

# Copy the rest of the source and build the jar
COPY . .

RUN rm -rf sbp-client-nuxt

RUN mvn clean package

# -------- Run stage --------
FROM eclipse-temurin:21-jre-alpine

WORKDIR /app

# Copy the built jar from the build stage
COPY --from=build /app/app/target/*.jar app.jar

# Run the Spring Boot application
ENTRYPOINT ["java", "-jar", "app.jar", "--spring.profiles.active=prod"]
