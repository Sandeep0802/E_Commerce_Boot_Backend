FROM eclipse-temurin:25-jdk AS build
WORKDIR /app

COPY . .

# ✅ Fix mvnw permission
RUN chmod +x mvnw

# Build Spring Boot app
RUN ./mvnw clean package -DskipTests


FROM eclipse-temurin:25-jre
WORKDIR /app
COPY --from=build /app/target/*.jar app.jar

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
