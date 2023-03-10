FROM openjdk:8-jdk-alpine
COPY "./target/transaction-api-1.0.jar" "app.jar"
EXPOSE 8080
ENTRYPOINT ["java","-jar","app.jar"]