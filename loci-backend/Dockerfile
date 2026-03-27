# Build
FROM maven:3.9-eclipse-temurin-17 AS build
WORKDIR /app

# Copy build files first (for better layer caching)
COPY pom.xml .
COPY src ./src
COPY proguard.cfg ./proguard.cfg
COPY pmd-rules.xml ./pmd-rules.xml
COPY .env ./.env

RUN mvn clean package -DskipTests

# Extract layers
FROM eclipse-temurin:17-jre AS builder
WORKDIR /extracted

COPY --from=build /app/target/*-SNAPSHOT-exec.jar app.jar

RUN java -Djarmode=layertools -jar app.jar extract

# Runtime
FROM eclipse-temurin:17-jre-alpine
WORKDIR /application

# Log endpoint
RUN mkdir -p /application/logs && chmod 777 /application/logs
VOLUME /application/logs

COPY --from=builder /extracted/dependencies/ ./
COPY --from=builder /extracted/spring-boot-loader/ ./
COPY --from=builder /extracted/snapshot-dependencies/ ./
COPY --from=builder /extracted/application/ ./

RUN addgroup -g 1000 spring && adduser -u 1000 -G spring -s /bin/sh -D spring
USER spring:spring

EXPOSE 8080

ENTRYPOINT ["java", "org.springframework.boot.loader.launch.JarLauncher"]
