# ======================
# STAGE 1: BUILD
# ======================
FROM maven:3.9.6-eclipse-temurin-17 AS build

WORKDIR /app

COPY pom.xml .
COPY .mvn .mvn
COPY mvnw .

# 🔑 DAR PERMISOS
RUN chmod +x mvnw

RUN ./mvnw dependency:go-offline

COPY src src

RUN ./mvnw clean package -DskipTests

# ======================
# STAGE 2: RUNTIME
# ======================
FROM eclipse-temurin:17-jre-alpine

WORKDIR /app

COPY --from=build /app/target/school-learning-0.0.1-SNAPSHOT.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]