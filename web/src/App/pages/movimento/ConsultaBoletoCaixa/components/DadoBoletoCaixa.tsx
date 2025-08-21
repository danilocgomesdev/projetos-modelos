import {
  AbsoluteCenter,
  Accordion,
  AccordionButton,
  AccordionIcon,
  AccordionItem,
  AccordionPanel,
  Box,
  Divider,
  Grid,
  GridItem,
  useColorModeValue,
} from "@chakra-ui/react";

import {
  formatarDataBrasil_2,
  formatarMoedaBrasil,
  mascaraCEP,
  mascaraCpfCnpj,
} from "../../../../../utils/mascaras";

import { ConsultaBoletoCaixaResponseDTO } from "../../../../../models/DTOs/ConsultaBoletoCaixa/ConsultaBoletoCaixaResponseDTO";

import { DadosNaoEncontrados } from "../../../../components/DadosNaoEncontrados";

import { Loading } from "../../../../components/Loading";

import { TextControl } from "../../../../components/TextControl/TextControl";

interface DadoBoletoCaixaProps {
  dadosCaixa: ConsultaBoletoCaixaResponseDTO | undefined;
  isFetchingCaixa: boolean;
  isErrorCaixa: boolean;
}

export function DadoBoletoCaixa({
  dadosCaixa,
  isFetchingCaixa,
  isErrorCaixa,
}: DadoBoletoCaixaProps) {
  return (
    <Accordion defaultIndex={[0, 1]} allowMultiple>
      <AccordionItem>
        <h2>
          <AccordionButton bg={useColorModeValue("light.cabecalho", "dark.cabecalho")}>
            <Box as="span" flex="1" textAlign="center" fontSize="sm" fontWeight="bold">
              Dados do Boleto Caixa
            </Box>
            <AccordionIcon />
          </AccordionButton>
        </h2>
        {isFetchingCaixa ? (
          <Loading mensagem="Buscando dados na CAIXA!" altura="50vh" />
        ) : isErrorCaixa && !dadosCaixa ? (
          <DadosNaoEncontrados
            altura="50vh"
            isError
            mensagem="Não foi possível carregar a aplicação. Procure o suporte."
          />
        ) : (
          <AccordionPanel pb={4} w="100%">
            <Box maxH="70vh" w="100%">
              <Grid w="100%" templateColumns={{ base: "¨100%", md: "repeat(12, 1fr)" }}>
                <TextControl
                  label="Número Documento:"
                  id="numeroDocumento"
                  naoEditavel
                  value={
                    dadosCaixa?.body.servicoSaida.dados?.consultaBoleto != null
                      ? dadosCaixa?.body.servicoSaida.dados.consultaBoleto.titulo.numeroDocumento
                      : "-"
                  }
                  numeroColunasMd={6}
                  numeroColunasLg={4}
                />

                <TextControl
                  label="Valor Pago:"
                  id="valorPago"
                  type="number"
                  naoEditavel
                  value={
                    dadosCaixa?.body.servicoSaida.dados?.consultaBoleto != null
                      ? formatarMoedaBrasil(
                          dadosCaixa?.body.servicoSaida.dados.consultaBoleto.titulo.valor?.toString() ??
                            0
                        )
                      : "-"
                  }
                  numeroColunasMd={6}
                  numeroColunasLg={4}
                />
                <TextControl
                  label="Valor Abatimento:"
                  id="valorAbatimento"
                  naoEditavel
                  value={
                    dadosCaixa?.body.servicoSaida.dados?.consultaBoleto != null
                      ? formatarMoedaBrasil(
                          dadosCaixa?.body.servicoSaida.dados.consultaBoleto.titulo.valorAbatimento?.toString() ??
                            0
                        )
                      : "-"
                  }
                  numeroColunasMd={6}
                  numeroColunasLg={4}
                />

                <TextControl
                  label="Data Emissão:"
                  id="dataEmissao"
                  naoEditavel
                  value={
                    dadosCaixa?.body.servicoSaida.dados?.consultaBoleto != null
                      ? formatarDataBrasil_2(
                          dadosCaixa?.body.servicoSaida.dados.consultaBoleto.titulo.dataEmissao
                        )
                      : "-"
                  }
                  numeroColunasMd={6}
                  numeroColunasLg={4}
                />

                <TextControl
                  label="Data Vencimento:"
                  id="dataVencimento"
                  naoEditavel
                  value={
                    dadosCaixa?.body.servicoSaida.dados?.consultaBoleto != null
                      ? formatarDataBrasil_2(
                          dadosCaixa?.body.servicoSaida.dados.consultaBoleto.titulo.dataVencimento
                        )
                      : "-"
                  }
                  numeroColunasMd={6}
                  numeroColunasLg={4}
                />

                <TextControl
                  label="Empresa:"
                  id="empresa"
                  naoEditavel
                  value={
                    dadosCaixa?.body.servicoSaida.dados?.consultaBoleto != null
                      ? dadosCaixa?.body.servicoSaida.dados.consultaBoleto.titulo
                          .identificacaoEmpresa === ""
                        ? "-"
                        : dadosCaixa?.body.servicoSaida.dados.consultaBoleto.titulo
                            .identificacaoEmpresa
                      : "-"
                  }
                  numeroColunasMd={6}
                  numeroColunasLg={4}
                />

                <TextControl
                  label="Situação:"
                  id="situacao"
                  naoEditavel
                  value={
                    dadosCaixa?.body.servicoSaida.dados?.controleNegocial != null
                      ? dadosCaixa?.body.servicoSaida.dados.controleNegocial.mensagens[0].retorno
                      : "-"
                  }
                  numeroColunasMd={12}
                  numeroColunasLg={12}
                />
              </Grid>

              <GridItem colSpan={{ base: 1, md: 4 }} mt={4}>
                <Box position="relative" p={4}>
                  <Divider />
                  <AbsoluteCenter
                    bg={useColorModeValue("light.text", "dark.primary")}
                    px="4"
                    fontSize={"sm"}
                  >
                    Responsável Financeiro
                  </AbsoluteCenter>
                </Box>
              </GridItem>

              <Grid w="100%" templateColumns={{ base: "¨100%", md: "repeat(12, 1fr)" }}>
                <TextControl
                  label="CPF/CNPJ:"
                  id="cpf"
                  naoEditavel
                  value={
                    dadosCaixa?.body.servicoSaida.dados?.consultaBoleto != null
                      ? dadosCaixa?.body.servicoSaida.dados.consultaBoleto.titulo.pagador.cpf !=
                        null
                        ? mascaraCpfCnpj(
                            dadosCaixa?.body.servicoSaida.dados.consultaBoleto.titulo.pagador.cpf
                          )
                        : mascaraCpfCnpj(
                            dadosCaixa?.body.servicoSaida.dados.consultaBoleto.titulo.pagador.cnpj
                          )
                      : "-"
                  }
                  numeroColunasMd={6}
                  numeroColunasLg={4}
                />
                <TextControl
                  label="Nome:"
                  id="nome"
                  naoEditavel
                  value={
                    dadosCaixa?.body.servicoSaida.dados?.consultaBoleto != null
                      ? dadosCaixa?.body.servicoSaida.dados.consultaBoleto.titulo.pagador.nome !=
                        null
                        ? dadosCaixa?.body.servicoSaida.dados.consultaBoleto.titulo.pagador.nome
                        : dadosCaixa?.body.servicoSaida.dados.consultaBoleto.titulo.pagador
                            .razao_SOCIAL
                      : "-"
                  }
                  numeroColunasMd={6}
                  numeroColunasLg={8}
                />
                <TextControl
                  label="Logradouro:"
                  id="logradouro"
                  naoEditavel
                  value={
                    dadosCaixa?.body.servicoSaida.dados?.consultaBoleto != null
                      ? dadosCaixa?.body.servicoSaida.dados.consultaBoleto.titulo.pagador.endereco
                          .logradouro
                      : "-"
                  }
                  numeroColunasMd={6}
                  numeroColunasLg={6}
                />
                <TextControl
                  label="Bairro:"
                  id="bairro"
                  naoEditavel
                  value={
                    dadosCaixa?.body.servicoSaida.dados?.consultaBoleto != null
                      ? dadosCaixa?.body.servicoSaida.dados.consultaBoleto.titulo.pagador.endereco
                          .bairro
                      : "-"
                  }
                  numeroColunasMd={6}
                  numeroColunasLg={6}
                />
                <TextControl
                  label="Cidade:"
                  id="cidade"
                  naoEditavel
                  value={
                    dadosCaixa?.body.servicoSaida.dados?.consultaBoleto != null
                      ? dadosCaixa?.body.servicoSaida.dados.consultaBoleto.titulo.pagador.endereco
                          .cidade
                      : "-"
                  }
                  numeroColunasMd={6}
                  numeroColunasLg={4}
                />
                <TextControl
                  label="UF:"
                  id="uf"
                  naoEditavel
                  value={
                    dadosCaixa?.body.servicoSaida.dados?.consultaBoleto != null
                      ? dadosCaixa?.body.servicoSaida.dados.consultaBoleto.titulo.pagador.endereco
                          .uf
                      : "-"
                  }
                  numeroColunasMd={6}
                  numeroColunasLg={4}
                />
                <TextControl
                  label="CEP:"
                  id="cep"
                  naoEditavel
                  value={
                    dadosCaixa?.body.servicoSaida.dados?.consultaBoleto != null
                      ? mascaraCEP(
                          dadosCaixa?.body.servicoSaida.dados.consultaBoleto.titulo.pagador.endereco
                            .cep
                        )
                      : "-"
                  }
                  numeroColunasMd={6}
                  numeroColunasLg={4}
                />
              </Grid>

              <Grid w="100%" templateColumns={{ base: "¨100%", md: "repeat(12, 1fr)" }} mt={4}>
                <GridItem colSpan={{ base: 1, md: 6, lg: 6 }} m={1} mb={0} bg={""}>
                  <GridItem colSpan={{ base: 1, md: 4 }} m={1}>
                    <Box position="relative" p={4}>
                      <Divider />
                      <AbsoluteCenter
                        bg={useColorModeValue("light.text", "dark.primary")}
                        px="4"
                        fontSize={"sm"}
                      >
                        Juros e Multa
                      </AbsoluteCenter>
                    </Box>
                  </GridItem>

                  <Grid w="100%" templateColumns={{ base: "¨100%", md: "repeat(12, 1fr)" }}>
                    <TextControl
                      label="Valor:"
                      id="Multa"
                      naoEditavel
                      value={
                        dadosCaixa?.body.servicoSaida.dados?.consultaBoleto != null
                          ? dadosCaixa?.body.servicoSaida.dados.consultaBoleto.titulo?.multa != null
                            ? formatarMoedaBrasil(
                                dadosCaixa?.body.servicoSaida.dados.consultaBoleto.titulo.multa.valor?.toString() ??
                                  0
                              )
                            : "-"
                          : "-"
                      }
                      numeroColunasMd={6}
                      numeroColunasLg={6}
                    />

                    <TextControl
                      label="Data:"
                      id="dataMulta"
                      naoEditavel
                      value={
                        dadosCaixa?.body.servicoSaida.dados?.consultaBoleto != null
                          ? dadosCaixa?.body.servicoSaida.dados.consultaBoleto.titulo?.multa != null
                            ? formatarDataBrasil_2(
                                dadosCaixa?.body.servicoSaida.dados.consultaBoleto.titulo.multa.data?.toString()
                              )
                            : "-"
                          : "-"
                      }
                      numeroColunasMd={6}
                      numeroColunasLg={6}
                    />
                    <TextControl
                      label="Percentual:"
                      id="percentualMulta"
                      naoEditavel
                      value={
                        dadosCaixa?.body.servicoSaida.dados?.consultaBoleto != null
                          ? dadosCaixa?.body.servicoSaida.dados.consultaBoleto.titulo?.multa != null
                            ? formatarMoedaBrasil(
                                dadosCaixa?.body.servicoSaida.dados.consultaBoleto.titulo.multa.percentual?.toString() ??
                                  0
                              )
                            : "-"
                          : "-"
                      }
                      numeroColunasMd={6}
                      numeroColunasLg={6}
                    />
                  </Grid>
                </GridItem>

                <GridItem colSpan={{ base: 1, md: 6, lg: 6 }} m={1} mb={0} bg={""}>
                  <GridItem colSpan={{ base: 1, md: 4 }} m={1}>
                    <Box position="relative" p={4}>
                      <Divider />
                      <AbsoluteCenter
                        bg={useColorModeValue("light.text", "dark.primary")}
                        px="4"
                        fontSize={"sm"}
                      >
                        Descontos
                      </AbsoluteCenter>
                    </Box>
                  </GridItem>

                  <Grid w="100%" templateColumns={{ base: "¨100%", md: "repeat(12, 1fr)" }}>
                    <TextControl
                      label="Valor:"
                      id="descontos"
                      naoEditavel
                      value={
                        dadosCaixa?.body.servicoSaida.dados?.consultaBoleto != null
                          ? dadosCaixa?.body.servicoSaida.dados.consultaBoleto.titulo?.descontos !=
                            null
                            ? formatarMoedaBrasil(
                                dadosCaixa?.body.servicoSaida.dados.consultaBoleto.titulo?.descontos[0].desconto.valor?.toString() ??
                                  0
                              )
                            : "-"
                          : "-"
                      }
                      numeroColunasMd={6}
                      numeroColunasLg={6}
                    />
                    <TextControl
                      label="Data:"
                      id="data"
                      naoEditavel
                      value={
                        dadosCaixa?.body.servicoSaida.dados?.consultaBoleto != null
                          ? dadosCaixa?.body.servicoSaida.dados.consultaBoleto.titulo?.descontos !=
                            null
                            ? formatarDataBrasil_2(
                                dadosCaixa?.body.servicoSaida.dados.consultaBoleto.titulo
                                  ?.descontos[0].desconto.data
                              )
                            : "-"
                          : "-"
                      }
                      numeroColunasMd={6}
                      numeroColunasLg={6}
                    />
                    <TextControl
                      label="Percentual:"
                      id="percentualDesconto"
                      naoEditavel
                      value={
                        dadosCaixa?.body.servicoSaida.dados?.consultaBoleto != null
                          ? dadosCaixa?.body.servicoSaida.dados.consultaBoleto.titulo?.descontos !=
                            null
                            ? formatarMoedaBrasil(
                                dadosCaixa?.body.servicoSaida.dados.consultaBoleto.titulo?.descontos[0].desconto.percentual?.toString() ??
                                  0
                              )
                            : "-"
                          : "-"
                      }
                      numeroColunasMd={6}
                      numeroColunasLg={6}
                    />
                  </Grid>
                </GridItem>
              </Grid>

              <GridItem colSpan={{ base: 1, md: 4 }} mt={4}>
                <Box position="relative" p={4}>
                  <Divider />
                  <AbsoluteCenter
                    bg={useColorModeValue("light.text", "dark.primary")}
                    px="4"
                    fontSize={"sm"}
                  >
                    Dias para Cancelamento
                  </AbsoluteCenter>
                </Box>
              </GridItem>
              <Grid w="100%" templateColumns={{ base: "¨100%", md: "repeat(12, 1fr)" }}>
                <TextControl
                  label="Pós Vencimento:"
                  id="posVencimento"
                  naoEditavel
                  value={
                    dadosCaixa?.body.servicoSaida.dados?.consultaBoleto != null
                      ? dadosCaixa?.body.servicoSaida.dados.consultaBoleto.titulo.posVencimento.numeroDias?.toString()
                      : "-"
                  }
                  numeroColunasMd={6}
                  numeroColunasLg={6}
                />
              </Grid>
            </Box>
          </AccordionPanel>
        )}
      </AccordionItem>
    </Accordion>
  );
}
