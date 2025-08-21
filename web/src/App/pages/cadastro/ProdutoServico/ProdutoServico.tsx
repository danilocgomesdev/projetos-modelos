import { Box, Grid } from "@chakra-ui/react";
import { useQuery } from "@tanstack/react-query";
import { useState } from "react";
import { useNavigate } from "react-router-dom";
import { fetchProdutoDadoContabilPaginado } from "../../../../requests/requestsProdutoDadoContabil";
import queryClient from "../../../../singletons/reactQueryClient";
import Acessos from "../../../../utils/Acessos";
import { trataValorInputNumberVazio } from "../../../../utils/componentUtil";
import { DelaysDebouncePadrao, TemposCachePadrao } from "../../../../utils/constantes";
import {
  formatarDataHoraBrasil,
  formatarMoedaBrasil,
  mascaraMoeda,
} from "../../../../utils/mascaras";
import { ButtonControl } from "../../../components/ButoonControl";
import { CabecalhoPages } from "../../../components/CabecalhoPages";
import { DropBoxSistemas } from "../../../components/DropBoxSistemas";
import { InputControl } from "../../../components/InputControl";
import { SelectControl } from "../../../components/SelectControl";
import { TabelaCR5 } from "../../../components/Tabelas";
import useCR5Axios from "../../../hooks/useCR5Axios";
import useDebounce from "../../../hooks/useDebounce";
import { useValidaAcessos } from "../../../hooks/useValidaAcessos";
import { useProdutoDadoContabilStore } from "../ProdutoDadosContabil/store/ProdutoDadoContabilStore";

export function ProdutoServico() {
  const { axios } = useCR5Axios();
  const itemsPerPage = 10;
  const delayDebounce = DelaysDebouncePadrao.PADRAO;
  const navigate = useNavigate();
  const [invalidando, setInvalidando] = useState(true);

  const [currentPage, setCurrentPage] = useState(1);

  const [idProdutoBusca, setIdProdutoBusca] = useState<number | null>();
  const [produtoBusca, setProdutoBusca] = useState<string | undefined>("");
  const [idSistemaBusca, setIdSistemaBusca] = useState<number | null>(null);
  const [precoBusca, setPrecoBusca] = useState<string | undefined>();
  const [statusBusca, setStatusBusca] = useState<"A" | "I" | undefined>("A");

  const [idProdutoDebounce] = useDebounce(idProdutoBusca, delayDebounce);
  const [produtoDebounce] = useDebounce(produtoBusca, delayDebounce);
  const [precoDebounce] = useDebounce(precoBusca, delayDebounce);
  const [idSistemaDebounce] = useDebounce(idSistemaBusca, delayDebounce);
  const { setProdutoDadoContabil } = useProdutoDadoContabilStore();
  const [validouPermissao, setValidouPermissao] = useState(false);

  useValidaAcessos([Acessos.PRODUTO_SERVICO_AVULSO], () => {
    setValidouPermissao(true);
    queryClient
      .invalidateQueries({ queryKey: ["fetchAgenciasPaginado"] })
      .then(() => setInvalidando(false));
  });

  const { data, error, isError, isFetching } = useQuery({
    queryKey: [
      "fetchProdutoDadoContabilPaginado",
      currentPage,
      idProdutoDebounce,
      idSistemaDebounce,
      produtoDebounce,
      precoDebounce,
      statusBusca,
    ],
    keepPreviousData: true,
    enabled: !invalidando && validouPermissao,
    staleTime: TemposCachePadrao.CURTO,
    cacheTime: TemposCachePadrao.CURTO,
    queryFn: () => {
      const precoFomatado = Number((Number(precoDebounce?.replace(/\D+/g, "")) / 100).toFixed(2));
      return fetchProdutoDadoContabilPaginado(
        {
          page: currentPage - 1,
          pageSize: itemsPerPage,
          idProduto: idProdutoDebounce,
          idSistema: idSistemaDebounce,
          produto: produtoDebounce,
          preco: precoFomatado ? precoFomatado : undefined,
          status: statusBusca,
        },
        axios
      );
    },
  });

  function atualizaValorPesquisa<T>(setter: (arg: T) => void, valor: T): void {
    setter(valor);
    setCurrentPage(1);
  }

  return (
    <>
      <CabecalhoPages titulo="Cadastro de Produto / Serviço " />
      <Grid w="100%" templateColumns={{ base: "¨100%", md: "repeat(12, 1fr)" }} mt={4} pb={3}>
        <InputControl
          label="Código"
          id="idProduto"
          value={idProdutoBusca?.toString() ?? ""}
          type="number"
          onChange={(event) =>
            trataValorInputNumberVazio(
              (val) => atualizaValorPesquisa(setIdProdutoBusca, val),
              event
            )
          }
          numeroColunasMd={2}
          numeroColunasLg={2}
        />
        <DropBoxSistemas
          numeroColunasMd={4}
          numeroColunasLg={2}
          onChange={(idSistema) => atualizaValorPesquisa(setIdSistemaBusca, idSistema)}
          value={idSistemaBusca}
        />
        <InputControl
          numeroColunasMd={6}
          numeroColunasLg={4}
          label="Nome / Descrição"
          id="produto"
          value={produtoBusca}
          type="text"
          onChange={(e) => atualizaValorPesquisa(setProdutoBusca, e.target.value)}
        />
        <InputControl
          numeroColunasMd={4}
          numeroColunasLg={2}
          label="Preço"
          id="preco"
          value={precoBusca}
          onChange={(e) => atualizaValorPesquisa(setPrecoBusca, mascaraMoeda(e))}
        />
        <SelectControl
          numeroColunasMd={4}
          numeroColunasLg={2}
          label="Status"
          id="status"
          w="100%"
          options={[
            {
              value: "A",
              label: "Ativo",
            },
            {
              value: "I",
              label: "Inativo",
            },
          ]}
          onChange={(event) =>
            atualizaValorPesquisa(
              setStatusBusca,
              (event.length === 0 ? undefined : event) as "A" | "I" | undefined
            )
          }
        />
      </Grid>
      <ButtonControl />
      <Box overflowX="auto">
        <Box p={1} mt={2}>
          <TabelaCR5
            cabecalhos={[
              {
                titulo: "CÓDIGO PRODUTO",
                dadoBuilder: (item) => item.idProduto?.toString() || "-",
              },
              {
                titulo: "NOME",
                alinhamento: "left",
                dadoBuilder: (item) => item.produto?.toUpperCase() || "-",
              },
              {
                titulo: "COD. PRODUTO PROTHEUS",
                dadoBuilder: (item) => item.codProdutoProtheus || "-",
              },
              {
                titulo: "PREÇO",
                dadoBuilder: (item) => formatarMoedaBrasil(item.preco) || "-",
              },
              {
                titulo: "STATUS",
                dadoBuilder: (item) =>
                  item.status !== undefined ? (item.status === "A" ? "ATIVO" : "INATIVO") : "-",
              },
              {
                titulo: "DATA INATIVAÇÃO",
                dadoBuilder: (item) =>
                  item.dataInativacao === null ? "-" : formatarDataHoraBrasil(item.dataInativacao),
              },
            ]}
            data={data?.result}
            keybuilder={(item) => item.idProdutoDadoContabil}
            isFetching={isFetching}
            isError={isError}
            error={error}
            onEdit={(item) => {
              setProdutoDadoContabil(item);
              navigate("./editar");
            }}
            totalPages={data?.pageTotal ?? 0}
            itemsPerPage={itemsPerPage}
            currentPage={currentPage}
            setCurrentPage={setCurrentPage}
            totalItems={data?.total ?? 0}
          />
        </Box>
      </Box>
    </>
  );
}
