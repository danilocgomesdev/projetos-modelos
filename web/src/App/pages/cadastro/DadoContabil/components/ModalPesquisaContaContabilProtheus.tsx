import { Grid } from "@chakra-ui/react";
import { useQuery } from "@tanstack/react-query";
import { useEffect, useState } from "react";
import { ContaContabilProtheusDTO } from "../../../../../models/DTOs/Contabil/ContaContabilProtheusDTO";
import { fetchContaContabilProtheusPaginado } from "../../../../../requests/requestsContaContabilProtheus";
import queryClient from "../../../../../singletons/reactQueryClient";
import { getEntidadeNome } from "../../../../../utils/componentUtil";
import { DelaysDebouncePadrao } from "../../../../../utils/constantes";
import { InputControl } from "../../../../components/InputControl";
import { ModalBase } from "../../../../components/ModalBase";
import { TabelaCR5 } from "../../../../components/Tabelas";
import useCR5Axios from "../../../../hooks/useCR5Axios";
import useDebounce from "../../../../hooks/useDebounce";

interface ModalPesquisaContaContabilProtheusProps {
  isOpen: boolean;
  onClose: () => void;
  setContaContabilProtheusFiltrado: (contaContabilProtheus: ContaContabilProtheusDTO) => void;
  entidade: string;
}

export function ModalPesquisaContaContabilProtheus({
  isOpen,
  onClose,
  setContaContabilProtheusFiltrado,
  entidade,
}: ModalPesquisaContaContabilProtheusProps) {
  const { axios } = useCR5Axios();
  const itemsPerPage = 7;
  const delayDebounce = DelaysDebouncePadrao.PADRAO;

  const [currentPage, setCurrentPage] = useState(1);
  const [contaContabilBusca, setContaContabilBusca] = useState("");
  const [contaContabilDescricaoBusca, setContaContabilDescricaoBusca] = useState("");

  const [contaContabilDebounce] = useDebounce(contaContabilBusca, delayDebounce);
  const [contaContabilDescricaoDebounce] = useDebounce(contaContabilDescricaoBusca, delayDebounce);

  const [hasFetched, setHasFetched] = useState(false);

  const { data, isFetching, isError, error } = useQuery({
    queryKey: [
      "fetchContaContabilProtheusModal",
      currentPage,
      contaContabilDebounce,
      contaContabilDescricaoDebounce,
    ],
    keepPreviousData: true,
    staleTime: 0,
    enabled: isOpen && !hasFetched,
    queryFn: () => {
      return fetchContaContabilProtheusPaginado(
        {
          page: currentPage - 1,
          pageSize: itemsPerPage,
          contaContabil: contaContabilDebounce,
          contaContabilDescricao: contaContabilDescricaoDebounce,
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
      queryClient.invalidateQueries({ queryKey: ["fetchContaContabilProtheusModal"] });
    }
  }, [isOpen]);

  function atualizaValorPesquisa<T>(setter: (arg: T) => void, valor: T): void {
    setter(valor);
    setCurrentPage(1);
  }

  const atualizarContaContabilProtheus = (contaContabilProtheus: ContaContabilProtheusDTO) => {
    setContaContabilProtheusFiltrado(contaContabilProtheus);
    setContaContabilBusca("");
    setContaContabilDescricaoBusca("");
    onClose();
  };

  function limparCampos() {
    setContaContabilBusca("");
    setContaContabilDescricaoBusca("");
  }

  return (
    <ModalBase
      isOpen={isOpen}
      onClose={() => {
        limparCampos();
        onClose();
      }}
      titulo="Pequisar Produto Conta Contábil"
      size="2xl"
      centralizado={true}
    >
      <Grid
        w="100%"
        templateColumns={{ base: "¨100%", md: "repeat(12, 1fr)" }}
        mt={4}
        pb={{ base: 4, md: 1 }}
      >
        <InputControl
          label="Conta Contábil"
          id="contaContabil"
          value={contaContabilBusca}
          onChange={(e) => atualizaValorPesquisa(setContaContabilBusca, e.target.value)}
          numeroColunasMd={6}
          numeroColunasLg={6}
        />
        <InputControl
          label="Descrição Conta Contábil"
          id="descricaoConta"
          value={contaContabilDescricaoBusca}
          onChange={(e) =>
            atualizaValorPesquisa(
              setContaContabilDescricaoBusca,
              e.target.value.toLocaleUpperCase()
            )
          }
          numeroColunasMd={6}
          numeroColunasLg={6}
        />
      </Grid>
      <TabelaCR5
        cabecalhos={[
          {
            titulo: "CONTA CONTÁBIL",
            alinhamento: "left",
            dadoBuilder: (item) => item.contaContabil,
          },
          {
            titulo: "DESC. CONTA CONTÁBIL",
            alinhamento: "left",
            dadoBuilder: (item) => item.contaContabilDescricao?.toUpperCase() || "-",
          },
          {
            titulo: "ENTIDADE",
            dadoBuilder: (item) => getEntidadeNome(item.entidade) || "-",
          },
        ]}
        data={data?.result}
        keybuilder={(item) => item.contaContabil + item.entidade}
        isFetching={isFetching}
        isError={isError}
        error={error}
        totalPages={data?.pageTotal ?? 0}
        itemsPerPage={itemsPerPage}
        currentPage={currentPage}
        setCurrentPage={setCurrentPage}
        totalItems={data?.total ?? 0}
        alturaLoading="20vh"
        onRowClick={atualizarContaContabilProtheus}
      />
    </ModalBase>
  );
}
