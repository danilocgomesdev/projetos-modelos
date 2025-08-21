import { Grid, useDisclosure } from "@chakra-ui/react";
import { useMutation, useQuery } from "@tanstack/react-query";
import { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import { fetchAgenciasPaginado, fetchDeleteAgencias } from "../../../../requests/requestsAgencias";
import queryClient from "../../../../singletons/reactQueryClient.ts";
import Acessos from "../../../../utils/Acessos.ts";
import { trataValorInputNumberVazio } from "../../../../utils/componentUtil.ts";
import { DelaysDebouncePadrao, TemposCachePadrao } from "../../../../utils/constantes";
import { getMensagemDeErroOuProcureSuporte } from "../../../../utils/errors";
import { mascaraCNPJ } from "../../../../utils/mascaras";
import { ButtonControl } from "../../../components/ButoonControl";
import { ButtonPesquisarLimpar } from "../../../components/ButoonControl/ButtonPesquisarLimpar.tsx";
import { CabecalhoPages } from "../../../components/CabecalhoPages";
import { InputControl } from "../../../components/InputControl";
import { ModalConfirma } from "../../../components/ModalConfirma";
import { ModalRelatorio } from "../../../components/ModalRelatorio/ModalRelatorio.tsx";
import { TabelaCR5 } from "../../../components/Tabelas";
import useCR5Axios from "../../../hooks/useCR5Axios";
import useDebounce from "../../../hooks/useDebounce";
import { useFuncaoNotificacao } from "../../../hooks/useFuncaoNotificacao.tsx";
import { useValidaAcessos } from "../../../hooks/useValidaAcessos.ts";
import { useAgenciaStore } from "./store/AgenciaStore.tsx";

export function Agencias() {
  const { axios } = useCR5Axios();
  const itemsPerPage = 7;
  const delayDebounce = DelaysDebouncePadrao.PADRAO;
  const navigate = useNavigate();
  const { isOpen, onOpen, onClose } = useDisclosure();
  const {
    isOpen: isOpenModalRelatorio,
    onOpen: onOpenModalRelatorio,
    onClose: onCloseModalRelatorio,
  } = useDisclosure();
  const [invalidando, setInvalidando] = useState(true);

  const [currentPage, setCurrentPage] = useState(1);
  const [numeroBusca, setNumeroBusca] = useState("");
  const [nomeBusca, setNomeBusca] = useState("");
  const [idBusca, setIdBusca] = useState<number | null>(null);
  const [cidadeBusca, setCidadeBusca] = useState("");
  const [cnpjBusca, setCnpjBusca] = useState("");
  const [nomeBancoBusca, setNomeBancoBusca] = useState("");

  const [numeroDebounce] = useDebounce(numeroBusca, delayDebounce);
  const [nomeDebounce] = useDebounce(nomeBusca, delayDebounce);
  const [idDebounce] = useDebounce(idBusca, delayDebounce);
  const [cidadeDebounce] = useDebounce(cidadeBusca, delayDebounce);
  const [cnpjDebounce] = useDebounce(cnpjBusca, delayDebounce);
  const [nomeBancoDebounce] = useDebounce(nomeBancoBusca, delayDebounce);
  const [isLimpar, setIsLimpar] = useState(true);
  const { agencia, setAgencia } = useAgenciaStore();
  const notificacao = useFuncaoNotificacao();
  const [validouPermissao, setValidouPermissao] = useState(false);

  useValidaAcessos([Acessos.AGENCIAS], () => {
    setValidouPermissao(true);
    queryClient
      .invalidateQueries({ queryKey: ["fetchAgenciasPaginado"] })
      .then(() => setInvalidando(false));
  });

  const { data, refetch, error, isError, isFetching } = useQuery({
    queryKey: [
      "fetchAgenciasPaginado",
      currentPage,
      nomeDebounce,
      idDebounce,
      numeroDebounce,
      cidadeDebounce,
      cnpjDebounce,
      nomeBancoDebounce,
    ],
    keepPreviousData: true,
    enabled: !invalidando && validouPermissao,
    staleTime: TemposCachePadrao.CURTO,
    cacheTime: TemposCachePadrao.CURTO,
    queryFn: () => {
      return fetchAgenciasPaginado(
        {
          page: currentPage - 1,
          pageSize: itemsPerPage,
          id: idDebounce ?? undefined,
          numero: numeroDebounce,
          nome: nomeDebounce,
          cidade: cidadeDebounce,
          cnpj: cnpjDebounce,
          nomeBanco: nomeBancoDebounce,
        },
        axios
      );
    },
  });

  function atualizaValorPesquisa<T>(setter: (arg: T) => void, valor: T): void {
    setter(valor);
    setCurrentPage(1);
  }

  const { mutate, isLoading: deleteAgenciaLoading } = useMutation(
    (idAgencia: number) => {
      return fetchDeleteAgencias({ idAgencia: idAgencia }, axios);
    },
    {
      onSuccess: () => {
        notificacao({ message: "Excluído com sucesso!", tipo: "success", titulo: "SUCESSO!" });
        queryClient
          .invalidateQueries({ queryKey: ["fetchAgenciasPaginado"] })
          .then(() => refetch());
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

  function handleDeleteAgencia() {
    if (agencia) {
      mutate(agencia.id);
    }
  }

  function handleLimparCampo() {
    setIdBusca(null);
    setNumeroBusca("");
    setNomeBusca("");
    setCidadeBusca("");
    setCnpjBusca("");
    setNomeBancoBusca("");
    queryClient.resetQueries(["fetchAgenciasPaginado"]);
    refetch();
  }

  function validaFiltros(): boolean {
    return (
      idBusca == null &&
      numeroBusca === "" &&
      nomeBusca === "" &&
      cidadeBusca === "" &&
      cnpjBusca === "" &&
      nomeBancoBusca === ""
    );
  }

  useEffect(() => {
    setIsLimpar(validaFiltros());
  }, [idBusca, numeroBusca, nomeBusca, cidadeBusca, cnpjBusca, nomeBancoBusca]);

  return (
    <>
      <CabecalhoPages titulo="Cadastro de Agências" />
      <Grid w="100%" templateColumns={{ base: "¨100%", md: "repeat(12, 1fr)" }} mt={4} p={1}>
        <InputControl
          label="Código"
          id="id"
          value={idBusca?.toString() || ""}
          onChange={(event) =>
            trataValorInputNumberVazio((val) => atualizaValorPesquisa(setIdBusca, val), event)
          }
          numeroColunasMd={4}
          numeroColunasLg={2}
        />
        <InputControl
          label="Número"
          id="numero"
          value={numeroBusca}
          onChange={(e) => atualizaValorPesquisa(setNumeroBusca, e.target.value)}
          numeroColunasMd={4}
          numeroColunasLg={2}
        />
        <InputControl
          label="Nome Agência"
          id="nome"
          value={nomeBusca}
          onChange={(e) => atualizaValorPesquisa(setNomeBusca, e.target.value)}
          numeroColunasMd={6}
          numeroColunasLg={4}
        />
        <InputControl
          label="Cidade"
          id="cidade"
          value={cidadeBusca}
          onChange={(e) => atualizaValorPesquisa(setCidadeBusca, e.target.value)}
          numeroColunasMd={6}
          numeroColunasLg={2}
        />
        <InputControl
          label="CNPJ"
          id="cnpj"
          value={cnpjBusca}
          onChange={(e) => atualizaValorPesquisa(setCnpjBusca, e.target.value.replace(/\D/g, ""))}
          numeroColunasMd={6}
          numeroColunasLg={2}
        />
        <InputControl
          label="Nome Banco"
          id="nome"
          value={nomeBancoBusca}
          onChange={(e) => atualizaValorPesquisa(setNomeBancoBusca, e.target.value)}
          numeroColunasMd={6}
          numeroColunasLg={4}
        />
        <ButtonPesquisarLimpar
          handleLimparCampo={handleLimparCampo}
          isLimpar={isLimpar}
          numeroColunasMd={2}
          numeroColunasLg={2}
        />
      </Grid>
      <ButtonControl onRelatorioClick={onOpenModalRelatorio} />
      <TabelaCR5
        cabecalhos={[
          { titulo: "CÓDIGO", dadoBuilder: (item) => item.id.toString() },
          { titulo: "NÚMERO", dadoBuilder: (item) => item.numero },
          { titulo: "NOME AGÊNCIA", dadoBuilder: (item) => item.nome.toUpperCase() },
          { titulo: "CIDADE", dadoBuilder: (item) => item.cidade.toUpperCase() },
          { titulo: "CNPJ", dadoBuilder: (item) => mascaraCNPJ(item.cnpj) },
          { titulo: "NOME BANCO", dadoBuilder: (item) => item.banco.nome.toUpperCase() },
        ]}
        data={data?.result}
        keybuilder={(item) => item.id}
        isFetching={isFetching}
        isError={isError}
        error={error}
        onDelete={(item) => {
          setAgencia(item);
          onOpen();
        }}
        onEdit={(item) => {
          setAgencia(item);
          navigate("./editar");
        }}
        totalPages={data?.pageTotal ?? 0}
        itemsPerPage={itemsPerPage}
        currentPage={currentPage}
        setCurrentPage={setCurrentPage}
        totalItems={data?.total ?? 0}
      />
      <ModalConfirma
        titulo="Atenção"
        texto={`Deseja realmente excluir:  ${agencia?.nome}?`}
        onClose={onClose}
        isOpen={isOpen}
        onConfirm={handleDeleteAgencia}
        isLoading={deleteAgenciaLoading}
      />
      <ModalRelatorio
        nomeRelatorio="relatorio-agencias"
        isOpen={isOpenModalRelatorio}
        onClose={onCloseModalRelatorio}
        rota="agencias"
        params={{
          page: currentPage - 1,
          pageSize: itemsPerPage,
          id: idDebounce,
          numero: numeroDebounce,
          nome: nomeDebounce,
          cidade: cidadeDebounce,
          cnpj: cnpjDebounce,
          nomeBanco: nomeBancoDebounce,
        }}
      />
    </>
  );
}
