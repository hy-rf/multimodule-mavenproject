# Build stage
FROM openjdk:21-jdk AS build
WORKDIR /app
COPY . .
RUN chmod +x ./mvnw
RUN ls
RUN ./mvnw clean package

# Run stage
FROM eclipse-temurin:21-jre
WORKDIR /app
COPY --from=build /app/app/target/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]