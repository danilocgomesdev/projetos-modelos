import { Checkbox, IconButton } from "@chakra-ui/react";

import { FiTrash2, FiZoomIn } from "react-icons/fi";

import { CobrancasGrupoDTO } from "../../../../../models/DTOs/CobrancasAgrupadas/CobrancasGrupoDTO";

import { situacoesCobrancaClienteValues } from "../../../../../models/DTOs/Cobrancas/SituacaoCobrancaCliente";

import { PageResult } from "../../../../../models/response/PageResult";

import { generateRandomKey } from "../../../../../utils/keyRandon";

import {
  formatarDataBrasil,
  formatarMoedaBrasil,
  mascaraCpfCnpj,
} from "../../../../../utils/mascaras";

import { TabelaCR5 } from "../../../../components/Tabelas";

import { useFuncaoNotificacao } from "../../../../hooks/useFuncaoNotificacao";

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
  handleChangeDetalhes: (item: CobrancasGrupoDTO) => void;
  handleAbrirModalEditarGrupo: (item: CobrancasGrupoDTO) => void;
}

export function TabelaAgrupado({
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
  handleChangeDetalhes,
  handleAbrirModalEditarGrupo,
}: Props) {
  const notificacao = useFuncaoNotificacao();

  return (
    <TabelaCR5
      cabecalhos={[
        {
          titulo: "VER MAIS",
          dadoBuilder: (item: CobrancasGrupoDTO) => (
            <IconButton
              icon={<FiZoomIn />}
              bg="none"
              aria-label="Search database"
              size="sm"
              onClick={() => handleChangeDetalhes(item)}
            />
          ),
        },
        {
          titulo: "",
          dadoBuilder: (item: CobrancasGrupoDTO) => (
            <Checkbox
              onChange={() => handleCheckboxChange(item)}
              isChecked={cobrancasSelecionadas.some(
                (selected) => selected.idCobrancaAgrupada === item.idCobrancaAgrupada
              )}
              disabled={
                situacoesCobrancaClienteValues[item.situacao]?.descricao === "Administrado Cadin"
              }
            />
          ),
        },
        {
          titulo: "GRUPO",
          dadoBuilder: (item) => item.idCobrancaAgrupada?.toString(),
          tamanho: "90px",
        },
        {
          titulo: "DT. VENCTO",
          dadoBuilder: (item) => formatarDataBrasil(item.dataVencimento),
          tamanho: "90px",
        },
        {
          titulo: "Excluir boleto",
          dadoBuilder: (item: CobrancasGrupoDTO) =>
            item.nossoNumero ? (
              <IconButton
                aria-label="Excluir Boleto"
                icon={<FiTrash2 />}
                color="red"
                bg="none"
                size="sm"
                onClick={() => handleExcluirBoleto(item)}
              />
            ) : (
              <IconButton
                aria-label="Excluir Boleto"
                icon={<FiTrash2 />}
                bg="none"
                color="gray"
                size="sm"
                isDisabled
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
          dadoBuilder: (item) => item.notaFiscal,
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
      onEdit={(item) => {
        if (item.idCobrancaAgrupada !== null) {
          handleAbrirModalEditarGrupo(item);
        } else {
          notificacao({
            tipo: "warning",
            titulo: "Aviso",
            message: "Produto não contém nenhum vínculo Contábil",
          });
        }
      }}
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
