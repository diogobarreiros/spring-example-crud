<h1 align="center"> 
    EXAMPLE API SPRING CRUD
</h1>

# Índice

- [Sobre](#sobre)
- [Tecnologias Utilizadas](#tecnologias-utilizadas)
- [Como Usar](#como-usar)
- [Testes](#testes)

<a id="sobre"></a>
## :bookmark: Sobre 

Exemplo de um pequeno projeto Java utilizando spring. 
Onde desenvolvi um CRUD de usuários com permissões de acesso, token JWT testes unitários, no padrão API RESTful.

<a id="tecnologias-utilizadas"></a>
## :computer: Tecnologias Utilizadas

- Spring-boot v2.5.6;
- Docker v20.10.11;
- Java 17;
- Swagger;
- Banco de dados: MariaDB: 10.6.1;
- Testes unitários: JUnity;
- Autenticação: JWT;

## :rocket: Resultado no Postman
Clique [**aqui**](./postman/Example_CRUD_API.postman_collection.json) para acessar o aquivo `json` da coleção do postman.

<a id="como-usar"></a>
## :fire: Como usar

> Caso não tiver o maven instalado localmente substitua `mvn` por `mvnw`

- Clone esse repositório: `git clone https://github.com/diogobarreiros/spring-example-crud.git`
- Instale as dependências: `mvn install`
- Subir imagem do banco de dados: `docker-compose up -d`
- Rode a aplicação: `mvn spring-boot:run`
- Acesse a api pela seguinte URL: [http://localhost:8080/documentation/swagger-ui/#/](http://localhost:8080/documentation/swagger-ui/#/)
- Para buildar para produção: `mvn clean package`

## :page_with_curl: Variáveis de ambiente

| **Descrição**                               | **parâmetro**                          | **Valor padrão**            |
| ------------------------------------------- | -------------------------------------- | --------------------------- |
| porta da aplicação                          | `SERVER_PORT`                          | 8080                        |
| url do banco                                | `DB_URL`                               | localhost:3306/example_crud |
| nome de usuário (banco)                     | `DB_USERNAME`                          | root                        |
| senha do usuário (banco)                    | `DB_PASSWORD`                          | root                        |
| mostrar sql na saida                        | `DB_SHOW_SQL`                          | false                       |
| máximo de conexões com o banco              | `DB_MAX_CONNECTIONS`                   | 5                           |
| valor do secret na geração dos tokens       | `TOKEN_SECRET`                         | secret                      |
| tempo de expiração do token em horas        | `TOKEN_EXPIRATION_IN_HOURS`            | 24                          |
| tempo de expiração do refresh token em dias | `REFRESH_TOKEN_EXPIRATION_IN_DAYS`     | 7                           |

> são definidas em: [**application.properties**](./src/main/resources/application.properties)

<a id="testes"></a>
## :heavy_check_mark: Testes

- Para rodar os testes unitários rode o seguinte comando: `mvn test`

---

<h4 align="center">
    Feito por <a href="https://www.linkedin.com/in/diogo-barreiros-b2a96836/" target="_blank">Diogo Barreiros</a>
</h4>
© 2021 GitHub, Inc.
Terms
Privacy
Security
Status
Docs
Contact GitHub
Pricing
AP
