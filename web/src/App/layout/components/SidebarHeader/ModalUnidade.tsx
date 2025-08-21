import { Grid, GridItem, Text } from "@chakra-ui/react";
import { useQuery } from "@tanstack/react-query";
import { useState } from "react";
import { UnidadeDTO } from "../../../../models/DTOs/Outros/UnidadeDTO";
import { fetchUnidadesPaginadas } from "../../../../requests/requestsUnidades";
import { DelaysDebouncePadrao, TemposCachePadrao } from "../../../../utils/constantes";
import { InputControl } from "../../../components/InputControl";
import { ModalBase } from "../../../components/ModalBase";
import { TabelaCR5 } from "../../../components/Tabelas";
import useCR5Axios from "../../../hooks/useCR5Axios";
import useDebounce from "../../../hooks/useDebounce";
import { useUnidadeStore } from "../../../stores/UnidadeStore";

interface ModalUnidadeProps {
  isOpen: boolean;
  onClose: () => void;
  handleUnidadeSelecionadaSelect?: (unidade: UnidadeDTO) => void;
}

export const ModalUnidade = ({
  isOpen,
  onClose,
  handleUnidadeSelecionadaSelect,
}: ModalUnidadeProps) => {
  const { axios } = useCR5Axios();
  const itemsPerPage = 7;
  const delayDebounce = DelaysDebouncePadrao.PADRAO;

  const [unidadeAtual, setUnidade] = useUnidadeStore((s) => [s.unidadeAtual, s.setUnidade]);

  const [unidadeFiltrada, setUnidadeFiltrada] = useState<UnidadeDTO | null>(unidadeAtual);

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

  const atualizarUnidade = () => {
    setUnidade(unidadeFiltrada ?? unidadeAtual);
    setCidadeBusca("");
    setCodigoBusca("");
    setNomeBusca("");
    setCurrentPage(1);
    onClose();
  };

  const handleCancelar = () => {
    setCidadeBusca("");
    setCodigoBusca("");
    setNomeBusca("");
    setCurrentPage(1);
    onClose();
  };

  function atualizaValorPesquisa<T>(setter: (arg: T) => void, valor: T): void {
    setter(valor);
    setCurrentPage(1);
  }

  function handleSetarUnidadeSelecionada(unidade: UnidadeDTO) {
    if (handleUnidadeSelecionadaSelect) {
      handleUnidadeSelecionadaSelect?.(unidade);
    } else {
      setUnidadeFiltrada(unidade);
    }
  }
  return (
    <ModalBase
      isOpen={isOpen}
      onClose={handleCancelar}
      titulo="Unidades Disponíveis"
      size="3xl"
      centralizado={true}
      botaoConfirma={{
        onClick: () => atualizarUnidade(),
      }}
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
          { titulo: "CÓDIGO", dadoBuilder: (item) => item.id.toString() },
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
        onRowClick={(item) => handleSetarUnidadeSelecionada(item)}
      />
      {unidadeFiltrada && (
        <Grid
          templateColumns={{ base: "1fr", md: "repeat(4, 1fr)" }}
          p={1}
          w="100%"
          fontSize={{ base: "xs", md: "sm" }}
        >
          <GridItem colSpan={{ base: 1, md: 2 }}>
            <Text>
              <strong>Unidade Atual:</strong> {unidadeFiltrada.nome}
            </Text>
          </GridItem>
          <GridItem colSpan={{ base: 1, md: 2 }}>
            <Text>
              <strong>Cidade:</strong> {unidadeFiltrada.cidade} - {unidadeFiltrada.uf}
            </Text>
          </GridItem>
          <GridItem colSpan={1}>
            <Text mr="1rem">
              <strong>Código Unidade:</strong> {unidadeFiltrada.id}
            </Text>
          </GridItem>
          <GridItem colSpan={1}>
            <Text mr="1rem">
              <strong>Código Local:</strong> {unidadeFiltrada.idLocal}
            </Text>
          </GridItem>
          <GridItem colSpan={1}>
            <Text mr="1rem">
              <strong>Código Unidade:</strong> {unidadeFiltrada.codigo}
            </Text>
          </GridItem>
          <GridItem colSpan={1}>
            <Text mr="1rem">
              <strong>Filial ERP:</strong> {unidadeFiltrada.filialERP}
            </Text>
          </GridItem>
        </Grid>
      )}
    </ModalBase>
  );
};
