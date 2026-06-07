# Paso 1: Usar una imagen de Maven con Java 17 para compilar el código
FROM maven:3.8.5-openjdk-17 AS build
WORKDIR /app
COPY . .
RUN mvn clean package -DskipTests

# Paso 2: Usar una imagen ligera de Java 17 para ejecutar la aplicación
FROM openjdk:17-jdk-slim
WORKDIR /app
COPY --from=build /app/target/*.jar app.jar

# Exponer el puerto que configuramos para Render
EXPOSE 10000

# Comando para arrancar tu backend de Spring Boot
ENTRYPOINT ["java", "-jar", "app.jar"]