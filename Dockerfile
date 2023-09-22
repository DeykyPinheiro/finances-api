FROM openjdk:17-jdk-alpine

WORKDIR /app

copy . .

RUN apk add --no-cache maven && \
    mvn package -DskipTests


CMD ["java", "-jar", "/app/target/demo-0.0.1-SNAPSHOT.jar"]