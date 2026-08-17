FROM openjdk:22-jdk

WORKDIR /home/app

ADD target/spring-app.jar .

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "spring-app.jar"]
