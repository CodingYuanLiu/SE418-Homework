FROM openjdk:8-jdk-alpine
MAINTAINER LQY
EXPOSE 8080
COPY ./target/wordladder-0.0.1-SNAPSHOT.jar /wordladder.jar
ENTRYPOINT [ "java", "-jar", "/wordladder.jar" ]
