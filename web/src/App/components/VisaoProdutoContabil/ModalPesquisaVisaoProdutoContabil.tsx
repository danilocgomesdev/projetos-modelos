import { Grid } from "@chakra-ui/react";
import { useQuery } from "@tanstack/react-query";
import { useState } from "react";
import { VisaoProdutoContabilDTO } from "../../../models/DTOs/Visoes/VisaoProdutoContabilDTOs.ts";
import { fetchVisaoProdutoContabilPaginadas } from "../../../requests/requestsVisaoProdutoContabil.ts";
import { trataValorInputNumberVazio } from "../../../utils/componentUtil.ts";
import { DelaysDebouncePadrao, TemposCachePadrao } from "../../../utils/constantes.ts";
import { getSistemaEnum } from "../../enums/SistemaEnum.ts";
import useCR5Axios from "../../hooks/useCR5Axios.ts";
import useDebounce from "../../hooks/useDebounce.ts";
import { DropBoxSistemas } from "../DropBoxSistemas/index.ts";
import { InputControl } from "../InputControl/index.tsx";
import { ModalBase } from "../ModalBase/index.tsx";
import { TabelaCR5 } from "../Tabelas/TabelaCR5.tsx";

interface ModalPesquisaVisaoProdutoContabilProps {
  abrirModal: boolean;
  onClose: () => void;
  setVisaoProdutoContabilFiltrado: (visaoProdutoDadoContabilDTO: VisaoProdutoContabilDTO) => void;
}

export function ModalPesquisaVisaoProdutoContabil({
  abrirModal,
  onClose,
  setVisaoProdutoContabilFiltrado,
}: ModalPesquisaVisaoProdutoContabilProps) {
  const { axios } = useCR5Axios();
  const itemsPerPage = 7;
  const delayDebouce = DelaysDebouncePadrao.PADRAO;

  const [currentPage, setCurrentPage] = useState(1);
  const [idProdutoBusca, setIdProdutoBusca] = useState<number | null>();
  const [idSistemaBusca, setIdSistemaBusca] = useState<number | null>();
  const [produtoBusca, setProdutoBusca] = useState<string>("");
  const [codProdutoProtheusBusca, setCodProdutoProtheusBusca] = useState("");

  const [idProdutoDebouce] = useDebounce(idProdutoBusca, delayDebouce);
  const [idSistemaDebouce] = useDebounce(idSistemaBusca, delayDebouce);
  const [produtoDebounce] = useDebounce(produtoBusca, delayDebouce);
  const [codProdutoProtheusDebouce] = useDebounce(codProdutoProtheusBusca, delayDebouce);

  const { data, isFetching, isError, error } = useQuery({
    queryKey: [
      "fetchVisaoServicosModal",
      currentPage,
      idProdutoDebouce,
      idSistemaDebouce,
      produtoDebounce,
      codProdutoProtheusDebouce,
    ],
    keepPreviousData: true,
    staleTime: TemposCachePadrao.MODERADO,
    cacheTime: TemposCachePadrao.MODERADO,
    queryFn: () => {
      return fetchVisaoProdutoContabilPaginadas(
        {
          page: currentPage - 1,
          pageSize: itemsPerPage,
          idProduto: idProdutoDebouce ?? undefined,
          idSistema: idSistemaDebouce ?? undefined,
          produto: produtoDebounce ?? undefined,
          codProdutoProtheus: codProdutoProtheusDebouce,
        },
        axios
      );
    },
  });

  function atualizaValorPesquisa<T>(setter: (arg: T) => void, valor: T): void {
    setter(valor);
    setCurrentPage(1);
  }

  const atualizarVisaoServicos = (visaoProdutoContabil: VisaoProdutoContabilDTO) => {
    setVisaoProdutoContabilFiltrado(visaoProdutoContabil);
    setIdProdutoBusca(null);
    setIdSistemaBusca(null);
    setProdutoBusca("");
    setCodProdutoProtheusBusca("");
    onClose();
  };
  return (
    <>
      <ModalBase
        isOpen={abrirModal}
        onClose={() => {
          onClose();
        }}
        titulo="Pequisar Visão Serviços"
        size="6xl"
        centralizado={true}
      >
        <Grid w="100%" templateColumns={{ base: "¨100%", md: "repeat(12, 1fr)" }} mt={4} p={1}>
          <InputControl
            label="Código Produto"
            id="idProduto"
            type="number"
            value={idProdutoBusca?.toString()}
            numeroColunasMd={2}
            onChange={(event) =>
              trataValorInputNumberVazio(
                (val) => atualizaValorPesquisa(setIdProdutoBusca, val),
                event
              )
            }
          />
          <DropBoxSistemas
            onChange={(idSistema) => atualizaValorPesquisa(setIdSistemaBusca, idSistema)}
            numeroColunasMd={3}
            value={idSistemaBusca}
          />
          <InputControl
            label="Nome"
            id="nome"
            value={produtoBusca}
            onChange={(e) =>
              atualizaValorPesquisa(setProdutoBusca, e.target.value.toLocaleUpperCase())
            }
            numeroColunasMd={3}
          />
          <InputControl
            label="Código Protheus Protheus"
            id="codProdutoProtheus"
            value={codProdutoProtheusBusca}
            onChange={(e) => atualizaValorPesquisa(setCodProdutoProtheusBusca, e.target.value)}
            numeroColunasMd={3}
          />
        </Grid>
        <TabelaCR5
          cabecalhos={[
            { titulo: "CÓDIGO", dadoBuilder: (item) => item.idProduto.toString() },
            {
              titulo: "SISTEMA",
              dadoBuilder: (item) => getSistemaEnum(item.idSistema).toUpperCase(),
            },
            { titulo: "NOME", dadoBuilder: (item) => item.produto.toUpperCase() },
            {
              titulo: "CÓDIGO PRODUTO PROTHEUS",
              dadoBuilder: (item) => item.codProdutoProtheus?.toString() || "-",
            },
            { titulo: "STATUS", dadoBuilder: (item) => item.status?.toUpperCase() || "-" },
          ]}
          data={data?.result}
          keybuilder={(item) => `${item.idProduto} - ${item.idSistema} `}
          isFetching={isFetching}
          isError={isError}
          error={error}
          totalPages={data?.pageTotal ?? 0}
          itemsPerPage={itemsPerPage}
          currentPage={currentPage}
          setCurrentPage={setCurrentPage}
          totalItems={data?.total ?? 0}
          onRowClick={(item) => atualizarVisaoServicos(item)}
        />
      </ModalBase>
    </>
  );
}
