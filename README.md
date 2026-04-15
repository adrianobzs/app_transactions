# financial_transactions

Esta é uma simples aplicação financeira contemplando unicamente 2 (duas) entidades básicas de domínio com seus respectivos atributos:
1. Account (..)
3. Transaction (..)

O sistema é um backend e fornece serviços tais como:
1. Cadastrar uma nova conta
2. Consultar uma conta por seu ID
3. Registrar Transações de 4 (quatro) tipos diferentes (..)
4. Consultar uma Transação com base em seu ID

Foi adotado o padrão já conhecido para aplicações SpringBoot onde define as seguintes camadas
1. Controllers -> responsáveis por publicar os endpoints e atender requisições do FrontEnd.
2. Services -> onde localizam-se as lógicas de negócios e tratamento de entidades
3. Repository -> camada que interage com a infra-estrutura de persistência (Banco de Dados)
4. Banco de Dados -> Persistência de dados com PostgreSQL 15

Algumas outras implementações foram utilizadas como acessórias e seguem os padrões já conhecidos
1. Uso de classes DTO (Data Transfer Object) para representar informações de entidades de domínio sem expo-las totalmente, servindo de uma espécie de "filtro" de dados. Utilizados tanto na aquisição de dados (requests) quanto na sua exposição (responses)
2. Uso de EntityMapper : uma classe utilitária fundamental para prover as transformações entre entre Entidade -> DTO e DTO -> Entidade
3. Exceçoes de Negócio : criação de um conjunto customizado de exceções e uma classe genérica responsável por capturá-las, registrar logs e retornar código de erro HTTP e uma mensagem mais amigável.

Containerização

A aplicação como um todo é executada através dos seguintes componentes:
1. Container de Aplicação com StringBoot
2. Container de Dados com o Postgres
3. Volume de dados externo para manutenção de dados durante o ciclo códifica - build - deploy, preservando os dados mesmo que o container de banco seja removido.
4. Rede local via Docker para garantir a comunicação entre os containers

Sendo assim, precisamos fazer uso do Docker Compose para orquestrar esses elementos.

Segue um roteiro resumido de como rodar localmente via Docker a aplicaçao:

1. Clone o Repositório
2. Rode o comando para preparar o ambiente containerizado localmente (..)
3. Acesse um dos seguintes links para visualizar e interagir com a API dos serviços. Recomendaos Postman ou o proprio SwaggerUI para enviar suas requisições.
   
   



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
