FROM amazoncorretto:21-alpine-jdk

EXPOSE 8080

ARG JAR_FILE=target/srvGateway-0.0.1-SNAPSHOT.jar

COPY ${JAR_FILE} app.jar

ENTRYPOINT ["java","-jar","app.jar"]