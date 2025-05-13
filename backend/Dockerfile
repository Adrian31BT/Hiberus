FROM eclipse-temurin:21-jdk-jammy

WORKDIR /app

COPY . .

RUN apt-get update && apt-get install -y maven

RUN mvn clean install

EXPOSE 8080

CMD ["java", "-jar", "target/backend-0.0.1-SNAPSHOT.jar"]
