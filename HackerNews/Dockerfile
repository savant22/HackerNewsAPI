FROM adoptopenjdk:17-jdk-hotspot

WORKDIR /app

COPY target/HackerNews-0.0.1-SNAPSHOT.jar /app/app.jar

EXPOSE 8080

CMD ["java", "-jar", "app.jar"]