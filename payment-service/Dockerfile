FROM maven:3.9.9-eclipse-temurin-21 AS build


WORKDIR /opt/payment-service

COPY target/payment-service-0.0.1-SNAPSHOT.jar payment-service.jar

EXPOSE 9020

# Run application
ENTRYPOINT ["java", "-jar", "payment-service.jar"]