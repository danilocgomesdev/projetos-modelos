import { Grid } from "@chakra-ui/react";
import { useQuery } from "@tanstack/react-query";
import { useState } from "react";
import { PessoaCIDTO } from "../../../models/DTOs/Outros/Gestor";
import { fetchPessoasCIPaginado } from "../../../requests/requestsGestor";
import { DelaysDebouncePadrao, TemposCachePadrao } from "../../../utils/constantes";
import useCR5Axios from "../../hooks/useCR5Axios";
import useDebounce from "../../hooks/useDebounce";
import { InputControl } from "../InputControl";
import { ModalBase } from "../ModalBase";
import { TabelaCR5 } from "../Tabelas";
interface ModalPesquisaPessoaCIProps {
  isOpen: boolean;
  onClose: () => void;
  setPessoaFiltrada: (pessoa: PessoaCIDTO) => void;
}

export function ModalPesquisaPessoaCI({
  isOpen,
  onClose,
  setPessoaFiltrada,
}: ModalPesquisaPessoaCIProps) {
  const { axios } = useCR5Axios();
  const itemsPerPage = 7;
  const delayDebounce = DelaysDebouncePadrao.PADRAO;

  const [currentPage, setCurrentPage] = useState(1);
  const [nomeBusca, setNomeBusca] = useState("");
  const [emailBusca, setEmailBusca] = useState("");
  const [matriculaBusca, setMatriculaBusca] = useState("");

  const [nomeDebounce] = useDebounce(nomeBusca, delayDebounce);
  const [emailDebounce] = useDebounce(emailBusca, delayDebounce);
  const [matriculaDebounce] = useDebounce(matriculaBusca, delayDebounce);

  const { data, isFetching, isError, error } = useQuery({
    queryKey: [
      "fetchPessoasCIPaginado",
      currentPage,
      nomeDebounce,
      emailDebounce,
      matriculaDebounce,
    ],
    keepPreviousData: true,
    staleTime: TemposCachePadrao.MODERADO,
    cacheTime: TemposCachePadrao.MODERADO,
    queryFn: () => {
      return fetchPessoasCIPaginado(
        {
          page: currentPage - 1,
          pageSize: itemsPerPage,
          nome: nomeDebounce,
          email: emailDebounce,
          matricula: matriculaDebounce ? Number(matriculaDebounce) : null,
        },
        axios
      );
    },
  });

  function atualizaValorPesquisa<T>(setter: (arg: T) => void, valor: T): void {
    setter(valor);
    setCurrentPage(1);
  }

  const atualizarPessoa = (pessoa: PessoaCIDTO) => {
    setPessoaFiltrada(pessoa);
    setNomeBusca("");
    setEmailBusca("");
    setMatriculaBusca("");
    onClose();
  };

  return (
    <ModalBase
      isOpen={isOpen}
      onClose={onClose}
      titulo="Pesquisar Pessoa"
      size="5xl"
      centralizado={true}
    >
      <Grid w="100%" templateColumns={{ base: "100%", md: "repeat(12, 1fr)" }} mt={4} p={1}>
        <InputControl
          label="Nome"
          id="nome"
          value={nomeBusca}
          numeroColunasMd={6}
          onChange={(e) => atualizaValorPesquisa(setNomeBusca, e.target.value.toUpperCase())}
        />
        <InputControl
          label="Email"
          id="email"
          value={emailBusca}
          numeroColunasMd={4}
          onChange={(e) => atualizaValorPesquisa(setEmailBusca, e.target.value)}
        />
        <InputControl
          label="Matrícula"
          id="matricula"
          value={matriculaBusca}
          numeroColunasMd={2}
          onChange={(e) => atualizaValorPesquisa(setMatriculaBusca, e.target.value)}
        />
      </Grid>
      <TabelaCR5
        cabecalhos={[
          { titulo: "ID", dadoBuilder: (item) => item.id.toString() },
          { titulo: "NOME", dadoBuilder: (item) => item.nome, alinhamento: "left" },
          { titulo: "EMAIL", dadoBuilder: (item) => item.email, alinhamento: "left" },
          {
            titulo: "MATRÍCULA",
            dadoBuilder: (item) => item.matricula ?? "N/A",
            alinhamento: "center",
          },
        ]}
        alturaLoading="30vh"
        data={data?.result}
        keybuilder={(item) => item.id}
        isFetching={isFetching}
        isError={isError}
        error={error}
        totalPages={data?.pageTotal ?? 0}
        itemsPerPage={itemsPerPage}
        currentPage={currentPage}
        setCurrentPage={setCurrentPage}
        totalItems={data?.total ?? 0}
        onRowClick={(item) => atualizarPessoa(item)}
      />
    </ModalBase>
  );
}
