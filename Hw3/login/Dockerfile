FROM openjdk:8-jdk-alpine
MAINTAINER LQY
EXPOSE 8099
COPY ./target/login-0.0.1-SNAPSHOT.jar /login.jar
ENTRYPOINT [ "java", "-jar", "/login.jar" ]
