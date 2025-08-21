import { Grid, useDisclosure } from "@chakra-ui/react";
import { useMutation, useQuery } from "@tanstack/react-query";
import { useState } from "react";
import { FiInfo, FiLayers } from "react-icons/fi";
import {
  fetchDeleteClienteResponsavel,
  fetchVinculoDependentePaginado,
} from "../../../../requests/resquestVinculoDependentes";
import queryClient from "../../../../singletons/reactQueryClient";
import Acessos from "../../../../utils/Acessos";
import { trataValorInputNumberVazio } from "../../../../utils/componentUtil";
import { DelaysDebouncePadrao, TemposCachePadrao } from "../../../../utils/constantes";
import { getMensagemDeErroOuProcureSuporte } from "../../../../utils/errors";
import { formatarDataBrasil, mascaraCpfCnpj } from "../../../../utils/mascaras";
import { CabecalhoPages } from "../../../components/CabecalhoPages";
import { InputControl } from "../../../components/InputControl";
import { ModalConfirma } from "../../../components/ModalConfirma";
import {
  ModalDetalhesOperador,
  buscaOperadorAlteracao,
  buscaOperadorInclusao,
} from "../../../components/ModalDetalhesOperador";
import { TabelaCR5 } from "../../../components/Tabelas";
import useCR5Axios from "../../../hooks/useCR5Axios";
import useDebounce from "../../../hooks/useDebounce";
import { useFuncaoNotificacao } from "../../../hooks/useFuncaoNotificacao";
import { useValidaAcessos } from "../../../hooks/useValidaAcessos";
import { ModalHistoricoVinculoDependente } from "./components/ModalHistoricoVinculoDependente";

export function VinculoDependente() {
  const { isOpen, onOpen, onClose } = useDisclosure();
  const { axios } = useCR5Axios();
  const itemsPerPage = 10;
  const delayDebounce = DelaysDebouncePadrao.PADRAO;
  const [invalidando, setInvalidando] = useState(true);

  const [currentPage, setCurrentPage] = useState(1);
  const [idDependenteBusca, setIdDependenteBusca] = useState<number | null>();
  const [nomeDependenteBusca, setNomeDependenteBusca] = useState<string | undefined>("");
  const [cpfCnpjDependenteBusca, setCpfCnpjDependenteBusca] = useState<string | undefined>("");
  const [nomeResponsavelBusca, setNomeResponsavelBusca] = useState<string | undefined>("");
  const [cpfCnpjResponsavelBusca, setCpfCnpjResponsavelBusca] = useState<string | undefined>("");

  const [idDependenteDebounce] = useDebounce(idDependenteBusca, delayDebounce);
  const [nomeDependenteDebounce] = useDebounce(nomeDependenteBusca, delayDebounce);
  const [cpfCnpjDependenteDebounce] = useDebounce(cpfCnpjDependenteBusca, delayDebounce);
  const [nomeResponsavelDebounce] = useDebounce(nomeResponsavelBusca, delayDebounce);
  const [cpfCnpjResponsavelDebounce] = useDebounce(cpfCnpjResponsavelBusca, delayDebounce);

  const [abrirModalDetalhesOperador, setAbrirModalDetalhesOperador] = useState(false);
  const [idOperadorInclusao, setIdOperadorInclusao] = useState<number>(0);
  const [dataInclusao, setDataInclusao] = useState<string>("");
  const [idOperadorAlteracao, setIdOperadorAlteracao] = useState<number>(0);
  const [dataAlteracao, setDataAlteracao] = useState<string>("");
  const [nomeCadastro, setNomeCadastro] = useState<string>("");

  const [idDependenteExclusao, setIdDependenteExclusao] = useState<number>(0);
  const [abrirModalHistoricoClienteResponsavel, setAbrirModalHistoricoClienteResponsavel] =
    useState(false);
  const [cpfDependente, setCpfDependente] = useState<string>("");

  const notificacao = useFuncaoNotificacao();
  const [validouPermissao, setValidouPermissao] = useState(false);

  useValidaAcessos([Acessos.PESSOAS], () => {
    setValidouPermissao(true);
    queryClient
      .invalidateQueries({ queryKey: ["fetchVinculoDependentePaginado"] })
      .then(() => setInvalidando(false));
  });

  const { data, error, isError, refetch, isFetching } = useQuery({
    queryKey: [
      "fetchVinculoDependentePaginado",
      currentPage,
      idDependenteDebounce,
      nomeDependenteDebounce,
      cpfCnpjDependenteDebounce,
      nomeResponsavelDebounce,
      cpfCnpjResponsavelDebounce,
    ],
    keepPreviousData: true,
    enabled: !invalidando && validouPermissao,
    staleTime: TemposCachePadrao.CURTO,
    cacheTime: TemposCachePadrao.CURTO,
    queryFn: () => {
      return fetchVinculoDependentePaginado(
        {
          page: currentPage - 1,
          pageSize: itemsPerPage,
          idDependente: idDependenteDebounce,
          nomeDependente: nomeDependenteDebounce,
          cpfCnpjDependente: cpfCnpjDependenteDebounce,
          nomeResponsavel: nomeResponsavelDebounce,
          cpfCnpjResponsavel: cpfCnpjResponsavelDebounce,
        },
        axios
      );
    },
  });
  function handleDadosOperador(
    idInclusao: number,
    idAlteracao: number,
    dataInclusao: string,
    dataAlteracao: string,
    nome: string
  ) {
    setAbrirModalDetalhesOperador(true);
    setIdOperadorInclusao(idInclusao), setDataInclusao(dataInclusao);
    setIdOperadorAlteracao(idAlteracao);
    setDataAlteracao(dataAlteracao);
    setNomeCadastro(nome);
  }

  function handleAbrirHistorico(cpf: string) {
    setAbrirModalHistoricoClienteResponsavel(true);
    setCpfDependente(cpf);
  }

  function atualizaValorPesquisa<T>(setter: (arg: T) => void, valor: T): void {
    setter(valor);
    setCurrentPage(1);
  }

  const { mutate, isLoading: deleteClienteResponsavelLoading } = useMutation(
    (idDependente: number) => {
      return fetchDeleteClienteResponsavel({ idDependente: idDependente }, axios);
    },
    {
      onSuccess: () => {
        notificacao({
          message: "Removido Vínculo com sucesso!",
          tipo: "success",
          titulo: "SUCESSO!",
        });
        queryClient
          .invalidateQueries({ queryKey: ["fetchVinculoDependentePaginado"] })
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

  function handleRemoverDependente() {
    if (idDependenteExclusao) {
      mutate(idDependenteExclusao);
    }
  }

  const tituloTela = "Cadastro de Vínculo Dependentes";
  return (
    <>
      <CabecalhoPages titulo={tituloTela} />
      <Grid w="100%" templateColumns={{ base: "¨100%", md: "repeat(12, 1fr)" }} mt={4}>
        <InputControl
          numeroColunasMd={2}
          numeroColunasLg={2}
          label="Código"
          id="idDependente"
          type="number"
          value={idDependenteBusca?.toString() ?? ""}
          onChange={(event) =>
            trataValorInputNumberVazio(
              (val) => atualizaValorPesquisa(setIdDependenteBusca, val),
              event
            )
          }
        />
        <InputControl
          numeroColunasMd={6}
          numeroColunasLg={4}
          label="Nome Dependente"
          id="nomeDependente"
          type="text"
          value={nomeDependenteBusca?.toString() ?? ""}
          onChange={(e) => atualizaValorPesquisa(setNomeDependenteBusca, e.target.value)}
        />
        <InputControl
          numeroColunasMd={4}
          numeroColunasLg={2}
          label="CPF/CNPJ Dependente"
          id="cpfCnpjDependenteBusca"
          type="number"
          value={cpfCnpjDependenteBusca?.toString() ?? ""}
          onChange={(e) => atualizaValorPesquisa(setCpfCnpjDependenteBusca, e.target.value)}
        />
        <InputControl
          numeroColunasMd={6}
          numeroColunasLg={4}
          label="Nome Responsável"
          id="nomeResponsavel"
          type="text"
          value={nomeResponsavelBusca?.toString() ?? ""}
          onChange={(e) => atualizaValorPesquisa(setNomeResponsavelBusca, e.target.value)}
        />
        <InputControl
          numeroColunasMd={4}
          numeroColunasLg={2}
          label="CPF/CNPJ Responsável"
          id="cpfCnpjResponsavel"
          type="number"
          value={cpfCnpjResponsavelBusca?.toString() ?? ""}
          onChange={(e) => atualizaValorPesquisa(setCpfCnpjResponsavelBusca, e.target.value)}
        />
      </Grid>
      <TabelaCR5
        cabecalhos={[
          {
            titulo: "ID DEPENDENTE",
            dadoBuilder: (item) => item.dependente.idDependente.toString(),
          },
          {
            titulo: "NOME DEPENDENTE ",
            dadoBuilder: (item) => item.dependente.nomeDependente.toUpperCase(),
          },
          {
            titulo: "CPF/CNPJ DEPENDENTE",
            dadoBuilder: (item) => mascaraCpfCnpj(item.dependente.cpfCnpjDependente),
          },
          {
            titulo: "DATA NASCIMENTO DEPENDENTE",
            dadoBuilder: (item) => formatarDataBrasil(item.dependente.dataNascimentoDependente),
          },
          {
            titulo: "ID RESPONSÁVEL",
            dadoBuilder: (item) => item.pessoaResponsavel?.idPessoa?.toString() ?? "-",
          },
          {
            titulo: "NOME RESPONSÁVEL ",
            dadoBuilder: (item) => item.pessoaResponsavel?.descricao.toUpperCase() ?? "-",
          },
          {
            titulo: "CPF/CNPJ RESPONSÁVEL",
            dadoBuilder: (item) => mascaraCpfCnpj(item.pessoaResponsavel?.cpfCnpj) ?? "-",
          },
        ]}
        data={data?.result}
        keybuilder={(item) => item.idDependenteResponsavel}
        isFetching={isFetching}
        isError={isError}
        error={error}
        customAction={[
          {
            title: "Ver Historico Responsável",
            icon: () => <FiLayers />,
            action: (item) => handleAbrirHistorico(item.dependente.cpfCnpjDependente),
          },
          {
            title: "Ver Detalhes Operador",
            icon: () => <FiInfo />,
            action: (item) =>
              handleDadosOperador(
                item.dependente.idOperadorInclusao,
                item.dependente.idOperadorAlteracao,
                item.dependente.dataInclusao,
                item.dependente.dataAlteracao,
                item.dependente.nomeDependente
              ),
          },
        ]}
        onDelete={(item) => {
          setIdDependenteExclusao(item.dependente.idDependente);
          onOpen();
        }}
        totalPages={data?.pageTotal ?? 0}
        itemsPerPage={itemsPerPage}
        currentPage={currentPage}
        setCurrentPage={setCurrentPage}
        totalItems={data?.total ?? 0}
      />
      <ModalHistoricoVinculoDependente
        abrirModal={abrirModalHistoricoClienteResponsavel}
        setAbrirModal={setAbrirModalHistoricoClienteResponsavel}
        cpf={cpfDependente}
      />
      <ModalDetalhesOperador
        abrirModal={abrirModalDetalhesOperador}
        setAbrirModal={setAbrirModalDetalhesOperador}
        operadoresABuscar={[
          buscaOperadorInclusao(idOperadorInclusao, dataInclusao),
          buscaOperadorAlteracao(idOperadorAlteracao, dataAlteracao),
        ]}
        tela={tituloTela}
        nomeCadastro={nomeCadastro}
      />
      <ModalConfirma
        titulo="Atenção"
        texto={"Deseja realmente remover Dependente?"}
        onClose={onClose}
        isOpen={isOpen}
        onConfirm={handleRemoverDependente}
        isLoading={deleteClienteResponsavelLoading}
      />
    </>
  );
}
