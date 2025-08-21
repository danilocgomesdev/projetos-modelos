import { Box, Button, Grid, GridItem, useColorModeValue, useDisclosure } from "@chakra-ui/react";

import { DadosBoletoCr5DTO } from "../../../../../models/DTOs/ConsultaBoletoCaixa/DadosBoletoCr5DTO";

import {
  formatarDataBrasil,
  formatarMoedaBrasil,
  mascaraCpfCnpj,
} from "../../../../../utils/mascaras";

import { useMutation } from "@tanstack/react-query";

import { fetchRetirarVinculoBoleto } from "../../../../../requests/requestBoleto";

import { getMensagemDeErroOuProcureSuporte } from "../../../../../utils/errors";

import { TextControl } from "../../../../components/TextControl/TextControl";

import useCR5Axios from "../../../../hooks/useCR5Axios";

import { ModalConfirma } from "../../../../components/ModalConfirma";

import { situacoesBoletoValues } from "../../../../../models/DTOs/ConsultaBoletoCaixa/SituacaoBoleto";

import { situacoesAgrupadoValues } from "../../../../../models/DTOs/CobrancasAgrupadas/SituacaoAgrupado";

import { useFuncaoNotificacao } from "../../../../hooks/useFuncaoNotificacao";

interface DadoBoletoCR5Props {
  dadosCR5: DadosBoletoCr5DTO | undefined;
  podeRemoverVinculo: boolean;
  handleLimparCampo: () => void;
}

export function DadoBoletoCR5({
  dadosCR5,
  podeRemoverVinculo,
  handleLimparCampo,
}: DadoBoletoCR5Props) {
  const { axios } = useCR5Axios();
  const notificacao = useFuncaoNotificacao();
  const { isOpen, onOpen, onClose } = useDisclosure();

  const vinculoBoleto =
    dadosCR5 && dadosCR5.dadosTituloBoletoCR5.length > 0 && dadosCR5.dadosTituloBoletoCR5[0].vinculo
      ? situacoesBoletoValues[dadosCR5.dadosTituloBoletoCR5[0].vinculo].descricao
      : "-";

  function handleRemoverVinculoBoleto() {
    if (!dadosCR5?.dadoBoletoCR5.idBoleto) return;

    mutate(dadosCR5.dadoBoletoCR5.idBoleto);
  }

  const { mutate, isLoading } = useMutation(
    (idBoleto: number) => {
      return fetchRetirarVinculoBoleto(idBoleto, axios);
    },
    {
      onSuccess: () => {
        notificacao({ message: "Salvo com sucesso!", tipo: "success", titulo: "SUCESSO!" });
        onClose();
        handleLimparCampo();
      },
      onError: (e) => {
        notificacao({
          message: getMensagemDeErroOuProcureSuporte(e),
          tipo: "error",
          titulo: "ERRO!",
        });
        onClose();
        handleLimparCampo();
      },
    }
  );

  return (
    <>
      <Box overflowX="auto">
        <GridItem colSpan={{ base: 1, md: 4 }} mt={2} pb={2}>
          <Box
            bg={useColorModeValue("light.cabecalho", "dark.cabecalho")}
            position="relative"
            padding="2"
            textAlign="center"
            fontSize="sm"
            fontWeight="bold"
          >
            Dados do Boleto CR5
          </Box>
        </GridItem>

        <Grid w="100%" templateColumns={{ base: "¨100%", md: "repeat(12, 1fr)" }} pl={4} pr={4}>
          <TextControl
            label="Id Boleto:"
            id="idBoleto"
            naoEditavel
            value={
              dadosCR5?.dadoBoletoCR5.idBoleto != null
                ? dadosCR5?.dadoBoletoCR5.idBoleto.toString()
                : "-"
            }
            numeroColunasMd={6}
            numeroColunasLg={4}
          />

          <TextControl
            label="Nosso Número:"
            id="nossoNumero"
            naoEditavel
            value={
              dadosCR5?.dadoBoletoCR5.nossoNumero != null
                ? dadosCR5?.dadoBoletoCR5.nossoNumero
                : "-"
            }
            numeroColunasMd={6}
            numeroColunasLg={4}
          />

          <TextControl
            label="Situação:"
            id="situacaoCR5"
            naoEditavel
            value={
              dadosCR5?.dadoBoletoCR5.bolSituacao &&
              dadosCR5?.dadoBoletoCR5.bolSituacao.toUpperCase() !== "NULL" &&
              dadosCR5?.dadoBoletoCR5.bolSituacao != undefined
                ? situacoesBoletoValues[dadosCR5?.dadoBoletoCR5.bolSituacao]?.descricao
                : "-"
            }
            numeroColunasMd={6}
            numeroColunasLg={4}
          />

          <TextControl
            label="CNPJ Cedente:"
            id="cnpjCedente"
            naoEditavel
            value={
              dadosCR5?.dadoBoletoCR5.cnpjCedente != null
                ? mascaraCpfCnpj(dadosCR5?.dadoBoletoCR5.cnpjCedente)
                : "-"
            }
            numeroColunasMd={6}
            numeroColunasLg={4}
          />

          <TextControl
            label="Cód. Beneficiário:"
            id="codigoBeneficiario"
            naoEditavel
            value={
              dadosCR5?.dadoBoletoCR5.codigoBeneficiario != null
                ? dadosCR5?.dadoBoletoCR5.codigoBeneficiario
                : "-"
            }
            numeroColunasMd={6}
            numeroColunasLg={4}
          />

          <TextControl
            label="Unidade:"
            id="unidade"
            type="number"
            naoEditavel
            value={
              dadosCR5?.dadoBoletoCR5.codUnidade != null
                ? dadosCR5?.dadoBoletoCR5.codUnidade.toString()
                : "-"
            }
            numeroColunasMd={6}
            numeroColunasLg={4}
          />
        </Grid>
        <Grid w="100%" templateColumns={{ base: "¨100%", md: "repeat(12, 1fr)" }} pl={4} pr={4}>
          <TextControl
            label="Vínculo Boleto:"
            id="vinculoBoleto"
            naoEditavel
            value={vinculoBoleto}
            numeroColunasMd={6}
            numeroColunasLg={4}
          />

          <TextControl
            label="Grupo:"
            id="grupo"
            naoEditavel
            value={
              dadosCR5?.dadoBoletoCR5.idCobrancasAgrupada != null
                ? dadosCR5?.dadoBoletoCR5.idCobrancasAgrupada.toString()
                : "-"
            }
            numeroColunasMd={6}
            numeroColunasLg={4}
          />

          <TextControl
            label="Status Grupo:"
            id="statusGrupo"
            naoEditavel
            value={
              dadosCR5?.dadoBoletoCR5.grupoStatus != null
                ? situacoesAgrupadoValues[dadosCR5?.dadoBoletoCR5.grupoStatus]?.descricao
                : "-"
            }
            numeroColunasMd={6}
            numeroColunasLg={4}
          />
        </Grid>
        <Grid w="100%" templateColumns={{ base: "¨100%", md: "repeat(10, 1fr)" }} pl={4} pr={4}>
          <TextControl
            label="Data Emissão:"
            id="dataEmissao"
            naoEditavel
            value={
              dadosCR5?.dadoBoletoCR5.bolEmissao != null
                ? formatarDataBrasil(dadosCR5?.dadoBoletoCR5.bolEmissao)
                : "-"
            }
            numeroColunasMd={5}
            numeroColunasLg={2}
          />

          <TextControl
            label="Data Vencimento:"
            id="dataVencimento"
            naoEditavel
            value={
              dadosCR5?.dadoBoletoCR5.bolVencimento != null
                ? formatarDataBrasil(dadosCR5?.dadoBoletoCR5.bolVencimento)
                : "-"
            }
            numeroColunasMd={5}
            numeroColunasLg={2}
          />

          <TextControl
            label="Data Pagamento:"
            id="dataPagamento"
            naoEditavel
            value={
              dadosCR5?.dadoBoletoCR5.bolDataPagamento != null
                ? formatarDataBrasil(dadosCR5?.dadoBoletoCR5.bolDataPagamento)
                : "-"
            }
            numeroColunasMd={5}
            numeroColunasLg={2}
          />

          <TextControl
            label="Data Crédito:"
            id="dataCredito"
            naoEditavel
            value={
              dadosCR5?.dadoBoletoCR5.bolDataCredito != null
                ? formatarDataBrasil(dadosCR5?.dadoBoletoCR5.bolDataCredito)
                : "-"
            }
            numeroColunasMd={5}
            numeroColunasLg={2}
          />

          <TextControl
            label="Data Cancelamento:"
            id="dataCancelamento"
            naoEditavel
            value={
              dadosCR5?.dadoBoletoCR5.bolCancelamento != null
                ? formatarDataBrasil(dadosCR5?.dadoBoletoCR5.bolCancelamento)
                : "-"
            }
            numeroColunasMd={5}
            numeroColunasLg={2}
          />

          <TextControl
            label="Valor:"
            id="valor"
            naoEditavel
            value={
              dadosCR5?.dadoBoletoCR5.bolValor != null
                ? formatarMoedaBrasil(dadosCR5?.dadoBoletoCR5.bolValor ?? 0)
                : "-"
            }
            numeroColunasMd={5}
            numeroColunasLg={2}
          />

          <TextControl
            label="Desconto:"
            id="desconto"
            naoEditavel
            value={
              dadosCR5?.dadoBoletoCR5.bolDesconto != null
                ? formatarMoedaBrasil(dadosCR5?.dadoBoletoCR5.bolDesconto ?? 0)
                : "-"
            }
            numeroColunasMd={5}
            numeroColunasLg={2}
          />

          <TextControl
            label="Juros:"
            id="juros"
            naoEditavel
            value={
              dadosCR5?.dadoBoletoCR5.bolJuros != null
                ? formatarMoedaBrasil(dadosCR5?.dadoBoletoCR5.bolJuros ?? 0)
                : "-"
            }
            numeroColunasMd={5}
            numeroColunasLg={2}
          />

          <TextControl
            label="Multa:"
            id="multa"
            naoEditavel
            value={
              dadosCR5?.dadoBoletoCR5.bolMulta != null
                ? formatarMoedaBrasil(dadosCR5?.dadoBoletoCR5.bolMulta ?? 0)
                : "-"
            }
            numeroColunasMd={5}
            numeroColunasLg={2}
          />

          <TextControl
            label="Pago:"
            id="pago"
            naoEditavel
            value={
              dadosCR5?.dadoBoletoCR5.bolPago != null
                ? formatarMoedaBrasil(dadosCR5?.dadoBoletoCR5.bolPago ?? 0)
                : "-"
            }
            numeroColunasMd={5}
            numeroColunasLg={2}
          />
        </Grid>

        <Grid w="100%" templateColumns={{ base: "¨100%", md: "repeat(12, 1fr)" }} pl={4} pr={4}>
          <TextControl
            label="Operador Inclusão:"
            id="operadorInclusao"
            naoEditavel
            value={
              dadosCR5?.dadoBoletoCR5.operadorInclusao != null
                ? dadosCR5?.dadoBoletoCR5.operadorInclusao
                : "-"
            }
            numeroColunasMd={6}
            numeroColunasLg={4}
          />

          <TextControl
            label="Operador Alteração:"
            id="operadorAlteracao"
            naoEditavel
            value={
              dadosCR5?.dadoBoletoCR5.operadorAlteracao &&
              dadosCR5?.dadoBoletoCR5.operadorAlteracao.toUpperCase() !== "NULL" &&
              dadosCR5?.dadoBoletoCR5.operadorAlteracao != undefined
                ? dadosCR5?.dadoBoletoCR5.operadorAlteracao
                : "-"
            }
            numeroColunasMd={6}
            numeroColunasLg={4}
          />

          <TextControl
            label="Operador Cancelamento:"
            id="operadorCancelamento"
            naoEditavel
            value={
              dadosCR5?.dadoBoletoCR5.operadorCancelamento &&
              dadosCR5?.dadoBoletoCR5.operadorCancelamento.toUpperCase() !== "NULL" &&
              dadosCR5?.dadoBoletoCR5.operadorCancelamento != undefined
                ? dadosCR5?.dadoBoletoCR5.operadorCancelamento
                : "-"
            }
            numeroColunasMd={6}
            numeroColunasLg={4}
          />
        </Grid>
        <Grid w="100%" templateColumns={{ base: "¨100%", md: "repeat(12, 1fr)" }} mt={4}>
          <GridItem colSpan={{ base: 1, md: 6, lg: 2 }} m={1} mb={2} alignContent={"end"}>
            <Button
              colorScheme="blue"
              variant="outline"
              size="sm"
              w="100%"
              isDisabled={!(podeRemoverVinculo && vinculoBoleto === "Ativo")}
              onClick={() => onOpen()}
            >
              Remover Vínculo
            </Button>
          </GridItem>
        </Grid>
      </Box>

      <ModalConfirma
        titulo="Atenção"
        texto={"Deseja realmente retirar o vínculo?"}
        onClose={onClose}
        isOpen={isOpen}
        onConfirm={handleRemoverVinculoBoleto}
        isLoading={isLoading}
      />
    </>
  );
}
