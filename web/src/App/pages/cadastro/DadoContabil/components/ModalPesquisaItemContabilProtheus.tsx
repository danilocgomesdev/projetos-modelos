import { Grid } from "@chakra-ui/react";
import { useQuery } from "@tanstack/react-query";
import { useEffect, useState } from "react";
import { ItemContabilProtheusDTO } from "../../../../../models/DTOs/Contabil/ItemContabilProtheusDTO";
import { fetchItemContabilProtheusPaginado } from "../../../../../requests/requestsItemContabilProtheus";
import queryClient from "../../../../../singletons/reactQueryClient";
import { getEntidadeNome } from "../../../../../utils/componentUtil";
import { DelaysDebouncePadrao, TemposCachePadrao } from "../../../../../utils/constantes";
import { InputControl } from "../../../../components/InputControl";
import { ModalBase } from "../../../../components/ModalBase";
import { TabelaCR5 } from "../../../../components/Tabelas";
import useCR5Axios from "../../../../hooks/useCR5Axios";
import useDebounce from "../../../../hooks/useDebounce";

interface ModalPesquisaItemContabilProtheusProps {
  isOpen: boolean;
  onClose: () => void;
  setItemContabilProtheusFiltrado: (itemContabilProtheus: ItemContabilProtheusDTO) => void;
  entidade: string;
}

export function ModalPesquisaItemContabilProtheus({
  isOpen,
  onClose,
  setItemContabilProtheusFiltrado,
  entidade,
}: ModalPesquisaItemContabilProtheusProps) {
  const { axios } = useCR5Axios();
  const itemsPerPage = 7;
  const delayDebounce = DelaysDebouncePadrao.PADRAO;

  const [currentPage, setCurrentPage] = useState(1);
  const [itemContabilBusca, setItemContabilBusca] = useState("");
  const [itemContabilDescricaoBusca, setItemContabilDescricaoBusca] = useState("");

  const [itemContabilDebounce] = useDebounce(itemContabilBusca, delayDebounce);
  const [itemContabilDescricaoDebounce] = useDebounce(itemContabilDescricaoBusca, delayDebounce);

  const [hasFetched, setHasFetched] = useState(false);

  const { data, isFetching, isError, error } = useQuery({
    queryKey: [
      "fetchItemContabilProtheusModal",
      currentPage,
      itemContabilDebounce,
      itemContabilDescricaoDebounce,
    ],
    keepPreviousData: true,
    staleTime: TemposCachePadrao.MODERADO,
    cacheTime: TemposCachePadrao.MODERADO,
    enabled: isOpen && !hasFetched,
    queryFn: () => {
      return fetchItemContabilProtheusPaginado(
        {
          page: currentPage - 1,
          pageSize: itemsPerPage,
          itemContabil: itemContabilDebounce,
          itemContabilDescricao: itemContabilDescricaoDebounce,
          entidade,
        },
        axios
      );
    },
    onSuccess: () => {
      setHasFetched(true);
    },
  });

  useEffect(() => {
    if (isOpen) {
      setHasFetched(false);
      setCurrentPage(1);
    }
  }, [isOpen]);

  useEffect(() => {
    if (!isOpen) {
      queryClient.invalidateQueries({ queryKey: ["fetchItemContabilProtheusModal"] });
    }
  }, [isOpen]);

  function atualizaValorPesquisa<T>(setter: (arg: T) => void, valor: T): void {
    setter(valor);
    setCurrentPage(1);
  }

  const atualizarItemContabilProtheus = (itemContabilProtheus: ItemContabilProtheusDTO) => {
    setItemContabilProtheusFiltrado(itemContabilProtheus);
    setItemContabilBusca("");
    setItemContabilDescricaoBusca("");
    onClose();
  };

  function limparCampos() {
    setItemContabilBusca("");
    setItemContabilDescricaoBusca("");
  }

  return (
    <ModalBase
      isOpen={isOpen}
      onClose={() => {
        limparCampos();
        onClose();
      }}
      titulo="Pequisar Item Contábil"
      size="3xl"
      centralizado={true}
    >
      <Grid
        w="100%"
        templateColumns={{ base: "¨100%", md: "repeat(12, 1fr)" }}
        mt={4}
        pb={{ base: 4, md: 1 }}
      >
        <InputControl
          label="Item Contábil"
          id="itemContabil"
          value={itemContabilBusca}
          onChange={(e) => atualizaValorPesquisa(setItemContabilBusca, e.target.value)}
          numeroColunasMd={6}
          numeroColunasLg={6}
        />
        <InputControl
          label="Descrição Item Contábil"
          id="descricaoItemContabilBusca"
          value={itemContabilDescricaoBusca}
          onChange={(e) =>
            atualizaValorPesquisa(setItemContabilDescricaoBusca, e.target.value.toLocaleUpperCase())
          }
          numeroColunasMd={6}
          numeroColunasLg={6}
        />
      </Grid>
      <TabelaCR5
        cabecalhos={[
          {
            titulo: "ITEM CONTÁBIL",
            alinhamento: "left",
            dadoBuilder: (item) => item.itemContabil.trim(),
          },
          {
            titulo: "DESC. ITEM CONTÁBIL",
            alinhamento: "left",
            dadoBuilder: (item) => item.itemContabilDescricao?.toUpperCase().trim() || "-",
          },
          {
            titulo: "ENTIDADE",
            dadoBuilder: (item) => getEntidadeNome(item.entidade) || "-",
          },
        ]}
        data={data?.result}
        keybuilder={(item) => item.itemContabil + item.entidade}
        isFetching={isFetching}
        isError={isError}
        error={error}
        totalPages={data?.pageTotal ?? 0}
        itemsPerPage={itemsPerPage}
        currentPage={currentPage}
        setCurrentPage={setCurrentPage}
        totalItems={data?.total ?? 0}
        alturaLoading="20vh"
        onRowClick={atualizarItemContabilProtheus}
      />
    </ModalBase>
  );
}
