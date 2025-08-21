# E-mail Services

Este projeto é uma aplicação Quarkus que fornece serviços de envio de e-mails.

### Executando a aplicação em modo de desenvolvimento

Você pode executar sua aplicação em modo de desenvolvimento, que permite live coding, usando:

```shell script
./mvnw compile quarkus:dev

```shell script
./mvnw compile quarkus:dev
```

> **_NOTA:_** A Url local: <http://localhost:8084/email-services/api>.



## Autenticação com Keycloak

A aplicação utiliza Keycloak para autenticação. As configurações do Keycloak podem ser encontradas no
arquivo `application.properties`. Certifique-se de configurar corretamente
o `client-id`, `auth-server-url` e outras propriedades relacionadas ao Keycloak.

## Documentação da API com Swagger

A documentação da API é gerada automaticamente pelo Swagger e pode ser acessada em `/email-services/docs`
quando a aplicação estiver em execução. Isso permite que você visualize e teste os endpoints da API de forma interativa.
