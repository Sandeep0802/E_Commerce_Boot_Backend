# -------------------- BUILD STAGE --------------------
FROM eclipse-temurin:25-jdk AS build

WORKDIR /app

# Copy everything
COPY . .

# Build Spring Boot app
RUN ./mvnw clean package -DskipTests


# -------------------- RUN STAGE --------------------
FROM eclipse-temurin:25-jre

WORKDIR /app

# Copy built jar
COPY --from=build /app/target/*.jar app.jar

# Render provides PORT env variable
EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]
