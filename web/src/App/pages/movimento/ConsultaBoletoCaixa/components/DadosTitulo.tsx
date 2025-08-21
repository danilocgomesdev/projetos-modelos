import { Box, Grid } from "@chakra-ui/react";

import { DadosTituloBoletoCr5DTO } from "../../../../../models/DTOs/ConsultaBoletoCaixa/DadosTituloBoletoCr5DTO";

import { TabelaCR5 } from "../../../../components/Tabelas";

import { formatarMoedaBrasil, mascaraCpfCnpj } from "../../../../../utils/mascaras";

import { formasDePagamentoValues } from "../../../../../models/DTOs/Cobrancas/StatusInterface";
import { TextControl } from "../../../../components/TextControl/TextControl";

interface DadosTituloProps {
  dadosTituloCR5: DadosTituloBoletoCr5DTO[] | undefined;
  isFetching: boolean;
  isError: boolean;
  errorCR5: unknown;
}
export function DadosTitulo({ dadosTituloCR5, isFetching, isError, errorCR5 }: DadosTituloProps) {
  const responsavelFinanceiro =
    dadosTituloCR5 && dadosTituloCR5.length > 0 && dadosTituloCR5[0].responsavelFinanceiro
      ? dadosTituloCR5[0].responsavelFinanceiro
      : "-";

  const cpfCnpj =
    dadosTituloCR5 && dadosTituloCR5.length > 0 && dadosTituloCR5[0].cpfCnpj
      ? dadosTituloCR5[0].cpfCnpj
      : "-";

  return (
    <Box overflowX="auto">
      <Grid w="100%" templateColumns={{ base: "¨100%", md: "repeat(10, 1fr)" }}>
        <TextControl
          label="Responsável Financeiro:"
          id="responsaveFinanceiro"
          naoEditavel
          value={responsavelFinanceiro}
          numeroColunasMd={6}
          numeroColunasLg={5}
        />

        <TextControl
          label="CPF/CNPJ:"
          id="cpfCnpj"
          naoEditavel
          value={mascaraCpfCnpj(cpfCnpj)}
          numeroColunasMd={6}
          numeroColunasLg={5}
        />
      </Grid>
      <TabelaCR5
        cabecalhos={[
          { titulo: "UNIDADE", dadoBuilder: (item) => item.unidade.toString(), tamanho: "90px" },
          {
            titulo: "CONTRATO",
            dadoBuilder: (item) => item.contrato.toString(),
            tamanho: "90px",
          },
          {
            titulo: "STATUS",
            dadoBuilder: (item) =>
              formasDePagamentoValues[item.status as keyof typeof formasDePagamentoValues]
                ?.descricao,
            tamanho: "90px",
          },
          {
            titulo: "PARCELA",
            dadoBuilder: (item) => item.parcela.toString(),
            tamanho: "120px",
          },
          {
            titulo: "COBRANÇA",
            dadoBuilder: (item) => formatarMoedaBrasil(item.cobranca),
            tamanho: "100px",
          },
          { titulo: "SITUAÇÃO", dadoBuilder: (item) => item.situacao, tamanho: "90px" },

          {
            titulo: "GRUPO ATIVO",
            dadoBuilder: (item) => item.grupo?.toString() ?? "-",
            tamanho: "80px",
          },
          { titulo: "BOLETO ATIVO", dadoBuilder: (item) => item.boletoAtivo, tamanho: "100px" },
        ]}
        data={dadosTituloCR5}
        keybuilder={(item) => item.idCbc}
        isError={isError}
        isFetching={isFetching}
        error={errorCR5}
      />
    </Box>
  );
}
