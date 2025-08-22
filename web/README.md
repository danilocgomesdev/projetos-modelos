# Web

## Projeto Frontend

### Tecnologias utilizadas:

- **Node.js**: Versão 20 ou superior.
- **React Js com Typescript**: Biblioteca JavaScript para construção de interfaces de usuário com a adição de Typescript para tipagem estática.
- **Chakra UI**: Uma biblioteca de componentes UI simples, modular e acessível que proporciona uma experiência de desenvolvimento mais eficiente e um design agradável.
- **Vite**: Utilizado para criar o projeto, o Vite é uma ferramenta de construção moderna que oferece uma inicialização mais rápida e um desenvolvimento mais eficiente.
- **Yarn**: Gerenciador de pacotes que permite instalar, atualizar e gerenciar dependências de projetos.

### Configuração inicial:

Se você ainda não tem o Yarn instalado, instale-o globalmente usando npm com o seguinte comando:

```shell
npm install --global yarn
```

Na raiz do projeto, execute o seguinte comando para instalar todas as dependências necessárias:

```shell
yarn
```

Executar o projeto em modo de desenvolvimento:

```shell
yarn dev
```

É importante garantir que seu código esteja de acordo com os padrões do projeto. Execute o comando abaixo para construir o projeto e identificar quaisquer avisos ou erros, antes de comitar suas modificações, execute o seguinte comando e corriga quaisquer warnings/erros:

```shell
yarn buid
```

# Devspace

Este projeto está configurado para fazer o build de duas formas:

## Build local

É feito o build dos pacotes na máquina do desenvolvedor e, em seguida são enviados ao servidor Devspace para compilar a imagem Docker. Dessa forma a publicação no Devspace acontecerá de forma mais rápida.

Execute os seguintes comandos:

Compile o projeto:

```shell
yarn run build:devspace
```

Faça o Build a partir dos pacotes locais:

```shell
devspace run local
```

## Build Devspace

O build dos pacotes e a compilação da imagem Docker é realizado completamente no servidor Devspace.

```shell
devspace run deploy-dev
```

## Padrões de Colunas dos campos em tela

- Todas as telas de consultas tem ao todo 12 colunas em cada tamanho:
  Base -> mobile --> (esse não precisa preocupar que já é o default)
  Médio -> tablet
  Large -> desktop
  Obs: Basta fazer as contas para que no tamanho medio e large sempre fica ocupando a tela inteira.

- Todas as telas de formulário tem 4 colunas em cada tamanho e segue as mesmas regras das telas de consulta.
