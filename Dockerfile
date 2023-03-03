FROM openjdk:17-alpine
EXPOSE 8080
COPY ./build/libs/chat-management-service-0.0.1.jar /tmp/
WORKDIR /tmp
ENTRYPOINT ["java","-jar", "-Dspring.profiles.active=docker", "chat-management-service-0.0.1.jar"]