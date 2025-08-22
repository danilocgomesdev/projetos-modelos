# Webservices

Projeto criado como a nova versão modernizada do webservices.

Feito para ser consumido pelo novo front-end do (web-v2) através do auth-middleware e por outros serviços
internos.

Somente é aceita autenticação por meio do KeyCloak usando JWTs enviados no header `Authorization` como `Bearer ...`.
Autenticação básica está totalmente depreciada. O healm do KeyCloak é o healm de serviços.

Veja também a documentação do projeto auth-middleware, ela irá esclarecer bastante como funciona a parte de permissões.
Veja também a classe `RequestFilters` e anotação `@PermissaoNecessaria`

Feito em Java 21 e Quarkus 3.

Ver também https://docs.fieg.com.br/financeiro/v2/plano_de_migracao

## Arquitetura

O projeto é separado em três grandes pastas:

- `core`: classes que lidam com o funcionamento geral da aplicação, como filtros HTTP, anotações, exceções gerais,
  classes de utilidade, producers, etc;
- `externos`: classes que dizem respeito a sistemas externos ao CR5, como Cadin e Protheus;
- `modulos`: classes específicas das entidades e regras de negócio do CR5.

Dentro de `externos` e `modulos`, são criadas pastas referêntes a entidades, como `cobrancacliente` e, dentro das pastas
das entidades, são separadas as classes por funcionalidade:

- `client`: classes que fazem comunicação com serviços externos por HTTP ou outro protocolo;
- `dto`: classes que servem como entrada ou saída de dados do sistema, geralmente como body de requisições ou de
  respostas HTTP;
- `enums`: enums estritamente ligados aquela entidade e seus conversores;
- `mapper`: classes responsáveis em mapear os dtos de/para a entidade;
- `model`: a classe que modela a tabela do banco propriamente dita (`@Entity`);
- `repository`: classes resposáveis por acessas o banco de dados (podendo ser o SQL ou outro) para obter a entidade;
- `service`: classes responsáveis pelas regras de negócio daquela entidade;
- `rest`: classes onde são definidos os endpoints que fazem jus aquela entidade.

### Sobre DTOs

Nos DTOs, quando possível usar as anotações do lombok para reduzir a quantidade de código boilerplate. Manter todas as
propridades privadas e deixar que sejam acessadas pelos getters e setters criados pelo lombok ou deixar todos públicos e
não criar getters e setters. Dê preferência à anotação `@Data` quando a classe não herdar de outra, já que ela se
equivale a usar as anotações `@Setter`, `@RequiredArgsConstructor`, `@ToString` e `@EqualsAndHashCode`.

Lembre-se que todo DTO deve ter um construtor sem argumentos, implícito ou explícito.

### Sobre mappers

No momento é usada a biblioteca `modelmapper` para gerar os mappers, porém, como isso pode mudar no futuro, foram
definidas interfaces personalizadas para modelar mapeadores: `Mapper<T, R>` (responsável por criar um R a partir de um
T) e `Setter<T, R>` (responsável por mapear todas as propridades adequadas de R em um T existente). Para definir um
mapper, basta definir uma função que retorna uma dessas interfaces e anotá-la com `@Singleton` e `@Produces` e então
injetar onde deseja usar com `@Inject`. O CDI sabe qual Bean injetar com base nos tipos genéricos das interfaces. Veja
exemplos mais complexos e leia a documentação da biblioteca para saber como mapear entidades com grandes
diferenças/transformações necessárias.

De preferência injetar em clients ou controllers, mas podem ser injetados em services também.

Fique atento ao mapear propriedades Lazy de entidade, se certifique o mapper será executado num contexto que permita a
busca ou carregue-as manualmente antes de mapear.

### Sobre models

Preferir mapear colunas que possuem um número fixo de opções usando enums com a anotação `@Convert`, caso não seja
possível usar a anotação `@Enumerated`. Veja exemplos existes para seguir o padrão. Bons candidatos a esse tratamento
são colunas de status, de tipo, etc. Busque também transformar colunas char com `S` ou `N` ou similares em `Boolean`
usando `@Convert`.

Buscar anotar entidades relacionadas
com `fetch = FetchType.LAZY` a menos que seja uma entidade que quse sempre (90%+) das vezes iremos buscar de toda forma.
Você pode pré-buscar entidades Lazy usando `join fetch` no HQL.

### Sobre repositories

NUNCA injetar um service, client ou mesmo outro repository num repository, fazer isso quebra a hierarquia de
resposabilidades e tende a criar dependências circulares. Faça a lógica que precisa de classes de outras entidades
dentro de um service, tomando cuidado para não criar dependências circulares.

As classes concretas dos repositórios devem implementar `PanacheRepositoryBase<T, K>`, dê preferência aos seus métodos
já definidos como `find`, `findByIdOptional` etc na hora de implementar os métodos definidos na interface. Pesquisas
paginadas devem retornar `PageResult<T>`.

### Sobre services

Evitar injetar respositórios de outras entidades em um service, injete seu service no lugar. Como essas são as classes
das regras de negócio, buscar escrever testes para gantir que o código atual funciona e futuras mudanças não irão
quebrá-lo.

Marcar quais métodos necessitam de transação também na interface do servido com `@Transactional`, isso é importante para
mostrar que o método abre/precisa de uma transação na própria assinatura. Também evitar retornar diretamente entidades
de métodos que abrem transações, pois, após fechada, não é mais possível caregar valores lazy das entidades. No lugar
disso, faça a função receber um mapper, para que o usuário possa transformar/mapear a entidade ainda no contexto de uma
transação, caso necessário. Ex:

```
  @Transactional
  <T> T salvaNovaAgencia(CriarAgenciaDTO criarAgenciaDTO, Function<Agencia, T> mapper)
```

Caso seja necessário obter a agência em si, é possível chamar:

```
  Agencia agencia = agenciaService.salvaNovaAgencia(criarAgenciaDTO, Function.identity());
```

Você pode criar um método default na interface do Service para facilitar esse padrão, Ex:

```
  @Transactional
  <T> T updatePessoaCr5(Integer idPessoa, AlteraPessoaCr5DTO alteraPessoaCr5DTO, Function<PessoaCr5, T> mapper);

  default PessoaCr5 updatePessoaCr5(Integer idPessoa, AlteraPessoaCr5DTO alteraPessoaCr5DTO) {
      return updatePessoaCr5(idPessoa, alteraPessoaCr5DTO, Function.identity());
  }
```

Note que pode ser necessário carregar entidades lazy antes de usar um mapeamento.

### Sobre rest (controllers)

Não usar try-catch nos Controllers/Rest, pois existe a classe `ApplicationExceptionMapper` que captura todas as exceções
lançadas dentro do contexto
de uma requisição e as mapeia para uma interface comum de erros. Se você capturar uma exceção e montar uma response de
erro manualmente, estará quebrando esse padrão.

Sempre retornar uma classe que representa o objeto a ser retornado dos controllers, e não `Response`. Isso é feito
tanto para manter o padrão do ponto anterior como para deixar claro o que será retornado daquele endpoint, tanto para
desenvolvedores quanto para quem analisar o swagger. Caso deseje alterar headers da resposta, fazer por meio de
anotações. Ex:

```
    @POST
    @Operation(summary = "Cria nova conta corrente")
    @ResponseStatus(HttpStatus.SC_CREATED)
    public ContaCorrenteDTO criarContaCorrente(@Valid @RequestBody CriarContaCorrenteDTO criarContaCorrenteDTO) {
        requestInfoHolder.getIdOperador().ifPresent(criarContaCorrenteDTO::setIdOperadorInclusao);
        return contaCorrenteService.criaContaCorrente(criarContaCorrenteDTO, this::mapeiaCarregandoEntidades);
    }
```

Sempre anotar classes controller com `@Authenticated` para garantir que somente requisições com o apropriado token
sejam aceitas.

Sempre anotar classes e/ou métodos de controller com `@PermissaoNecessaria(value = {Acessos.<acesso apropriado>})`. Caso
qualquer usuário possa ter acesso àquela funcionalidade, anotar com `@PermissaoNecessaria` sem nenhum argumento e
escrever um comentário para deixar claro. Nota: essa anotação serve de validação apenas para requisições vindas do
front-end (passaram pelo auth-middleware).

Anotar a classe com `@Produces(MediaType.APPLICATION_JSON)` e `@Consumes(MediaType.APPLICATION_JSON)`, anotando métodos
individuais com outros _media types_ conforme necessário.

### Sobre classes de utilidade (utils)

Nunca colocar regras de negócio específicas do CR5 numa classe de utilidade, evitar também usar muitas bibliotecas
específicas do projeto. Classes de utilidade não devem ser beans, ou seja, não use `@ApplicationScoped` ou `@Inject`em
classes de utilidade. Isso é feito para facilitar que essas classes possam ser reutilizadas em outros projetos e
contextos, elas devem ser o mais genéricas e reutilizáveis quanto possível.

Busque sempre escrever testes para as classes de utilidade.

Buscar nomear as classes como `UtilQualquerCoisa` (Util no início) para facilitar busca e autocomplete.

## Recomendações gerais e padrões

- Não usar a classe `Date`, pois ela está sendo depreciada, especialmente pela dificuldade de trabalhar com ela
  (adicionar dias, calcular diferenças, ...) e seu comportamento com fusos horários. Sempre usar no lugar `LocalDate`
  quando não importar o horário e `LocalDateTime` quando importar;

- Toda saída de datas é feito no formato de Unix Timestamp, que, por padrão, sempre se refere ao fuso horário UTC. Isso
  é tratado na classe `SerializacaoDeDataTimestamp`. Já para a entrada de dados, pode ser no formato Unix Timestamp ou
  ISO_LOCAL_DATE(\_TIME) como "2023-08-01" ou "2023-08-01T08:33:36.937", nesse caso considremos que as datas já estão no
  fuso do servidor (-3);

- Sempre declarar uma interface pública para Services, Repositories e afins e sua implementação deve ser package-private
  (sem modificador de visibilidade). Isso é feito para evitar que a implementação seja injetada diretamente e suas
  variáveis usadas. Exemplo:

```
  @ApplicationScoped
  class ParcelaCadinServiceImpl implements ParcelaCadinService {
  ...
  }
```

- Em métodos que podem ou não retornar um objeto (por ele não existir, por exemplo), especialmente em repositories,
  preferir retornar `Optional<Classe>` no lugar de retornar null ou lançar exceção. O propósito disso é deixar a decisão
  de usar null, um valor padrão ou lançar exceção explícita para quem for chamar a função;

- Caso queria obter o id do operador ou da pessoa que fez a requisição, para gravar num campo id_operador_alteração ou
  similar, injetar a classe `RequestInfoHolder` NO CONTROLLER (pois é necessário um contexto de requisição) e usar os
  métodos `getIdPessoaOu`, `getIdOperadorOu`, `getIdPessoa`, `getIdOperador`, etc para obter essas informações. Note que
  essas informações só estaram presentes se a requisição vier do auth-middleware, e, é necessário serem
  informados em algum outro parâmetro da requisição para que serviços possam usar o endpoint, a menos que o endpoint
  tenha propósito de ser usado apenas pelo front, sempre dando prioridade à informação do `RequestInfoHolder`;

- Anotar métodos claros que são muito utilizados
  com `@CacheResultcacheName = "cr5WebservicesV2-{nomeDaClasse}-{nomeDoMetodo}-cache")`. Não usamos o cache pelo Redis
  pois infelizmente ele não funciona muito bem com genéricos, tornando incoveniente trabalhar com coleções. Caso isso
  mude no futuro, podemos simplemente instalar o plugin `quarkus-redis-cache` e o cache será guardado no Redis
  automaticamente (será necessário mudar as configurações). Lembre-se que cache serve apenas quando o método é chamado
  com os mesmos parâmetros e retorna quase sempre as mesmas informações.

# Devspace

Este projeto está configurado para fazer o build de duas formas:

## Build local

É feito o build dos pacotes na máquina do desenvolvedor e, em seguida são enviados ao servidor Devspace para compilar a
imagem Docker. Dessa forma a publicação no Devspace acontecerá de forma mais rápida.

Execute os seguintes comandos:

Compile o projeto:

```shell
mvnw -T 1C package --batch-mode --errors --fail-at-end --show-version -DinstallAtEnd=true -DdeployAtEnd=true -Dverify=false -Pgeapp -DskipTests -Dquarkus.package.type=fast-jar -Dquarkus.profile=devspace -Dorg.slf4j.simpleLogger.log.org.apache.maven.cli.transfer.Slf4jMavenTransferListener=WARN
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
