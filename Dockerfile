FROM amazoncorretto:22
WORKDIR /app
COPY SWP/target/SWP-0.0.1-SNAPSHOT.jar /app/app.jar
CMD ["java","-jar","/app/app.jar"]
