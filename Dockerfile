FROM openjdk:17
ADD target/quizapi-0.0.1-SNAPSHOT.jar quizapi-0.0.1-SNAPSHOT.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "quizapi-0.0.1-SNAPSHOT.jar"]