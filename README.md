# Agendamento

API REST para controle de clientes, servicos e agendamentos, desenvolvida com Spring Boot. O projeto expõe endpoints paginados para cadastro e consulta, aplica migracoes com Flyway, persiste os dados em MySQL e possui cobertura de testes para camadas de controller e service.

## Visao geral

O dominio principal do sistema esta dividido em 3 recursos:

- `clientes`: cadastro e consulta de clientes.
- `servicos`: cadastro, consulta e exclusao logica de servicos.
- `agendamentos`: criacao, consulta, atualizacao e cancelamento de horarios.

Regras observadas no codigo:

- telefone de cliente nao pode se repetir;
- servicos sao listados apenas quando `ativo = true`;
- `DELETE /servicos/{id}` nao remove o registro do banco, apenas marca `ativo = false`;
- o horario final do agendamento e calculado automaticamente com base na duracao do servico;
- nao e permitido criar ou atualizar agendamentos com sobreposicao de horario;
- apenas agendamentos com status `AGENDADO` podem ser alterados ou cancelados.

## Stack

- Java 17
- Spring Boot 3.4
- Spring Web
- Spring Data JPA
- Bean Validation
- MySQL
- Flyway
- MapStruct
- Springdoc OpenAPI / Swagger
- JUnit 5, Mockito e Spring Boot Test
- H2 para testes de integracao
- Docker e Docker Compose

## Estrutura do projeto

```text
src/main/java/br/com/david
|- appointment
|  |- controller
|  |- DTO
|  |- model
|  |- repository
|  \- service
\- shared
   |- annotation
   |- config
   |- exception
   \- Mapper
```

Arquivos relevantes:

- [pom.xml](/C:/Users/david/IdeaProjects/Agendamento/pom.xml)
- [application.yml](/C:/Users/david/IdeaProjects/Agendamento/src/main/resources/application.yml)
- [application-test.yml](/C:/Users/david/IdeaProjects/Agendamento/src/main/resources/application-test.yml)
- [docker-compose.yml](/C:/Users/david/IdeaProjects/Agendamento/docker-compose.yml)
- [Dockerfile](/C:/Users/david/IdeaProjects/Agendamento/Dockerfile)
- migrations Flyway em [src/main/resources/db/migration](/C:/Users/david/IdeaProjects/Agendamento/src/main/resources/db/migration)

## Modelo de dados

### Cliente

- `id`
- `nome`
- `telefone`
- `createdAt`

### Servico

- `id`
- `nome`
- `duracao` em minutos
- `preco`
- `ativo`

### Agendamento

- `id`
- `inicio`
- `termino`
- `status`: `AGENDADO`, `CANCELADO`, `COMPLETO`
- `cliente`
- `servico`
- `createdAt`
- `updatedAt`

## Endpoints

### Clientes

- `POST /clientes`
- `GET /clientes`
- `GET /clientes/{id}`

Exemplo de request:

```json
{
  "nome": "Joao Silva",
  "telefone": "22999101010"
}
```

### Servicos

- `POST /servicos`
- `GET /servicos`
- `GET /servicos/{id}`
- `DELETE /servicos/{id}`

Exemplo de request:

```json
{
  "nome": "Corte de Cabelo",
  "duracao": 30,
  "preco": 35.00,
  "ativo": true
}
```

### Agendamentos

- `POST /agendamentos`
- `GET /agendamentos`
- `GET /agendamentos/{id}`
- `GET /agendamentos/data/{data}`
- `GET /agendamentos/cliente/{id}`
- `PUT /agendamentos/{id}`
- `DELETE /agendamentos/{id}`

Exemplo de request:

```json
{
  "inicio": "2026-04-27T09:00:00",
  "clienteId": 1,
  "servicoId": 1
}
```

## Paginacao e ordenacao

As listagens usam os parametros:

- `page`: pagina atual
- `size`: quantidade de registros
- `ordem`: campo usado no `Sort`

Exemplo:

```http
GET /agendamentos?page=0&size=10&ordem=inicio
```

## Executando localmente

### Pre-requisitos

- JDK 17
- Maven 3.9+
- MySQL 8

### Banco de dados

O `application.yml` aponta para:

```text
jdbc:mysql://localhost:3306/agendamentodb?useTimezone=true&serverTimezone=UTC
```

Credenciais padrao:

- `DB_USERNAME=root`
- `DB_PASSWORD=root`

Crie o banco antes de subir a aplicacao:

```sql
CREATE DATABASE agendamentodb;
```

### Subindo com Maven

```bash
mvn spring-boot:run
```

### Gerando o jar

```bash
mvn clean package
java -jar target/Agendamento-0.0.1-SNAPSHOT.jar
```

## Executando com Docker

O projeto possui `Dockerfile` e `docker-compose.yml`.

```bash
docker compose up --build
```

Servicos esperados:

- API em `http://localhost:8080`
- MySQL em `localhost:3306`

## Migrations e dados iniciais

As migrations Flyway criam:

- tabela `clientes`
- tabela `servicos`
- tabela `agendamentos`

Tambem existe carga inicial em `V4__insert_dados_iniciais.sql` com:

- 5 clientes
- 7 servicos
- 10 agendamentos

## Testes

### Rodar a suite

```bash
mvn test
```

Cobertura atual identificada no projeto:

- testes de controller com `@SpringBootTest` e `MockMvc`
- testes de service com Mockito
- perfil `test` usando H2 em memoria

## Documentacao da API

O projeto inclui Springdoc OpenAPI. Em uma execucao padrao do Spring Boot, os endpoints normalmente ficam disponiveis em:

- `http://localhost:8080/swagger-ui/index.html`
- `http://localhost:8080/v3/api-docs`

## Formatos de resposta

Existe configuracao de content negotiation para:

- JSON
- XML
- YAML

O formato padrao da API e JSON.

## Tratamento de erros

Existe um `GlobalExceptionHandler` centralizando respostas para:

- erro de validacao
- recurso nao encontrado
- conflito de horario
- regras de negocio

O corpo de erro usa o DTO `ErrorResponseDTO`.

## Observacoes

- O projeto esta pronto para uso como API backend, mas ha alguns textos de mensagens e configuracoes com problemas de acentuacao/formatacao no codigo-fonte.
- A configuracao customizada de `springdoc` no `application.yml` merece revisao se a intencao for alterar os caminhos padrao da documentacao.
- As dependencias de CSV e Apache POI estao declaradas no `pom.xml`, mas nao ha uso delas no codigo atual.
