#start with a base image containing java runtime
FROM openjdk:17-jdk-slim
#information about who maintains the image
MAINTAINER akmall

#Add the Applications jar to the image
COPY target/accounts-service.jar accounts-service.jar

#thhis is teh used command to execute The Application
ENTRYPOINT ["java","-jar","accounts-service.jar"]

#this is the port will be used by the application
EXPOSE 8080