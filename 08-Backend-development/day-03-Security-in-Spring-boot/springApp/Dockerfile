FROM openjdk:22-jdk

WORKDIR /home/app

COPY target/spring-app.jar spring-app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "spring-app.jar"]
