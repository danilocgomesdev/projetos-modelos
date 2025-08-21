import { CabecalhoPages } from "../../../components/CabecalhoPages";

import { Box, Grid, Text, useDisclosure } from "@chakra-ui/react";
import { useMutation, useQuery } from "@tanstack/react-query";
import { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import {
  deleteContaCorrente,
  fetchContasCorrentePaginado,
} from "../../../../requests/requestsContasCorrente.ts";
import queryClient from "../../../../singletons/reactQueryClient.ts";
import Acessos from "../../../../utils/Acessos.ts";
import { trataValorInputNumberVazio } from "../../../../utils/componentUtil.ts";
import { DelaysDebouncePadrao, TemposCachePadrao } from "../../../../utils/constantes.ts";
import { getMensagemDeErroOuProcureSuporte } from "../../../../utils/errors.ts";
import { formatarDataBrasil } from "../../../../utils/mascaras.ts";
import { ButtonControl } from "../../../components/ButoonControl/ButtonControl.tsx";
import { ButtonPesquisarLimpar } from "../../../components/ButoonControl/ButtonPesquisarLimpar.tsx";
import { FiltroEntidadeUnidade } from "../../../components/FiltroEntidadeUnidade";
import { InputControl } from "../../../components/InputControl";
import { ModalConfirma } from "../../../components/ModalConfirma";
import { TabelaCR5 } from "../../../components/Tabelas";
import useCR5Axios from "../../../hooks/useCR5Axios.ts";
import useDebounce from "../../../hooks/useDebounce.ts";
import { useFuncaoNotificacao } from "../../../hooks/useFuncaoNotificacao.tsx";
import { useValidaAcessos } from "../../../hooks/useValidaAcessos.ts";
import { useContaCorrenteStore } from "./store/ContaCorrenteStore.ts";

export function ContasCorrente() {
  const { axios } = useCR5Axios();
  const itemsPerPage = 7;
  const delayDebounce = DelaysDebouncePadrao.PADRAO;
  const navigate = useNavigate();
  const { isOpen, onOpen, onClose } = useDisclosure();
  const [invalidando, setInvalidando] = useState(true);

  const { contaCorrente, setContaCorrente } = useContaCorrenteStore();

  const [currentPage, setCurrentPage] = useState(1);
  const [idBusca, setIdBusca] = useState<number | null>(null);
  const [numeroBusca, setNumeroBusca] = useState("");
  const [nomeAgenciaBusca, setNomeAgenciaBusca] = useState("");
  const [nomeBancoBusca, setNomeBancoBusca] = useState("");
  const [idUnidadeBusca, setIdUnidadeBusca] = useState<number | null>(null);
  const [idEntidadeBusca, setIdEntidadeBusca] = useState<number | null>(null);

  const [idDebounce] = useDebounce(idBusca, delayDebounce);
  const [numeroDebounce] = useDebounce(numeroBusca, delayDebounce);
  const [nomeAgenciaDebounce] = useDebounce(nomeAgenciaBusca, delayDebounce);
  const [nomeBancoDebounce] = useDebounce(nomeBancoBusca, delayDebounce);
  const [isLimpar, setIsLimpar] = useState(true);

  const notificacao = useFuncaoNotificacao();

  const [validouPermissao, setValidouPermissao] = useState(false);

  useValidaAcessos([Acessos.CONTAS_CORRENTES], () => {
    setValidouPermissao(true);
  });

  function atualizaValorPesquisa<T>(setter: (arg: T) => void, valor: T): void {
    setter(valor);
    setCurrentPage(1);
  }

  useEffect(() => {
    queryClient
      .invalidateQueries({ queryKey: ["fetchContasCorrentePaginado"] })
      .then(() => setInvalidando(false));
  }, []);

  const { data, refetch, isFetching, isError, error } = useQuery({
    queryKey: [
      "fetchContasCorrentePaginado",
      currentPage,
      idDebounce,
      numeroDebounce,
      nomeAgenciaDebounce,
      nomeBancoDebounce,
      idEntidadeBusca,
      idUnidadeBusca,
    ],
    enabled: !invalidando && validouPermissao,
    keepPreviousData: true,
    staleTime: TemposCachePadrao.MODERADO,
    cacheTime: TemposCachePadrao.MODERADO,
    queryFn: () => {
      return fetchContasCorrentePaginado(
        {
          page: currentPage - 1,
          pageSize: itemsPerPage,
          id: idDebounce ?? undefined,
          nomeAgencia: nomeAgenciaDebounce,
          numeroConta: numeroDebounce,
          nomeBanco: nomeBancoDebounce,
          idEntidade: idEntidadeBusca ?? undefined,
          idUnidade: idUnidadeBusca ?? undefined,
        },
        axios
      );
    },
  });

  const { mutate, isLoading: deleteContaCorrenteLoading } = useMutation(
    (idContaCorrente: number) => {
      return deleteContaCorrente(idContaCorrente, axios);
    },
    {
      onSuccess: () => {
        notificacao({ message: "Excluído com sucesso!", tipo: "success", titulo: "SUCESSO!" });
        queryClient
          .invalidateQueries({ queryKey: ["fetchContasCorrentePaginado"] })
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

  function handleLimparCampo() {
    setIdBusca(null);
    setNumeroBusca("");
    setNomeAgenciaBusca("");
    setNomeBancoBusca("");
    setIdUnidadeBusca(null);
    setIdEntidadeBusca(null);
    queryClient.resetQueries(["fetchContasCorrentePaginado"]);
    refetch();
  }

  function validaFiltros(): boolean {
    return (
      idBusca == null &&
      numeroBusca === "" &&
      nomeAgenciaBusca === "" &&
      nomeBancoBusca === "" &&
      idUnidadeBusca === null &&
      idEntidadeBusca === null
    );
  }

  useEffect(() => {
    setIsLimpar(validaFiltros());
  }, [idBusca, numeroBusca, nomeAgenciaBusca, nomeBancoBusca, idUnidadeBusca, idEntidadeBusca]);

  function handleDeleteContaCorrente() {
    if (contaCorrente && contaCorrente.id) {
      mutate(contaCorrente.id);
    }
  }

  function textoConfirmaExclusa() {
    if (contaCorrente !== null) {
      const a = (
        <>
          <Text>Deseja realmente excluir a Conta Corrente? </Text>
          <Text>
            Conta: {contaCorrente.numeroConta}-{contaCorrente.digitoConta}
          </Text>
          <Text>Agêcia: {contaCorrente.agencia.nome}</Text>
          <Text>Banco: {contaCorrente.agencia.banco.nome}</Text>
        </>
      );
      return a;
    }
  }

  return (
    <>
      <CabecalhoPages titulo="Cadastro de Contas Corrente" />
      <Grid
        w="100%"
        templateColumns={{ base: "¨100%", md: "repeat(12, 1fr)" }}
        mt={4}
        pb={{ base: 4, md: 1 }}
      >
        <InputControl
          label="Código"
          id="id"
          value={idBusca?.toString() || ""}
          onChange={(event) =>
            trataValorInputNumberVazio((val) => atualizaValorPesquisa(setIdBusca, val), event)
          }
          numeroColunasMd={2}
          numeroColunasLg={2}
        />
        <InputControl
          label="Número"
          id="numeroConta"
          value={numeroBusca}
          onChange={(e) => atualizaValorPesquisa(setNumeroBusca, e.target.value)}
          numeroColunasMd={2}
          numeroColunasLg={2}
        />
        <InputControl
          label="Nome Agência"
          id="nomeAgencia"
          value={nomeAgenciaBusca}
          onChange={(e) => atualizaValorPesquisa(setNomeAgenciaBusca, e.target.value)}
          numeroColunasMd={4}
          numeroColunasLg={4}
        />
        <InputControl
          label="Nome Banco"
          id="nomeBanco"
          value={nomeBancoBusca}
          onChange={(e) => atualizaValorPesquisa(setNomeBancoBusca, e.target.value)}
          numeroColunasMd={4}
          numeroColunasLg={4}
        />
        <FiltroEntidadeUnidade
          numeroColunasMd={12}
          numeroColunasLg={4}
          width="100%"
          onChange={(arg) => {
            if (arg.tipo === "LIMPAR") {
              setIdEntidadeBusca(null);
              setIdUnidadeBusca(null);
            } else if (arg.tipo === "ENTIDADE") {
              setIdEntidadeBusca(arg.id);
              setIdUnidadeBusca(null);
            } else {
              setIdEntidadeBusca(null);
              setIdUnidadeBusca(arg.id);
            }

            setCurrentPage(1);
          }}
        />
        <ButtonPesquisarLimpar
          handleLimparCampo={handleLimparCampo}
          isLimpar={isLimpar}
          numeroColunasMd={4}
          numeroColunasLg={2}
          marginTopLg={7}
        />
      </Grid>
      <ButtonControl />
      <Box overflowX="auto">
        <Box p={1} mt={2}>
          <TabelaCR5
            cabecalhos={[
              { titulo: "CÓDIGO", dadoBuilder: (item) => item.id.toString() },
              {
                titulo: "NÚMERO-DÍGITO",
                dadoBuilder: (item) => item.numeroConta + "-" + item.digitoConta,
              },
              { titulo: "COFRE CONTA", dadoBuilder: (item) => item.cofreConta?.toString() ?? "" },
              { titulo: "OPERAÇÃO", dadoBuilder: (item) => item.numeroOperacao },
              { titulo: "NOME BANCO", dadoBuilder: (item) => item.agencia.banco.nome },
              { titulo: "NOME AGÊNCIA", dadoBuilder: (item) => item.agencia.nome },
              {
                titulo: "UNIDADE",
                dadoBuilder: (item) => `${item.unidade.nome}-${item.unidade.codigo}`,
              },
            ]}
            data={data?.result}
            keybuilder={(item) => item.id}
            isFetching={isFetching}
            isError={isError}
            error={error}
            onDelete={(item) => {
              setContaCorrente(item);
              onOpen();
            }}
            onEdit={(item) => {
              setContaCorrente(item);
              navigate("./editar");
            }}
            detalhesBuilder={{
              type: "Expandir",
              buildDetalhes: (item) => {
                return [
                  [
                    { nome: "Data inclusão", valor: formatarDataBrasil(item.dataInclusao) },
                    { nome: "Data alteracao", valor: formatarDataBrasil(item.dataAlteracao) },
                  ],
                  [
                    { nome: "Conta Banco", valor: item.contaBanco },
                    { nome: "Conta Cliente", valor: item.contaCliente },
                    { nome: "Conta Caixa", valor: item.contaCaixa },
                    { nome: "Conta Juros", valor: item.contaJuros },
                    { nome: "Conta Descontos", valor: item.contaDescontos },
                  ],
                  [
                    { nome: "Cofre Banco", valor: item.cofreBanco },
                    { nome: "Cofre Agência", valor: item.cofreAgencia },
                    { nome: "Cofre Conta", valor: item.cofreConta },
                  ],
                ];
              },
            }}
            totalPages={data?.pageTotal ?? 0}
            itemsPerPage={itemsPerPage}
            currentPage={currentPage}
            setCurrentPage={setCurrentPage}
            totalItems={data?.total ?? 0}
          />
        </Box>
      </Box>
      <ModalConfirma
        titulo="Atenção"
        texto={textoConfirmaExclusa()}
        onClose={onClose}
        isOpen={isOpen}
        onConfirm={handleDeleteContaCorrente}
        isLoading={deleteContaCorrenteLoading}
      />
    </>
  );
}
