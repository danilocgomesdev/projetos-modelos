import { Box } from "@chakra-ui/react";
import { useQuery } from "@tanstack/react-query";
import { useState } from "react";
import { fetchRecorrenciaPaginadoCompleto } from "../../../../../requests/requestsRecorrencia.ts";
import { TemposCachePadrao } from "../../../../../utils/constantes.ts";
import { formatarDataBrasil_2, formatarMoedaBrasil } from "../../../../../utils/mascaras.ts";
import { TabelaCR5 } from "../../../../components/Tabelas";
import useCR5Axios from "../../../../hooks/useCR5Axios.ts";

interface FormPagamentoRecorrenteProps {
  onOpen: () => void;
  pagamentoRecorrenteFiltradaItem: string | undefined;
}

export function ListaRecorrenciaSelecionada({
  pagamentoRecorrenteFiltradaItem,
}: FormPagamentoRecorrenteProps) {
  console.log(pagamentoRecorrenteFiltradaItem?.toString());

  const varVazio = "";
  const itemsPerPage = 10;
  const [currentPage, setCurrentPage] = useState(1);
  const { axios } = useCR5Axios();

  const {
    data: listaConsulta,
    isFetching,
    isError,
    error,
  } = useQuery({
    queryKey: [
      "fetchRecorrenciaPaginadoCompleto",
      currentPage,

      pagamentoRecorrenteFiltradaItem?.toString(),
    ],
    keepPreviousData: true,
    staleTime: TemposCachePadrao.MODERADO,
    cacheTime: TemposCachePadrao.MODERADO,
    queryFn: () => {
      return fetchRecorrenciaPaginadoCompleto(
        {
          page: currentPage - 1,
          pageSize: itemsPerPage,
          cpfCnpj: varVazio,
          nome: varVazio,
          idRecorrencia: pagamentoRecorrenteFiltradaItem?.toString(),
          dataPagamento: varVazio,
          dataInicioRecorrencia: varVazio,
          dataFimRecorrencia: varVazio,
          tid: varVazio,
        },
        axios
      );
    },
  });

  return (
    <Box overflowX="auto">
      <Box p={1} mt={6}>
        <TabelaCR5
          cabecalhos={[
            { titulo: "Contrato", dadoBuilder: (item) => item.contrato?.toString() || "" },
            { titulo: "Parcela", dadoBuilder: (item) => item.parcela?.toString() || "" },
            {
              titulo: "Valor Cobrança",
              dadoBuilder: (item) => formatarMoedaBrasil(item.valorCobranca?.toString() || ""),
            },
            {
              titulo: "Data Vencimento",
              dadoBuilder: (item) => formatarDataBrasil_2(item.dataVencimento?.toString() || ""),
            },
            {
              titulo: "Valor Pagamento",
              dadoBuilder: (item) => formatarMoedaBrasil(item.valorPago?.toString() || ""),
            },
            {
              titulo: "Data Pagamento",
              dadoBuilder: (item) => formatarDataBrasil_2(item.dataPagamento?.toString() || ""),
            },
            { titulo: "TID", dadoBuilder: (item) => item.tidCielo || "" },
            {
              titulo: "Autorização",
              dadoBuilder: (item) => item.autorizacaoCielo?.toString() || "",
            },
            { titulo: "NSU", dadoBuilder: (item) => item.nsuCielo?.toString() || "" },
            {
              titulo: "Valor Cielo",
              dadoBuilder: (item) =>
                formatarMoedaBrasil(item.valorVendaCielo?.toString() || "0,00"),
            },
          ]}
          data={listaConsulta?.result}
          keybuilder={(item) => item.tidCielo}
          isFetching={isFetching}
          isError={isError}
          error={error}
          totalPages={listaConsulta?.pageTotal ?? 0}
          itemsPerPage={itemsPerPage}
          currentPage={currentPage}
          setCurrentPage={setCurrentPage}
          totalItems={listaConsulta?.total ?? 0}
        />
      </Box>
    </Box>
  );
}
