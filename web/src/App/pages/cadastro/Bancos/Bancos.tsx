import { Grid, useDisclosure } from "@chakra-ui/react";
import { useMutation, useQuery } from "@tanstack/react-query";
import { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import { deleteBanco, fetchBancosPaginado } from "../../../../requests/requestsBancos.ts";
import queryClient from "../../../../singletons/reactQueryClient.ts";
import Acessos from "../../../../utils/Acessos.ts";
import { trataValorInputNumberVazio } from "../../../../utils/componentUtil.ts";
import { DelaysDebouncePadrao, TemposCachePadrao } from "../../../../utils/constantes.ts";
import { getMensagemDeErroOuProcureSuporte } from "../../../../utils/errors.ts";
import { ButtonControl } from "../../../components/ButoonControl/ButtonControl.tsx";
import { ButtonPesquisarLimpar } from "../../../components/ButoonControl/ButtonPesquisarLimpar.tsx";
import { CabecalhoPages } from "../../../components/CabecalhoPages";
import { InputControl } from "../../../components/InputControl";
import { ModalConfirma } from "../../../components/ModalConfirma";
import { TabelaCR5 } from "../../../components/Tabelas";
import useCR5Axios from "../../../hooks/useCR5Axios.ts";
import useDebounce from "../../../hooks/useDebounce.ts";
import { useFuncaoNotificacao } from "../../../hooks/useFuncaoNotificacao.tsx";
import { useValidaAcessos } from "../../../hooks/useValidaAcessos.ts";
import { useBancoStore } from "./store/BancoStore.tsx";

export function Bancos() {
  const { axios } = useCR5Axios();
  const itemsPerPage = 7;
  const delayDebounce = DelaysDebouncePadrao.PADRAO;
  const navigate = useNavigate();
  const { isOpen, onOpen, onClose } = useDisclosure();
  const [invalidando, setInvalidando] = useState(true);

  const { banco, setBanco } = useBancoStore();

  const [currentPage, setCurrentPage] = useState(1);
  const [idBusca, setIdBusca] = useState<number | null>(null);
  const [numeroBusca, setNumeroBusca] = useState("");
  const [nomeBusca, setNomeBusca] = useState("");
  const [abreviaturaBusca, setAbreviaturaBusca] = useState("");

  const [idDebounce] = useDebounce(idBusca, delayDebounce);
  const [numeroDebounce] = useDebounce(numeroBusca, delayDebounce);
  const [nomeDebounce] = useDebounce(nomeBusca, delayDebounce);
  const [abreviaturaDebounce] = useDebounce(abreviaturaBusca, delayDebounce);
  const [isLimpar, setIsLimpar] = useState(true);

  const notificacao = useFuncaoNotificacao();

  function atualizaValorPesquisa<T>(setter: (arg: T) => void, valor: T): void {
    setter(valor);
    setCurrentPage(1);
  }

  const [validouPermissao, setValidouPermissao] = useState(false);

  useValidaAcessos([Acessos.BANCOS], () => {
    setValidouPermissao(true);
    queryClient
      .invalidateQueries({ queryKey: ["fetchBancosPaginado"] })
      .then(() => setInvalidando(false));
  });

  const { data, refetch, error, isError, isFetching } = useQuery({
    queryKey: [
      "fetchBancosPaginado",
      currentPage,
      idDebounce,
      numeroDebounce,
      nomeDebounce,
      abreviaturaDebounce,
    ],
    enabled: !invalidando && validouPermissao,
    keepPreviousData: true,
    staleTime: TemposCachePadrao.CURTO,
    cacheTime: TemposCachePadrao.CURTO,
    queryFn: () => {
      return fetchBancosPaginado(
        {
          page: currentPage - 1,
          id: idDebounce ?? undefined,
          pageSize: itemsPerPage,
          nome: nomeDebounce,
          numero: numeroDebounce,
          abreviatura: abreviaturaDebounce,
        },
        axios
      );
    },
  });

  const { mutate, isLoading: deleteBancoLoading } = useMutation(
    (idBanco: number) => {
      return deleteBanco(idBanco, axios);
    },
    {
      onSuccess: () => {
        notificacao({ message: "Excluído com sucesso!", tipo: "success", titulo: "SUCESSO!" });
        queryClient.invalidateQueries({ queryKey: ["fetchBancosPaginado"] }).then(() => refetch());
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

  function handleDeleteBanco() {
    if (banco && banco.id) {
      mutate(banco.id);
    }
  }

  function handleLimparCampo() {
    setIdBusca(null);
    setNumeroBusca("");
    setNomeBusca("");
    setAbreviaturaBusca("");
    queryClient.resetQueries(["fetchBancosPaginado"]);
    refetch();
  }

  function validaFiltros(): boolean {
    return idBusca == null && numeroBusca === "" && nomeBusca === "" && abreviaturaBusca === "";
  }

  useEffect(() => {
    setIsLimpar(validaFiltros());
  }, [idBusca, numeroBusca, nomeBusca, abreviaturaBusca]);

  return (
    <>
      <CabecalhoPages titulo="Cadastro de Bancos" />
      <Grid w="100%" templateColumns={{ base: "100%", md: "repeat(12, 1fr)" }} mt={4}>
        <InputControl
          label="Código"
          id="id"
          value={idBusca?.toString() || ""}
          onChange={(event) =>
            trataValorInputNumberVazio((val) => atualizaValorPesquisa(setIdBusca, val), event)
          }
          numeroColunasMd={6}
          numeroColunasLg={2}
        />
        <InputControl
          label="Número"
          id="numero"
          value={numeroBusca}
          onChange={(e) => atualizaValorPesquisa(setNumeroBusca, e.target.value)}
          numeroColunasMd={6}
          numeroColunasLg={2}
        />
        <InputControl
          label="Nome Banco"
          id="nome"
          value={nomeBusca}
          onChange={(e) => atualizaValorPesquisa(setNomeBusca, e.target.value)}
          numeroColunasMd={8}
          numeroColunasLg={4}
        />
        <InputControl
          label="Abreviatura Banco"
          id="abreviatura"
          value={abreviaturaBusca}
          onChange={(e) => atualizaValorPesquisa(setAbreviaturaBusca, e.target.value)}
          numeroColunasMd={4}
          numeroColunasLg={2}
        />
        <ButtonPesquisarLimpar
          handleLimparCampo={handleLimparCampo}
          isLimpar={isLimpar}
          numeroColunasMd={6}
          numeroColunasLg={2}
        />
      </Grid>
      <ButtonControl />
      <TabelaCR5
        cabecalhos={[
          { titulo: "CÓDIGO", dadoBuilder: (item) => item.id.toString() },
          { titulo: "NÚMERO", dadoBuilder: (item) => item.numero },
          { titulo: "NOME BANCO", dadoBuilder: (item) => item.nome?.toUpperCase() },
          { titulo: "ABREVIATURA", dadoBuilder: (item) => item.abreviatura?.toUpperCase() },
        ]}
        data={data?.result}
        keybuilder={(item) => item.id}
        isFetching={isFetching}
        isError={isError}
        error={error}
        onDelete={(item) => {
          setBanco(item);
          onOpen();
        }}
        onEdit={(item) => {
          navigate("./editar");
          setBanco(item);
        }}
        totalPages={data?.pageTotal ?? 0}
        itemsPerPage={itemsPerPage}
        currentPage={currentPage}
        setCurrentPage={setCurrentPage}
        totalItems={data?.total ?? 0}
      />
      <ModalConfirma
        titulo="Atenção"
        texto={`Deseja realmente excluir: ${banco?.nome}?`}
        onClose={onClose}
        isOpen={isOpen}
        onConfirm={handleDeleteBanco}
        isLoading={deleteBancoLoading}
      />
    </>
  );
}
