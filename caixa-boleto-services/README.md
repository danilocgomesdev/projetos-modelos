# caixa-boleto

This project uses Quarkus, the Supersonic Subatomic Java Framework.

If you want to learn more about Quarkus, please visit its website: https://quarkus.io/ .

## Running the application in dev mode

You can run your application in dev mode that enables live coding using:
```shell script
./mvnw compile quarkus:dev
```

> **_NOTE:_**  Quarkus now ships with a Dev UI, which is available in dev mode only at http://localhost:8080/q/dev/.

## Packaging and running the application

The application can be packaged using:
```shell script
./mvnw package
```
It produces the `quarkus-run.jar` file in the `target/quarkus-app/` directory.
Be aware that it’s not an _über-jar_ as the dependencies are copied into the `target/quarkus-app/lib/` directory.

The application is now runnable using `java -jar target/quarkus-app/quarkus-run.jar`.

If you want to build an _über-jar_, execute the following command:
```shell script
./mvnw package -Dquarkus.package.type=uber-jar
```

The application, packaged as an _über-jar_, is now runnable using `java -jar target/*-runner.jar`.

## Creating a native executable

You can create a native executable using: 
```shell script
./mvnw package -Dnative
```

Or, if you don't have GraalVM installed, you can run the native executable build in a container using: 
```shell script
./mvnw package -Dnative -Dquarkus.native.container-build=true
```

You can then execute your native executable with: `./target/caixa-boleto-1.0.0-SNAPSHOT-runner`

If you want to learn more about building native executables, please consult https://quarkus.io/guides/maven-tooling.

## Related Guides

- Quarkus CXF ([guide](https://quarkiverse.github.io/quarkiverse-docs/quarkus-cxf/dev/reference/extensions/quarkus-cxf.html)): Core capabilities for implementing SOAP clients and JAX-WS services


# Devspace

Este projeto está configurado para fazer o build de duas formas:

## Build local

É feito o build dos pacotes na máquina do desenvolvedor e, em seguida são enviados ao servidor Devspace para compilar a imagem Docker. Dessa forma a publicação no Devspace acontecerá de forma mais rápida.

Execute os seguintes comandos:

Compile o projeto:

``` shell
mvnw -T 1C package --errors --fail-at-end --show-version -DinstallAtEnd=true -DdeployAtEnd=true -Dverify=false -Pgeapp -DskipTests -Dquarkus.package.type=fast-jar -Dquarkus.profile=devspace
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