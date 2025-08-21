import { Button, Checkbox, Grid, GridItem, useDisclosure } from "@chakra-ui/react";
import { useMutation, useQuery, useQueryClient } from "@tanstack/react-query";
import { useState } from "react";
import { FiArrowRight, FiSearch, FiTrash } from "react-icons/fi";
import { situacoesCobrancaClienteValues } from "../../../../models/DTOs/Cobrancas/SituacaoCobrancaCliente.ts";
import { CobrancaParaContratoEmRedeDTO } from "../../../../models/DTOs/CobrancasAgrupadas/CobrancasParaContratoRedeDTO";
import { PessoaCr5DTO } from "../../../../models/DTOs/Outros/PessoaCr5DTO.ts";
import {
  fetchAgruparParcelasProtheus,
  fetchCobrancaParaContratoEmRedePaginado,
} from "../../../../requests/requestCobrancasAgrupadas";
import Acessos from "../../../../utils/Acessos.ts";
import { TemposCachePadrao } from "../../../../utils/constantes";
import { converteDataStringDate } from "../../../../utils/date";
import { getMensagemDeErroOuProcureSuporte } from "../../../../utils/errors";
import {
  formatarDataBrasil,
  formatarMoedaBrasil,
  mascaraCpfCnpj,
} from "../../../../utils/mascaras";
import { CabecalhoPages } from "../../../components/CabecalhoPages";
import { InputControl } from "../../../components/InputControl";
import { TabelaCR5 } from "../../../components/Tabelas";
import useCR5Axios from "../../../hooks/useCR5Axios";
import { useFuncaoNotificacao } from "../../../hooks/useFuncaoNotificacao";
import { useValidaAcessos } from "../../../hooks/useValidaAcessos.ts";
import ModalDataVencimento from "./components/ModalDataVencimento";
import { ModalPesquisaPessoa } from "./components/ModalPesquisaPessoa";

export function ContratoRede() {
  const itemsPerPage = 10;
  const { axios } = useCR5Axios();
  const notificacao = useFuncaoNotificacao();
  const { isOpen, onOpen, onClose } = useDisclosure();
  const {
    isOpen: isModalDataVencimentoOpen,
    onOpen: onOpenModalDataVencimento,
    onClose: onCloseModalDataVencimento,
  } = useDisclosure();

  const [proposta, setProposta] = useState<string>("");
  const [nomePessoa, setNomePessoa] = useState<string | null>(null);
  const [cpfCnpj, setCpfCnpj] = useState<string>("");
  const [currentPage, setCurrentPage] = useState(1);
  const [isEnabled, setIsEnabled] = useState(false);
  const [filtros, setFiltros] = useState({
    proposta: "",
    cpfCnpj: "",
  });
  const [cobrancasSelecionadas, setCobrancasSelecionadas] = useState<
    CobrancaParaContratoEmRedeDTO[]
  >([]);
  const [dataVencimentoSelecionada, setDataVencimentoSelecionada] = useState<Date | null>(null);

  const queryClient = useQueryClient();

  useValidaAcessos([Acessos.AGRUPAR_PARCELAS]);

  const { data, isFetching, isError, error } = useQuery({
    queryKey: ["fetchCobrancaParaContratoEmRedePaginado", currentPage, filtros],
    keepPreviousData: true,
    enabled: isEnabled,
    staleTime: TemposCachePadrao.CURTO,
    cacheTime: TemposCachePadrao.CURTO,
    retry: false,
    queryFn: () => {
      return fetchCobrancaParaContratoEmRedePaginado(
        {
          page: currentPage - 1,
          pageSize: itemsPerPage,
          proposta: filtros.proposta,
          cpfCnpj: filtros.cpfCnpj,
        },
        axios
      );
    },
  });

  function atualizaValorPesquisa<T>(setter: (arg: T) => void, valor: T): void {
    setter(valor);
    setCurrentPage(1);
  }

  function handlePessoaSelecionada(pessoa: PessoaCr5DTO) {
    setNomePessoa(pessoa.descricao);
    setCpfCnpj(pessoa.cpfCnpj);
    onClose();
  }

  function handleLimparCampo() {
    setProposta("");
    setNomePessoa("");
    setCpfCnpj("");
    setFiltros({
      proposta: "",
      cpfCnpj: "",
    });
    setCobrancasSelecionadas([]);
    queryClient.removeQueries(["fetchCobrancaParaContratoEmRedePaginado", currentPage, filtros]);
  }

  function handlePesquisar() {
    if (proposta == "") {
      notificacao({
        tipo: "warning",
        titulo: "Aviso",
        message: "Favor informar o código da proposta!",
      });
    } else if (cpfCnpj == "") {
      notificacao({
        tipo: "warning",
        titulo: "Aviso",
        message: "Favor selecionar o cliente!",
      });
    } else {
      setFiltros({
        proposta: proposta,
        cpfCnpj: cpfCnpj,
      });
      setIsEnabled(true);
    }
  }

  function handleCheckboxChange(item: CobrancaParaContratoEmRedeDTO) {
    setCobrancasSelecionadas((cobranca) => {
      if (cobranca.includes(item)) {
        return cobranca.filter((selected) => selected !== item);
      }
      return [...cobranca, item];
    });
  }

  function handleCriarContratoRede() {
    if (cobrancasSelecionadas.length === 0)
      return notificacao({
        tipo: "warning",
        titulo: "Aviso",
        message: "Por favor, selecione as cobranças!",
      });

    if (cobrancasSelecionadas.length < 2)
      return notificacao({
        tipo: "warning",
        titulo: "Aviso",
        message: "Necessário marcar duas ou mais cobranças!",
      });

    console.log("verificar cobrancas selecionadas : ");
    console.log(cobrancasSelecionadas);

    const entidades = filtrarEntidades();

    const entidadeUnica = new Set(entidades);

    console.log(entidadeUnica);

    if (entidadeUnica.size > 1) {
      return notificacao({
        tipo: "warning",
        titulo: "Aviso",
        message: "As cobranças selecionadas pertencem a entidades diferentes.",
      });
    }

    onOpenModalDataVencimento();
  }

  function filtrarEntidades(): string[] {
    return cobrancasSelecionadas.map((cobranca) => cobranca.unidade.entidade);
  }

  function filtrarIdInterfaces(): number[] {
    return cobrancasSelecionadas.map((cobranca) => cobranca.idInterface);
  }

  function handleDataSelecionada(data: string) {
    setDataVencimentoSelecionada(converteDataStringDate(data));
    const listaIdInterfaces: number[] = filtrarIdInterfaces();
    agruparParcelasProtheus(listaIdInterfaces, dataVencimentoSelecionada!);
  }

  function agruparParcelasProtheus(listaIdInterfaces: number[], dataVencimento: Date) {
    mutate({ listaIdInterfaces, dataVencimento });
  }

  const { mutate, isLoading } = useMutation(
    ({
      listaIdInterfaces,
      dataVencimento,
    }: {
      listaIdInterfaces: number[];
      dataVencimento: Date;
    }) => {
      const params = { listaIdInterfaces, dataVencimento };
      return fetchAgruparParcelasProtheus(params, axios);
    },
    {
      onSuccess: () => {
        onCloseModalDataVencimento();
        notificacao({ message: "Salvo com sucesso!", tipo: "success", titulo: "SUCESSO!" });

        handleLimparCampo();
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

  function findMenorDataVencimento() {
    if (cobrancasSelecionadas.length === 0) return new Date();
    return cobrancasSelecionadas.reduce((menorData, item) => {
      return item.dataVencimento < menorData ? item.dataVencimento : menorData;
    }, new Date(cobrancasSelecionadas[0].dataVencimento));
  }

  console.log(" resultado ...: ");
  console.log(data?.result);

  return (
    <>
      <CabecalhoPages titulo="Criar Contratos em Rede" />
      <Grid
        w="100%"
        templateColumns={{ base: "¨100%", md: "repeat(12, 1fr)" }}
        mt={4}
        pb={{ base: 4, md: 1 }}
      >
        <InputControl
          label="Proposta"
          id="proposta"
          type="string"
          value={proposta || ""}
          numeroColunasMd={4}
          numeroColunasLg={2}
          onChange={(e) => atualizaValorPesquisa(setProposta, e.target.value)}
        />
        <InputControl
          label="Cliente"
          id="pessoa"
          value={nomePessoa || ""}
          numeroColunasMd={8}
          numeroColunasLg={5}
          icon={<FiSearch />}
          onClick={onOpen}
          cursor="pointer"
          naoEditavel
        />
        <InputControl
          numeroColunasMd={10}
          numeroColunasLg={3}
          label="CPF/CNPJ"
          id="cpfCnpj"
          value={cpfCnpj || ""}
          naoEditavel
        />
        <GridItem colSpan={{ base: 1, md: 1, lg: 1 }} m={1} mb={2} alignContent={"end"}>
          <Button colorScheme="blue" size="sm" w="100%" onClick={() => handlePesquisar()}>
            <FiSearch style={{ marginRight: "0.2rem" }} />
            Pesquisar
          </Button>
        </GridItem>
        <GridItem colSpan={{ base: 1, md: 1, lg: 1 }} m={1} mb={2} alignContent={"end"}>
          <Button colorScheme="gray" size="sm" w="100%" onClick={() => handleLimparCampo()}>
            <FiTrash style={{ marginRight: "0.2rem" }} />
            Limpar
          </Button>
        </GridItem>
      </Grid>
      <Grid
        w="100%"
        templateColumns={{ base: "1fr", md: "repeat(auto-fit, minmax(180px, auto))" }}
        justifyContent={{ base: "stretch", md: "start" }}
        gap={2}
        mb={5}
      >
        <Button
          colorScheme="blue"
          size="sm"
          minWidth="200px"
          onClick={handleCriarContratoRede}
          leftIcon={<FiArrowRight />}
          m={1}
          mt={2}
        >
          Criar Contrato Rede
        </Button>
      </Grid>
      <TabelaCR5
        cabecalhos={[
          {
            titulo: "",
            dadoBuilder: (item: CobrancaParaContratoEmRedeDTO) => (
              <Checkbox
                onChange={() => handleCheckboxChange(item)}
                checked={cobrancasSelecionadas.some(
                  (selected) => selected.idCobrancaCliente === item.idCobrancaCliente
                )}
                disabled={
                  situacoesCobrancaClienteValues[item.situacao]?.descricao === "Administrado Cadin"
                }
              />
            ),
          },
          {
            titulo: "CONTRATO PROTHEUS",
            dadoBuilder: (item) => item.contratoProtheus,
            tamanho: "130px",
          },
          {
            titulo: "UNID.",
            dadoBuilder: (item) => item.unidade.codigoUnidade.toString(),
            tamanho: "50px",
          },
          {
            titulo: "CONTRATO PRODUÇÃO",
            dadoBuilder: (item) => item.contratoProducao.toString(),
            tamanho: "90px",
          },
          {
            titulo: "DT. VENCTO",
            dadoBuilder: (item) => formatarDataBrasil(item.dataVencimento),
            tamanho: "90px",
          },
          {
            titulo: "PARCELA",
            dadoBuilder: (item) => item.parcela.toString(),
            tamanho: "75px",
          },
          {
            titulo: "SITUAÇÃO",
            dadoBuilder: (item) => situacoesCobrancaClienteValues[item.situacao].descricao,
            tamanho: "100px",
          },
          {
            titulo: "CPF/CNPJ",
            dadoBuilder: (item) => mascaraCpfCnpj(item.cpfCnpjResponsavelFinanceiro),
            tamanho: "180px",
          },
          {
            titulo: "RESPONSÁVEL FINANCEIRO",
            dadoBuilder: (item) => item.nomeResponsavelFinanceiro,
            tamanho: "300px",
          },
          {
            titulo: "VL. COBRANÇA",
            dadoBuilder: (item) => formatarMoedaBrasil(item.valorCobranca),
            tamanho: "120px",
          },
          {
            titulo: "JUROS/MULTAS",
            dadoBuilder: (item) => formatarMoedaBrasil(item.jurosEMultas),
            tamanho: "120px",
          },
          {
            titulo: "DESCONTOS",
            dadoBuilder: (item) => formatarMoedaBrasil(item.descontos),
            tamanho: "100px",
          },
          {
            titulo: "VL TOTAL PARCELA",
            dadoBuilder: (item) => formatarMoedaBrasil(item.valorTotalParcela),
            tamanho: "100px",
          },
        ]}
        data={data?.result ?? undefined}
        keybuilder={(item) => item.idCobrancaCliente}
        isFetching={isFetching}
        isError={isError}
        error={error}
        totalPages={data?.pageTotal}
        itemsPerPage={itemsPerPage}
        currentPage={currentPage ?? undefined}
        setCurrentPage={setCurrentPage ?? undefined}
        totalItems={data?.total}
      />
      <ModalPesquisaPessoa
        isOpen={isOpen}
        onClose={onClose}
        onPessoaSelecionada={handlePessoaSelecionada}
      />

      <ModalDataVencimento
        isOpen={isModalDataVencimentoOpen}
        onClose={onCloseModalDataVencimento}
        onDataSelecionada={handleDataSelecionada}
        dataMax={findMenorDataVencimento()}
        isLoanding={isLoading}
      />
    </>
  );
}
