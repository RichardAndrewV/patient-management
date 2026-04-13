# #commands and instruction to create a docker file.
# #first do base image which contains runtime environment and build tools.
# FROM maven:3.9.9-eclipse-temurin-21 AS builder
# #keeps our code in this particular directory
# WORKDIR /app
# #copies all the dependencies into a folder in the working directory
# COPY pom.xml .
# #mvn from maven used for installing the dependency the go-offline -B is used for caching the dependencies to make app more faster.
# RUN mvn dependency:go-offline -B
# #copy src code
# COPY src ./src
# #so all the src code and dependencies are done so we create .jar file at the end ie a new package is asked to be created by the springboot.
# RUN mvn clean package -DskipTests
#
# FROM eclipse-temurin:21-jdk AS runner
#
# WORKDIR /app
#
# COPY --from=builder ./app/target/billing-service-0.0.1-SNAPSHOT.jar ./app.jar
#
# EXPOSE 4001
# EXPOSE 9001
# #The JDK CLI command for running the app using runner
# ENTRYPOINT ["java", "-jar", "app.jar"]
# 🔹 Build Stage
# FROM maven:3.9.9-eclipse-temurin-21 AS builder
#
# WORKDIR /app
#
# COPY pom.xml .
# # RUN mvn dependency:go-offline -B
#
# COPY src ./src
# RUN mvn clean package -DskipTests
#
# # 🔹 Runtime Stage
# FROM eclipse-temurin:21-jdk AS runner
#
# WORKDIR /app
#
# COPY --from=builder /app/target/billing-service-0.0.1-SNAPSHOT.jar app.jar
#
# # REST + gRPC ports
# EXPOSE 4001
# EXPOSE 9001
#
# ENTRYPOINT ["java", "-jar", "app.jar"]

FROM eclipse-temurin:21-jdk

WORKDIR /app

COPY target/*.jar app.jar

EXPOSE 4001
EXPOSE 9001

ENTRYPOINT ["java", "-jar", "app.jar"]