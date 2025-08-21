import { Button, Checkbox, Grid, GridItem, useDisclosure } from "@chakra-ui/react";
import { useMutation, useQuery, useQueryClient } from "@tanstack/react-query";
import { useState } from "react";
import { FiPlusSquare } from "react-icons/fi";
import { CobrancaClienteAdicionarDTO } from "../../../../models/DTOs/CobrancaCliente/CobrancaClienteAdicionarDTO.ts";
import { situacoesCobrancaClienteValues } from "../../../../models/DTOs/Cobrancas/SituacaoCobrancaCliente.ts";
import {
  fetchAdicionarParcela,
  fetchBuscaDadosContratoPaginado,
  fetchDeletaCobrancaCliente,
} from "../../../../requests/requestsCobrancasCliente.ts";
import Acessos from "../../../../utils/Acessos.ts";
import { trataValorInputNumberVazio } from "../../../../utils/componentUtil.ts";
import { TemposCachePadrao } from "../../../../utils/constantes";
import {
  getMensagemDeErroOuProcureSuporte,
  getTipoPorCodigoRetorno,
  getTituloPorCodigoRetorno,
} from "../../../../utils/errors.ts";
import {
  formatarDataBrasil,
  formatarMoedaBrasil,
  mascaraCpfCnpj,
} from "../../../../utils/mascaras";
import { ButtonPesquisarLimpar } from "../../../components/ButoonControl/ButtonPesquisarLimpar.tsx";
import { CabecalhoPages } from "../../../components/CabecalhoPages";
import { DropBoxSistemas } from "../../../components/DropBoxSistemas/DropBoxSistemas.tsx";
import { InputControl } from "../../../components/InputControl";
import { ModalConfirma } from "../../../components/ModalConfirma/ModalConfirma.tsx";
import { TabelaCR5 } from "../../../components/Tabelas";
import useCR5Axios from "../../../hooks/useCR5Axios";
import { useFuncaoNotificacao } from "../../../hooks/useFuncaoNotificacao";
import { useValidaAcessos } from "../../../hooks/useValidaAcessos.ts";
import ModalQuantidadeParcelas from "./components/ModalQuantidadeParcelas.tsx";

export function AdicionaParcela() {
  const itemsPerPage = 10;
  const { axios } = useCR5Axios();
  const notificacao = useFuncaoNotificacao();
  const { isOpen, onOpen, onClose } = useDisclosure();

  const {
    isOpen: isModalConfirma,
    onOpen: onOpenModalConfirma,
    onClose: onCloseModalConfirma,
  } = useDisclosure();

  const [idContrato, setIdContrato] = useState<number | null>(null);
  const [idSistema, setIdSistema] = useState<number | null>(null);
  const [currentPage, setCurrentPage] = useState(1);
  const [cobrancasSelecionadas, setCobrancasSelecionadas] = useState<CobrancaClienteAdicionarDTO[]>(
    []
  );
  const [itemSelecionado, setItemSelecionado] = useState<CobrancaClienteAdicionarDTO>();
  const queryClient = useQueryClient();

  useValidaAcessos([Acessos.ADICIONAR_PARCELA]);

  const { data, isFetching, isError, error, refetch } = useQuery({
    queryKey: ["fetchBuscaDadosContratoPaginado", currentPage, idContrato, idSistema],
    keepPreviousData: true,
    enabled: false,
    staleTime: TemposCachePadrao.CURTO,
    retry: false,
    queryFn: () => {
      return fetchBuscaDadosContratoPaginado(
        {
          page: currentPage - 1,
          pageSize: itemsPerPage,
          contId: idContrato ?? undefined,
          idSistema: idSistema ?? undefined,
        },
        axios
      );
    },
  });

  function atualizaValorPesquisa<T>(setter: (arg: T) => void, valor: T): void {
    setter(valor);
    setCurrentPage(1);
  }

  function handleLimparCampo() {
    setIdContrato(null);
    setIdSistema(null);
    setCurrentPage(1);

    setCobrancasSelecionadas([]);
    queryClient.resetQueries(["fetchBuscaDadosContratoPaginado"]);
    refetch();
  }

  function handlePesquisar() {
    if (idSistema == 25) {
      return notificacao({
        tipo: "warning",
        titulo: "Aviso",
        message: "Cadin não é permitido a alteração da parcela!",
      });
    }

    if (!idContrato || !idSistema) {
      return notificacao({
        tipo: "warning",
        titulo: "Aviso",
        message: "Favor informar um contrato!",
      });
    }

    refetch();
  }

  function handleCheckboxChange(item: CobrancaClienteAdicionarDTO) {
    setCobrancasSelecionadas((cobranca) => {
      if (cobranca.includes(item)) {
        return cobranca.filter((selected) => selected !== item);
      }
      return [...cobranca, item];
    });
  }

  function handleAdicionar() {
    if (cobrancasSelecionadas.length !== 1) {
      return notificacao({
        tipo: "warning",
        titulo: "Aviso",
        message:
          cobrancasSelecionadas.length === 0
            ? "Favor selecionar uma cobrança!"
            : "Favor selecionar apenas uma cobrança!",
      });
    }

    onOpen();
  }

  async function inserirParcelas(valor: number) {
    const { idCobrancaCliente, idInterfaceCobranca } = prepararValores(cobrancasSelecionadas);
    mutate({ idCobrancaCliente, idInterfaceCobranca, qtdParcela: valor });
  }

  function prepararValores(cobrancasSelecionadas: CobrancaClienteAdicionarDTO[]) {
    const item = cobrancasSelecionadas[0];
    return {
      idCobrancaCliente: item.idCobrancaCliente,
      idInterfaceCobranca: item.idInterface,
    };
  }

  const { mutate, isLoading } = useMutation(
    ({
      idCobrancaCliente,
      idInterfaceCobranca,
      qtdParcela,
    }: {
      idCobrancaCliente: number;
      idInterfaceCobranca: number;
      qtdParcela: number;
    }) => {
      const params = { idCobrancaCliente, idInterfaceCobranca, qtdParcela };
      return fetchAdicionarParcela(params, axios);
    },
    {
      onSuccess: () => {
        onClose();
        notificacao({ message: "Salvo com sucesso!", tipo: "success", titulo: "SUCESSO!" });
        setCobrancasSelecionadas([]);
        refetch();
      },
      onError: (e) => {
        notificacao({
          message: getMensagemDeErroOuProcureSuporte(e),
          tipo: getTipoPorCodigoRetorno(e),
          titulo: getTituloPorCodigoRetorno(e),
        });
        onClose();
        handleLimparCampo();
      },
    }
  );

  const { mutate: mutateDeletaCobranca, isLoading: isLoadingDeletaCobranca } = useMutation(
    (idCobrancaCliente: number) => {
      return fetchDeletaCobrancaCliente({ idCobrancaCliente: idCobrancaCliente }, axios);
    },
    {
      onSuccess: async () => {
        notificacao({ message: "Excluído com sucesso!", tipo: "success", titulo: "SUCESSO!" });

        setCobrancasSelecionadas([]);
        refetch();
        onCloseModalConfirma();
      },
      onError: (e) => {
        notificacao({
          message: getMensagemDeErroOuProcureSuporte(e),
          tipo: getTipoPorCodigoRetorno(e),
          titulo: getTituloPorCodigoRetorno(e),
        });
      },
    }
  );

  function handleDeleteCobrancaCliente() {
    if (!itemSelecionado) {
      return notificacao({
        tipo: "warning",
        titulo: "Aviso",
        message: "Nenhuma cobrança selecionada!",
      });
    }

    if (itemSelecionado?.numParcela == 1) {
      onCloseModalConfirma();
      return notificacao({
        tipo: "warning",
        titulo: "Aviso",
        message: "Não pode excluir a parcela principal!",
      });
    }

    const parcelasOrdenadas = [...(data?.result ?? [])].sort((a, b) => a.numParcela - b.numParcela);
    const existeParcelaMaior = parcelasOrdenadas.some(
      (parcela) => parcela.numParcela > itemSelecionado.numParcela
    );

    if (existeParcelaMaior) {
      onCloseModalConfirma();
      return notificacao({
        tipo: "warning",
        titulo: "Aviso",
        message: `Não pode excluir a parcela ${itemSelecionado.numParcela} antes das parcelas seguintes!`,
      });
    }

    if (itemSelecionado) {
      mutateDeletaCobranca(itemSelecionado.idCobrancaCliente);
    }
  }

  return (
    <>
      <CabecalhoPages titulo="Adiciona Parcela" />
      <Grid
        w="100%"
        templateColumns={{ base: "¨100%", md: "repeat(12, 1fr)" }}
        mt={4}
        pb={{ base: 4, md: 1 }}
      >
        <InputControl
          label="Contrato CR5"
          id="contrato"
          type="number"
          value={idContrato?.toString() || ""}
          numeroColunasMd={4}
          numeroColunasLg={2}
          onChange={(e) =>
            trataValorInputNumberVazio((val) => atualizaValorPesquisa(setIdContrato, val), e)
          }
        />
        <DropBoxSistemas
          value={idSistema}
          onChange={(idSistema) => atualizaValorPesquisa(setIdSistema, idSistema)}
          numeroColunasMd={8}
          numeroColunasLg={4}
        />
        <ButtonPesquisarLimpar
          handlePesquisar={handlePesquisar}
          handleLimparCampo={handleLimparCampo}
          numeroColunasMd={2}
          numeroColunasLg={1}
        />
      </Grid>
      <Grid
        w="100%"
        templateColumns={{ base: "¨100%", md: "repeat(12, 1fr)" }}
        mt={4}
        pb={{ base: 4, md: 1 }}
      >
        <GridItem colSpan={{ base: 1, md: 4, lg: 2 }} m={1} mb={2} alignContent={"end"}>
          <Button
            colorScheme="blue"
            size="sm"
            w={"100%"}
            onClick={handleAdicionar}
            leftIcon={<FiPlusSquare />}
            isDisabled={data?.total === undefined}
          >
            Adicionar
          </Button>
        </GridItem>
      </Grid>
      <TabelaCR5
        cabecalhos={[
          {
            titulo: "",
            dadoBuilder: (item: CobrancaClienteAdicionarDTO) => (
              <Checkbox
                checked={cobrancasSelecionadas.some(
                  (selected) => selected.idCobrancaCliente === item.idCobrancaCliente
                )}
                onChange={() => handleCheckboxChange(item)}
                isDisabled={!!item.idBoleto}
              />
            ),
          },
          {
            titulo: "RESPONSÁVEL FINANCEIRO",
            dadoBuilder: (item) => item.sacadoNome,
            alinhamento: "left",
          },
          {
            titulo: "CPF/CNPJ",
            dadoBuilder: (item) => mascaraCpfCnpj(item.sacadoCpfCnpj),
          },
          {
            titulo: "ENTIDADE",
            dadoBuilder: (item) => item.nomeEntidade,
          },
          {
            titulo: "Cont Protheus",
            dadoBuilder: (item) => item.contratoProtheus,
          },
          {
            titulo: "PARCELA",
            dadoBuilder: (item) => item.numParcelaProtheus.toString(),
          },
          {
            titulo: "PARCELA CR5",
            dadoBuilder: (item) => item.numParcela.toString(),
          },
          {
            titulo: "SITUAÇÃO",
            dadoBuilder: (item) => situacoesCobrancaClienteValues[item.situacao].descricao,
          },
          {
            titulo: "DT. VENCTO",
            dadoBuilder: (item) => formatarDataBrasil(item.dataVencimento),
          },
          {
            titulo: "VL. COBRANÇA",
            dadoBuilder: (item) => formatarMoedaBrasil(item.valorCobranca),
          },
        ]}
        data={data?.result ?? undefined}
        keybuilder={(item) => item.idCobrancaCliente}
        isFetching={isFetching}
        isError={isError}
        error={error}
        onDelete={(item) => {
          setItemSelecionado(item);
          onOpenModalConfirma();
        }}
        totalPages={data?.pageTotal}
        itemsPerPage={itemsPerPage}
        currentPage={currentPage ?? undefined}
        setCurrentPage={setCurrentPage ?? undefined}
        totalItems={data?.total}
      />
      <ModalQuantidadeParcelas
        isOpen={isOpen}
        onClose={onClose}
        isLoanding={isLoading}
        onQtdParcelaSelecionada={inserirParcelas}
      />

      <ModalConfirma
        titulo="Atenção"
        texto={"Deseja realmente excluir a Cobrança?"}
        onClose={onCloseModalConfirma}
        isOpen={isModalConfirma}
        onConfirm={handleDeleteCobrancaCliente}
        isLoading={isLoadingDeletaCobranca}
      />
    </>
  );
}
