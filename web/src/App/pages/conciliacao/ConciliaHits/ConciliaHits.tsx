import { Grid, useDisclosure } from "@chakra-ui/react";
import { useMutation, useQuery } from "@tanstack/react-query";
import { useEffect, useState } from "react";
import { FiRepeat } from "react-icons/fi";
import { useNavigate } from "react-router-dom";
import {
  fetchConciliacaoHits,
  fetchConciliacaoPaginado,
} from "../../../../requests/requestConciliacao";
import queryClient from "../../../../singletons/reactQueryClient";
import Acessos from "../../../../utils/Acessos";
import { DelaysDebouncePadrao, TemposCachePadrao } from "../../../../utils/constantes";
import { getMensagemDeErroOuProcureSuporte } from "../../../../utils/errors";
import {
  formatarDataBrasil,
  formatarMoedaBrasil,
  mascaraCpfCnpj,
} from "../../../../utils/mascaras";
import { ButtonControl } from "../../../components/ButoonControl";
import { ButtonPesquisarLimpar } from "../../../components/ButoonControl/ButtonPesquisarLimpar";
import { CabecalhoPages } from "../../../components/CabecalhoPages";
import { InputControl } from "../../../components/InputControl";
import { ModalRelatorio } from "../../../components/ModalRelatorio/ModalRelatorio";
import { SelectControl } from "../../../components/SelectControl";
import { TabelaCR5 } from "../../../components/Tabelas";
import useCR5Axios from "../../../hooks/useCR5Axios";
import useDebounce from "../../../hooks/useDebounce";
import { useFuncaoNotificacao } from "../../../hooks/useFuncaoNotificacao";
import { useValidaAcessos } from "../../../hooks/useValidaAcessos";
import ModalConfirmaDataConciliacao from "./components/ModalConfirmaDataConciliacao";

export function ConciliaHits() {
  const { axios } = useCR5Axios();
  const itemsPerPage = 7;
  const delayDebounce = DelaysDebouncePadrao.PADRAO;
  const navigate = useNavigate();
  const [invalidando, setInvalidando] = useState(true);
  const NOME_SISTEMA = "HITS";
  const ENTIDADE = 2;

  const {
    isOpen: isOpenModalConfirmaDataConciliacao,
    onOpen: onOpenModalConfirmaDataConciliacao,
    onClose: onCloseModalConfirmaDataConciliacao,
  } = useDisclosure();

  const {
    isOpen: isOpenModalRelatorio,
    onOpen: onOpenModalRelatorio,
    onClose: onCloseModalRelatorio,
  } = useDisclosure();

  const [currentPage, setCurrentPage] = useState(1);
  const [idPagamentoBusca, setIdPagamentoBusca] = useState<number | null>(null);
  const [contIdBusca, setContIdBusca] = useState<number | null>(null);
  const [numeroParcelaBusca, setNumeroParcelaBusca] = useState<number | null>(null);
  const [criadoCr5Busca, setCriadoCr5Busca] = useState<string>("");
  const [criadoProtheusBusca, setCriadoProtheusBusca] = useState<string>("");
  const [baixadoProtheusBusca, setBaixadoProtheusBusca] = useState<string>("");
  const [dataPagamentoBusca, setDataPagamentolBusca] = useState("");

  const [idPagamentoDebounce] = useDebounce(idPagamentoBusca, delayDebounce);
  const [contIdDebounce] = useDebounce(contIdBusca, delayDebounce);
  const [numeroParcelaDebounce] = useDebounce(numeroParcelaBusca, delayDebounce);
  const [criadoCr5Debounce] = useDebounce(criadoCr5Busca, delayDebounce);
  const [criadoProtheusDebounce] = useDebounce(criadoProtheusBusca, delayDebounce);
  const [baixadoProtheusDebounce] = useDebounce(baixadoProtheusBusca, delayDebounce);
  const [dataPagamentoDebounce] = useDebounce(dataPagamentoBusca, delayDebounce);

  const [isLimpar, setIsLimpar] = useState(true);

  const notificacao = useFuncaoNotificacao();

  function atualizaValorPesquisa<T>(setter: (arg: T) => void, valor: T): void {
    setter(valor);
    setCurrentPage(1);
  }

  const [validouPermissao, setValidouPermissao] = useState(false);

  useValidaAcessos([Acessos.ADMINISTRADOR_CR5], () => {
    setValidouPermissao(true);
    queryClient
      .invalidateQueries({ queryKey: ["fetchConciliacaoPaginado"] })
      .then(() => setInvalidando(false));
  });

  const { data, refetch, error, isError, isFetching } = useQuery({
    queryKey: [
      "fetchConciliacaoPaginado",
      currentPage,
      idPagamentoDebounce,
      contIdDebounce,
      numeroParcelaDebounce,
      criadoCr5Debounce,
      dataPagamentoDebounce,
      criadoProtheusDebounce,
      baixadoProtheusDebounce,
    ],
    enabled: !invalidando && validouPermissao,
    keepPreviousData: true,
    staleTime: TemposCachePadrao.CURTO,
    cacheTime: TemposCachePadrao.CURTO,
    queryFn: () =>
      fetchConciliacaoPaginado(
        {
          page: currentPage - 1,
          pageSize: itemsPerPage,
          idPagamento: idPagamentoDebounce,
          contId: contIdDebounce,
          numeroParcela: numeroParcelaDebounce,
          dataPagamento: dataPagamentoDebounce ? new Date(dataPagamentoDebounce) : null,
          criadoCr5:
            criadoCr5Debounce === "SIM" ? true : criadoCr5Debounce === "NÃO" ? false : null,
          criadoProtheus:
            criadoProtheusDebounce === "SIM"
              ? true
              : criadoProtheusDebounce === "NÃO"
              ? false
              : null,
          baixadoProtheus:
            baixadoProtheusDebounce === "SIM"
              ? true
              : baixadoProtheusDebounce === "NÃO"
              ? false
              : null,
        },
        axios
      ),
  });

  const { mutate: handleConcilidarHits, isLoading: isLoadingConcilidarHits } = useMutation(
    (data: string) => {
      return fetchConciliacaoHits(
        {
          data: data,
        },
        axios
      );
    },
    {
      onSuccess: () => {
        notificacao({
          tipo: "info",
          titulo: "Processamento iniciado",
          message:
            "O processo de conciliação pode levar até 5 minutos. Retorne mais tarde para verificar o resultado.",
        });
        onCloseModalConfirmaDataConciliacao();
        setTimeout(() => {
          navigate("/");
        }, 2000);
      },
      onError: (e) => {
        notificacao({
          message: getMensagemDeErroOuProcureSuporte(e),
          tipo: "error",
          titulo: "ERRO!",
        });
        handleLimparCampo();
      },
    }
  );

  function handleLimparCampo() {
    setContIdBusca(null);
    setNumeroParcelaBusca(null);
    setCriadoCr5Busca("");
    setDataPagamentolBusca("");
    setCriadoProtheusBusca("");
    setBaixadoProtheusBusca("");
    setIdPagamentoBusca(null);
    queryClient.resetQueries(["fetchConciliacaoPaginado"]);
    refetch();
  }

  function validaFiltros(): boolean {
    return (
      contIdBusca === null &&
      numeroParcelaBusca === null &&
      criadoCr5Busca === "" &&
      dataPagamentoBusca === "" &&
      criadoProtheusBusca === "" &&
      baixadoProtheusBusca === "" &&
      idPagamentoBusca === null
    );
  }

  useEffect(() => {
    setIsLimpar(validaFiltros());
  }, [
    contIdBusca,
    numeroParcelaBusca,
    criadoCr5Busca,
    dataPagamentoBusca,
    criadoProtheusBusca,
    baixadoProtheusBusca,
    idPagamentoBusca,
  ]);

  return (
    <>
      <CabecalhoPages
        titulo="Concilia Hits - Aruanã"
        subtitulo="Conciliação dos pagamentos realizados no sistema Hits - Aruanã"
      />
      <Grid w="100%" templateColumns={{ base: "100%", md: "repeat(12, 1fr)" }} mt={4}>
        <InputControl
          label="Código Pagamento"
          id="idPagamento"
          value={idPagamentoBusca?.toString() || ""}
          onChange={(e) => atualizaValorPesquisa(setIdPagamentoBusca, Number(e.target.value))}
          numeroColunasMd={6}
          numeroColunasLg={2}
        />
        <InputControl
          label="Contrato"
          id="contId"
          value={contIdBusca?.toString() || ""}
          onChange={(e) => atualizaValorPesquisa(setContIdBusca, Number(e.target.value))}
          numeroColunasMd={6}
          numeroColunasLg={2}
        />
        <InputControl
          label="Parcela"
          id="numeroParcela"
          value={numeroParcelaBusca?.toString() || ""}
          onChange={(e) => atualizaValorPesquisa(setNumeroParcelaBusca, Number(e.target.value))}
          numeroColunasMd={6}
          numeroColunasLg={2}
        />
        <SelectControl
          numeroColunasMd={6}
          numeroColunasLg={2}
          label="Criado CR5"
          id="conciliado"
          value={criadoCr5Busca}
          options={[
            {
              value: "SIM",
              label: "SIM",
            },
            {
              value: "NÃO",
              label: "NÃO",
            },
          ]}
          onChange={(e) => atualizaValorPesquisa(setCriadoCr5Busca, e ?? undefined)}
        />
        <SelectControl
          numeroColunasMd={6}
          numeroColunasLg={2}
          label="Criado Protheus"
          id="criadoProtheus"
          value={criadoProtheusBusca}
          options={[
            {
              value: "SIM",
              label: "SIM",
            },
            {
              value: "NÃO",
              label: "NÃO",
            },
          ]}
          onChange={(e) => atualizaValorPesquisa(setCriadoProtheusBusca, e ?? undefined)}
        />
        <SelectControl
          numeroColunasMd={6}
          numeroColunasLg={2}
          label="Baixado Protheus"
          id="baixadoProtheus"
          value={baixadoProtheusBusca}
          options={[
            {
              value: "SIM",
              label: "SIM",
            },
            {
              value: "NÃO",
              label: "NÃO",
            },
          ]}
          onChange={(e) => atualizaValorPesquisa(setBaixadoProtheusBusca, e ?? undefined)}
        />
        <InputControl
          label="Data Pagamento"
          id="dataPagamento"
          type="date"
          value={dataPagamentoBusca}
          onChange={(e) => atualizaValorPesquisa(setDataPagamentolBusca, e.target.value)}
          numeroColunasMd={6}
          numeroColunasLg={2}
        />
        <ButtonPesquisarLimpar
          handleLimparCampo={handleLimparCampo}
          isLimpar={isLimpar}
          numeroColunasMd={6}
          numeroColunasLg={2}
        />
      </Grid>
      <ButtonControl
        customButtons={[
          {
            label: "Conciliar Pagamentos",
            icon: <FiRepeat />,
            onClick: () => {
              onOpenModalConfirmaDataConciliacao();
            },
          },
        ]}
        onRelatorioClick={onOpenModalRelatorio}
      />
      <TabelaCR5
        cabecalhos={[
          { titulo: "Sistema", dadoBuilder: () => NOME_SISTEMA },
          { titulo: "Entidade", dadoBuilder: () => "SESI" },
          {
            titulo: "Criado CR5",
            dadoBuilder: (item) => (item.criadoCr5 ? "SIM" : "NÃO"),
            tamanho: "100px",
          },
          {
            titulo: "Motivo Falha",
            dadoBuilder: (item) => (item.motivoFalha.toUpperCase() ?? "").trim() || "-",
            tamanho: "200px",
          },
          {
            titulo: "Data Pagamento",
            dadoBuilder: (item) => formatarDataBrasil(new Date(item.dataPagamento)),
          },
          {
            titulo: "Parcela",
            dadoBuilder: (item) => (item.numeroParcela?.toString() ?? "").trim() || "-",
          },
          { titulo: "Valor Pago", dadoBuilder: (item) => formatarMoedaBrasil(item.valorPago) },
          {
            titulo: "Contrato Protheus",
            dadoBuilder: (item) => (item.contratoProtheus ?? "").trim() || "-",
          },
          { titulo: "Contrato", dadoBuilder: (item) => item.contrato?.toString() },
          {
            titulo: "Cliente",
            dadoBuilder: (item) => (item.sacadoNome.toUpperCase() ?? "").trim() || "-",
          },
          {
            titulo: "CPF/CNPJ",
            dadoBuilder: (item) => (mascaraCpfCnpj(item.sacadoCpfCnpj) ?? "").trim() || "-",
            tamanho: "150px",
          },
          {
            titulo: "Incluído Protheus",
            dadoBuilder: (item) => (item.dataInclusaoProtheus ? "SIM" : "NÃO"),
          },
          {
            titulo: "Baixado Protheus",
            dadoBuilder: (item) => (item.dataAlteracaoProtheus ? "SIM" : "NÃO"),
          },
        ]}
        data={data?.result}
        keybuilder={(item) => item.contrato}
        isFetching={isFetching}
        isError={isError}
        error={error}
        totalPages={data?.pageTotal ?? 0}
        itemsPerPage={itemsPerPage}
        currentPage={currentPage}
        setCurrentPage={setCurrentPage}
        totalItems={data?.total ?? 0}
      />

      <ModalConfirmaDataConciliacao
        isOpen={isOpenModalConfirmaDataConciliacao}
        onClose={onCloseModalConfirmaDataConciliacao}
        onDataSelecionada={(data: string) => handleConcilidarHits(data)}
        isLoanding={isLoadingConcilidarHits}
      />
      <ModalRelatorio
        nomeRelatorio="relatorio-conciliacao-hits"
        isOpen={isOpenModalRelatorio}
        onClose={onCloseModalRelatorio}
        rota="conciliacao/hits"
        entidade={ENTIDADE}
        params={{
          page: currentPage - 1,
          pageSize: itemsPerPage,
          idPagamento: idPagamentoDebounce,
          contId: contIdDebounce,
          numeroParcela: numeroParcelaDebounce,
          dataPagamento: dataPagamentoDebounce ? new Date(dataPagamentoDebounce) : null,
          criadoCr5:
            criadoCr5Debounce === "SIM" ? true : criadoCr5Debounce === "NÃO" ? false : null,
          criadoProtheus:
            criadoProtheusDebounce === "SIM"
              ? true
              : criadoProtheusDebounce === "NÃO"
              ? false
              : null,
          baixadoProtheus:
            baixadoProtheusDebounce === "SIM"
              ? true
              : baixadoProtheusDebounce === "NÃO"
              ? false
              : null,
        }}
      />
    </>
  );
}
