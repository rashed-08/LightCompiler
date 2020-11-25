FROM maven:3.5.2-jdk-8 AS build
COPY src /usr/app/src
COPY pom.xml /usr/app
RUN mvn -f /usr/app/pom.xml clean package

FROM openjdk:8-alpine
COPY --from=build /usr/app/target/*.jar /usr/app/compiler-1.0.0.jar
RUN apk update
RUN apk add build-base
RUN apk add --upgrade g++
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/usr/app/compiler-1.0.0.jar"]