# financial_transactions

Este projeto é um serviço Spring Boot para gerenciamento de transações financeiras com PostgreSQL.

## Visão geral

- Spring Boot 4.0.5
- Java 21
- PostgreSQL como banco de dados
- Docker Compose para execução local com contêineres
- Maven Wrapper (`./mvnw`) para build e testes

## Pré-requisitos

- Java 21 ou superior
- Docker Engine instalado na máquina
- Docker Compose
- Git (opcional)

## Clonar o repositório

```bash
git clone <repo-url>
cd app_transactions
```

## Executar com Docker Compose

### 1. Pré-requisito

- Docker instalado na máquina
- Docker Compose disponível

### 2. Construir e subir os serviços

```bash
docker compose -f compose.yml up --build
```

### 2.1 Alternativa com Docker Compose legacy

```bash
docker-compose up -d
```

### 3. Serviços disponíveis

- `postgres`: PostgreSQL 15
- `app`: aplicação Spring Boot

### 4. URLs de acesso

- Aplicação: `http://localhost:8080`
- Swagger UI: `http://localhost:8080/swagger-ui/index.html`
- (ou) `http://localhost:8080/swagger-ui.html`
- Banco de dados PostgreSQL: `jdbc:postgresql://postgres:5432/transactiondb`

### 5. Parar os serviços

```bash
docker compose -f compose.yml down
```

ou

```bash
docker-compose down
```

## Executar localmente

### 1. Instalar dependências e compilar

```bash
./mvnw clean package -DskipTests
```

### 2. Configurar o banco local

O arquivo `src/main/resources/application.properties` já está configurado para um PostgreSQL local:

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/transactiondb
spring.datasource.username=postgres
spring.datasource.password=postgres
```

Certifique-se de que um PostgreSQL esteja rodando em `localhost:5432` com banco, usuário e senha:

- database: `transactiondb`
- username: `postgres`
- password: `postgres`

### 3. Executar a aplicação

```bash
./mvnw spring-boot:run
```

ou

```bash
java -jar target/financial_transactions-0.0.1-SNAPSHOT.jar
```

### 4. Acessar a aplicação

- API principal: `http://localhost:8080`
- Porta exposta: `8080`

## Observações de configuração

O `compose.yml` define:

- `postgres` com `POSTGRES_DB=transactiondb`, `POSTGRES_USER=postgres`, `POSTGRES_PASSWORD=postgres`
- `app` com variáveis de ambiente para conectar no PostgreSQL do contêiner:
  - `SPRING_DATASOURCE_URL=jdbc:postgresql://postgres:5432/transactiondb`
  - `SPRING_DATASOURCE_USERNAME=postgres`
  - `SPRING_DATASOURCE_PASSWORD=postgres`

## Testes

Executar todos os testes com:

```bash
./mvnw test
```

Executar testes de uma classe específica:

```bash
./mvnw -Dtest=AccountServiceTest test
```

## Dockerfile

O `Dockerfile` do projeto:

- usa `eclipse-temurin:21-jdk-alpine`
- copia `mvnw`, `.mvn`, `pom.xml` e `src`
- baixa dependências offline
- empacota o JAR com `./mvnw package -DskipTests`
- expõe a porta `8080`
- executa `java -jar target/financial_transactions-0.0.1-SNAPSHOT.jar`

## Links úteis

- Spring Boot: https://spring.io/projects/spring-boot
- PostgreSQL: https://www.postgresql.org
- Docker Compose: https://docs.docker.com/compose/
