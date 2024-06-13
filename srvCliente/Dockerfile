FROM amazoncorretto:21-alpine-jdk

EXPOSE 9510

ARG JAR_FILE=target/srvCliente-0.0.1-SNAPSHOT.jar

COPY ${JAR_FILE} app.jar

ENTRYPOINT ["java","-jar","app.jar"]