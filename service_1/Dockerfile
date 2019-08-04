FROM openjdk:11-jre
VOLUME /tmp
ARG JAR_FILE

# to troubleshoot
RUN apt-get update && apt-get install -y net-tools procps

EXPOSE 34701
COPY target/service-1-0.0.1.jar app.jar
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-Dspring.profiles.active=qa","-jar","/app.jar"]
