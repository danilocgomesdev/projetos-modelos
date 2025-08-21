import { CobrancasGrupoDTO } from "../../../../../models/DTOs/CobrancasAgrupadas/CobrancasGrupoDTO";
import { PageResult } from "../../../../../models/response/PageResult";
import { generateRandomKey } from "../../../../../utils/keyRandon";
import {
  formatarDataBrasil,
  formatarMoedaBrasil,
  mascaraCpfCnpj,
} from "../../../../../utils/mascaras";
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
  handleChangeDetalhes: (item: CobrancasGrupoDTO) => void;
}

export function TabelaGrupoCancelado({
  data,
  isFetching,
  isError,
  error,
  itemsPerPage,
  currentPage,
  setCurrentPage,
  handleChangeDetalhes,
}: Props) {
  return (
    <TabelaCR5
      cabecalhos={[
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
      data={data?.result ?? undefined}
      keybuilder={() => generateRandomKey()}
      isFetching={isFetching}
      isError={isError}
      error={error}
      detalhesBuilder={{ type: "OnClick", onClick: handleChangeDetalhes }}
      totalPages={data?.pageTotal ?? undefined}
      itemsPerPage={itemsPerPage ?? undefined}
      currentPage={currentPage ?? undefined}
      setCurrentPage={setCurrentPage ?? undefined}
      totalItems={data?.total}
    />
  );
}
