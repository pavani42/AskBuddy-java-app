FROM openjdk:17-jdk-slim
EXPOSE 8086
ADD target/Ask-Buddy_MS_User-0.0.1-SNAPSHOT.jar Ask-Buddy_MS_User-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java", "-jar","/Ask-Buddy_MS_User-0.0.1-SNAPSHOT.jar"]