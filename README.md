# Sistema de Suporte Técnico - Backend

Este projeto é um sistema backend para gerenciamento de chamados de suporte técnico, desenvolvido em Java com Spring Boot. Ele oferece autenticação JWT, cadastro e gerenciamento de usuários, controle de permissões por papel, envio de e-mails para redefinição de senha, e integração com banco de dados MySQL.

## Funcionalidades

- **Autenticação e Autorização:**  
  Utiliza JWT para autenticação e controle de acesso baseado em papéis (admin, servidor, bolsista, cliente, etc).

- **Gerenciamento de Usuários:**  
  Cadastro, atualização, remoção e autenticação de usuários, com validação de senha e telefone.

- **Recuperação de Senha:**  
  Envio de e-mail com token para redefinição de senha, validação de token e atualização segura da senha.

- **Cadastro e Gerenciamento de Chamados:**  
  Permite criar, atualizar e consultar chamados de suporte, associando clientes, equipamentos e locais.

- **Envio de E-mails:**  
  Integração com SMTP para envio de notificações e redefinição de senha.

## Estrutura do Projeto

- `src/main/java/com/suporte/demo/`  
  Código-fonte principal, dividido em camadas:
  - `LAYERS/controllers`: Controllers REST.
  - `LAYERS/entities`: Entidades JPA.
  - `LAYERS/repositories`: Repositórios Spring Data.
  - `LAYERS/services`: Lógica de negócio.
  - `LAYERS/security`: Configuração de segurança e filtros JWT.
  - `LAYERS/passwordforgot`: Recuperação de senha.
  - `utils`: Utilitários (e-mail, validação, etc).

- `src/main/resources/`  
  - `application.properties`: Configurações do banco de dados e e-mail.
  - `static/` e `templates/`: Recursos estáticos e templates (se necessário).

- `src/test/java/`:  
  Testes automatizados.

## Configuração

1. **Banco de Dados:**  
   - MySQL rodando localmente.
   - Crie o banco de dados `suporte`.
   - Atualize usuário e senha em `application.properties` se necessário.

2. **Configuração de E-mail:**  
   - Configure as credenciais SMTP do Gmail em `application.properties`.

3. **Dependências:**  
   - Gerenciadas via Maven (`pom.xml`).

## Como Rodar

1. **Instale as dependências:**
   ```sh
   ./mvnw clean install
   ```

2. **Execute a aplicação:**
   ```sh
   ./mvnw spring-boot:run
   ```

3. **Acesse a API:**  
   - Servidor padrão: `http://localhost:8080`

## Endpoints Principais

- `POST /usuario/cadastrar` — Cadastro de usuário.
- `POST /usuario/login` — Login de usuário.
- `GET /usuario/{id}` — Buscar usuário por ID.
- `DELETE /usuario/{id}` — Remover usuário.
- `POST /usuario` — Criar usuário (admin).
- `PUT /usuario/{id}` — Atualizar usuário (admin).
- `GET /status` — Verificar status do servidor.
- `GET /api/auth/forgot-password?email=...` — Solicitar redefinição de senha.
- `GET /api/auth/validate-reset-token?token=...` — Validar token de redefinição.
- `POST /api/auth/reset-password?token=...` — Redefinir senha.

## Observações

- Para acessar endpoints protegidos, é necessário autenticar-se e fornecer o token JWT no header `Authorization`.
- O projeto utiliza validação de senha e telefone.
- O envio de e-mails depende de configuração correta do SMTP.

## Exemplo de Cadastro de Usuário

```json
{
  "login": "Cleberson Luiz",
  "senha": "Gustavo$23"
}
```

## Exemplo de Criação de Chamado

```json
{
  "cliente": {
    "nome": "Maria Oliveira",
    "telefone": "(11) 98765-4321"
  },
  "equipamento": {
    "nome": "Computador",
    "descricao": "Computador de mesa",
    "tombamento": "TOMB223345",
    "tipo": "COMPUTADOR"
  },
  "local": {
    "departamento": "TI",
    "descricao": "Departamento de Tecnologia"
  }
}
```

## Tecnologias Utilizadas

- Java 17
- Spring Boot
- Spring Security (JWT)
- Spring Data JPA
- MySQL
- JavaMail (envio de e-mails)
- Lombok

---

> **Atenção:**  
> Lembre-se de criar um usuário administrador no banco de dados para acessar funcionalidades restritas.
