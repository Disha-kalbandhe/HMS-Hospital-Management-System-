# --- Build Stage ---
FROM maven:3.9.11-eclipse-temurin-17 AS builder
WORKDIR /build

COPY pom.xml .
COPY eureka-server ./eureka-server
COPY api-gateway ./api-gateway
COPY patient-service ./patient-service
COPY doctor-service ./doctor-service

RUN mvn clean package -DskipTests --no-transfer-progress

# --- Eureka Server Runner ---
FROM eclipse-temurin:17-jre AS eureka-server
WORKDIR /app
COPY --from=builder /build/eureka-server/target/*.jar app.jar
EXPOSE 8761
ENTRYPOINT ["java","-jar","/app/app.jar"]

# --- API Gateway Runner ---
FROM eclipse-temurin:17-jre AS api-gateway
WORKDIR /app
COPY --from=builder /build/api-gateway/target/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","/app/app.jar"]

# --- Patient Service Runner ---
FROM eclipse-temurin:17-jre AS patient-service
WORKDIR /app
COPY --from=builder /build/patient-service/target/*.jar app.jar
EXPOSE 8081
ENTRYPOINT ["java","-jar","/app/app.jar"]

# --- Doctor Service Runner ---
FROM eclipse-temurin:17-jre AS doctor-service
WORKDIR /app
COPY --from=builder /build/doctor-service/target/*.jar app.jar
EXPOSE 8082
ENTRYPOINT ["java","-jar","/app/app.jar"]

# --- All-in-One Runner (Default for Render/Production) ---
FROM eclipse-temurin:17-jre AS production
WORKDIR /app
COPY --from=builder /build/eureka-server/target/*.jar eureka-server.jar
COPY --from=builder /build/api-gateway/target/*.jar api-gateway.jar
COPY --from=builder /build/patient-service/target/*.jar patient-service.jar
COPY --from=builder /build/doctor-service/target/*.jar doctor-service.jar

RUN echo '#!/bin/sh' > run.sh && \
    echo 'java -jar eureka-server.jar > eureka.log 2>&1 &' >> run.sh && \
    echo 'echo "Starting Eureka Server..." && sleep 15' >> run.sh && \
    echo 'java -jar patient-service.jar > patient.log 2>&1 &' >> run.sh && \
    echo 'java -jar doctor-service.jar > doctor.log 2>&1 &' >> run.sh && \
    echo 'echo "Starting Microservices..." && sleep 15' >> run.sh && \
    echo 'echo "Starting API Gateway on port 8080..."' >> run.sh && \
    echo 'exec java -jar api-gateway.jar' >> run.sh && \
    chmod +x run.sh

EXPOSE 8080
ENTRYPOINT ["/app/run.sh"]
