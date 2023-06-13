FROM openjdk:17
WORKDIR /app
COPY ./target/FreelancersBackend-0.0.1-SNAPSHOT.jar FreelancersBackend-0.0.1-SNAPSHOT.jar
CMD ["java", "-jar", "FreelancersBackend-0.0.1-SNAPSHOT.jar"]
EXPOSE 8080