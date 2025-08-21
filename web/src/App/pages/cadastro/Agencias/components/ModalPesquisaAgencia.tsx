import { Grid } from "@chakra-ui/react";

import { useQuery } from "@tanstack/react-query";

import { useState } from "react";

import { AgenciaDTO } from "../../../../../models/DTOs/AgenciaBancoConta/AgenciaDTO.ts";

import { fetchAgenciasPaginado } from "../../../../../requests/requestsAgencias";

import { DelaysDebouncePadrao, TemposCachePadrao } from "../../../../../utils/constantes";

import { mascaraCNPJ } from "../../../../../utils/mascaras";

import { InputControl } from "../../../../components/InputControl";

import { ModalBase } from "../../../../components/ModalBase";

import useCR5Axios from "../../../../hooks/useCR5Axios";

import { trataValorInputNumberVazio } from "../../../../../utils/componentUtil.ts";
import { TabelaCR5 } from "../../../../components/Tabelas/TabelaCR5.tsx";
import useDebounce from "../../../../hooks/useDebounce";

interface ModalPesquisaAgenciaProps {
  isOpen: boolean;
  onClose: () => void;
  setAgenciaFiltrado: (agencia: AgenciaDTO) => void;
}

export function ModalPesquisaAgencia({
  isOpen,
  onClose,
  setAgenciaFiltrado,
}: ModalPesquisaAgenciaProps) {
  const { axios } = useCR5Axios();
  const itemsPerPage = 7;
  const delayDebounce = DelaysDebouncePadrao.PADRAO;

  const [currentPage, setCurrentPage] = useState(1);
  const [numeroBusca, setNumeroBusca] = useState("");
  const [nomeBusca, setNomeBusca] = useState("");
  const [idBusca, setIdBusca] = useState<number | null>();
  const [cidadeBusca, setCidadeBusca] = useState("");
  const [cnpjBusca, setCnpjBusca] = useState("");
  const [nomeBancoBusca, setNomeBancoBusca] = useState("");

  const [numeroDebounce] = useDebounce(numeroBusca, delayDebounce);
  const [nomeDebounce] = useDebounce(nomeBusca, delayDebounce);
  const [idDebounce] = useDebounce(idBusca, delayDebounce);
  const [cidadeDebounce] = useDebounce(cidadeBusca, delayDebounce);
  const [cnpjDebounce] = useDebounce(cnpjBusca, delayDebounce);
  const [nomeBancoDebounce] = useDebounce(nomeBancoBusca, delayDebounce);

  const { data, isFetching, error, isError } = useQuery({
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
    staleTime: TemposCachePadrao.MODERADO,
    cacheTime: TemposCachePadrao.MODERADO,
    queryFn: () => {
      return fetchAgenciasPaginado(
        {
          page: currentPage - 1,
          pageSize: itemsPerPage,
          id: idDebounce,
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

  const selecionaAgencia = (agencia: AgenciaDTO) => {
    setAgenciaFiltrado(agencia);
    onClose();
  };

  return (
    <>
      <ModalBase
        isOpen={isOpen}
        onClose={onClose}
        titulo="Pequisar Agência"
        size="6xl"
        centralizado={true}
      >
        <Grid w="100%" templateColumns={{ base: "¨100%", md: "repeat(10, 1fr)" }} mt={4} p={1}>
          <InputControl
            label="Código"
            id="id"
            value={idBusca?.toString() ?? ""}
            onChange={(event) =>
              trataValorInputNumberVazio((val) => atualizaValorPesquisa(setIdBusca, val), event)
            }
          />
          <InputControl
            label="Número"
            id="numero"
            value={numeroBusca}
            onChange={(e) => atualizaValorPesquisa(setNumeroBusca, e.target.value)}
          />
          <InputControl
            numeroColunasMd={2}
            label="Nome Agência"
            id="nome"
            value={nomeBusca}
            onChange={(e) =>
              atualizaValorPesquisa(setNomeBusca, e.target.value.toLocaleUpperCase())
            }
          />
          <InputControl
            numeroColunasMd={2}
            label="Cidade"
            id="cidade"
            value={cidadeBusca}
            onChange={(e) =>
              atualizaValorPesquisa(setCidadeBusca, e.target.value.toLocaleUpperCase())
            }
          />
          <InputControl
            numeroColunasMd={2}
            label="CNPJ"
            id="cnpj"
            value={cnpjBusca}
            onChange={(e) => atualizaValorPesquisa(setCnpjBusca, e.target.value)}
          />
          <InputControl
            numeroColunasMd={2}
            label="Nome Banco"
            id="nome"
            value={nomeBancoBusca}
            onChange={(e) =>
              atualizaValorPesquisa(setNomeBancoBusca, e.target.value.toLocaleUpperCase())
            }
          />
        </Grid>
        <TabelaCR5
          cabecalhos={[
            { titulo: "CÓDIGO", dadoBuilder: (item) => item.id.toString() },
            { titulo: "NÚMERO", dadoBuilder: (item) => item.numero },
            { titulo: "NOME AGÊNCIA", dadoBuilder: (item) => item.nome.toUpperCase() },
            { titulo: "CIDADE", dadoBuilder: (item) => item.cidade.toUpperCase() },
            { titulo: "CNPJ", dadoBuilder: (item) => mascaraCNPJ(item.cnpj) },
            { titulo: "NOME BANCO", dadoBuilder: (item) => item.banco.nome.toUpperCase() },
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
          onRowClick={(item) => selecionaAgencia(item)}
        />
      </ModalBase>
    </>
  );
}
