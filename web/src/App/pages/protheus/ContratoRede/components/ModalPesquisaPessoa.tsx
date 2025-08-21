import { Grid } from "@chakra-ui/react";
import { useQuery } from "@tanstack/react-query";
import { useState } from "react";
import { PessoaCr5DTO } from "../../../../../models/DTOs/Outros/PessoaCr5DTO.ts";
import { fetchPessoasCr5Paginado } from "../../../../../requests/requestsPessoasCr5.ts";
import { DelaysDebouncePadrao, TemposCachePadrao } from "../../../../../utils/constantes.ts";
import { mascaraCpfCnpj } from "../../../../../utils/mascaras.ts";
import { removeCaracteresEspeciais } from "../../../../../utils/removeCaracteresEspeciais.ts";
import { InputControl } from "../../../../components/InputControl/index.tsx";
import { ModalBase } from "../../../../components/ModalBase/index.tsx";
import { TabelaCR5 } from "../../../../components/Tabelas/TabelaCR5.tsx";
import useCR5Axios from "../../../../hooks/useCR5Axios.ts";
import useDebounce from "../../../../hooks/useDebounce.ts";

interface ModalPesquisaPessoaProps {
  isOpen: boolean;
  onClose: () => void;
  onPessoaSelecionada: (pessoa: PessoaCr5DTO) => void;
}

export function ModalPesquisaPessoa({
  isOpen,
  onClose,
  onPessoaSelecionada,
}: ModalPesquisaPessoaProps) {
  const { axios } = useCR5Axios();
  const itemsPerPage = 7;
  const delayDebounce = DelaysDebouncePadrao.PADRAO;

  const [currentPage, setCurrentPage] = useState(1);
  const [nomeBusca, setNomeBusca] = useState("");
  const [cpfCnpjBusca, setCpfCnpjBusca] = useState("");

  const [nomeDebounce] = useDebounce(nomeBusca, delayDebounce);
  const [cpfCnpjDebounce] = useDebounce(cpfCnpjBusca, delayDebounce);

  const { data, isFetching, error, isError } = useQuery({
    queryKey: ["fetchPessoasCr5Paginado", currentPage, nomeDebounce, cpfCnpjDebounce],
    keepPreviousData: true,
    staleTime: TemposCachePadrao.MODERADO,
    cacheTime: TemposCachePadrao.MODERADO,
    queryFn: () => {
      return fetchPessoasCr5Paginado(
        {
          page: currentPage - 1,
          pageSize: itemsPerPage,
          nome: nomeDebounce,
          cpfCnpj: removeCaracteresEspeciais(cpfCnpjDebounce),
        },
        axios
      );
    },
  });

  function atualizaValorPesquisa<T>(setter: (arg: T) => void, valor: T): void {
    setter(valor);
    setCurrentPage(1);
  }

  function handlePessoaSelicionada(item: PessoaCr5DTO) {
    onPessoaSelecionada(item);
    limparCampos();
    console.log(item);
  }

  function limparCampos() {
    setNomeBusca("");
    setCpfCnpjBusca("");
    setCurrentPage(1);
  }

  return (
    <>
      <ModalBase
        isOpen={isOpen}
        onClose={() => {
          limparCampos();
          onClose();
        }}
        titulo="Pequisar Cliente"
        size="6xl"
        centralizado={true}
      >
        <Grid w="100%" templateColumns={{ base: "¨100%", md: "repeat(10, 1fr)" }} mt={4} p={1}>
          <InputControl
            numeroColunasMd={4}
            label="Nome"
            id="nome"
            value={nomeBusca}
            onChange={(e) =>
              atualizaValorPesquisa(setNomeBusca, e.target.value.toLocaleUpperCase().trim())
            }
          />
          <InputControl
            numeroColunasMd={4}
            label="CPF/CNPJ"
            id="cpfCnpj"
            value={cpfCnpjBusca}
            onChange={(e) => atualizaValorPesquisa(setCpfCnpjBusca, e.target.value.trim())}
          />
        </Grid>
        <TabelaCR5
          cabecalhos={[
            { titulo: "CPF/CNPJ", dadoBuilder: (item) => mascaraCpfCnpj(item.cpfCnpj) },
            { titulo: "NOME", dadoBuilder: (item) => item.descricao, alinhamento: "left" },
            { titulo: "CIDADE", dadoBuilder: (item) => item.cidade.toUpperCase() },
          ]}
          data={data?.result}
          keybuilder={(item) => item.cpfCnpj}
          isFetching={isFetching}
          isError={isError}
          error={error}
          totalPages={data?.pageTotal ?? 0}
          itemsPerPage={itemsPerPage}
          currentPage={currentPage}
          setCurrentPage={setCurrentPage}
          totalItems={data?.total ?? 0}
          onRowClick={(item) => handlePessoaSelicionada(item)}
        />
      </ModalBase>
    </>
  );
}
