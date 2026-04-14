FROM eclipse-temurin:21-jdk-alpine

WORKDIR /app

# Copiar o Maven Wrapper e pom.xml
COPY mvnw .
COPY .mvn .mvn
COPY pom.xml .

# Baixar dependências (cache layer)
RUN ./mvnw dependency:go-offline -B

# Copiar código fonte
COPY src src

# Build da aplicação
RUN ./mvnw package -DskipTests

# Porta da aplicação
EXPOSE 8080

# Executar o JAR
CMD ["java", "-jar", "target/financial_transactions-0.0.1-SNAPSHOT.jar"]