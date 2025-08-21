import { Button, Grid, useDisclosure } from "@chakra-ui/react";
import { useMutation, useQuery } from "@tanstack/react-query";
import { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import { PessoaCr5DTO } from "../../../../models/DTOs/Outros/PessoaCr5DTO.ts";
import {
  fetchDeletePessoa,
  fetchPessoasCr5Paginado,
} from "../../../../requests/requestsPessoasCr5.ts";
import queryClient from "../../../../singletons/reactQueryClient.ts";
import Acessos from "../../../../utils/Acessos.ts";
import { trataValorInputNumberVazio } from "../../../../utils/componentUtil.ts";
import {
  DelaysDebouncePadrao,
  EstadosBrasil,
  TemposCachePadrao,
} from "../../../../utils/constantes";
import { getMensagemDeErroOuProcureSuporte } from "../../../../utils/errors";
import { formatarDataBrasil, mascaraCpfCnpj } from "../../../../utils/mascaras";
import { ButtonControl } from "../../../components/ButoonControl/ButtonControl.tsx";
import { CabecalhoPages } from "../../../components/CabecalhoPages";
import { InputControl } from "../../../components/InputControl";
import { ModalConfirma } from "../../../components/ModalConfirma";
import {
  ModalDetalhesOperador,
  buscaOperadorAlteracao,
  buscaOperadorInclusao,
} from "../../../components/ModalDetalhesOperador/ModalDetalhesOperador.tsx";
import { SelectControl } from "../../../components/SelectControl";
import { TabelaCR5 } from "../../../components/Tabelas";
import useCR5Axios from "../../../hooks/useCR5Axios";
import useDebounce from "../../../hooks/useDebounce";
import { useFuncaoNotificacao } from "../../../hooks/useFuncaoNotificacao.tsx";
import { useValidaAcessos } from "../../../hooks/useValidaAcessos.ts";
import { ModalTipoCadastro } from "./components/ModalTipoCadastro.tsx";
import { useClientesStore } from "./store/ClientesStore.tsx";

export function Clientes() {
  const { axios } = useCR5Axios();
  const itemsPerPage = 7;
  const delayDebounce = DelaysDebouncePadrao.PADRAO;
  const navigate = useNavigate();
  const { isOpen, onOpen, onClose } = useDisclosure();
  const [invalidando, setInvalidando] = useState(true);
  const [abrirModalTipoCadastro, setAbrirModalTipoCadastro] = useState(false);
  const [abrirModalDetalhesOperador, setAbrirModalDetalhesOperador] = useState(false);

  const [currentPage, setCurrentPage] = useState(1);
  const [idPessoaBusca, setIdPessoaBusca] = useState<number | null>(null);
  const [nomeBusca, setNomeBusca] = useState("");
  const [cpfCnpjBusca, setCpfCnpjBusca] = useState("");
  const [bairroBusca, setBairroBusca] = useState("");
  const [cidadeBusca, setCidadeBusca] = useState("");
  const [estadoBusca, setEstadoBusca] = useState("");
  const [cepBusca, setCepBusca] = useState("");
  const [idEstrangeiroBusca, setIdEstrangeiroBusca] = useState("");

  const [idPessoaDebounce] = useDebounce(idPessoaBusca, delayDebounce);
  const [nomeDebounce] = useDebounce(nomeBusca, delayDebounce);
  const [cpfCnpjDebounce] = useDebounce(cpfCnpjBusca, delayDebounce);
  const [bairroDebounce] = useDebounce(bairroBusca, delayDebounce);
  const [cidadeDebounce] = useDebounce(cidadeBusca, delayDebounce);
  const [estadoDebounce] = useDebounce(estadoBusca, delayDebounce);
  const [cepDebounce] = useDebounce(cepBusca, delayDebounce);
  const [idEstrangeiroDebounce] = useDebounce(idEstrangeiroBusca, delayDebounce);

  const [idOperadorInclusao, setIdOperadorInclusao] = useState<number>(0);
  const [dataInclusao, setDataInclusao] = useState<string>("");
  const [idOperadorAlteracao, setIdOperadorAlteracao] = useState<number>(0);
  const [dataAlteracao, setDataAlteracao] = useState<string>("");
  const [nomeCadastro, setNomeCadastro] = useState<string>("");

  const [validouPermissao, setValidouPermissao] = useState(false);

  useValidaAcessos([Acessos.PESSOAS], () => {
    setValidouPermissao(true);
    queryClient
      .invalidateQueries({ queryKey: ["fetchPessoasCr5Paginado"] })
      .then(() => setInvalidando(false));
  });

  const { cliente, setCliente } = useClientesStore();

  useEffect(() => {
    queryClient
      .invalidateQueries({ queryKey: ["fetchPessoasCr5Paginado"] })
      .then(() => setInvalidando(false));
  }, []);

  const { data, refetch, error, isError, isFetching } = useQuery({
    queryKey: [
      "fetchPessoasCr5Paginado",
      currentPage,
      idPessoaDebounce,
      nomeDebounce,
      cpfCnpjDebounce,
      bairroDebounce,
      cidadeDebounce,
      estadoDebounce,
      cepDebounce,
      idEstrangeiroDebounce,
    ],
    keepPreviousData: true,
    enabled: !invalidando && validouPermissao,
    staleTime: TemposCachePadrao.CURTO,
    cacheTime: TemposCachePadrao.CURTO,
    queryFn: () => {
      return fetchPessoasCr5Paginado(
        {
          page: currentPage - 1,
          pageSize: itemsPerPage,
          idPessoa: idPessoaBusca ?? undefined,
          nome: nomeDebounce,
          cpfCnpj: cpfCnpjDebounce,
          bairro: bairroDebounce,
          cidade: cidadeDebounce,
          estado: estadoDebounce,
          cep: cepDebounce,
          idEstrangeiro: idEstrangeiroDebounce,
        },
        axios
      );
    },
  });

  function atualizaValorPesquisa<T>(setter: (arg: T) => void, valor: T): void {
    setter(valor);
    setCurrentPage(1);
  }

  const { mutate, isLoading: deletePessoaLoading } = useMutation(
    (idPessoa: number) => {
      return fetchDeletePessoa({ idPessoa: idPessoa }, axios);
    },
    {
      onSuccess: () => {
        notificacao({ message: "Excluído com sucesso!", tipo: "success", titulo: "SUCESSO!" });
        queryClient
          .invalidateQueries({ queryKey: ["fetchPessoasCr5Paginado"] })
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

  function handleDeleteCliente() {
    if (cliente) {
      mutate(cliente.idPessoa);
    }
  }

  const notificacao = useFuncaoNotificacao();

  function handleNavigateTipo(pessoa: PessoaCr5DTO) {
    if (pessoa.pessoaFisica) {
      navigate("./pessoa-fisica-editar");
    } else if (pessoa.estrangeiro) {
      navigate("./estrangeiro-editar");
    } else {
      navigate("./pessoa-juridica-editar");
    }
  }

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

  const tituloTela = "Cadastro de Clientes";

  return (
    <>
      <CabecalhoPages titulo={tituloTela} />

      <Grid w="100%" templateColumns={{ base: "¨100%", md: "repeat(12, 1fr)" }} mt={4}>
        <InputControl
          label="Código"
          id="idPessoa"
          value={idPessoaBusca?.toString() || ""}
          onChange={(event) =>
            trataValorInputNumberVazio((val) => atualizaValorPesquisa(setIdPessoaBusca, val), event)
          }
          numeroColunasMd={2}
          numeroColunasLg={2}
        />
        <InputControl
          label="Nome"
          id="nome"
          value={nomeBusca.toUpperCase()}
          onChange={(e) => atualizaValorPesquisa(setNomeBusca, e.target.value)}
          numeroColunasMd={6}
          numeroColunasLg={4}
        />
        <InputControl
          label="CPF/CNPJ"
          id="cpfCnpj"
          value={cpfCnpjBusca}
          onChange={(e) =>
            atualizaValorPesquisa(setCpfCnpjBusca, e.target.value.replace(/\D/g, ""))
          }
          numeroColunasMd={4}
          numeroColunasLg={2}
        />
        <InputControl
          label="Bairro"
          id="bairro"
          value={bairroBusca}
          onChange={(e) => atualizaValorPesquisa(setBairroBusca, e.target.value)}
          numeroColunasMd={6}
          numeroColunasLg={4}
        />
        <InputControl
          label="Cidade"
          id="cidade"
          value={cidadeBusca}
          onChange={(e) => atualizaValorPesquisa(setCidadeBusca, e.target.value)}
          numeroColunasMd={6}
          numeroColunasLg={4}
        />
        <SelectControl
          label="Estado"
          id="estado"
          w="200px"
          options={EstadosBrasil}
          onChange={(e) => atualizaValorPesquisa(setEstadoBusca, e)}
          numeroColunasMd={8}
          numeroColunasLg={2}
        />
        <InputControl
          label="CEP"
          id="cep"
          value={cepBusca}
          onChange={(e) => atualizaValorPesquisa(setCepBusca, e.target.value)}
          numeroColunasMd={4}
          numeroColunasLg={2}
        />
        <InputControl
          label="Código Estrangeiro"
          id="idEstrangeiro"
          value={idEstrangeiroBusca}
          onChange={(e) => atualizaValorPesquisa(setIdEstrangeiroBusca, e.target.value)}
          numeroColunasMd={6}
          numeroColunasLg={2}
        />
      </Grid>
      <ButtonControl onNovoClick={() => setAbrirModalTipoCadastro(true)} />
      <TabelaCR5
        cabecalhos={[
          { titulo: "CÓDIGO", dadoBuilder: (item) => item.idPessoa.toString() },
          { titulo: "NOME", dadoBuilder: (item) => item.descricao.toUpperCase() },
          {
            titulo: "CPF/CNPJ",
            dadoBuilder: (item) => (item.cpfCnpj ? mascaraCpfCnpj(item.cpfCnpj) : "ESTRANGEIRO"),
          },
          { titulo: "CIDADE", dadoBuilder: (item) => item.cidade.toUpperCase() },
        ]}
        data={data?.result}
        keybuilder={(item) => item.idPessoa}
        isFetching={isFetching}
        isError={isError}
        error={error}
        detalhesBuilder={{
          type: "Expandir",
          buildDetalhes: (item) => {
            return [
              [
                { nome: "Logradouro", valor: item.logradouro },
                { nome: "Complemento", valor: item.complemento ?? "-" },
                { nome: "Número", valor: item.numeroResidencia ?? "-" },
                { nome: "Estado", valor: item.estado ?? "-" },
                { nome: "Páis", valor: item.pais },
              ],
              [
                { nome: "Telefone 1", valor: item.telefone ?? "-" },
                { nome: "Telefone 2", valor: item.telefone2 ?? "-" },
                { nome: "Celular 1", valor: item.celular ?? "-" },
                { nome: "Celular 2", valor: item.celular2 ?? "-" },
              ],
              item.pessoaFisica
                ? [
                    { nome: "Pessoa Física", valor: "SIM" },
                    { nome: "RG", valor: item.rg },
                    { nome: "Data Nascimento", valor: formatarDataBrasil(item.dataNascimento) },
                  ]
                : item.estrangeiro
                ? [
                    { nome: "Estrangeiro", valor: "SIM" },
                    { nome: "Id Estrangeiro", valor: item.idEstrangeiro },
                  ]
                : [
                    {
                      nome: "Pessoa Jurídica",
                      valor: "SIM",
                    },
                    { nome: "Inscrição Estadual", valor: item.inscricaoEstadual },
                  ],
            ];
          },
          children: (item) => (
            <Button
              size={"sm"}
              onClick={() =>
                handleDadosOperador(
                  item.idOperadorInclusao,
                  item.idOperadorAlteracao,
                  item.dataInclusao,
                  item.dataAlteracao,
                  item.descricao
                )
              }
            >
              Ver Detalhes Operador
            </Button>
          ),
        }}
        onDelete={(item) => {
          setCliente(item);
          onOpen();
        }}
        onEdit={(item) => {
          setCliente(item);
          handleNavigateTipo(item);
        }}
        totalPages={data?.pageTotal ?? 0}
        itemsPerPage={itemsPerPage}
        currentPage={currentPage}
        setCurrentPage={setCurrentPage}
        totalItems={data?.total ?? 0}
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
      <ModalTipoCadastro
        abrirModal={abrirModalTipoCadastro}
        setAbrirModal={setAbrirModalTipoCadastro}
      />
      <ModalConfirma
        titulo="Atenção"
        texto={`Deseja realmente excluir:  ${cliente?.descricao}?`}
        onClose={onClose}
        isOpen={isOpen}
        onConfirm={handleDeleteCliente}
        isLoading={deletePessoaLoading}
      />
    </>
  );
}
