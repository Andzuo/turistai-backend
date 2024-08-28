FROM maven:3.9.6-eclipse-temurin-21-alpine AS build
COPY src /app/src
COPY pom.xml /app
RUN mvn -f /app/pom.xml clean package -Dmaven.test.skip

FROM openjdk:21-slim
EXPOSE 8080
RUN mkdir -p /app/uploads
COPY --from=build /app/target/*.jar app.jar
ENTRYPOINT ["java", "-jar", "/app.jar"]
