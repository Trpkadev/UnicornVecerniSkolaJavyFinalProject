FROM openjdk:19-jdk-alpine

COPY target/nakupniseznam-0.1.0.jar app.jar
COPY docker-compose.yml docker-compose.yml

EXPOSE 3004

ENTRYPOINT ["java", "-jar", "/app.jar"]