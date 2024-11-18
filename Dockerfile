# Используем базовый образ с JDK 21
FROM openjdk:21-jdk-slim

WORKDIR /app

# Копируем сгенерированный jar файл
COPY build/libs/notion-gateway-0.0.1-SNAPSHOT.jar app.jar

# Устанавливаем права на файл, если нужно
RUN chmod +x /app/app.jar

# Открываем порт приложения
EXPOSE 8765

# Запуск приложения
ENTRYPOINT ["java", "-jar", "app.jar", "--spring.profiles.active=prod"]
