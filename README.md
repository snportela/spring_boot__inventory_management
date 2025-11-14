[JAVA__BADGE]:https://img.shields.io/badge/java-%23ED8B00.svg?style=for-the-badge&logo=openjdk&logoColor=white
[SPRING__BADGE]: https://img.shields.io/badge/spring-%236DB33F.svg?style=for-the-badge&logo=spring&logoColor=white
[MAVEN__BADGE]: https://img.shields.io/badge/Apache%20Maven-C71A36?style=for-the-badge&logo=Apache%20Maven&logoColor=white
[POSTGRES__BADGE]: https://img.shields.io/badge/postgres-%23316192.svg?style=for-the-badge&logo=postgresql&logoColor=white
[DOCKER__BADGE]: https://img.shields.io/badge/docker-%230db7ed.svg?style=for-the-badge&logo=docker&logoColor=white

[English](#english) | [Português](#portugues)

# <a name="english"></a>Inventory Management System API 🧾

Spring Boot REST API for managing inventory, loans and appointments in university labs.

## 💻Technologies
![JAVA__BADGE] ![SPRING__BADGE] ![MAVEN__BADGE] ![POSTGRES__BADGE] ![DOCKER__BADGE]

## Requirements

- PostgreSQL 17
- JDK 21

## ⚙️ Features

- Allows the management of:
    - Resources
    - Areas
    - Categories
    - Receipts
    - Loans
    - Appointments
    - Users
- User authentication with Spring Security:
    - Password encryption with Bcrypt
    - Role-Based Access Control
    - JWT authorization
- Password reset through generated token sent in email
- Pagination and sorting for all major endpoints
-   Filtering and searching with JPA Specifications
-   Soft delete logic for data integrity
-   Database migrations handled by Flyway

## Endpoints

The API provides the following main endpoints:

-   `/auth/**`: Authentication (login, password redeem, password reset).
-   `/api/users`: User management.
-   `/api/areas`: Management of physical areas.
-   `/api/categories`: Management of resource categories.
-   `/api/labs`: Management of university labs.
-   `/api/receipts`: Management of acquisition receipts.
-   `/api/resources`: Management of inventory resources.
-   `/api/loans`: Management of resource loans.
-   `/api/loan-items`: Management of items within a loan.
-   `/api/appointments`: Management of lab appointments.


## 🚀 Getting Started (Local Development)

1.  **Clone the repository:**
    ```bash
    git clone https://github.com/snportela/spring_boot__inventory_management
    ```
2.  **Database Setup:**
  -   Create a PostgreSQL database.
  -   The application uses Flyway and will automatically run the migrations from `src/main/resources/db/migration` on startup.

3.  **Environment Variables:**
  -   Configure the environment variables. You can set them in your IDE or create an `application.properties` file in `src/main/resources/`.
  -   The following variables are required (see `application.properties` for details):
    -   `DB_URL`: The full JDBC URL for your PostgreSQL database.
    -   `DB_USER`: Database username.
    -   `DB_PASSWORD`: Database password.
    -   `JWT_SECRET`: A secret key for signing JWT tokens.
    -   `RESET_SECRET`: A secret key for signing password reset tokens.
    -   `MAIL_USERNAME`: Gmail account for sending emails.
    -   `MAIL_PASSWORD`: Gmail App Password (not your regular password).
    -   `HOST_URL`: The base URL of your frontend application (for the password reset link).

4.  **Install Dependencies and Run:**
  -   The project uses the Maven wrapper.
    
  ```bash
    # Install dependencies
    ./mvnw clean install
    
    # Run the application
    ./mvnw spring-boot:run
   ```

## 🐳 Running with Docker

**Note:** Make sure you have [Docker](https://www.docker.com/get-started) installed on your machine.

You can also build and run the application using the provided `Dockerfile`.

1.  **Build the Docker Image:**
    ```bash
    docker build -t inventory-api .
    ```

2.  **Run the Docker Container:**
  -   You must pass the required environment variables to the container using the `-e` flag.
  -   Make sure to expose port `8080`.

      ```bash
          docker run -p 8080:8080 \
              -e DB_URL="jdbc:postgresql://<your-db-host>:<port>/<db-name>" \
              -e DB_USER="<your-db-user>" \
              -e DB_PASSWORD="<your-db-password>" \
              -e JWT_SECRET="<your-jwt-secret>" \
              -e RESET_SECRET="<your-reset-secret>" \
              -e MAIL_USERNAME="<your-gmail-username>" \
              -e MAIL_PASSWORD="<your-gmail-app-password>" \
              -e HOST_URL="<your-frontend-host-url>" \
              --name inventory-api-container \
              -d inventory-api
      ```

  **Notes:**
  -   **Gmail:** `MAIL_PASSWORD` must be a [Google App Password](https://support.google.com/accounts/answer/185833), not your standard account password.

---

# <a name="portugues"></a>API de Sistema de Gerenciamento de Inventário 🧾

![JAVA__BADGE] ![SPRING__BADGE] ![MAVEN__BADGE] ![POSTGRES__BADGE]

API REST em Spring Boot para gerenciamento de inventário, empréstimos e agendamentos em laboratórios universitários.

## 💻 Tecnologias

![JAVA__BADGE] ![SPRING__BADGE] ![MAVEN__BADGE] ![POSTGRES__BADGE] ![DOCKER__BADGE]

## Requisitos

-   JDK 21
-   PostgreSQL 17

## ⚙️ Funcionalidades

-   Permite o gerenciamento de:
    -   Recursos
    -   Áreas
    -   Categorias
    -   Notas Fiscais 
    -   Empréstimos e Itens de Empréstimo
    -   Agendamentos
    -   Usuários
-   Autenticação de usuário com Spring Security:
    -   Criptografia de senha com Bcrypt
    -   Controle de Acesso Baseado em Cargos 
    -   Autorização com JWT
-   Redefinição de senha por meio de token gerado enviado por e-mail.
-   Paginação e ordenação para todos os endpoints principais.
-   Filtragem e busca com JPA Specifications.
-   Lógica de "soft delete" para integridade dos dados.
-   Migrações de banco de dados gerenciadas pelo Flyway.

## Endpoints

A API fornece os seguintes endpoints principais:

-   `/auth/**`: Autenticação (login, recuperação de senha, redefinição de senha).
-   `/api/users`: Gerenciamento de usuários.
-   `/api/areas`: Gerenciamento de áreas físicas.
-   `/api/categories`: Gerenciamento de categorias de recursos.
-   `/api/labs`: Gerenciamento de laboratórios.
-   `/api/receipts`: Gerenciamento de notas fiscais de aquisição.
-   `/api/resources`: Gerenciamento de recursos do inventário.
-   `/api/loans`: Gerenciamento de empréstimos de recursos.
-   `/api/loan-items`: Gerenciamento de itens num empréstimo.
-   `/api/appointments`: Gerenciamento de agendamentos nos laboratórios.

## 🚀 Rodando o Projeto (Desenvolvimento Local)

1.  **Clone o repositório:**
    ```bash
    git clone https://github.com/snportela/spring_boot__inventory_management
    ```
2.  **Configuração do Banco de Dados:**
    -   Crie um banco de dados PostgreSQL.
    -   A aplicação usa Flyway e executará automaticamente as migrações da pasta `src/main/resources/db/migration` ao iniciar.

3.  **Variáveis de Ambiente:**
    -   Configure as variáveis de ambiente. Você pode defini-las na sua IDE ou criar um arquivo `application.properties` em `src/main/resources/`.
    -   As seguintes variáveis são necessárias (veja `application.properties` para detalhes):
        -   `DB_URL`: A URL JDBC completa para seu banco PostgreSQL.
        -   `DB_USER`: Nome de usuário do banco.
        -   `DB_PASSWORD`: Senha do banco.
        -   `JWT_SECRET`: Uma chave secreta para assinar os tokens JWT.
        -   `RESET_SECRET`: Uma chave secreta para assinar os tokens de redefinição de senha.
        -   `MAIL_USERNAME`: Conta do Gmail para enviar e-mails.
        -   `MAIL_PASSWORD`: Senha de Aplicativo do Gmail (não sua senha normal).
        -   `HOST_URL`: A URL base da sua aplicação frontend (para o link de redefinição de senha).

4.  **Instale as Dependências e Execute:**
    -   O projeto usa o Maven wrapper.
    ```bash
    # Instalar dependências
    ./mvnw clean install
    
    # Executar a aplicação
    ./mvnw spring-boot:run
    ```

## 🐳 Executando com Docker

**Nota:** Certifique-se de ter o [Docker](https://www.docker.com/get-started) instalado em sua máquina.

Você também pode construir e executar a aplicação usando o `Dockerfile` fornecido.

1.  **Construa a Imagem Docker:**
    ```bash
    docker build -t inventory-api .
    ```

2.  **Execute o Contêiner Docker:**
    -   Você deve passar as variáveis de ambiente necessárias para o contêiner usando a flag `-e`.
    -   Lembre-se de expor a porta `8080`.

    ```bash
    docker run -p 8080:8080 \
      -e DB_URL="jdbc:postgresql://<seu-host-db>:<porta>/<nome-db>" \
      -e DB_USER="<seu-usuario-db>" \
      -e DB_PASSWORD="<sua-senha-db>" \
      -e JWT_SECRET="<seu-segredo-jwt>" \
      -e RESET_SECRET="<seu-segredo-reset>" \
      -e MAIL_USERNAME="<seu-usuario-gmail>" \
      -e MAIL_PASSWORD="<sua-senha-app-gmail>" \
      -e HOST_URL="<url-do-seu-frontend>" \
      --name inventory-api-container \
      -d inventory-api
    ```

    **Notas:**
    -   **Gmail:** `MAIL_PASSWORD` deve ser uma [Senha de Aplicativo](https://support.google.com/accounts/answer/185833) do Google, não sua senha de conta padrão.
    