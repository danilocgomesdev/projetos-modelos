# auth-middleware

Projeito feito com Quarkus 3. Para documentação do framework e os seus plugins, visite: https://quarkus.io/ .

## Objetivo do projeto

Esse projeto visa servir como "ponte" entre o healm de usuuários (login corporativo) e o healm de serviços, ou seja, ele
recebe requisições diretamente de um frontend, valida se ela tem um token válido do healm de usuários e então a redireciona
para um back-end (serviço) com a sua própria autenticação, mas ainda com informaçõs de quem fez a requisição original.

Essa informação é inseriada no header `operadorId`, que contém o operador id de quem fez a requisição originalmente.
Também é adicionado um header `uuid` que contém um identificador único para ajudar a associar de onde veio uma requisição
ao analisar o back-end.

Além disso, o projeto usa o `idSistema` configurado e o `idOperador` de quem fez a requisição para buscar os direitos do
operador no sistema no `compartilhad-service` e armazena essa informação no redis com essas informações como chave e com
mesmo tempo de expiração do Token. O serviço redirecionado pode então usar esses direitos para decidir se esse operador
tem direitos para fazer tal requisição sem precisar ele mesmo buscar e cachear as informações.

Existe apenas um endpoint que não é redirecionado: `/verifica-permissoes/invalida-caches`. Esse endpoint simplesmente
limpa as informações de direito associadas ao operador que fez a requisição e ao sistema configurado.

Além disso, existe o endpoint de healthcheck, que forcenece informações do sistema e se o Redis está funcionando: `/q/health`
ver as classes no pacote `fieg.core.health`.

Para mais informações, ver [a documentação](https://docs.fieg.com.br/financeiro/cr5-v2/auth-middleware) e veja a classe `fieg.core.filters.RequestFilters`.

## Configurações importantes

> `auth-middleware.chave-jasypt`: chave usada para criptografar e descriptogravar o `operadorId`

> `auth-middleware.id-sistema-redirecionado`: id do sistema para quais as requisições serão redirecionadas. Importante,
pois temos direitos diferentes para cada sistema

> `auth-middleware.url-redirecionamento`: url do sistema redirecionado
 
Inicialmetne o projeto foi feito para redirecionar para o `cr5-webservices-v2`, mas mudando as configurações ele pode ser
usado para outros sistemas.

Ver `application.properties` para as outras configurações.


# Devspace

Este projeto está configurado para fazer o build de duas formas:

## Build local

É feito o build dos pacotes na máquina do desenvolvedor e, em seguida são enviados ao servidor Devspace para compilar a imagem Docker. Dessa forma a publicação no Devspace acontecerá de forma mais rápida.

Execute os seguintes comandos:

Compile o projeto:
``` shell
mvnw -T 1C package --batch-mode --errors --fail-at-end --show-version -DinstallAtEnd=true -DdeployAtEnd=true -Dverify=false -Pgeapp -DskipTests -Dquarkus.package.type=fast-jar -Dquarkus.profile=devspace -Dorg.slf4j.simpleLogger.log.org.apache.maven.cli.transfer.Slf4jMavenTransferListener=WARN
```

Faça o Build a partir dos pacotes locais:
``` shell
devspace run local
```

## Build Devspace

O build dos pacotes e a compilação da imagem Docker é realizado completamente no servidor Devspace.
``` shell
devspace run deploy-dev
```