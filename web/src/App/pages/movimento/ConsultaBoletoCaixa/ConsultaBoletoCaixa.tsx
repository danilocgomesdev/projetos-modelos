import { Grid } from "@chakra-ui/react";
import { useQuery } from "@tanstack/react-query";
import { useEffect, useState } from "react";
import {
  fetchConsultaBoletoCR5Paginado,
  fetchConsultaBoletoCaixaPaginado,
} from "../../../../requests/requestBoleto";
import queryClient from "../../../../singletons/reactQueryClient.ts";
import Acessos from "../../../../utils/Acessos.ts";
import { DelaysDebouncePadrao, TemposCachePadrao } from "../../../../utils/constantes";
import { ButtonPesquisarLimpar } from "../../../components/ButoonControl/ButtonPesquisarLimpar.tsx";
import { CabecalhoPages } from "../../../components/CabecalhoPages";
import { DadosNaoEncontrados } from "../../../components/DadosNaoEncontrados";
import { InputControl } from "../../../components/InputControl/InputControl.tsx";
import { Loading } from "../../../components/Loading";
import useCR5Axios from "../../../hooks/useCR5Axios";
import useDebounce from "../../../hooks/useDebounce.ts";
import { useValidaAcessos } from "../../../hooks/useValidaAcessos.ts";
import { DadoBoletoCR5 } from "./components/DadoBoletoCR5.tsx";
import { DadoBoletoCaixa } from "./components/DadoBoletoCaixa.tsx";
import { DadosTituloCR5 } from "./components/DadosTituloCR5.tsx";

export function ConsultaBoletoCaixa() {
  const { axios } = useCR5Axios();
  const itemsPerPage = 10;
  const delayDebounce = DelaysDebouncePadrao.PADRAO;

  const [invalidando, setInvalidando] = useState(true);
  const [currentPage, setCurrentPage] = useState(1);

  const [nossoNumeroBusca, setNossoNumeroBusca] = useState("");
  const [nossoNumeroDebounce] = useDebounce(nossoNumeroBusca, delayDebounce);

  const [validouPermissao, setValidouPermissao] = useState(false);
  const [isLimpar, setIsLimpar] = useState(true);

  useValidaAcessos([Acessos.REMOVER_VINCULO], () => {
    setValidouPermissao(true);
    queryClient
      .invalidateQueries({ queryKey: ["fetchConsultaBoletoCaixaPaginado"] })
      .then(() => setInvalidando(false));
  });

  const isEnabledNossoNumero = !!nossoNumeroBusca;

  const {
    data: dadosCR5,
    error: errorCR5,
    isFetching: isFetchingCR5,
    isError: isErrorCR5,
  } = useQuery({
    queryKey: ["fetchConsultaBoletoCR5Paginado", currentPage, nossoNumeroDebounce],
    enabled: validouPermissao && isEnabledNossoNumero && !invalidando && !!nossoNumeroBusca,
    staleTime: TemposCachePadrao.CURTO,
    cacheTime: TemposCachePadrao.CURTO,
    queryFn: () => {
      return fetchConsultaBoletoCR5Paginado(
        {
          page: currentPage - 1,
          pageSize: itemsPerPage,
          nossoNumero: nossoNumeroBusca,
        },
        axios
      );
    },
  });

  const {
    data: dadosCaixa,
    isFetching: isFetchingCaixa,
    isError: isErrorCaixa,
  } = useQuery({
    queryKey: ["fetchConsultaBoletoCaixaPaginado", currentPage, nossoNumeroDebounce],
    enabled: validouPermissao && isEnabledNossoNumero && !invalidando && !!nossoNumeroBusca,
    staleTime: TemposCachePadrao.CURTO,
    cacheTime: TemposCachePadrao.CURTO,
    queryFn: () => {
      return fetchConsultaBoletoCaixaPaginado(
        {
          page: currentPage - 1,
          pageSize: itemsPerPage,
          nossoNumero: nossoNumeroBusca,
        },
        axios
      );
    },
  });

  const podeRemoverVinculo =
    dadosCaixa?.body?.servicoSaida?.dados?.controleNegocial?.codRetorno === "999";

  function atualizaValorPesquisa<T>(setter: (arg: T) => void, valor: T): void {
    setter(valor);
    setCurrentPage(1);
  }

  function handleLimparCampo() {
    setNossoNumeroBusca("");

    queryClient.removeQueries(["fetchConsultaBoletoCaixaPaginado"]);
    queryClient.removeQueries(["fetchConsultaBoletoCR5Paginado"]);
  }

  function validaFiltros(): boolean {
    return nossoNumeroBusca == "";
  }

  useEffect(() => {
    setIsLimpar(validaFiltros());
  }, [nossoNumeroBusca]);

  return (
    <>
      <CabecalhoPages titulo="Consulta Boleto" />
      <Grid w="100%" templateColumns={{ base: "¨100%", md: "repeat(12, 1fr)" }} gap={2} mt={2}>
        <InputControl
          label="Nosso Número"
          id="nossoNumero"
          type="text"
          value={nossoNumeroBusca}
          onChange={(e) => atualizaValorPesquisa(setNossoNumeroBusca, e.target.value)}
          numeroColunasMd={4}
          numeroColunasLg={4}
        />
        <ButtonPesquisarLimpar
          handleLimparCampo={handleLimparCampo}
          isLimpar={isLimpar}
          numeroColunasMd={2}
          numeroColunasLg={2}
        />
      </Grid>

      {isFetchingCR5 ? (
        <Loading mensagem="Carregando dados!" altura="50vh" />
      ) : isErrorCR5 && !dadosCR5 ? (
        <DadosNaoEncontrados
          altura="50vh"
          isError
          mensagem="Não foi possível carregar a aplicação. Procure o suporte."
        />
      ) : (
        <>
          <DadoBoletoCR5
            dadosCR5={dadosCR5}
            podeRemoverVinculo={podeRemoverVinculo}
            handleLimparCampo={handleLimparCampo}
          />

          <DadosTituloCR5
            dadosTituloCR5={dadosCR5?.dadosTituloBoletoCR5}
            isFetchingCR5={isFetchingCR5}
            isErrorCR5={isErrorCR5}
            errorCR5={errorCR5}
          />
        </>
      )}

      <DadoBoletoCaixa
        dadosCaixa={dadosCaixa}
        isFetchingCaixa={isFetchingCaixa}
        isErrorCaixa={isErrorCaixa}
      />
    </>
  );
}
