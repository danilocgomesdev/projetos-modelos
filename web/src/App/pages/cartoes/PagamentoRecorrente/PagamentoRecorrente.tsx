import { Box, Button, Flex, Grid, Text } from "@chakra-ui/react";
import { useQuery } from "@tanstack/react-query";
import { useState } from "react";
import { FiPrinter } from "react-icons/fi";
import { useNavigate } from "react-router-dom";
import { fetchRecorrenciaPaginado } from "../../../../requests/requestsRecorrencia.ts";
import queryClient from "../../../../singletons/reactQueryClient.ts";
import Acessos from "../../../../utils/Acessos.ts";
import { TemposCachePadrao } from "../../../../utils/constantes.ts";
import { formatarDataBrasil_2, mascaraCpfCnpj } from "../../../../utils/mascaras";
import { CabecalhoPages } from "../../../components/CabecalhoPages";
import { InputControl } from "../../../components/InputControl";
import { TabelaCR5 } from "../../../components/Tabelas";
import useCR5Axios from "../../../hooks/useCR5Axios.ts";
import useDebounce from "../../../hooks/useDebounce.ts";
import { useFuncaoNotificacao } from "../../../hooks/useFuncaoNotificacao";
import { useValidaAcessos } from "../../../hooks/useValidaAcessos.ts";
import { usePagamentoRecorrenteStore } from "./store/PagamentoRecorrenteStore.ts";

export function PagamentoRecorrente() {
  const notificacao = useFuncaoNotificacao();
  const navigate = useNavigate();

  const { axios } = useCR5Axios();

  const itemsPerPage = 10;
  const delayDebounce = 3000;

  const [invalidando, setInvalidando] = useState(true);

  const [currentPage, setCurrentPage] = useState(1);

  const [cpfCnpjBusca, setCpfCnpjBusca] = useState("");
  const [nomeBusca, setNomeBusca] = useState("");
  const [idRecorrenciaBusca, setIdRecorrenciaBusca] = useState("");
  const [dataPagamentoBusca, setDataPagamentoBusca] = useState("");
  const [dataInicioRecorrenciaBusca, setDataInicioRecorrenciaBusca] = useState("");
  const [dataFimRecorrenciaBusca, setDataFimRecorrenciaBusca] = useState("");
  const [tidBusca, setTidBusca] = useState("");

  const [cpfCnpjDebounce] = useDebounce(cpfCnpjBusca, delayDebounce);
  const [nomeDebounce] = useDebounce(nomeBusca, delayDebounce);
  const [idRecorrenciaDebounce] = useDebounce(idRecorrenciaBusca, delayDebounce);
  const [dataPagamentoDebounce] = useDebounce(dataPagamentoBusca, delayDebounce);

  const [dataFimRecorrenciaDebounce] = useDebounce(dataFimRecorrenciaBusca, delayDebounce);
  const [tidDebounce] = useDebounce(tidBusca, delayDebounce);

  const { setPagamentoRecorrente } = usePagamentoRecorrenteStore();
  const [validouPermissao, setValidouPermissao] = useState(false);

  function atualizaValorPesquisa<T>(setter: (arg: T) => void, valor: T): void {
    setter(valor);
    setCurrentPage(1);
  }

  useValidaAcessos([Acessos.RECORRENCIA], () => {
    setValidouPermissao(true);
    queryClient
      .invalidateQueries({ queryKey: ["fetchRecorrenciaPaginado"] })
      .then(() => setInvalidando(false));
  });

  const { data, isFetching, isError, error } = useQuery({
    queryKey: [
      "fetchRecorrenciaPaginado",
      currentPage,
      cpfCnpjDebounce,
      nomeDebounce,
      idRecorrenciaDebounce,
      dataPagamentoDebounce,
      dataFimRecorrenciaDebounce,
      tidDebounce,
    ],
    enabled: !invalidando && validouPermissao,
    keepPreviousData: true,
    staleTime: TemposCachePadrao.MODERADO,
    cacheTime: TemposCachePadrao.MODERADO,
    queryFn: () => {
      return fetchRecorrenciaPaginado(
        {
          page: currentPage - 1,
          pageSize: itemsPerPage,
          cpfCnpj: cpfCnpjDebounce ?? undefined,
          nome: nomeDebounce,
          idRecorrencia: idRecorrenciaDebounce,
          dataPagamento: dataPagamentoDebounce,
          dataInicioRecorrencia: dataInicioRecorrenciaBusca ?? undefined,
          dataFimRecorrencia: dataFimRecorrenciaDebounce ?? undefined,
          tid: tidDebounce,
        },
        axios
      );
    },
  });

  return (
    <>
      <CabecalhoPages titulo="Pagamento Recorrente" />
      <Flex direction="row" wrap="wrap" mt={7}>
        <Grid w="100%" templateColumns={{ base: "¨100%", md: "repeat(12, 1fr)" }} mt={4} p={1}>
          <InputControl
            label="CPF"
            id="cpfCnpj"
            value={cpfCnpjBusca}
            onChange={(e) => atualizaValorPesquisa(setCpfCnpjBusca, e.target.value)}
            numeroColunasMd={4}
            numeroColunasLg={2}
          />
          <InputControl
            label="Responsável Financeiro"
            id="nome"
            value={nomeBusca}
            onChange={(e) => atualizaValorPesquisa(setNomeBusca, e.target.value)}
            numeroColunasMd={8}
            numeroColunasLg={6}
          />
          <InputControl
            label="ID Recorrência"
            id="idRecorrencia"
            value={idRecorrenciaBusca}
            onChange={(e) => atualizaValorPesquisa(setIdRecorrenciaBusca, e.target.value)}
            numeroColunasMd={4}
            numeroColunasLg={4}
          />
          <InputControl
            label="Data Pagamento"
            type="Date"
            id="dataPagamento"
            value={dataPagamentoBusca}
            onChange={(e) => atualizaValorPesquisa(setDataPagamentoBusca, e.target.value)}
            numeroColunasMd={4}
            numeroColunasLg={2}
          />
          <InputControl
            label="Data Início"
            type="Date"
            id="dataInicioRecorrencia"
            value={dataInicioRecorrenciaBusca}
            onChange={(e) => atualizaValorPesquisa(setDataInicioRecorrenciaBusca, e.target.value)}
            numeroColunasMd={4}
            numeroColunasLg={2}
          />
          <InputControl
            label="Data Fim"
            type="Date"
            id="dataFimRecorrencia"
            value={dataFimRecorrenciaBusca}
            onChange={(e) => atualizaValorPesquisa(setDataFimRecorrenciaBusca, e.target.value)}
            numeroColunasMd={4}
            numeroColunasLg={2}
          />
          <InputControl
            label="TID"
            id="tid"
            value={tidBusca}
            onChange={(e) => atualizaValorPesquisa(setTidBusca, e.target.value)}
            numeroColunasMd={4}
            numeroColunasLg={4}
          />
        </Grid>

        <Flex mt={2} justifyContent="flex-end" ml={5} w="100%" wrap="wrap">
          <Button
            colorScheme="blue"
            size="sm"
            ml={2}
            w="10rem"
            onClick={() =>
              notificacao({
                titulo: "Relatório não disponível",
                message: "Por enquanto, esse relatório não está disponível",
                tipo: "warning",
              })
            }
          >
            <FiPrinter style={{ marginRight: "0.2rem" }} />
            Relatório
          </Button>
        </Flex>
      </Flex>
      <Box overflowX="auto">
        <Box p={1} mt={2}>
          <TabelaCR5
            cabecalhos={[
              { titulo: "Entidade", dadoBuilder: (item) => item.entidade },
              { titulo: "Unidade", dadoBuilder: (item) => item.unidade },
              { titulo: "Sistema", dadoBuilder: (item) => item.idSistema.toString() },
              { titulo: "CPF", dadoBuilder: (item) => mascaraCpfCnpj(item.cpfCnpj.toString()) },
              {
                titulo: "Responsável Financeiro",
                dadoBuilder: (item) => item.responsavelFinanceiro,
              },
              {
                titulo: "ID Recorrência",
                dadoBuilder: (item) => item.idRecorrencia?.toString() || "",
              },
              { titulo: "Status", dadoBuilder: (item) => item.statusRecorrencia?.toString() || "" },
              {
                titulo: "Data Início",
                dadoBuilder: (item) =>
                  formatarDataBrasil_2(item.dataInicioRecorrencia?.toString() || ""),
              },
              {
                titulo: "Data Fim",
                dadoBuilder: (item) =>
                  formatarDataBrasil_2(item.dataFimRecorrencia?.toString() || ""),
              },
            ]}
            data={data?.result}
            keybuilder={(item) => item.idRecorrencia}
            isFetching={isFetching}
            isError={isError}
            error={error}
            onEdit={(item) => {
              setPagamentoRecorrente(item);
              navigate("./editar");
            }}
            totalPages={data?.pageTotal ?? 0}
            itemsPerPage={itemsPerPage}
            currentPage={currentPage}
            setCurrentPage={setCurrentPage}
            totalItems={data?.total ?? 0}
          />
          <Text fontSize="sm" mt={4}>
            Obs: Sem informar os filtros, consulta lista os pagamentos dos últimos 03 meses.
          </Text>
        </Box>
      </Box>
    </>
  );
}
