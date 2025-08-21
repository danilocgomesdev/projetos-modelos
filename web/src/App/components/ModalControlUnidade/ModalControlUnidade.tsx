import { Grid } from "@chakra-ui/react";
import { useQuery } from "@tanstack/react-query";
import { useState } from "react";
import { UnidadeDTO } from "../../../models/DTOs/Outros/UnidadeDTO";
import { fetchUnidadesPaginadas } from "../../../requests/requestsUnidades";
import { DelaysDebouncePadrao, TemposCachePadrao } from "../../../utils/constantes";
import useCR5Axios from "../../hooks/useCR5Axios";
import useDebounce from "../../hooks/useDebounce";
import { InputControl } from "../InputControl";
import { ModalBase } from "../ModalBase";
import { TabelaCR5 } from "../Tabelas";

interface ModalControlUnidadeProps {
  isOpen: boolean;
  onClose: () => void;
  setUnidadeFiltrada: (unidade: UnidadeDTO) => void;
}

export const ModalControlUnidade = ({
  isOpen,
  onClose,
  setUnidadeFiltrada,
}: ModalControlUnidadeProps) => {
  const { axios } = useCR5Axios();
  const itemsPerPage = 7;
  const delayDebounce = DelaysDebouncePadrao.PADRAO;

  const [currentPage, setCurrentPage] = useState(1);
  const [codigoBusca, setCodigoBusca] = useState("");
  const [nomeBusca, setNomeBusca] = useState("");
  const [cidadeBusca, setCidadeBusca] = useState("");

  const [codigoDebounce] = useDebounce(codigoBusca, delayDebounce);
  const [nomeDebounce] = useDebounce(nomeBusca, delayDebounce);
  const [cidadeDebounce] = useDebounce(cidadeBusca, delayDebounce);

  const { data, isFetching, isError, error } = useQuery({
    queryKey: ["fetchUnidadesPaginado", currentPage, nomeDebounce, cidadeDebounce, codigoDebounce],
    keepPreviousData: true,
    staleTime: TemposCachePadrao.ESTATICO,
    cacheTime: TemposCachePadrao.ESTATICO,
    queryFn: () => {
      return fetchUnidadesPaginadas(
        {
          page: currentPage - 1,
          pageSize: itemsPerPage,
          nome: nomeDebounce,
          cidade: cidadeDebounce,
          codigo: codigoDebounce,
        },
        axios
      );
    },
  });

  function atualizaValorPesquisa<T>(setter: (arg: T) => void, valor: T): void {
    setter(valor);
    setCurrentPage(1);
  }

  const selecionaUniade = (unidade: UnidadeDTO) => {
    setUnidadeFiltrada(unidade);
    setNomeBusca("");
    setCidadeBusca("");
    setCodigoBusca("");
    onClose();
  };

  return (
    <ModalBase
      isOpen={isOpen}
      onClose={onClose}
      titulo="Unidades Disponíveis"
      size="3xl"
      centralizado={true}
    >
      {" "}
      <Grid w="100%" templateColumns={{ base: "¨100%", md: "repeat(5, 1fr)" }} mt={4} p={1}>
        <InputControl
          label="Código"
          id="codigo"
          value={codigoBusca}
          onChange={(e) => atualizaValorPesquisa(setCodigoBusca, e.target.value)}
        />
        <InputControl
          label="Unidade"
          id="unidade"
          value={nomeBusca}
          onChange={(e) => atualizaValorPesquisa(setNomeBusca, e.target.value.toLocaleUpperCase())}
          numeroColunasMd={2}
        />
        <InputControl
          label="Cidade"
          id="cidade"
          value={cidadeBusca}
          onChange={(e) =>
            atualizaValorPesquisa(setCidadeBusca, e.target.value.toLocaleUpperCase())
          }
          numeroColunasMd={2}
        />
      </Grid>
      <TabelaCR5
        cabecalhos={[
          { titulo: "CÓDIGO", dadoBuilder: (item) => item.codigo.toString() },
          {
            titulo: "UNIDADE",
            dadoBuilder: (item) => item.nome.toUpperCase(),
            alinhamento: "left",
          },
          { titulo: "CIDADE", dadoBuilder: (item) => item.cidade.toUpperCase() },
        ]}
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
        onRowClick={(item) => selecionaUniade(item)}
      />
    </ModalBase>
  );
};
