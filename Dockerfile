# Use an official Maven image as the base image
FROM maven:3.6.3 AS build

# Set the working directory in the container
WORKDIR /app

# Copy the Maven project files into the container
COPY ./ /app/

# Build the Maven project and create a JAR file
RUN mvn clean install

# Use a lightweight Alpine image for the final runtime image
FROM adoptopenjdk:13-jre-hotspot

# Set the working directory in the container
WORKDIR /app

# Copy the JAR file from the build stage to the runtime image
COPY --from=build /app/target/learn-lynx.jar /app/app.jar

# Expose the port that the application will run on
EXPOSE 8084

# Command to run the application
CMD ["java", "-jar", "app.jar"]
