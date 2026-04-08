FROM maven:3.9.9-eclipse-temurin-21 AS build


WORKDIR /opt/order-service

COPY target/order-service-0.0.1-SNAPSHOT.jar order-service.jar

EXPOSE 9010

# Run application
ENTRYPOINT ["java", "-jar", "order-service.jar"]