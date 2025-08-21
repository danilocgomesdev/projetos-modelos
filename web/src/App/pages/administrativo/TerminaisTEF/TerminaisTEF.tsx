import { Grid } from "@chakra-ui/react";
import { useQuery } from "@tanstack/react-query";
import { useState } from "react";
import { fetchTerminaisTEFPaginado } from "../../../../requests/requestTerminaisTEF";
import queryClient from "../../../../singletons/reactQueryClient.ts";
import Acessos from "../../../../utils/Acessos.ts";
import { trataValorInputNumberVazio } from "../../../../utils/componentUtil.ts";
import { DelaysDebouncePadrao, TemposCachePadrao } from "../../../../utils/constantes";
import { CabecalhoPages } from "../../../components/CabecalhoPages";
import { InputControl } from "../../../components/InputControl";
import { TabelaCR5 } from "../../../components/Tabelas";
import useCR5Axios from "../../../hooks/useCR5Axios";
import useDebounce from "../../../hooks/useDebounce";
import { useValidaAcessos } from "../../../hooks/useValidaAcessos.ts";

export function TerminaisTEF() {
  const { axios } = useCR5Axios();
  const itemsPerPage = 15;
  const delayDebounce = DelaysDebouncePadrao.PADRAO;

  const [invalidando, setInvalidando] = useState(true);
  const [currentPage, setCurrentPage] = useState(1);

  const [unidCodigoBusca, setUnidCodigoBusca] = useState("");
  const [smpVersaoBusca, setSmpVersaoBusca] = useState<number | null>(null);
  const [smpDtAtualizacaoBusca, setSmpDtAtualizacaoBusca] = useState("");
  const [entidadeIdLocalBusca, setEntidadeIdLocalBusca] = useState<number | null>(null);

  const [unidCodigoDebounce] = useDebounce(unidCodigoBusca, delayDebounce);
  const [smpVersaoDebounce] = useDebounce(smpVersaoBusca, delayDebounce);
  const [smpDtAtualizacaoDebounce] = useDebounce(smpDtAtualizacaoBusca, delayDebounce);
  const [entidadeIdLocalDebounce] = useDebounce(entidadeIdLocalBusca, delayDebounce);
  const [validouPermissao, setValidouPermissao] = useState(false);

  useValidaAcessos([Acessos.TERMINAIS_TEF], () => {
    setValidouPermissao(true);
    queryClient
      .invalidateQueries({ queryKey: ["fetchTerminaisTEFPaginado"] })
      .then(() => setInvalidando(false));
  });

  const { data, error, isError, isFetching } = useQuery({
    queryKey: [
      "fetchTerminaisTEFPaginado",
      currentPage,
      unidCodigoDebounce,
      smpVersaoDebounce,
      smpDtAtualizacaoDebounce,
      entidadeIdLocalDebounce,
    ],
    keepPreviousData: true,
    enabled: !invalidando && validouPermissao,
    staleTime: TemposCachePadrao.CURTO,
    cacheTime: TemposCachePadrao.CURTO,
    queryFn: () => {
      return fetchTerminaisTEFPaginado(
        {
          page: currentPage - 1,
          pageSize: itemsPerPage,

          unidCodigo: unidCodigoDebounce,
          smpVersao: smpVersaoDebounce,
          smpDtAtualizacao: smpDtAtualizacaoDebounce,
          entidadeIdLocal: entidadeIdLocalDebounce,
        },
        axios
      );
    },
  });

  function atualizaValorPesquisa<T>(setter: (arg: T) => void, valor: T): void {
    setter(valor);
    setCurrentPage(1);
  }

  return (
    <>
      <CabecalhoPages titulo="Terminais TEF" />
      <Grid w="100%" templateColumns={{ base: "¨100%", md: "repeat(12, 1fr)" }} mt={4} p={1}>
        <InputControl
          label="Unidade"
          id="unidCodigo"
          value={unidCodigoBusca}
          onChange={(e) => atualizaValorPesquisa(setUnidCodigoBusca, e.target.value)}
          numeroColunasMd={4}
          numeroColunasLg={2}
        />
        <InputControl
          label="Versão"
          id="smpVersao"
          value={smpVersaoBusca?.toString()}
          onChange={(event) =>
            trataValorInputNumberVazio(
              (val) => atualizaValorPesquisa(setSmpVersaoBusca, val),
              event
            )
          }
          numeroColunasMd={4}
          numeroColunasLg={2}
        />
        <InputControl
          label="Data Atualização"
          id="smpDtAtualizacao"
          type="date"
          value={smpDtAtualizacaoBusca}
          onChange={(e) => atualizaValorPesquisa(setSmpDtAtualizacaoBusca, e.target.value)}
          numeroColunasMd={4}
          numeroColunasLg={2}
        />
        <InputControl
          label="Id Local"
          id="entidadeIdLocal"
          value={entidadeIdLocalBusca?.toString()}
          onChange={(event) =>
            trataValorInputNumberVazio(
              (val) => atualizaValorPesquisa(setEntidadeIdLocalBusca, val),
              event
            )
          }
          numeroColunasMd={4}
          numeroColunasLg={2}
        />
      </Grid>

      <TabelaCR5
        cabecalhos={[
          { titulo: "Unidade", dadoBuilder: (item) => item.unidCodigo },
          { titulo: "Descrição", dadoBuilder: (item) => item.unidDescricao?.toUpperCase() || "-" },
          { titulo: "Empresa", dadoBuilder: (item) => item.empresaDescricao?.toUpperCase() || "-" },
          { titulo: "Local", dadoBuilder: (item) => item.entidadeIdLocal.toString() },
          { titulo: "Terminal", dadoBuilder: (item) => item.codTerminal.toString() },
          { titulo: "Data Atualização", dadoBuilder: (item) => item.smpDtAtualizacao },
          { titulo: "IP", dadoBuilder: (item) => item.hostIp || "-" },
          { titulo: "Host Name", dadoBuilder: (item) => item.hostName?.toUpperCase() || "-" },
          { titulo: "Versão", dadoBuilder: (item) => item.smpVersao.toString() },
        ]}
        data={data?.result}
        keybuilder={(item) => item.id}
        isFetching={isFetching}
        isError={isError}
        error={error}
        totalPages={data?.pageTotal ?? 0}
        itemsPerPage={itemsPerPage}
        currentPage={currentPage}
        setCurrentPage={setCurrentPage}
        totalItems={data?.total ?? 0}
      />
    </>
  );
}
