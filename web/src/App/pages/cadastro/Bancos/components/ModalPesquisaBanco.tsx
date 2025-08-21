import { Grid } from "@chakra-ui/react";
import { useQuery } from "@tanstack/react-query";
import { useState } from "react";
import { BancoDTO } from "../../../../../models/DTOs/AgenciaBancoConta/BancoDTO.ts";
import { fetchBancosPaginado } from "../../../../../requests/requestsBancos.ts";
import { DelaysDebouncePadrao, TemposCachePadrao } from "../../../../../utils/constantes.ts";
import { InputControl } from "../../../../components/InputControl";
import { ModalBase } from "../../../../components/ModalBase";
import { TabelaCR5 } from "../../../../components/Tabelas/TabelaCR5.tsx";
import useCR5Axios from "../../../../hooks/useCR5Axios.ts";
import useDebounce from "../../../../hooks/useDebounce.ts";

interface ModalPesquisaBancoProps {
  isOpen: boolean;
  onClose: () => void;
  setBancoFiltrado: (banco: BancoDTO) => void;
}

export function ModalPesquisaBanco({ isOpen, onClose, setBancoFiltrado }: ModalPesquisaBancoProps) {
  const { axios } = useCR5Axios();
  const itemsPerPage = 7;
  const delayDebounce = DelaysDebouncePadrao.PADRAO;

  const [currentPage, setCurrentPage] = useState(1);
  const [numeroBusca, setNumeroBusca] = useState("");
  const [nomeBusca, setNomeBusca] = useState("");
  const [abreviaturaBusca, setAbreviaturaBusca] = useState("");

  const [numeroDebounce] = useDebounce(numeroBusca, delayDebounce);
  const [nomeDebounce] = useDebounce(nomeBusca, delayDebounce);
  const [abreviaturaDebounce] = useDebounce(abreviaturaBusca, delayDebounce);

  const { data, isFetching, isError, error } = useQuery({
    queryKey: [
      "fetchBancosPaginado",
      currentPage,
      nomeDebounce,
      abreviaturaDebounce,
      numeroDebounce,
    ],
    keepPreviousData: true,
    staleTime: TemposCachePadrao.MODERADO,
    cacheTime: TemposCachePadrao.MODERADO,
    queryFn: () => {
      return fetchBancosPaginado(
        {
          page: currentPage - 1,
          pageSize: itemsPerPage,
          nome: nomeDebounce,
          numero: numeroDebounce,
          abreviatura: abreviaturaDebounce,
        },
        axios
      );
    },
  });

  function atualizaValorPesquisa<T>(setter: (arg: T) => void, valor: T): void {
    setter(valor);
    setCurrentPage(1);
  }

  const atualizarBanco = (banco: BancoDTO) => {
    setBancoFiltrado(banco);
    setNomeBusca("");
    setNumeroBusca("");
    setAbreviaturaBusca("");
    onClose();
  };

  return (
    <ModalBase
      isOpen={isOpen}
      onClose={onClose}
      titulo="Pequisar Banco"
      size="5xl"
      centralizado={true}
    >
      <Grid w="100%" templateColumns={{ base: "¨100%", md: "repeat(4, 1fr)" }} mt={4} p={1}>
        <InputControl
          label="Número"
          id="numero"
          value={numeroBusca}
          onChange={(e) => atualizaValorPesquisa(setNumeroBusca, e.target.value)}
        />
        <InputControl
          label="Nome"
          id="nome"
          value={nomeBusca}
          onChange={(e) => atualizaValorPesquisa(setNomeBusca, e.target.value.toLocaleUpperCase())}
          numeroColunasMd={2}
        />
        <InputControl
          label="Abreviatura"
          id="abreviatura"
          value={abreviaturaBusca}
          onChange={(e) =>
            atualizaValorPesquisa(setAbreviaturaBusca, e.target.value.toLocaleUpperCase())
          }
        />
      </Grid>
      <TabelaCR5
        cabecalhos={[
          { titulo: "CÓDIGO", dadoBuilder: (item) => item.id.toString() },
          { titulo: "NÚMERO", dadoBuilder: (item) => item.numero },
          { titulo: "NOME BANCO", dadoBuilder: (item) => item.nome, alinhamento: "left" },
          {
            titulo: "ABREVIATURA",
            dadoBuilder: (item) => item.abreviatura,
            alinhamento: "left",
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
        onRowClick={(item) => atualizarBanco(item)}
      />
    </ModalBase>
  );
}
