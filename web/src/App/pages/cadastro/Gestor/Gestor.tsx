import { Grid, useDisclosure } from "@chakra-ui/react";
import { useMutation, useQuery } from "@tanstack/react-query";
import { useState } from "react";
import { useNavigate } from "react-router-dom";
import { deleteGestor, fetchGestoresPaginado } from "../../../../requests/requestsGestor.ts";
import queryClient from "../../../../singletons/reactQueryClient.ts";
import Acessos from "../../../../utils/Acessos.ts";
import { trataValorInputNumberVazio } from "../../../../utils/componentUtil.ts";
import { DelaysDebouncePadrao, TemposCachePadrao } from "../../../../utils/constantes.ts";
import { getMensagemDeErroOuProcureSuporte } from "../../../../utils/errors.ts";
import { ButtonControl } from "../../../components/ButoonControl/ButtonControl.tsx";
import { CabecalhoPages } from "../../../components/CabecalhoPages";
import { InputControl } from "../../../components/InputControl";
import { ModalConfirma } from "../../../components/ModalConfirma";
import { TabelaCR5 } from "../../../components/Tabelas";
import useCR5Axios from "../../../hooks/useCR5Axios.ts";
import useDebounce from "../../../hooks/useDebounce.ts";
import { useFuncaoNotificacao } from "../../../hooks/useFuncaoNotificacao.tsx";
import { useValidaAcessos } from "../../../hooks/useValidaAcessos.ts";
import { useGestorStore } from "./store/GestorStore.tsx";

export function Gestores() {
  const { axios } = useCR5Axios();
  const itemsPerPage = 10;
  const delayDebounce = DelaysDebouncePadrao.PADRAO;
  const navigate = useNavigate();
  const { isOpen, onOpen, onClose } = useDisclosure();
  const [invalidando, setInvalidando] = useState(true);

  const { gestor, setGestor } = useGestorStore();

  const [currentPage, setCurrentPage] = useState(1);
  const [nomeBusca, setNomeBusca] = useState("");
  const [emailBusca, setEmailBusca] = useState("");
  const [matriculaBusca, setMatriculaBusca] = useState<number | null>(null);

  const [nomeDebounce] = useDebounce(nomeBusca, delayDebounce);
  const [emailDebounce] = useDebounce(emailBusca, delayDebounce);
  const [matriculaDebounce] = useDebounce(matriculaBusca, delayDebounce);

  const notificacao = useFuncaoNotificacao();

  const [validouPermissao, setValidouPermissao] = useState(false);

  useValidaAcessos([Acessos.ADMINISTRADOR_CR5], () => {
    setValidouPermissao(true);
    queryClient
      .invalidateQueries({ queryKey: ["fetchGestoresPaginado"] })
      .then(() => setInvalidando(false));
  });

  const { data, refetch, error, isError, isFetching } = useQuery({
    queryKey: [
      "fetchGestoresPaginado",
      currentPage,
      nomeDebounce,
      emailDebounce,
      matriculaDebounce,
    ],
    enabled: !invalidando && validouPermissao,
    keepPreviousData: true,
    staleTime: TemposCachePadrao.CURTO,
    cacheTime: TemposCachePadrao.CURTO,
    queryFn: () =>
      fetchGestoresPaginado(
        {
          page: currentPage - 1,
          pageSize: itemsPerPage,
          nome: nomeDebounce,
          email: emailDebounce,
          matricula: matriculaDebounce,
        },
        axios
      ),
  });

  const { mutate, isLoading: deleteGestorLoading } = useMutation(
    (idGestor: number) => deleteGestor(idGestor, axios),
    {
      onSuccess: () => {
        notificacao({ message: "Excluído com sucesso!", tipo: "success", titulo: "SUCESSO!" });
        queryClient
          .invalidateQueries({ queryKey: ["fetchGestoresPaginado"] })
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

  function handleDeleteGestor() {
    if (gestor && gestor.idGestor) {
      mutate(gestor.idGestor);
    }
  }

  function atualizaValorPesquisa<T>(setter: (arg: T) => void, valor: T): void {
    setter(valor);
    setCurrentPage(1);
  }

  return (
    <>
      <CabecalhoPages titulo="Gestão de Gestores" />
      <Grid w="100%" templateColumns={{ base: "100%", md: "repeat(12, 1fr)" }} mt={4}>
        <InputControl
          label="Matricula"
          id="matricula"
          value={matriculaBusca?.toString() || ""}
          onChange={(event) =>
            trataValorInputNumberVazio(
              (val) => atualizaValorPesquisa(setMatriculaBusca, val),
              event
            )
          }
          numeroColunasMd={2}
          numeroColunasLg={2}
        />
        <InputControl
          label="Nome"
          id="nome"
          value={nomeBusca}
          onChange={(e) => setNomeBusca(e.target.value)}
          numeroColunasMd={6}
          numeroColunasLg={4}
        />
        <InputControl
          label="E-mail"
          id="email"
          value={emailBusca}
          onChange={(e) => setEmailBusca(e.target.value)}
          numeroColunasMd={4}
          numeroColunasLg={4}
        />
      </Grid>
      <ButtonControl />
      <TabelaCR5
        cabecalhos={[
          { titulo: "CÓDIGO", dadoBuilder: (item) => item.idGestor.toString() },
          { titulo: "MATRICULA", dadoBuilder: (item) => item.matricula.toString() },
          { titulo: "NOME", dadoBuilder: (item) => item.nome, alinhamento: "left" },
          { titulo: "E-MAIL", dadoBuilder: (item) => item.email, alinhamento: "left" },
          { titulo: "DESCRIÇÃO", dadoBuilder: (item) => item.descricao, alinhamento: "left" },
          {
            titulo: "UNIDADE",
            alinhamento: "left",
            dadoBuilder: (item) => `${item.unidade.codigo} - ${item.unidade.descricaoUnidade}`,
          },
        ]}
        data={data?.result}
        keybuilder={(item) => item.idGestor}
        isFetching={isFetching}
        isError={isError}
        error={error}
        onDelete={(item) => {
          setGestor(item);
          onOpen();
        }}
        onEdit={(item) => {
          navigate("./editar");
          setGestor(item);
        }}
        totalPages={data?.pageTotal ?? 0}
        itemsPerPage={itemsPerPage}
        currentPage={currentPage}
        setCurrentPage={setCurrentPage}
        totalItems={data?.total ?? 0}
      />
      <ModalConfirma
        titulo="Atenção"
        texto={`Deseja realmente excluir: ${gestor?.nome}?`}
        onClose={onClose}
        isOpen={isOpen}
        onConfirm={handleDeleteGestor}
        isLoading={deleteGestorLoading}
      />
    </>
  );
}
