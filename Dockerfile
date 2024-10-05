FROM openjdk:17-jdk-slim
COPY build/libs/backend-0.0.1-SNAPSHOT.jar daejangjangi.jar
ENTRYPOINT ["java", "-jar", "daejangjangi.jar"]