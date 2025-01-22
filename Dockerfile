FROM maven:3.8.1-openjdk-17 AS builder

WORKDIR /app

COPY ./pom.xml ./pom.xml
COPY ./src ./src

RUN mvn clean package -DskipTests

FROM openjdk:17

WORKDIR /app

COPY --from=builder /app/target/FreelancersBackend-0.0.1-SNAPSHOT.jar FreelancersBackend-0.0.1-SNAPSHOT.jar

CMD ["java", "-jar", "FreelancersBackend-0.0.1-SNAPSHOT.jar"]

EXPOSE 8080
