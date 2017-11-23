# Openjdk-8 base image
FROM java:openjdk-8-jdk

# Set working directory
WORKDIR /opt

# Copy all gradle files necessary to build application
COPY src src
COPY gradle gradle
COPY \
  build.gradle \
  gradlew \
./