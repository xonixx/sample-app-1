FROM openjdk:11-jre
VOLUME /tmp
ARG JAR_FILE
COPY target/service-1-0.0.1.jar app.jar
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/app.jar"]