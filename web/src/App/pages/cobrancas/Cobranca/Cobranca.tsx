import { Grid } from "@chakra-ui/react";
import { useQuery } from "@tanstack/react-query";
import { useState } from "react";
import { fetchCobrancasPaginado } from "../../../../requests/requestCobranca";
import queryClient from "../../../../singletons/reactQueryClient";
import Acessos from "../../../../utils/Acessos";
import { DelaysDebouncePadrao, TemposCachePadrao } from "../../../../utils/constantes";
import { mascaraCpfCnpj } from "../../../../utils/mascaras";
import { ButtonPesquisarLimpar } from "../../../components/ButoonControl/ButtonPesquisarLimpar";
import { CabecalhoPages } from "../../../components/CabecalhoPages";
import { InputControl } from "../../../components/InputControl";
import { TabelaCR5 } from "../../../components/Tabelas";
import useCR5Axios from "../../../hooks/useCR5Axios";
import useDebounce from "../../../hooks/useDebounce";
import { useValidaAcessos } from "../../../hooks/useValidaAcessos";

export function Cobranca() {
  const { axios } = useCR5Axios();
  const itemsPerPage = 10;
  const delayDebounce = DelaysDebouncePadrao.PADRAO;

  const [invalidando, setInvalidando] = useState(true);
  const [currentPage, setCurrentPage] = useState(1);
  const [validouPermissao, setValidouPermissao] = useState(false);

  const [idSistema, setIdSistema] = useState<number | null>(null);
  const [idUnidade, setIdUnidade] = useState<number | null>(null);
  const [ano, setAno] = useState<number | null>(2025);
  const [numeroParcela, setNumeroParcela] = useState<string>("");
  const [statusInterface, setStatusInterface] = useState<string>("");
  const [dataInicioCobranca, setDataInicioCobranca] = useState<string>("");
  const [dataFimCobranca, setDataFimCobranca] = useState<string>("");
  const [dataInicioVigencia, setDataInicioVigencia] = useState<string>("");
  const [dataFimVigencia, setDataFimVigencia] = useState<string>("");
  const [contratoInicio, setContratoInicio] = useState<string>("");
  const [contratoFim, setContratoFim] = useState<string>("");
  const [contratoProtheusInicio, setContratoProtheusInicio] = useState<string>("");
  const [contratoProtheusFim, setContratoProtheusFim] = useState<string>("");

  const [idSistemaDebounce] = useDebounce(idSistema, delayDebounce);
  const [idUnidadeDebounce] = useDebounce(idUnidade, delayDebounce);
  const [anoDebounce] = useDebounce(ano, delayDebounce);
  const [numeroParcelaDebounce] = useDebounce(numeroParcela, delayDebounce);
  const [statusInterfaceDebounce] = useDebounce(statusInterface, delayDebounce);
  const [dataInicioCobrancaDebounce] = useDebounce(dataInicioCobranca, delayDebounce);
  const [dataFimCobrancaDebounce] = useDebounce(dataFimCobranca, delayDebounce);
  const [dataInicioVigenciaDebounce] = useDebounce(dataInicioVigencia, delayDebounce);
  const [dataFimVigenciaDebounce] = useDebounce(dataFimVigencia, delayDebounce);
  const [contratoInicioDebounce] = useDebounce(contratoInicio, delayDebounce);
  const [contratoFimDebounce] = useDebounce(contratoFim, delayDebounce);
  const [contratoProtheusInicioDebounce] = useDebounce(contratoProtheusInicio, delayDebounce);
  const [contratoProtheusFimDebounce] = useDebounce(contratoProtheusFim, delayDebounce);

  useValidaAcessos([Acessos.GERAR_COBRANCA_DE_CONTRATOS], () => {
    setValidouPermissao(true);
    queryClient
      .invalidateQueries({ queryKey: ["fetchCobrancasPaginado"] })
      .then(() => setInvalidando(false));
  });

  const { data, refetch, error, isError, isFetching } = useQuery({
    queryKey: [
      "fetchCobrancasPaginado",
      currentPage,
      idSistemaDebounce,
      idUnidadeDebounce,
      anoDebounce,
      numeroParcelaDebounce,
      statusInterfaceDebounce,
      dataInicioCobrancaDebounce,
      dataFimCobrancaDebounce,
      dataInicioVigenciaDebounce,
      dataFimVigenciaDebounce,
      contratoInicioDebounce,
      contratoFimDebounce,
      contratoProtheusInicioDebounce,
      contratoProtheusFimDebounce,
    ],
    enabled: !invalidando && validouPermissao,
    keepPreviousData: true,
    staleTime: TemposCachePadrao.CURTO,
    cacheTime: TemposCachePadrao.CURTO,
    queryFn: () =>
      fetchCobrancasPaginado(
        {
          page: currentPage - 1,
          pageSize: itemsPerPage,
          idSistema: idSistemaDebounce,
          idUnidade: idUnidadeDebounce,
          ano: anoDebounce,
          numeroParcela: numeroParcelaDebounce,
          statusInterface: statusInterfaceDebounce,
          dataInicioCobranca: dataInicioCobrancaDebounce,
          dataFimCobranca: dataFimCobrancaDebounce,
          dataInicioVigencia: dataInicioVigenciaDebounce,
          dataFimVigencia: dataFimVigenciaDebounce,
          contratoInicio: contratoInicioDebounce,
          contratoFim: contratoFimDebounce,
          contratoProtheusInicio: contratoProtheusInicioDebounce,
          contratoProtheusFim: contratoProtheusFimDebounce,
        },
        axios
      ),
  });

  function handleLimparCampo() {
    setIdSistema(null);
    setIdUnidade(null);
    setAno(2025);
    setNumeroParcela("");
    setStatusInterface("");
    setDataInicioCobranca("");
    setDataFimCobranca("");
    setDataInicioVigencia("");
    setDataFimVigencia("");
    setContratoInicio("");
    setContratoFim("");
    setContratoProtheusInicio("");
    setContratoProtheusFim("");
    queryClient.resetQueries(["fetchCobrancasPaginado"]);
    refetch();
  }

  function handlePesquisar() {
    setValidouPermissao(true);
  }

  return (
    <>
      <CabecalhoPages titulo="Cobranças" subtitulo="Consulta das cobranças do CR5" />
      <Grid w="100%" templateColumns={{ base: "100%", md: "repeat(12, 1fr)" }} mt={4}>
        <InputControl
          label="ID Sistema"
          id="idSistema"
          value={idSistema?.toString() || ""}
          onChange={(e) => setIdSistema(Number(e.target.value))}
          numeroColunasMd={6}
          numeroColunasLg={2}
        />
        <InputControl
          label="ID Unidade"
          id="idUnidade"
          value={idUnidade?.toString() || ""}
          onChange={(e) => setIdUnidade(Number(e.target.value))}
          numeroColunasMd={6}
          numeroColunasLg={2}
        />
        <InputControl
          label="Ano"
          id="ano"
          type="number"
          value={ano?.toString() || ""}
          onChange={(e) => setAno(Number(e.target.value))}
          numeroColunasMd={6}
          numeroColunasLg={2}
        />
        <InputControl
          label="Parcela"
          id="numeroParcela"
          value={numeroParcela}
          onChange={(e) => setNumeroParcela(e.target.value)}
          numeroColunasMd={6}
          numeroColunasLg={2}
        />
        <InputControl
          label="Status Interface"
          id="statusInterface"
          value={statusInterface}
          onChange={(e) => setStatusInterface(e.target.value)}
          numeroColunasMd={6}
          numeroColunasLg={2}
        />
        <InputControl
          label="Data Início Cobrança"
          id="dataInicioCobranca"
          type="date"
          value={dataInicioCobranca}
          onChange={(e) => setDataInicioCobranca(e.target.value)}
          numeroColunasMd={6}
          numeroColunasLg={2}
        />
        <InputControl
          label="Data Fim Cobrança"
          id="dataFimCobranca"
          type="date"
          value={dataFimCobranca}
          onChange={(e) => setDataFimCobranca(e.target.value)}
          numeroColunasMd={6}
          numeroColunasLg={2}
        />
        <ButtonPesquisarLimpar
          handlePesquisar={handlePesquisar}
          handleLimparCampo={handleLimparCampo}
          numeroColunasMd={2}
          numeroColunasLg={1}
        />
      </Grid>

      <TabelaCR5
        cabecalhos={[
          { titulo: "Contrato", dadoBuilder: (item) => item.contrato?.toString() },
          { titulo: "Parcela", dadoBuilder: (item) => item.codUnidade?.toString() },
          { titulo: "Entidade", dadoBuilder: (item) => item.entidade },
          {
            titulo: "Filial",
            dadoBuilder: (item) => item.filial,
          },
          {
            titulo: "CPF/CNPJ",
            dadoBuilder: (item) => mascaraCpfCnpj(item.cpfCnpj),
          },
          { titulo: "Consumidor", dadoBuilder: (item) => item.consumidor.toUpperCase() },
        ]}
        data={data?.result ?? undefined}
        keybuilder={(item) => item.contrato}
        isFetching={isFetching}
        isError={isError}
        error={error}
        totalPages={data?.pageTotal}
        itemsPerPage={itemsPerPage}
        currentPage={currentPage ?? undefined}
        setCurrentPage={setCurrentPage ?? undefined}
        totalItems={data?.total ?? 0}
      />
    </>
  );
}
