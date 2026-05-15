
# API REST - Sistema de Academia

---
 
## Descrição

API REST desenvolvida com Spring Boot para gerenciamento de alunos, treinos, exercícios e avaliações físicas.
A principal mudança desse projeto para o outro é a adição de segurança na API com authenticação pelo Spring Security!

O projeto simula um sistema de academia completo, com foco em aplicação de regras de negócio, organização em camadas, autenticação JWT e controle de acesso baseado em roles

---

## Tecnologias utilizadas
- Java 21
- Spring Boot
- Spring Security
- JWT (JSON Web Token)
- Spring Data JPA
- Hibernate
- MySQL
- Lombok
- Maven

---

## Arquitetura

O projeto foi estruturado seguindo o padrão em camadas:

- **Controller** → responsável pelos endpoints REST
- **Service** → regras de negócio
- **Repository** → acesso ao banco de dados
- **DTOs** → entrada de dados
- **Entities** → representação das tabelas
- **Security** → autenticação e autorização JWT

---

## Segurança da Aplicação

Foi implementado um sistema completo de autenticação utilizando Spring Security e JWT.

### Funcionalidades de segurança
- Login com email e senha
- Registro de usuários
- Geração de token JWT
- Criptografia de senha com BCrypt
- Autenticação Stateless
- Controle de acesso baseado em roles
- Filtro JWT personalizado

---

## Roles da aplicação

A aplicação possui controle de permissões utilizando roles:

- `ROLE_ALUNO`
- `ROLE_ADMIN`

### Controle de acesso

- Endpoints de autenticação → públicos
- Endpoints privados → exigem token JWT
- Algumas rotas → acessíveis apenas para ADMIN

Exemplo:

```java
.requestMatchers(HttpMethod.POST,"/v1/avaliacoes")
.hasRole("ADMIN")
````

---

## Modelagem de Dados

Foram criadas entidades com diferentes tipos de relacionamento:

### **Alunos:**

* 1 avaliação física (**OneToOne**)
* possui vários treinos (**OneToMany**)
* possui roles (**ManyToMany**)

### **Avaliações Físicas:**

* pertence a um aluno (**OneToOne**)

### **Treinos:**

* pertence a um aluno (**ManyToOne**)
* possui vários exercícios (**ManyToMany**)

### **Exercícios:**

* pode estar em vários treinos (**ManyToMany**)

### **Roles:**

* associadas aos usuários (**ManyToMany**)

---

## Funcionalidades

### Autenticação

* Registrar usuário
* Login com JWT
* Controle de acesso por roles

### Alunos

* Criar aluno
* Buscar avaliação física do aluno
* Deletar aluno (com remoção de treinos e avaliação)

### Treinos

* Criar treino para um aluno
* Associar exercícios ao treino

### Exercícios

* Listar todos os exercícios
* Buscar exercícios por grupo muscular
* Cadastrar exercícios

### Avaliações Físicas

* Criar avaliação física
* Listar todas as avaliações
* Listar avaliações com paginação
* Consulta otimizada com Projection

---

## Endpoints da API

### Autenticação

* Registrar usuário:

```http
POST /v1/auth/register
```

* Login:

```http
POST /v1/auth/login
```

---

### Alunos

* Criar aluno:

```http
POST /v1/alunos
```

* Buscar avaliação física do aluno:

```http
GET /v1/alunos/{alunoId}/avaliacao
```

* Deletar aluno e seus dados relacionados:

```http
DELETE /v1/alunos/{alunoId}
```

---

### Treinos

* Criar treino para um aluno
* Associar exercícios ao treino:

```http
POST /v1/treinos
```

---

### Exercícios

* Listar todos os exercícios:

```http
GET /v1/exercicios
```

* Criar novo exercício:

```http
POST /v1/exercicios
```

* Buscar exercícios por grupo muscular:

```http
GET /v1/exercicios/grupos/{grupoMuscular}
```

---

### Avaliações Físicas

* Criar avaliação física:

```http
POST /v1/avaliacoes
```

* Listar todas as avaliações:

```http
GET /v1/avaliacoes
```

* Listar avaliações com paginação:

```http
GET /v1/avaliacoes/page/{page}/size/{size}
```

---

### Segurança dos Endpoints

### Públicos

```http
POST /v1/auth/**
```

### Privados

Todos os endpoints restantes exigem autenticação JWT.

### Restritos a ADMIN

```http
POST /v1/avaliacoes
```

---

### Autenticação JWT

Após realizar login, a API retorna um token JWT:

```json
{
  "token": "eyJhbGciOiJIUzI1NiJ9...",
  "expiresDTO": 3600000
}
```

Esse token deve ser enviado nas requisições privadas:

```http
Authorization: Bearer SEU_TOKEN
```

---

### Paginação

Foi implementada paginação utilizando Spring Data Pageable, permitindo controlar:

Número da página (page)
Quantidade de registros por página (size)

Exemplo:

```http
GET /v1/avaliacoes/page/0/size/5
```

Parâmetros:

* `page` → número da página
* `size` → quantidade de registros

---

-> Retorna os primeiros 5 registros de forma paginada.

---

### Consultas Otimizadas (Projection)

Foi utilizada **Projection** para melhorar a performance das consultas, retornando apenas os dados necessários:

* ID do aluno
* Nome do aluno
* Dados da avaliação física

Isso evita carregar entidades completas desnecessariamente.

---

### Regras de Negócio

* Não permite cadastro de alunos com email duplicado
* Um aluno só pode ter uma avaliação física
* Não permite treinos com nomes duplicados para o mesmo aluno
* Validação de existência de aluno e exercícios
* Exclusão controlada com remoção de dependências
* Senhas criptografadas com BCrypt
* Apenas ADMIN pode criar avaliações físicas

---

### Tratamento de Erros

Implementado utilizando:

@RestControllerAdvice

Exceções customizadas:

`NotFoundException` → (404)

`BadRequestException` → (400)

`BadCredentialsException` → (401)

### Exemplo de resposta:

```json
{
  "message": "Aluno não encontrado",
  "status": 404
}
```

---

## Exemplos de Requisição

### Registrar usuário

```json
{
  "nome": "Keven",
  "email": "keven@email.com",
  "senha": "123456"
}
```

### Login

```json
{
  "email": "keven@email.com",
  "senha": "123456"
}
```

### Criar treino

```json
{
  "id": 1,
  "nome": "Treino A",
  "exerciciosIds": [1, 2, 3]
}
```

### Criar avaliação física

```json
{
  "alunoId": 1,
  "peso": 70.5,
  "altura": 1.75,
  "porcentagemGorduraCorporal": 12.5
}
```

---

## Aprendizados

Durante o desenvolvimento deste projeto, foram consolidados conceitos importantes:

* Estruturação de API REST

* Organização em camadas

* Uso de DTOs

* Validações com Bean Validation

* Relacionamentos JPA

* Tratamento global de exceções

* Paginação com Pageable

* Uso de Projection

* Queries com JPQL e Native SQL

* Spring Security

* JWT Authentication

* Controle de acesso baseado em roles

* Criptografia de senhas com BCrypt

* Filtros personalizados com Spring Security

---

## Como rodar o projeto

### Pré-requisitos

* Java 21
* MySQL instalado e rodando
* Maven

---

### Configuração do banco

Crie um banco de dados:

```sql
CREATE DATABASE gym;
```

Ou utilize a configuração automática do projeto.

---

### Configuração JWT

No `application.properties`:

```properties
jwt.key=SUA_CHAVE_SECRETA
jwt.expiration=3600000
```

---

### Executando a aplicação

```bash
# Clonar o repositório
git clone https://github.com/SEU-USUARIO/SEU-REPO.git

# Entrar na pasta
cd SEU-REPO

# Rodar o projeto
./mvnw spring-boot:run
```

A aplicação estará disponível em:

```
http://localhost:8082
```

---

## 📄 Licença

Este projeto está sob a licença MIT.
Sinta-se livre para usar e modificar.

## 📌 Créditos

Este projeto foi desenvolvido com base em um tutorial do YouTuber e desenvolvedor SouzaDev, com pequenas mudanças, correções e implementações adicionais para fins de aprendizado.

```
```
