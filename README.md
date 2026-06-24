# Notifications API 📬

API REST para envio de notificações assíncronas com RabbitMQ desenvolvida com Spring Boot.

## 🚀 Tecnologias

- Java 21
- Spring Boot 3
- Spring Data JPA
- RabbitMQ
- PostgreSQL
- Docker
- Swagger/OpenAPI
- JUnit 5
- Mockito

## ⚙️ Como rodar o projeto

### Pré-requisitos
- Java 21
- Docker

### 1. Clone o repositório
```bash
git clone https://github.com/SEU_USUARIO/notifications-api.git
cd notifications-api
```

### 2. Suba o banco de dados e o RabbitMQ
```bash
docker-compose up -d
```

### 3. Rode a aplicação
```bash
./mvnw spring-boot:run
```

### 4. Acesse o Swagger
http://localhost:8081/swagger-ui.html

### 5. Acesse o painel do RabbitMQ
http://localhost:15672
usuário: admin
senha: admin123

## 📌 Endpoints

| Método | Endpoint | Descrição |
|---|---|---|
| POST | /notificacoes | Cria e envia uma notificação para a fila |
| GET | /notificacoes | Lista todas as notificações |
| GET | /notificacoes/{id} | Busca uma notificação por ID |

## 📝 Exemplos de uso

### Criar uma notificação
```json
POST /notificacoes
{
  "destinatario": "teste@gmail.com",
  "assunto": "Bem vindo!",
  "conteudo": "Sua conta foi criada com sucesso."
}
```

### Resposta
```json
{
  "id": 1,
  "destinatario": "teste@gmail.com",
  "assunto": "Bem vindo!",
  "conteudo": "Sua conta foi criada com sucesso.",
  "status": "PENDENTE",
  "mensagemErro": null,
  "criadoEm": "2026-06-23T19:12:12",
  "enviadoEm": null
}
```

## 🔄 Fluxo da notificação
POST /notificacoes → Salva como PENDENTE → Envia para fila → 202 Accepted

↓

Consumer processa

↓

Atualiza para ENVIADA

## 🧪 Rodando os testes
```bash
./mvnw test
```

## 📄 Licença
MIT