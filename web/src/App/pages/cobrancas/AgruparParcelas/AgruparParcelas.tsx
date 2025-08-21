import {
  Button,
  Grid,
  Tab,
  TabList,
  TabPanel,
  TabPanels,
  Tabs,
  useDisclosure,
} from "@chakra-ui/react";
import { useMutation, useQuery, useQueryClient } from "@tanstack/react-query";
import { useEffect, useState } from "react";
import { FiFileText, FiLayers, FiPrinter, FiSearch } from "react-icons/fi";
import { CobrancasGrupoDTO } from "../../../../models/DTOs/CobrancasAgrupadas/CobrancasGrupoDTO";
import { PessoaCr5DTO } from "../../../../models/DTOs/Outros/PessoaCr5DTO.ts";
import {
  AlteraDadosGrupoParams,
  fetchAgruparParcelas,
  fetchAlteraDadosGrupo,
  fetchCobrancaParaAgruparPaginado,
  fetchDesagrupar,
  fetchExcluirBoleto,
  fetchExcluirBoletoGrupo,
} from "../../../../requests/requestCobrancasAgrupadas";
import Acessos, { criaValidacaoTodas } from "../../../../utils/Acessos.ts";
import { trataValorInputNumberVazio } from "../../../../utils/componentUtil";
import { TemposCachePadrao } from "../../../../utils/constantes";
import { converteDataStringDate } from "../../../../utils/date";
import { getMensagemDeErroOuProcureSuporte } from "../../../../utils/errors";
import { mascaraCpfCnpj } from "../../../../utils/mascaras";
import { ButtonPesquisarLimpar } from "../../../components/ButoonControl/ButtonPesquisarLimpar.tsx";
import { CabecalhoPages } from "../../../components/CabecalhoPages";
import { InputControl } from "../../../components/InputControl";
import { ModalConfirma } from "../../../components/ModalConfirma";
import { SelectControl } from "../../../components/SelectControl";
import useCR5Axios from "../../../hooks/useCR5Axios";
import { useFuncaoNotificacao } from "../../../hooks/useFuncaoNotificacao";
import { useValidaAcessos } from "../../../hooks/useValidaAcessos.ts";
import useValidaPermissoes from "../../../hooks/useValidaPermissoes";
import ModalDataVencimento from "../../protheus/ContratoRede/components/ModalDataVencimento";
import { ModalPesquisaPessoa } from "../../protheus/ContratoRede/components/ModalPesquisaPessoa";
import ModalDadosGrupo from "./components/ModalDadosGrupo.tsx";
import ModalEditarDadosGrupo from "./components/ModalEditarDadosGrupo.tsx";
import { ModalExluiBoleto } from "./components/ModalExcluiBoleto.tsx";
import { TabelaAgrupado } from "./components/TabelaAgrupado.tsx";
import { TabelaAgrupar } from "./components/TabelaAgrupar.tsx";
import { TabelaGrupoCancelado } from "./components/TabelaGrupoCancelado.tsx";
import { TabelaGrupoPago } from "./components/TabelaGrupoPago.tsx";

export function AgruparParcelas() {
  const itemsPerPage = 10;
  const { axios } = useCR5Axios();
  const notificacao = useFuncaoNotificacao();
  const { isOpen, onOpen, onClose } = useDisclosure();
  const {
    isOpen: isModalDataVencimentoOpen,
    onOpen: onOpenModalDataVencimento,
    onClose: onCloseModalDataVencimento,
  } = useDisclosure();

  const {
    isOpen: isModalEditarGrupoOpen,
    onOpen: onOpenModalEditarGrupo,
    onClose: onCloseModalEditarGrupo,
  } = useDisclosure();

  const {
    isOpen: isModalConfirmaDesagrupar,
    onOpen: onOpenModalConfirmaDesagrupar,
    onClose: onCloseModalConfirmaDesagrupar,
  } = useDisclosure();

  const {
    isOpen: isModalDadosGrupo,
    onOpen: onOpenModalDadosGrupo,
    onClose: onCloseModalDadosGrupo,
  } = useDisclosure();

  const {
    isOpen: isModalExcluiBoletoGrupo,
    onOpen: onOpenModalExcluiBoletoGrupo,
    onClose: onCloseModalExcluiBoletoGrupo,
  } = useDisclosure();

  const [nomeCliente, setNomeCliente] = useState<string | null>(null);
  const [cpfCnpj, setCpfCnpj] = useState<string>("");
  const [dataInicialBusca, setDataInicialBusca] = useState("");
  const [dataFinalBusca, setDataFinalBusca] = useState("");
  const [contIdInicialBusca, setContIdInicialBusca] = useState<number | null>(null);
  const [contIdFinalBusca, setContIdFinalBusca] = useState<number | null>(null);
  const [contProtheusInicialBusca, setContProtheusInicialBusca] = useState("");
  const [contProtheusFinalBusca, setContProtheusFinalBusca] = useState("");
  const [parcelaBusca, setParcelaBusca] = useState<number | null>(null);
  const [produtoBusca, setProdutoBusca] = useState("");
  const [nomeConsumidorBusca, setNomeConsumidorBusca] = useState("");
  const [grupo, setGrupo] = useState<number | null>(null);
  const [isContratoRedeBusca, setIsContratoRedeBusca] = useState<"S" | "N" | undefined>("N");
  const [cobrancaSelecionadaEditar, setCobrancaSelecionadaEditar] =
    useState<CobrancasGrupoDTO | null>(null);
  const [nomePainelBusca, setNomePainelBusca] = useState<string>("agrupar");
  const [currentPage, setCurrentPage] = useState(1);
  const [isEnabled, setIsEnabled] = useState(false);
  const [isEnabledTab, setIsEnabledTab] = useState(false);
  const [cobrancasSelecionadas, setCobrancasSelecionadas] = useState<CobrancasGrupoDTO[]>([]);
  const [tabIndex, setTabIndex] = useState(0);
  const [idCobrancaAgrupada, setIdCobrancaAgrupada] = useState<number | undefined>(undefined);
  const [itemParaExcluir, setItemParaExcluir] = useState<CobrancasGrupoDTO | null>(null);
  const [isLimpar, setIsLimpar] = useState(true);
  const queryClient = useQueryClient();

  const { validaPermissoes } = useValidaPermissoes();

  useValidaAcessos([Acessos.AGRUPAR_PARCELAS]);

  const { data, isFetching, isError, error, refetch } = useQuery({
    queryKey: [
      "fetchCobrancaParaAgruparPaginado",
      currentPage,
      nomeCliente,
      cpfCnpj,
      dataInicialBusca,
      dataFinalBusca,
      contIdInicialBusca,
      contIdFinalBusca,
      contProtheusInicialBusca,
      contProtheusFinalBusca,
      parcelaBusca,
      produtoBusca,
      nomeConsumidorBusca,
      isContratoRedeBusca,
      nomePainelBusca,
      grupo,
    ],
    enabled: isEnabled,
    keepPreviousData: true,
    staleTime: TemposCachePadrao.CURTO,
    cacheTime: TemposCachePadrao.CURTO,
    retry: false,
    queryFn: async () => {
      try {
        const response = await fetchCobrancaParaAgruparPaginado(
          {
            page: currentPage - 1,
            pageSize: itemsPerPage,
            nomeCliente: nomeCliente ?? undefined,
            cpfCnpj: cpfCnpj,
            dataInicial: converteDataStringDate(dataInicialBusca),
            dataFinal: converteDataStringDate(dataFinalBusca),
            contIdInicial: contIdInicialBusca ?? undefined,
            contIdFinal: contIdFinalBusca ?? undefined,
            contProtheusInicial: contProtheusInicialBusca ?? "",
            contProtheusFinal: contProtheusFinalBusca ?? "",
            parcela: parcelaBusca ?? undefined,
            produto: produtoBusca ?? undefined,
            nomeConsumidor: nomeConsumidorBusca,
            isContratoRede: isContratoRedeBusca,
            nomePainel: nomePainelBusca,
            idGrupo: grupo ?? undefined,
          },
          axios
        );
        setIsEnabledTab(true);
        return response;
      } catch (err) {
        setIsEnabledTab(false);
        console.error("Erro na consulta:", err);
      }
    },
  });

  function atualizaValorPesquisa<T>(setter: (arg: T) => void, valor: T): void {
    setter(valor);
    setCurrentPage(1);
  }

  function handlePessoaSelecionada(pessoa: PessoaCr5DTO) {
    setNomeCliente(pessoa.descricao.trim());
    setCpfCnpj(pessoa.cpfCnpj.trim());
    onClose();
  }

  function handleLimparCampo() {
    setIsEnabled(false);
    setIsEnabledTab(false);
    setNomeCliente("");
    setCpfCnpj("");
    setDataInicialBusca("");
    setDataFinalBusca("");
    setContIdInicialBusca(null);
    setContIdFinalBusca(null);
    setContProtheusInicialBusca("");
    setContProtheusFinalBusca("");
    setParcelaBusca(null);
    setProdutoBusca("");
    setNomeConsumidorBusca("");
    setIsContratoRedeBusca(undefined);
    setNomePainelBusca("");
    setCobrancasSelecionadas([]);
    setIsContratoRedeBusca(undefined);
    setGrupo(null);
    queryClient.resetQueries(["fetchCobrancaParaAgruparPaginado"]);
    refetch();
    setTabIndex(0);
  }

  function handlePesquisar() {
    if (validaFiltros()) {
      notificacao({
        tipo: "warning",
        titulo: "Aviso",
        message: "Favor inserir algum filtro!",
      });
    } else {
      setIsEnabled(true);
    }
  }

  function validaFiltros(): boolean {
    return (
      cpfCnpj == "" &&
      dataInicialBusca == "" &&
      dataFinalBusca == "" &&
      contIdInicialBusca == null &&
      contIdFinalBusca == null &&
      isContratoRedeBusca == null &&
      parcelaBusca == null &&
      produtoBusca == "" &&
      nomeConsumidorBusca == "" &&
      grupo == null
    );
  }

  useEffect(() => {
    setIsLimpar(validaFiltros());
  }, [
    cpfCnpj,
    dataInicialBusca,
    dataFinalBusca,
    contIdInicialBusca,
    contIdFinalBusca,
    isContratoRedeBusca,
    parcelaBusca,
    produtoBusca,
    nomeConsumidorBusca,
  ]);

  function handleCheckboxChange(item: CobrancasGrupoDTO) {
    setCobrancasSelecionadas((cobranca) => {
      const exists = cobranca.some(
        (selected) => selected.idCobrancaCliente === item.idCobrancaCliente
      );

      if (exists) {
        return cobranca.filter((selected) => selected.idCobrancaCliente !== item.idCobrancaCliente);
      }

      return [...cobranca, item];
    });
  }

  function handleAGruparParcelas() {
    if (cobrancasSelecionadas.length === 0)
      return notificacao({
        tipo: "warning",
        titulo: "Aviso",
        message: "Por favor, selecione as cobranças.!",
      });

    if (cobrancasSelecionadas.length < 2)
      return notificacao({
        tipo: "warning",
        titulo: "Aviso",
        message: "Necessário marcar duas ou mais cobranças!",
      });

    const existeBoleto = cobrancasSelecionadas.some((cobranca) => cobranca.nossoNumero);

    if (existeBoleto) {
      return notificacao({
        tipo: "warning",
        titulo: "Aviso",
        message: "Necessário excluir o boleto para o agrupamento!",
      });
    }

    onOpenModalDataVencimento();
  }

  function filtrarIdCobrancaCliente(): number[] {
    return cobrancasSelecionadas.map((cobranca) => cobranca.idCobrancaCliente);
  }

  function handleDataSelecionada(dataVencimento: string) {
    const dataConvertida = converteDataStringDate(dataVencimento);

    const listaIdCobrancaCliente: number[] = filtrarIdCobrancaCliente();
    mutateAguparParcelas({ listaIdCobrancaCliente, dataVencimento: dataConvertida });
  }

  const { mutate: mutateAguparParcelas, isLoading: isLoadingAgruparParcelas } = useMutation(
    ({
      listaIdCobrancaCliente,
      dataVencimento,
    }: {
      listaIdCobrancaCliente: number[];
      dataVencimento: Date;
    }) => {
      const params = { listaIdCobrancaCliente, dataVencimento };
      return fetchAgruparParcelas(params, axios);
    },
    {
      onSuccess: () => {
        onCloseModalDataVencimento();
        notificacao({ message: "Salvo com sucesso!", tipo: "success", titulo: "SUCESSO!" });

        refetch();
      },
      onError: (e) => {
        onCloseModalDataVencimento();
        notificacao({
          message: getMensagemDeErroOuProcureSuporte(e),
          tipo: "error",
          titulo: "ERRO!",
        });
        handleLimparCampo();
      },
    }
  );

  function findMenorDataVigenciaCobranca() {
    if (cobrancasSelecionadas.length === 0) return new Date();

    const menorTimestamp = Math.min(
      ...cobrancasSelecionadas.map((item) => new Date(item.dataVencimento).getTime())
    );

    return new Date(menorTimestamp);
  }

  function handleImprimir() {
    notificacao({
      tipo: "warning",
      titulo: "Aviso",
      message: "Em desenvolvimento!",
    });
  }

  const { mutate: mutateExcluirBoletoGrupo, isLoading: isLoadingExcluirBoletoGrupo } = useMutation(
    (item: CobrancasGrupoDTO) => fetchExcluirBoletoGrupo(item.idCobrancaAgrupada, axios),
    {
      onSuccess: () => {
        setItemParaExcluir(null);
        onCloseModalExcluiBoletoGrupo();
        notificacao({
          message: "Boleto excluído com sucesso!",
          tipo: "success",
          titulo: "SUCESSO!",
        });
        refetch();
      },
      onError: (e) => {
        setItemParaExcluir(null);
        onCloseModalExcluiBoletoGrupo();
        notificacao({
          message: getMensagemDeErroOuProcureSuporte(e),
          tipo: "error",
          titulo: "ERRO!",
        });
      },
    }
  );

  function handleExcluirBoleto(item: CobrancasGrupoDTO) {
    if (!validaPermissoes(criaValidacaoTodas([Acessos.CANCELAMENTO_DE_BOLETOS]))) {
      notificacao({
        tipo: "warning",
        titulo: "Aviso",
        message: "Você não tem permissão para essa operação!",
      });
      return;
    }
    setItemParaExcluir(item);
    onOpenModalExcluiBoletoGrupo();
  }

  const { mutate: mutateExcluirBoleto, isLoading: isLoadingExcluirBoletoSimples } = useMutation(
    (item: CobrancasGrupoDTO) => fetchExcluirBoleto(item.idCobrancaCliente, axios),
    {
      onSuccess: () => {
        setItemParaExcluir(null);
        onCloseModalExcluiBoletoGrupo();
        notificacao({
          message: "Boleto excluído com sucesso!",
          tipo: "success",
          titulo: "SUCESSO!",
        });
        refetch();
      },
      onError: (e) => {
        setItemParaExcluir(null);
        onCloseModalExcluiBoletoGrupo();
        notificacao({
          message: getMensagemDeErroOuProcureSuporte(e),
          tipo: "error",
          titulo: "ERRO!",
        });
      },
    }
  );

  function onBuscarGrupo(painel: string) {
    setIsEnabled(true);
    setIsEnabledTab(true);
    setNomePainelBusca(painel);
    setCobrancasSelecionadas([]);
    queryClient.resetQueries(["fetchCobrancaParaAgruparPaginado"]);
    refetch();
  }

  function handleAbrirModalEditarGrupo(item: CobrancasGrupoDTO) {
    setCobrancaSelecionadaEditar(item);
    onOpenModalEditarGrupo();
  }

  const { mutate: mutateAlteraDadosGrupo, isLoading: isLoadingAlteraDadosGrupo } = useMutation(
    ({ dados, idGrupo }: { dados: AlteraDadosGrupoParams; idGrupo: number }) => {
      return fetchAlteraDadosGrupo(dados, idGrupo, axios);
    },
    {
      onSuccess: () => {
        onCloseModalEditarGrupo();
        notificacao({ message: "Salvo com sucesso!", tipo: "success", titulo: "SUCESSO!" });

        refetch();
      },
      onError: (e) => {
        onCloseModalEditarGrupo();
        notificacao({
          message: getMensagemDeErroOuProcureSuporte(e),
          tipo: "error",
          titulo: "ERRO!",
        });
        handleLimparCampo();
      },
    }
  );

  async function handleSalvarEdicao(dadosEditados: Partial<CobrancasGrupoDTO>) {
    const dados = {
      dataVencimento: dadosEditados.dataVencimento,
      notaFiscal: dadosEditados.notaFiscal,
      dataEmissaoNotaFiscal: dadosEditados.dataEmissaoNotaFiscal,
      avisoLancamentoNota: dadosEditados.avisoLancamentoNota,
      dataAvisoLancamentoNota: dadosEditados.dataAvisoLancamentoNota,
    };

    if (!dadosEditados.idCobrancaAgrupada) {
      notificacao({
        message: "Favor realizar a operação novamente!",
        tipo: "warning",
        titulo: "Aviso!",
      });

      return;
    }

    mutateAlteraDadosGrupo({ dados, idGrupo: dadosEditados.idCobrancaAgrupada });
  }

  function handleDesagruparParcelas() {
    if (cobrancasSelecionadas.length === 0) {
      onCloseModalConfirmaDesagrupar();
      return notificacao({
        tipo: "warning",
        titulo: "Aviso",
        message: "Por favor, selecione o grupo!",
      });
    }

    if (cobrancasSelecionadas.length > 1) {
      onCloseModalConfirmaDesagrupar();
      return notificacao({
        tipo: "warning",
        titulo: "Aviso",
        message: "Por favor, selecione um grupo por vez!",
      });
    }

    const existeNossoNumero = cobrancasSelecionadas.some((cobranca) => cobranca.nossoNumero);

    if (existeNossoNumero) {
      onCloseModalConfirmaDesagrupar();
      return notificacao({
        tipo: "warning",
        titulo: "Aviso",
        message: "Exite boleto, necessário a exclusão para continuar o processo!",
      });
    }
    const existeNotaFiscal = cobrancasSelecionadas.some((cobranca) => cobranca.notaFiscal);

    if (existeNotaFiscal) {
      onCloseModalConfirmaDesagrupar();
      return notificacao({
        tipo: "warning",
        titulo: "Aviso",
        message: "Exite Nota Fiscal, necessário a exclusão para continuar o processo!",
      });
    }

    const idGrupo = cobrancasSelecionadas[0]?.idCobrancaAgrupada;

    mutateDesagrupar({ idGrupo });
  }

  const { mutate: mutateDesagrupar, isLoading: isLoadingDesagrupar } = useMutation(
    ({ idGrupo }: { idGrupo: number }) => {
      return fetchDesagrupar(idGrupo, axios);
    },
    {
      onSuccess: () => {
        notificacao({ message: "Salvo com sucesso!", tipo: "success", titulo: "SUCESSO!" });
        refetch();
      },
      onError: (e) => {
        notificacao({
          message: getMensagemDeErroOuProcureSuporte(e),
          tipo: "error",
          titulo: "ERRO!",
        });
      },
    }
  );

  function handleChangeDetalhes(item: CobrancasGrupoDTO) {
    setIdCobrancaAgrupada(item.idCobrancaAgrupada);
    onOpenModalDadosGrupo();
  }

  function handleOnConfirmaExcluirBoleto() {
    if (!itemParaExcluir) return;

    if (itemParaExcluir.idCobrancaAgrupada) {
      mutateExcluirBoletoGrupo(itemParaExcluir);
    } else if (itemParaExcluir.idCobrancaCliente) {
      mutateExcluirBoleto(itemParaExcluir);
    }
  }

  function getIsLoadingExcluirBoleto() {
    if (!itemParaExcluir) return false;

    return itemParaExcluir.idCobrancaAgrupada
      ? isLoadingExcluirBoletoGrupo
      : isLoadingExcluirBoletoSimples;
  }

  return (
    <>
      <CabecalhoPages titulo="Agrupar Parcelas" />
      <Grid
        w="100%"
        templateColumns={{ base: "¨100%", md: "repeat(12, 1fr)" }}
        mt={4}
        pb={{ base: 4, md: 1 }}
      >
        <InputControl
          label="Cliente"
          id="cliente"
          value={nomeCliente || ""}
          numeroColunasMd={12}
          numeroColunasLg={4}
          icon={<FiSearch />}
          onClick={onOpen}
          cursor="pointer"
          naoEditavel
        />
        <InputControl
          numeroColunasMd={12}
          numeroColunasLg={4}
          label="CPF/CNPJ"
          id="cpfCnpj"
          value={mascaraCpfCnpj(cpfCnpj) || ""}
          naoEditavel
          cursor="not-allowed"
        />
        <InputControl
          label="Início"
          id="dataInicial"
          type="date"
          value={dataInicialBusca}
          onChange={(e) => atualizaValorPesquisa(setDataInicialBusca, e.target.value)}
          numeroColunasMd={6}
          numeroColunasLg={2}
        />
        <InputControl
          label="Fim"
          id="dataFinal"
          type="date"
          value={dataFinalBusca}
          onChange={(e) => atualizaValorPesquisa(setDataFinalBusca, e.target.value)}
          numeroColunasMd={6}
          numeroColunasLg={2}
        />
        <InputControl
          label="Contrato CR5"
          id="contIdInicio"
          type="number"
          value={contIdInicialBusca?.toString() ?? ""}
          onChange={(e) =>
            trataValorInputNumberVazio(
              (val) => atualizaValorPesquisa(setContIdInicialBusca, val),
              e
            )
          }
          numeroColunasMd={6}
          numeroColunasLg={2}
        />
        <InputControl
          label="Até"
          id="contIdFinal"
          type="number"
          value={contIdFinalBusca?.toString() ?? ""}
          onChange={(e) =>
            trataValorInputNumberVazio((val) => atualizaValorPesquisa(setContIdFinalBusca, val), e)
          }
          numeroColunasMd={6}
          numeroColunasLg={2}
        />
        <InputControl
          label="Contrato Protheus"
          id="contProtheusInicio"
          type="number"
          value={contProtheusInicialBusca?.toString() ?? ""}
          onChange={(e) => atualizaValorPesquisa(setContProtheusInicialBusca, e.target.value)}
          numeroColunasMd={6}
          numeroColunasLg={2}
        />
        <InputControl
          label="Até"
          id="contProtheusFinal"
          type="number"
          value={contProtheusFinalBusca?.toString() ?? ""}
          onChange={(e) => atualizaValorPesquisa(setContProtheusFinalBusca, e.target.value)}
          numeroColunasMd={6}
          numeroColunasLg={2}
        />
        <SelectControl
          label="Contrato/Rede"
          id="contratoRede"
          options={[
            { value: "S", label: "Sim" },
            { value: "N", label: "Não" },
          ]}
          onChange={(event) =>
            atualizaValorPesquisa(
              setIsContratoRedeBusca,
              (event.length === 0 ? undefined : event) as "S" | "N" | undefined
            )
          }
          numeroColunasMd={12}
          numeroColunasLg={2}
        />
        <InputControl
          label="Parcela"
          id="parcela"
          type="number"
          value={parcelaBusca?.toString() ?? ""}
          onChange={(e) =>
            trataValorInputNumberVazio((val) => atualizaValorPesquisa(setParcelaBusca, val), e)
          }
          numeroColunasMd={4}
          numeroColunasLg={2}
        />
        <InputControl
          label="Produto"
          id="produto"
          value={produtoBusca?.toString() ?? ""}
          onChange={(e) => atualizaValorPesquisa(setProdutoBusca, e.target.value)}
          numeroColunasMd={4}
          numeroColunasLg={4}
        />
        <InputControl
          label="Nome Consumidor"
          id="nomeConsumidor"
          value={nomeConsumidorBusca}
          onChange={(e) => atualizaValorPesquisa(setNomeConsumidorBusca, e.target.value)}
          numeroColunasMd={4}
          numeroColunasLg={4}
        />
        <InputControl
          label="Grupo"
          id="grupo"
          type="number"
          value={grupo?.toString() ?? ""}
          onChange={(e) =>
            trataValorInputNumberVazio((val) => atualizaValorPesquisa(setGrupo, val), e)
          }
          numeroColunasMd={6}
          numeroColunasLg={2}
        />
        <ButtonPesquisarLimpar
          handlePesquisar={handlePesquisar}
          handleLimparCampo={handleLimparCampo}
          isLimpar={isLimpar}
          numeroColunasMd={2}
          numeroColunasLg={1}
        />
      </Grid>
      <Grid
        w="100%"
        templateColumns={{ base: "1fr", md: "repeat(auto-fit, minmax(180px, auto))" }}
        justifyContent={{ base: "stretch", md: "start" }}
        gap={2}
      >
        {tabIndex === 0 && (
          <Button
            colorScheme="blue"
            size="sm"
            minWidth="200px"
            onClick={handleAGruparParcelas}
            leftIcon={<FiLayers />}
            m={1}
            mt={2}
          >
            Agrupar
          </Button>
        )}
        {tabIndex === 1 && (
          <Button
            colorScheme="blue"
            size="sm"
            minWidth="200px"
            onClick={onOpenModalConfirmaDesagrupar}
            leftIcon={<FiLayers />}
            m={1}
            mt={2}
          >
            Desagrupar
          </Button>
        )}
        <Button
          colorScheme="blue"
          size="sm"
          minWidth="200px"
          onClick={handleImprimir}
          leftIcon={<FiPrinter />}
          m={1}
          mt={2}
        >
          Imprimir
        </Button>
        <Button
          colorScheme="blue"
          size="sm"
          minWidth="200px"
          onClick={handleImprimir}
          leftIcon={<FiFileText />}
          m={1}
          mt={2}
        >
          Boletos
        </Button>
      </Grid>
      <Tabs variant="enclosed" mt={5} index={tabIndex} onChange={(index) => setTabIndex(index)}>
        <TabList>
          <Tab
            value="agrupar"
            onClick={() => onBuscarGrupo("agrupar")}
            isDisabled={!isEnabledTab || data?.total === undefined}
          >
            A agrupar
          </Tab>
          <Tab
            value="agrupado"
            onClick={() => onBuscarGrupo("grupo")}
            isDisabled={!isEnabledTab || data?.total === undefined}
          >
            Agrupado
          </Tab>
          <Tab
            value="agrupadoPago"
            onClick={() => onBuscarGrupo("agrupadoPago")}
            isDisabled={!isEnabledTab || data?.total === undefined}
          >
            Agrupado Pago
          </Tab>
          <Tab
            value="grupoCancelado"
            onClick={() => onBuscarGrupo("grupoCancelado")}
            isDisabled={!isEnabledTab || data?.total === undefined}
          >
            Grupo Cancelado
          </Tab>
        </TabList>
        <TabPanels>
          <TabPanel>
            <TabelaAgrupar
              data={data}
              isFetching={isFetching}
              isError={isError}
              error={error}
              itemsPerPage={itemsPerPage}
              currentPage={currentPage}
              setCurrentPage={setCurrentPage}
              totalItems={data?.total}
              handleCheckboxChange={handleCheckboxChange}
              cobrancasSelecionadas={cobrancasSelecionadas}
              handleExcluirBoleto={handleExcluirBoleto}
            />
          </TabPanel>
          <TabPanel>
            <TabelaAgrupado
              data={data}
              isFetching={isFetching}
              isError={isError}
              error={error}
              itemsPerPage={itemsPerPage}
              currentPage={currentPage}
              setCurrentPage={setCurrentPage}
              totalItems={data?.total}
              handleCheckboxChange={handleCheckboxChange}
              cobrancasSelecionadas={cobrancasSelecionadas}
              handleExcluirBoleto={handleExcluirBoleto}
              handleChangeDetalhes={handleChangeDetalhes}
              handleAbrirModalEditarGrupo={handleAbrirModalEditarGrupo}
            />
          </TabPanel>
          <TabPanel>
            <TabelaGrupoPago
              data={data}
              isFetching={isFetching}
              isError={isError}
              error={error}
              itemsPerPage={itemsPerPage}
              currentPage={currentPage}
              setCurrentPage={setCurrentPage}
              totalItems={data?.total}
              handleChangeDetalhes={handleChangeDetalhes}
              handleAbrirModalEditarGrupo={handleAbrirModalEditarGrupo}
            />
          </TabPanel>
          <TabPanel>
            <TabelaGrupoCancelado
              data={data}
              isFetching={isFetching}
              isError={isError}
              error={error}
              itemsPerPage={itemsPerPage}
              currentPage={currentPage}
              setCurrentPage={setCurrentPage}
              totalItems={data?.total}
              handleChangeDetalhes={handleChangeDetalhes}
            />
          </TabPanel>
        </TabPanels>
      </Tabs>

      <ModalPesquisaPessoa
        isOpen={isOpen}
        onClose={onClose}
        onPessoaSelecionada={handlePessoaSelecionada}
      />

      <ModalDataVencimento
        isOpen={isModalDataVencimentoOpen}
        onClose={onCloseModalDataVencimento}
        onDataSelecionada={handleDataSelecionada}
        dataMax={findMenorDataVigenciaCobranca()}
        isLoanding={isLoadingAgruparParcelas}
      />

      <ModalEditarDadosGrupo
        isOpen={isModalEditarGrupoOpen}
        onClose={onCloseModalEditarGrupo}
        onSalvar={handleSalvarEdicao}
        onItemSelecionada={cobrancaSelecionadaEditar}
        isLoading={isLoadingAlteraDadosGrupo}
      />

      <ModalConfirma
        titulo="Atenção"
        texto={"Deseja realmente desfazer o grupo?"}
        onClose={onCloseModalConfirmaDesagrupar}
        isOpen={isModalConfirmaDesagrupar}
        onConfirm={handleDesagruparParcelas}
        isLoading={isLoadingDesagrupar}
      />

      <ModalExluiBoleto
        titulo="Confirma"
        texto="Tem certeza que deseja excluir este boleto?"
        isOpen={isModalExcluiBoletoGrupo}
        onClose={onCloseModalExcluiBoletoGrupo}
        onConfirm={handleOnConfirmaExcluirBoleto}
        item={itemParaExcluir}
        isLoading={getIsLoadingExcluirBoleto()}
      />

      <ModalDadosGrupo
        isOpen={isModalDadosGrupo}
        onClose={onCloseModalDadosGrupo}
        idCobrancaAgrupada={idCobrancaAgrupada}
      />
    </>
  );
}
