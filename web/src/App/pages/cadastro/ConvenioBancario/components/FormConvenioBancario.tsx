import {
  Box,
  Button,
  Center,
  Checkbox,
  Flex,
  Grid,
  GridItem,
  Input,
  Tab,
  TabList,
  TabPanel,
  TabPanels,
  Tabs,
  Text,
  useColorModeValue,
} from "@chakra-ui/react";
import { FiCheckCircle } from "react-icons/fi";
import { ContaCorrenteDTO } from "../../../../../models/DTOs/AgenciaBancoConta/ContaCorrenteDTO";
import {
  FaixaNossoNumeroDTO,
  carteiraConvenioBancarioValues,
  sistemaBancarioValues,
} from "../../../../../models/DTOs/ConvenioBancario";
import { InputForm } from "../../../../components/InputControl";
import { SelectForm } from "../../../../components/SelectControl";
import { TabelaCR5 } from "../../../../components/Tabelas";

interface FormConvenioBancarioProps {
  onOpenContaCorrente: () => void;
  contaCorrenteFiltrada: ContaCorrenteDTO | undefined;
  onAtivarFaixaNossoNumero: ((faixa: FaixaNossoNumeroDTO) => void) | undefined;
  onEditFaixaNossoNumero: ((faixa: FaixaNossoNumeroDTO) => void) | undefined;
  onCriarFaixaNossoNumero: () => void;
  faixasNossoNumero: FaixaNossoNumeroDTO[] | undefined;
  editando: boolean;
  aceite: boolean;
  setAceite: (val: boolean) => void;
  unidadeCentralizadora: boolean;
  setUnidadeCentralizadora: (val: boolean) => void;
  editFaixaNossoNumeroLoading: boolean;
}

export function FormConvenioBancario({
  onOpenContaCorrente,
  contaCorrenteFiltrada,
  onAtivarFaixaNossoNumero,
  onEditFaixaNossoNumero,
  onCriarFaixaNossoNumero,
  faixasNossoNumero,
  editando,
  aceite,
  setAceite,
  unidadeCentralizadora,
  setUnidadeCentralizadora,
}: FormConvenioBancarioProps) {
  return (
    <>
      <Grid w="100%" templateColumns={{ base: "100%", md: "repeat(4, 1fr)" }} mt={4}>
        <InputForm name="numero" size="sm" label="Número Convênio" numeroColunasMd={2} />
        <SelectForm
          name="sistemaBancario"
          label="Conta Caixa"
          aceitaVazio={false}
          numeroColunasMd={2}
          options={sistemaBancarioValues.map((sistema) => ({
            label: sistema.toString(),
            value: sistema.toString(),
          }))}
        />

        <InputForm name="nomeCedente" size="sm" label="Nome Cedente" numeroColunasMd={2} />
        <InputForm name="localPagamento" size="sm" label="Local de Pagamento" numeroColunasMd={2} />

        <SelectForm
          name="carteira"
          label="Carteira"
          aceitaVazio={false}
          options={carteiraConvenioBancarioValues.map((sistema) => ({
            label: sistema.toString(),
            value: sistema.toString(),
          }))}
          numeroColunasMd={2}
        />
        <GridItem colSpan={{ base: 1, md: 2 }} m={1}>
          <Checkbox
            size="md"
            mt={{ base: 4, md: 8 }}
            borderRadius={3}
            isChecked={unidadeCentralizadora}
            onChange={(e) => setUnidadeCentralizadora(e.target.checked)}
          >
            Unidade Centralizadora
          </Checkbox>
        </GridItem>
      </Grid>
      <Tabs size="sm" mt={4} variant="enclosed">
        <TabList
          display="flex"
          flexDirection="row"
          flexWrap={{ base: "wrap", md: "nowrap" }}
          justifyContent={{ base: "center", md: "flex-start" }}
          p={2}
        >
          <Tab fontSize={"sm"}>Conta Corrente</Tab>
          <Tab fontSize={"sm"}>Aceite / Índice</Tab>
          <Tab fontSize={"sm"}>Mensagens</Tab>
          <Tab fontSize={"sm"}>Nosso Número</Tab>
        </TabList>

        <TabPanels mt="4">
          <TabPanel tabIndex={2}>
            <Grid templateColumns={{ base: "1fr", md: "25% 1fr" }} m={2}>
              <GridItem colSpan={{ base: 1, md: 1 }} m={1}>
                <Text fontSize="sm">Conta Corrente / Operação / CC / DV:</Text>
              </GridItem>

              <GridItem colSpan={{ base: 1, md: 1 }}>
                <Grid
                  templateColumns={{ base: "1fr", md: "repeat(12, 1fr)", lg: "repeat(24, 1fr)" }}
                >
                  <GridItem colSpan={{ base: 12, md: 4, lg: 4 }} m={1}>
                    <Input
                      size="sm"
                      disabled={true}
                      defaultValue={contaCorrenteFiltrada?.id ?? ""}
                      border="1px"
                      borderColor={useColorModeValue("black", "gray.100")}
                    />
                  </GridItem>
                  <GridItem m={2} display={{ base: "none", md: "block" }}>
                    <Text>/</Text>
                  </GridItem>

                  <GridItem colSpan={{ base: 12, md: 3, lg: 3 }} m={1}>
                    <Input
                      size="sm"
                      disabled={true}
                      defaultValue={contaCorrenteFiltrada?.numeroOperacao ?? ""}
                      border="1px"
                      borderColor={useColorModeValue("black", "gray.100")}
                    />
                  </GridItem>
                  <GridItem m={2} display={{ base: "none", md: "block" }}>
                    <Text>/</Text>
                  </GridItem>

                  <GridItem colSpan={{ base: 12, md: 4, lg: 5 }} m={1}>
                    <Input
                      size="sm"
                      disabled={true}
                      defaultValue={contaCorrenteFiltrada?.numeroConta ?? ""}
                      border="1px"
                      borderColor={useColorModeValue("black", "gray.100")}
                    />
                  </GridItem>
                  <GridItem m={2} display={{ base: "none", md: "block" }}>
                    <Text>/</Text>
                  </GridItem>

                  <GridItem colSpan={{ base: 12, md: 3, lg: 3 }} m={1}>
                    <Input
                      size="sm"
                      disabled={true}
                      defaultValue={contaCorrenteFiltrada?.digitoConta ?? ""}
                      border="1px"
                      borderColor={useColorModeValue("black", "gray.100")}
                    />
                  </GridItem>
                  <GridItem colSpan={{ base: 12, md: 12, lg: 6 }} m={1}>
                    <Button
                      onClick={onOpenContaCorrente}
                      width="100%"
                      size={"sm"}
                      colorScheme="gray"
                    >
                      Selecionar
                    </Button>
                  </GridItem>
                </Grid>
              </GridItem>
              <GridItem colSpan={{ base: 1, md: 1 }} m={1}>
                <Text fontSize="sm">Agência:</Text>
              </GridItem>

              <GridItem colSpan={{ base: 1, md: 1 }}>
                <Grid
                  templateColumns={{ base: "1fr", md: "repeat(12, 1fr)", lg: "repeat(24, 1fr)" }}
                >
                  <GridItem colSpan={{ base: 12, md: 6, lg: 12 }} m={1}>
                    <Input
                      size="sm"
                      disabled={true}
                      defaultValue={contaCorrenteFiltrada?.agencia.id ?? ""}
                      border="1px"
                      borderColor={useColorModeValue("black", "gray.100")}
                    />
                  </GridItem>
                  <GridItem colSpan={{ base: 12, md: 6, lg: 12 }} m={1}>
                    <Input
                      size="sm"
                      disabled={true}
                      defaultValue={contaCorrenteFiltrada?.agencia.nome ?? ""}
                      border="1px"
                      borderColor={useColorModeValue("black", "gray.100")}
                    />
                  </GridItem>
                </Grid>
              </GridItem>
              <GridItem colSpan={{ base: 1, md: 1 }} m={1}>
                <Text fontSize="sm">Banco:</Text>
              </GridItem>

              <GridItem colSpan={{ base: 1, md: 1 }}>
                <Grid
                  templateColumns={{ base: "1fr", md: "repeat(12, 1fr)", lg: "repeat(24, 1fr)" }}
                >
                  <GridItem colSpan={{ base: 12, md: 6, lg: 12 }} m={1}>
                    <Input
                      size="sm"
                      disabled={true}
                      defaultValue={contaCorrenteFiltrada?.agencia.banco.id ?? ""}
                      border="1px"
                      borderColor={useColorModeValue("black", "gray.100")}
                    />
                  </GridItem>
                  <GridItem colSpan={{ base: 12, md: 6, lg: 12 }} m={1}>
                    <Input
                      size="sm"
                      disabled={true}
                      defaultValue={contaCorrenteFiltrada?.agencia.banco.nome ?? ""}
                      border="1px"
                      borderColor={useColorModeValue("black", "gray.100")}
                    />
                  </GridItem>
                </Grid>
              </GridItem>
            </Grid>
          </TabPanel>
          <TabPanel>
            <Grid templateColumns={{ base: "1fr", md: "repeat(3, 1fr)" }} m={4}>
              <GridItem colSpan={{ base: 1, md: 1 }} display="flex" alignItems="center">
                <Checkbox
                  size="md"
                  borderRadius={3}
                  isChecked={aceite}
                  onChange={(e) => setAceite(e.target.checked)}
                >
                  Aceite
                </Checkbox>
              </GridItem>
              <InputForm name="indiceMulta" size="sm" label="Multa %" />
              <InputForm name="indiceJuros" size="sm" label="Juros ao dia %" />
            </Grid>
          </TabPanel>
          <TabPanel>
            <Grid templateColumns={{ base: "1fr", lg: "repeat(4, 1fr)" }} m={4}>
              <InputForm name="observacao1" size="sm" label="Observacao 1" numeroColunasMd={2} />
              <InputForm name="observacao2" size="sm" label="Observacao 2" numeroColunasMd={2} />
              <InputForm name="observacao3" size="sm" label="Observacao 3" numeroColunasMd={2} />
              <InputForm name="observacao4" size="sm" label="Observacao 4" numeroColunasMd={2} />
              <InputForm name="observacao4" size="sm" label="Observacao 5" numeroColunasMd={2} />
            </Grid>
          </TabPanel>
          <TabPanel>
            {editando && (
              <Center mb={10}>
                <Text fontWeight="bold" fontSize="sm">
                  Alterações nas faixas de nosso número são refletidas de imediato e não precisam
                  ser salvas
                </Text>
              </Center>
            )}
            <Box overflowX="auto">
              <TabelaCR5
                cabecalhos={[
                  { titulo: "Número Inicial", dadoBuilder: (faixa) => faixa.nossoNumeroInicial },
                  { titulo: "Número Final", dadoBuilder: (faixa) => faixa.nossoNumeroFinal },
                  { titulo: "Número Atual", dadoBuilder: (faixa) => faixa.nossoNumeroAtual },
                  {
                    titulo: "Ativo/Inativo",
                    dadoBuilder: (faixa) => (faixa.ativo ? "Ativo" : "Inativo"),
                  },
                ]}
                data={faixasNossoNumero}
                error={undefined}
                isError={false}
                isFetching={!faixasNossoNumero}
                keybuilder={(faixa) => faixa.id}
                onEdit={onEditFaixaNossoNumero}
                customAction={
                  onAtivarFaixaNossoNumero
                    ? [
                        {
                          title: "Ativar Faixa",
                          icon: (faixa) => (faixa.ativo ? undefined : <FiCheckCircle />),
                          action: () => onAtivarFaixaNossoNumero,
                        },
                      ]
                    : undefined
                }
              />
            </Box>
            <Flex placeItems="flex-end" width="100%" flexDir="column" mt={10}>
              <Button
                colorScheme="blue"
                onClick={onCriarFaixaNossoNumero}
                disabled={!editando && (faixasNossoNumero?.length ?? 0) > 0}
              >
                Criar Faixa Nosso Número
              </Button>
            </Flex>
          </TabPanel>
        </TabPanels>
      </Tabs>
    </>
  );
}
