#Pull the official jdk from docker hub
FROM openjdk:17
#set working directory inside the container
WORKDIR /app
#copy the jar file to the container
COPY ./target/demo.jar /app
#Expose the port Spring boot app will run on
EXPOSE 9090
#Run the jar file
CMD ["java", "-jar", "demo.jar"]