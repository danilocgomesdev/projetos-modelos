# Jobs

Job que executa de tempo em tempo para fazer a leitura dos arquivos de retorno da CIELO.
Com isso e realizado as alterações, conforme as informações presente no arquivo.
Para obter mais informações sobre como funciona o arquivo de retorna da CIELO consultar a documentação.
[readme-uploads/CIELOExtratoEletronicoManualVersão13.pdf](readme-uploads/CIELOExtratoEletronicoManualVersão13.pdf)

O arquivo de leitura e obtido apatia da pasta, **_\\\\PRDLNXAPL030.fieg.com.br\cr5-cielo\cielo_**.
Após a leitura do arquivo ele e movido para a pasta **_\\\\PRDLNXAPL030.fieg.com.br\cr5-cielo\cielo_bkp_**.
Estes arquivos são colocados manualmente diariamente pela GEFIN na pasta.

### Recursos mínimos necessários para o projeto

```
- Java 17
- Maven 3.8.1
```

### Compilar projeto em modo dev

Você pode apenas rodar esse script

```shell script
./mvnw compile quarkus:dev
```

> **_NOTE:_** O projeto irá rodar no http://localhost:9090

### Para rodar a aplicação por linha de comando

Basta usar os case usado na classe Main.java

```shell script
./mvn quarkus:dev -Dquarkus.args=<args>
```

Comandos disponíveis:

```
-executaConciliacao
```
