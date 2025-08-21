import { Grid, useDisclosure } from "@chakra-ui/react";
import { useMutation, useQuery } from "@tanstack/react-query";
import { useState } from "react";
import { useNavigate } from "react-router-dom";
import {
  fetchDeleteImpressora,
  fetchImpressorasPaginado,
} from "../../../../requests/resquestImpressoras.ts";
import queryClient from "../../../../singletons/reactQueryClient.ts";
import Acessos from "../../../../utils/Acessos.ts";
import { trataValorInputNumberVazio } from "../../../../utils/componentUtil.ts";
import { DelaysDebouncePadrao, TemposCachePadrao } from "../../../../utils/constantes";
import { getMensagemDeErroOuProcureSuporte } from "../../../../utils/errors";
import { ButtonControl } from "../../../components/ButoonControl/ButtonControl.tsx";
import { CabecalhoPages } from "../../../components/CabecalhoPages";
import { InputControl } from "../../../components/InputControl";
import { ModalConfirma } from "../../../components/ModalConfirma";
import { TabelaCR5 } from "../../../components/Tabelas";
import useCR5Axios from "../../../hooks/useCR5Axios";
import useDebounce from "../../../hooks/useDebounce";
import { useFuncaoNotificacao } from "../../../hooks/useFuncaoNotificacao.tsx";
import { useValidaAcessos } from "../../../hooks/useValidaAcessos.ts";
import { useUnidadeStore } from "../../../stores/UnidadeStore.tsx";
import { useImpressoraStore } from "./store/ImpressoraStore.tsx";

export function Impressoras() {
  const { axios } = useCR5Axios();
  const itemsPerPage = 10;
  const delayDebounce = DelaysDebouncePadrao.PADRAO;
  const navigate = useNavigate();
  const { isOpen, onOpen, onClose } = useDisclosure();
  const [invalidando, setInvalidando] = useState(true);

  const [currentPage, setCurrentPage] = useState(1);
  const [idImpressoraBusca, setIdImpressoraBusca] = useState<number | null>(null);
  const [descricaoBusca, setDescricaoBusca] = useState("");
  const [modeloBusca, setModeloBusca] = useState("");
  const [ipMaquinaBusca, setIpMaquinaBusca] = useState("");

  const [idImpressoraDebounce] = useDebounce(idImpressoraBusca, delayDebounce);
  const [descricaoDebounce] = useDebounce(descricaoBusca, delayDebounce);
  const [modeloDebounce] = useDebounce(modeloBusca, delayDebounce);
  const [ipMaquinaDebounce] = useDebounce(ipMaquinaBusca, delayDebounce);

  const { impressora, setImpressora } = useImpressoraStore();
  const { unidadeAtual } = useUnidadeStore();

  const [validouPermissao, setValidouPermissao] = useState(false);

  useValidaAcessos([Acessos.CADASTRO_DE_IMPRESSORAS], () => {
    setValidouPermissao(true);
    queryClient
      .invalidateQueries({ queryKey: ["fetchAgenciasPaginado"] })
      .then(() => setInvalidando(false));
  });

  const { data, refetch, error, isError, isFetching } = useQuery({
    queryKey: [
      "fetchImpressorasPaginado",
      currentPage,
      idImpressoraDebounce,
      descricaoDebounce,
      modeloDebounce,
      ipMaquinaBusca,
      unidadeAtual,
    ],
    keepPreviousData: true,
    enabled: !invalidando && validouPermissao,
    staleTime: TemposCachePadrao.CURTO,
    cacheTime: TemposCachePadrao.CURTO,
    queryFn: () => {
      return fetchImpressorasPaginado(
        {
          page: currentPage - 1,
          pageSize: itemsPerPage,
          idImpressora: idImpressoraDebounce ?? undefined,
          descricao: descricaoDebounce,
          modelo: modeloDebounce,
          ipMaquina: ipMaquinaDebounce,
          idUnidade: unidadeAtual.id,
        },
        axios
      );
    },
  });

  function atualizaValorPesquisa<T>(setter: (arg: T) => void, valor: T): void {
    setter(valor);
    setCurrentPage(1);
  }

  const { mutate, isLoading: deleteImpressoraLoading } = useMutation(
    (idImpressora: number) => {
      return fetchDeleteImpressora({ idImpressora: idImpressora }, axios);
    },
    {
      onSuccess: () => {
        notificacao({ message: "Excluído com sucesso!", tipo: "success", titulo: "SUCESSO!" });
        queryClient
          .invalidateQueries({ queryKey: ["fetchImpressorasPaginado"] })
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

  function handleDeleteImpressora() {
    if (impressora) {
      mutate(impressora.idImpressora);
    }
  }

  const notificacao = useFuncaoNotificacao();
  const tituloTela = "Cadastro de Impressoras";
  const subtitulo = "Gerencie as Impressoras da Unidade Atual";

  return (
    <>
      <CabecalhoPages titulo={tituloTela} subtitulo={subtitulo} />
      <Grid
        w="100%"
        templateColumns={{ base: "¨100%", md: "repeat(12, 1fr)" }}
        mt={4}
        pb={{ base: 4, md: 1 }}
      >
        <InputControl
          label="Código"
          id="idImpressora"
          value={idImpressoraBusca?.toString() || ""}
          onChange={(event) =>
            trataValorInputNumberVazio(
              (val) => atualizaValorPesquisa(setIdImpressoraBusca, val),
              event
            )
          }
          numeroColunasMd={4}
          numeroColunasLg={2}
        />
        <InputControl
          numeroColunasMd={8}
          numeroColunasLg={4}
          label="Descrição"
          id="descricao"
          value={descricaoBusca}
          onChange={(e) => atualizaValorPesquisa(setDescricaoBusca, e.target.value)}
        />
        <InputControl
          numeroColunasMd={6}
          numeroColunasLg={4}
          label="Modelo"
          id="modelo"
          value={modeloBusca}
          onChange={(e) => atualizaValorPesquisa(setModeloBusca, e.target.value)}
        />
        <InputControl
          numeroColunasMd={6}
          numeroColunasLg={2}
          label="IP Máquina"
          id="ipMaquina"
          value={ipMaquinaBusca}
          onChange={(e) => atualizaValorPesquisa(setIpMaquinaBusca, e.target.value)}
        />
      </Grid>
      <ButtonControl />
      <TabelaCR5
        cabecalhos={[
          { titulo: "ID IMPRESSORA", dadoBuilder: (item) => item.idImpressora.toString() },
          { titulo: "IP MÁQUINA", dadoBuilder: (item) => item.ipMaquina },
          { titulo: "DESCRIÇÃO", dadoBuilder: (item) => item.descricao.toUpperCase() },
          { titulo: "MODELO", dadoBuilder: (item) => item.modelo.toUpperCase() },
          { titulo: "GAVETA", dadoBuilder: (item) => (item.gaveta ? "SIM" : "NÃO") },
          { titulo: "GUILHOTINA", dadoBuilder: (item) => (item.guilhotina ? "SIM" : "NÃO") },
          { titulo: "PORTA", dadoBuilder: (item) => `${item.tipoPorta} ${item.porta}` },
        ]}
        data={data?.result}
        keybuilder={(item) => item.idImpressora}
        isFetching={isFetching}
        isError={isError}
        error={error}
        totalPages={data?.pageTotal ?? 0}
        itemsPerPage={itemsPerPage}
        currentPage={currentPage}
        setCurrentPage={setCurrentPage}
        totalItems={data?.total ?? 0}
        onDelete={(item) => {
          setImpressora(item);
          onOpen();
        }}
        onEdit={(item) => {
          setImpressora(item);
          navigate("./editar");
        }}
      />
      <ModalConfirma
        titulo="Atenção"
        texto={`Deseja realmente excluir a Impressora:  ${impressora?.descricao}?`}
        onClose={onClose}
        isOpen={isOpen}
        onConfirm={handleDeleteImpressora}
        isLoading={deleteImpressoraLoading}
      />
    </>
  );
}
