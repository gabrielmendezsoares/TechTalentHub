# TechTalentHub - Frontend

Este é um projeto de Recrutamento Interno desenvolvido com Angular no frontend. O objetivo deste projeto é gerenciar vagas de emprego internas, permitindo que os administradores criem, atualizem e excluam vagas, e que os usuários se candidatem a essas vagas.

## Funcionalidades

- **Administração de Vagas**: Apenas administradores podem criar, atualizar e excluir vagas.
- **Candidatura a Vagas**: Usuários podem se candidatar a vagas disponíveis.
- **Autenticação**: Verificação de permissões para garantir que apenas administradores possam realizar certas ações.

## Tecnologias Utilizadas

- **Angular**: Framework para construção do frontend.
- **TypeScript**: Linguagem de programação utilizada no desenvolvimento do frontend.
- **SCSS**: Pré-processador CSS para estilização.

## Pré-requisitos

- **Node.js**: Certifique-se de ter o Node.js instalado. Você pode baixá-lo em [nodejs.org](https://nodejs.org/).
- **Angular CLI**: Instale a Angular CLI globalmente usando o comando:
  ```bash
  npm install -g @angular/cli
  ```

## Instalação

Clone o repositório:

```bash
git clone https://github.com/seu-usuario/recrutamento-interno.git
cd recrutamento-interno/frontend
```

Instale as dependências:

```bash
npm install
```

## Como Rodar

Inicie o servidor de desenvolvimento:

```bash
ng serve
```

Acesse a aplicação: Abra o navegador e vá para http://localhost:4200/.

## Estrutura do Projeto

- `src/app/components`: Contém os componentes da aplicação, como job-create, job-list, job-detail, job-update.
- `src/app/services`: Contém os serviços da aplicação, como job.service.ts e auth.service.ts.
- `src/app/models`: Contém os modelos de dados, como job.model.ts.

## Autenticação e Autorização

- `AuthService`: Serviço responsável por gerenciar a autenticação e verificar as permissões do usuário.
- `Guards`: Implementação de guards para proteger rotas e garantir que apenas usuários autorizados possam acessá-las.
