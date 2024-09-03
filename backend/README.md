# TechTalentHub - Backend

TechTalentHub é uma aplicação Spring Boot que serve como backend para um sistema de gerenciamento de talentos técnicos.

## Pré-requisitos

- Java 11 ou superior
- Maven 3 ou superior
- PostgreSQL

## Configuração

1. Clone o repositório:

   ```bash
   git clone <URL_DO_REPOSITÓRIO>
   cd <NOME_DO_REPOSITÓRIO>
   ```

2. Configure o banco de dados PostgreSQL:

   Crie um banco de dados PostgreSQL e atualize o arquivo `src/main/resources/application.properties` com as credenciais do banco de dados.

   ```properties
   spring.datasource.url=jdbc:postgresql://<HOST_DO_BANCO_DE_DADOS>:<PORTA_DO_BANCO_DE_DADOS>/<NOME_DO_BANCO_DE_DADOS>
   spring.datasource.username=<NOME_DE_USUÁRIO_DO_BANCO_DE_DADOS>
   spring.datasource.password=<SENHA_DO_BANCO_DE_DADOS>
   ```

3. Compile e execute a aplicação:

   ```bash
   mvn spring-boot:run
   ```

4. Acesse a aplicação:

   Uma vez que a aplicação esteja em execução, você pode acessá-la em `http://localhost:8080`.

### .env e .env.example

O arquivo `.env` é comumente usado para armazenar variáveis de ambiente para sua aplicação. Ele permite que você defina pares de chave-valor que podem ser acessados dentro do seu código. O arquivo `env.example` serve como um modelo para o arquivo `.env`, fornecendo uma lista de variáveis de ambiente necessárias e seus valores padrão. É recomendado incluir o arquivo `env.example` no seu repositório para que outros desenvolvedores possam configurar facilmente seu ambiente copiando-o e preenchendo os valores reais no arquivo `.env`.
