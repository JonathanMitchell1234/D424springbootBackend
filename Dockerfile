# Start with a base image containing Java runtime
FROM openjdk:21-jdk

# Add Maintainer Info
LABEL maintainer="jonmitchell1234@gmail.com"

# Add a volume pointing to /tmp
VOLUME /tmp

COPY src/main/resources/service-account.json /resources/service-account.json

# Make port 8080 available to the world outside this container
EXPOSE 8080

# The application's jar file
ARG JAR_FILE=target/demo-0.0.1-SNAPSHOT.jar

# Add the application's jar to the container
ADD ${JAR_FILE} app.jar

# Run the jar file
ENTRYPOINT ["java", "-Djava.security.egd=file:/dev/./urandom", "-jar", "/app.jar"]
