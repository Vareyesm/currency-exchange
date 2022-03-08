FROM openjdk:11-jdk-slim-buster
WORKDIR /app
COPY mvnw .
COPY .mvn .mvn
COPY pom.xml .
COPY src src
RUN ./mvnw clean install -DskipTests
ENTRYPOINT ["java","-jar","/app/target/currency-exchange.jar"]