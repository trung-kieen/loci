# Build
FROM maven:3.9-eclipse-temurin-17 AS build
WORKDIR /app

COPY pom.xml .
COPY src ./src
COPY proguard.cfg ./proguard.cfg
COPY pmd-rules.xml ./pmd-rules.xml
COPY .env ./.env

RUN mvn clean package -DskipTests

# Extract layers
FROM eclipse-temurin:17-jre-jammy AS builder
WORKDIR /extracted

COPY --from=build /app/target/*-SNAPSHOT-exec.jar app.jar

RUN java -Djarmode=layertools -jar app.jar extract

# Runtime
FROM eclipse-temurin:17-jre-jammy
WORKDIR /application

# Log endpoint
RUN mkdir -p /application/logs && chmod 777 /application/logs
VOLUME /application/logs

COPY --from=builder /extracted/dependencies/ ./
COPY --from=builder /extracted/spring-boot-loader/ ./
COPY --from=builder /extracted/snapshot-dependencies/ ./
COPY --from=builder /extracted/application/ ./

RUN groupadd -g 1000 spring && useradd -u 1000 -g spring -s /bin/sh -m spring
USER spring:spring




EXPOSE 8080

ENTRYPOINT ["java", \
    "-XX:+UseContainerSupport", \
    "-XX:MaxRAMPercentage=75.0", \
    "-XX:InitialRAMPercentage=50.0", \
    "-XX:+UseG1GC", \
    "-XX:MaxGCPauseMillis=200", \
    "-XX:+UseStringDeduplication", \
    "-Djava.security.egd=file:/dev/./urandom", \
    "-Dspring.backgroundpreinitializer.ignore=true", \
    "org.springframework.boot.loader.launch.JarLauncher"]
