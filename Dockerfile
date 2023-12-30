FROM openjdk:17-jdk-slim
EXPOSE 8084
ADD target/answer-0.0.1-SNAPSHOT.jar answer-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java", "-jar","/answer-0.0.1-SNAPSHOT.jar"]