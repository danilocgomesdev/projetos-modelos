import { Grid } from "@chakra-ui/react";
import { useQuery } from "@tanstack/react-query";
import { useState } from "react";
import { NaturezaProtheusDTO } from "../../../../../models/DTOs/Protheus/NaturezaProtheusDTO";
import { fetchNaturezaProtheusPaginado } from "../../../../../requests/requestsNaturezaProtheus";
import { DelaysDebouncePadrao, TemposCachePadrao } from "../../../../../utils/constantes";
import { InputControl } from "../../../../components/InputControl";
import { ModalBase } from "../../../../components/ModalBase";
import { TabelaCR5 } from "../../../../components/Tabelas";
import useCR5Axios from "../../../../hooks/useCR5Axios";
import useDebounce from "../../../../hooks/useDebounce";

interface ModalPesquisaNaturezaProtheusProps {
  isOpen: boolean;
  onClose: () => void;
  setNaturezaProtheusFiltrado: (naturezaProtheus: NaturezaProtheusDTO) => void;
}

export function ModalPesquisaNaturezaProtheus({
  isOpen,
  onClose,
  setNaturezaProtheusFiltrado,
}: ModalPesquisaNaturezaProtheusProps) {
  const { axios } = useCR5Axios();
  const itemsPerPage = 7;
  const delayDebounce = DelaysDebouncePadrao.PADRAO;

  const [currentPage, setCurrentPage] = useState(1);
  const [naturezaBusca, setNaturezaBusca] = useState("");
  const [descricaoNaturezaBusca, setDescricaoNaturezaBusca] = useState("");

  const [naturezaDebounce] = useDebounce(naturezaBusca, delayDebounce);
  const [descricaoNaturezaDebounce] = useDebounce(descricaoNaturezaBusca, delayDebounce);

  const { data, isFetching, isError, error } = useQuery({
    queryKey: [
      "fetchNaturezaProtheusModal",
      currentPage,
      naturezaDebounce,
      descricaoNaturezaDebounce,
    ],
    keepPreviousData: true,
    staleTime: TemposCachePadrao.MODERADO,
    cacheTime: TemposCachePadrao.MODERADO,
    queryFn: () => {
      return fetchNaturezaProtheusPaginado(
        {
          page: currentPage - 1,
          pageSize: itemsPerPage,
          natureza: naturezaDebounce,
          descricaoNatureza: descricaoNaturezaDebounce,
        },
        axios
      );
    },
  });

  function atualizaValorPesquisa<T>(setter: (arg: T) => void, valor: T): void {
    setter(valor);
    setCurrentPage(1);
  }

  const atualizarNaturezaProtheus = (naturezaProtheus: NaturezaProtheusDTO) => {
    setNaturezaProtheusFiltrado(naturezaProtheus);
    setNaturezaBusca("");
    setDescricaoNaturezaBusca("");
    onClose();
  };

  function limparCampos() {
    setNaturezaBusca("");
    setDescricaoNaturezaBusca("");
  }

  return (
    <>
      <ModalBase
        isOpen={isOpen}
        onClose={() => {
          limparCampos();
          onClose();
        }}
        titulo="Pequisar Natureza Protheus"
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
            label="Natureza"
            id="natureza"
            value={naturezaBusca}
            onChange={(e) => atualizaValorPesquisa(setNaturezaBusca, e.target.value)}
            numeroColunasMd={6}
            numeroColunasLg={6}
          />
          <InputControl
            label="Descrição Natureza"
            id="descricaoNaturezaBusca"
            value={descricaoNaturezaBusca}
            onChange={(e) =>
              atualizaValorPesquisa(setDescricaoNaturezaBusca, e.target.value.toLocaleUpperCase())
            }
            numeroColunasMd={6}
            numeroColunasLg={6}
          />
        </Grid>
        <TabelaCR5
          cabecalhos={[
            {
              titulo: "NATUREZA",
              alinhamento: "left",
              dadoBuilder: (item) => item.natureza.trim(),
            },
            {
              titulo: "DESC. NATUREZA",
              alinhamento: "left",
              dadoBuilder: (item) => item.naturezaDescricao?.toUpperCase().trim() || "-",
            },
          ]}
          data={data?.result}
          keybuilder={(item) => item.natureza + item.natureza}
          isFetching={isFetching}
          isError={isError}
          error={error}
          totalPages={data?.pageTotal ?? 0}
          itemsPerPage={itemsPerPage}
          currentPage={currentPage}
          setCurrentPage={setCurrentPage}
          totalItems={data?.total ?? 0}
          alturaLoading="20vh"
          onRowClick={atualizarNaturezaProtheus}
        />
      </ModalBase>
    </>
  );
}
