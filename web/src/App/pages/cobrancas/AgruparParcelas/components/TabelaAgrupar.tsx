import { Checkbox, IconButton } from "@chakra-ui/react";

import { FiTrash2 } from "react-icons/fi";

import { CobrancasGrupoDTO } from "../../../../../models/DTOs/CobrancasAgrupadas/CobrancasGrupoDTO";

import { situacoesCobrancaClienteValues } from "../../../../../models/DTOs/Cobrancas/SituacaoCobrancaCliente";

import { generateRandomKey } from "../../../../../utils/keyRandon";

import {
  formatarDataBrasil,
  formatarMoedaBrasil,
  mascaraCpfCnpj,
} from "../../../../../utils/mascaras";

import { PageResult } from "../../../../../models/response/PageResult";

import { TabelaCR5 } from "../../../../components/Tabelas";

interface Props {
  data: PageResult<CobrancasGrupoDTO> | undefined;
  isFetching: boolean;
  isError: boolean;
  error: unknown;
  itemsPerPage: number;
  currentPage: number;
  setCurrentPage: (page: number) => void;
  totalItems: number | undefined;
  handleCheckboxChange: (item: CobrancasGrupoDTO) => void;
  cobrancasSelecionadas: CobrancasGrupoDTO[];
  handleExcluirBoleto: (item: CobrancasGrupoDTO) => void;
}

export function TabelaAgrupar({
  data,
  isFetching,
  isError,
  error,
  itemsPerPage,
  currentPage,
  setCurrentPage,
  handleCheckboxChange,
  cobrancasSelecionadas,
  handleExcluirBoleto,
}: Props) {
  return (
    <TabelaCR5
      cabecalhos={[
        {
          titulo: "",
          dadoBuilder: (item: CobrancasGrupoDTO) => (
            <Checkbox
              type="checkbox"
              onChange={() => handleCheckboxChange(item)}
              isChecked={cobrancasSelecionadas.some(
                (selected) => selected.idCobrancaCliente === item.idCobrancaCliente
              )}
              disabled={
                situacoesCobrancaClienteValues[item.situacao]?.descricao === "Administrado Cadin"
              }
            />
          ),
        },
        {
          titulo: "CONTRATO",
          dadoBuilder: (item) => item.contrato?.toString() ?? "",
          tamanho: "90px",
        },
        {
          titulo: "DT. VENCTO",
          dadoBuilder: (item) => formatarDataBrasil(item.dataVencimento),
          tamanho: "90px",
        },
        {
          titulo: "PARCELA",
          dadoBuilder: (item) => item.parcela?.toString() ?? "",
          tamanho: "75px",
        },
        {
          titulo: "SITUAÇÃO",
          dadoBuilder: (item) => situacoesCobrancaClienteValues[item.situacao]?.descricao,
          tamanho: "100px",
        },
        {
          titulo: "Excluir boleto",
          dadoBuilder: (item: CobrancasGrupoDTO) =>
            item.nossoNumero ? (
              <IconButton
                aria-label="Excluir Boleto"
                title="Excluir Boleto"
                icon={<FiTrash2 />}
                color="red"
                bg="none"
                size="sm"
                onClick={() => handleExcluirBoleto(item)}
                _hover={{
                  bg: "red",
                  color: "white",
                }}
              />
            ) : (
              <IconButton
                aria-label="Excluir Boleto"
                title="Excluir Boleto"
                icon={<FiTrash2 />}
                bg="none"
                color="gray"
                size="sm"
                isDisabled
                _hover={{
                  bg: "red",
                  color: "white",
                }}
              />
            ),
          tamanho: "70px",
        },
        {
          titulo: "Nosso Número",
          dadoBuilder: (item) => item.nossoNumero,
          tamanho: "100px",
        },
        {
          titulo: "CPF/CNPJ",
          dadoBuilder: (item) => mascaraCpfCnpj(item.cpfCnpj),
          tamanho: "180px",
        },
        {
          titulo: "RESPONSÁVEL FINANCEIRO",
          dadoBuilder: (item) => item.sacadoNome,
          tamanho: "350px",
        },
        {
          titulo: "VL. COBRANÇA",
          dadoBuilder: (item) => formatarMoedaBrasil(item.valorCobranca),
          tamanho: "120px",
        },
        {
          titulo: "JUROS/MULTAS",
          dadoBuilder: (item) => formatarMoedaBrasil(item.totalDebito),
          tamanho: "120px",
        },
        {
          titulo: "DESCONTOS",
          dadoBuilder: (item) => formatarMoedaBrasil(item.totalDesconto),
          tamanho: "100px",
        },
        {
          titulo: "VL TOTAL PARCELA",
          dadoBuilder: (item) => formatarMoedaBrasil(item.valorTotalParcela),
          tamanho: "100px",
        },
        {
          titulo: "NF",
          dadoBuilder: (item) => item.notaFiscal?.toString() || "",
          tamanho: "100px",
        },
        {
          titulo: "Dt. Emissão NF",
          dadoBuilder: (item) => formatarDataBrasil(item.dataEmissaoNotaFiscal),
          tamanho: "100px",
        },
        {
          titulo: "AL",
          dadoBuilder: (item) => item.avisoLancamentoNota,
          tamanho: "100px",
        },
        {
          titulo: "Dt. Emissão AL",
          dadoBuilder: (item) => formatarDataBrasil(item.dataAvisoLancamentoNota),
          tamanho: "100px",
        },
      ]}
      data={data?.result ?? undefined}
      keybuilder={() => generateRandomKey()}
      isFetching={isFetching}
      isError={isError}
      error={error}
      totalPages={data?.pageTotal}
      itemsPerPage={itemsPerPage}
      currentPage={currentPage ?? undefined}
      setCurrentPage={setCurrentPage ?? undefined}
      totalItems={data?.total}
    />
  );
}
