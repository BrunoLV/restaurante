FROM openjdk:15-alpine
ARG JAR_FILE
ADD ${JAR_FILE} /app/app.jar
EXPOSE 8761
ENTRYPOINT ["java", "-jar", "/app/app.jar"]