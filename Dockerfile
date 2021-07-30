FROM openjdk:8
ADD target/Audit-Authentication.jar Audit-Authentication.jar
EXPOSE 9400
ENTRYPOINT ["java","-jar","Audit-Authentication.jar"]
