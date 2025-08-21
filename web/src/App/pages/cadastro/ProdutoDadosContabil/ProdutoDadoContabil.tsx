import { Box, Grid } from "@chakra-ui/react";
import { useQuery } from "@tanstack/react-query";
import { useState } from "react";
import { useNavigate } from "react-router-dom";
import { fetchVisaoProdutoContabilPaginadas } from "../../../../requests/requestsVisaoProdutoContabil";
import queryClient from "../../../../singletons/reactQueryClient";
import Acessos from "../../../../utils/Acessos";
import { trataValorInputNumberVazio } from "../../../../utils/componentUtil";
import { DelaysDebouncePadrao } from "../../../../utils/constantes";
import { ButtonControl } from "../../../components/ButoonControl";
import { CabecalhoPages } from "../../../components/CabecalhoPages";
import { DropBoxSistemas } from "../../../components/DropBoxSistemas";
import { InputControl } from "../../../components/InputControl";
import { SelectControl } from "../../../components/SelectControl";
import { TabelaCR5 } from "../../../components/Tabelas";
import useCR5Axios from "../../../hooks/useCR5Axios";
import useDebounce from "../../../hooks/useDebounce";
import { useFuncaoNotificacao } from "../../../hooks/useFuncaoNotificacao";
import { useValidaAcessos } from "../../../hooks/useValidaAcessos";
import { useVisaoProdutoContabilStore } from "./store/VisaoProdutoContabilStore";

export function ProdutoDadoContabil() {
  const { axios } = useCR5Axios();
  const itemsPerPage = 10;
  const delayDebounce = DelaysDebouncePadrao.PADRAO;
  const navigate = useNavigate();
  const [invalidando, setInvalidando] = useState(true);

  const [currentPage, setCurrentPage] = useState(1);
  const [idDadoContabilBusca, setIdDadoContabilBusca] = useState<number | null>(null);
  const [idProdutoDadoContabilBusca, setIdProdutoDadoContabilBusca] = useState<number | null>(null);
  const [idProdutoBusca, setIdProdutoBusca] = useState<number | null>(null);
  const [idSistemaBusca, setIdSistemaBusca] = useState<number | null>(null);
  const [produtoBusca, setProdutoBusca] = useState<string>("");
  const [codProdutoProtheusBusca, setCodProdutoProtheusBusca] = useState<string>("");
  const [contaContabilBusca, setContaContabilBusca] = useState("");
  const [contaContabilDescricaoBusca, setContaContabilDescricaoBusca] = useState("");
  const [itemContabilBusca, setItemContabilBusca] = useState("");
  const [itemContabilDescricaoBusca, setItemContabilDescricaoBusca] = useState("");
  const [naturezaBusca, setNaturezaBusca] = useState("");
  const [naturezaDescricaoBusca, setNaturezaDescricaoBusca] = useState("");
  const [dmedBusca, setDmedBusca] = useState<"S" | "N" | undefined>("N");
  const [statusBusca, setStatusBusca] = useState<"A" | "I" | undefined>("A");

  const [idDadoContabilDebouce] = useDebounce(idDadoContabilBusca, delayDebounce);
  const [idProdutoDadoContabilDebouce] = useDebounce(idProdutoDadoContabilBusca, delayDebounce);
  const [codContaContabilDebouce] = useDebounce(contaContabilBusca, delayDebounce);
  const [contaContabilDescricaoDebouce] = useDebounce(contaContabilDescricaoBusca, delayDebounce);
  const [itemContabilDebouce] = useDebounce(itemContabilBusca, delayDebounce);
  const [descrItemContabilDebouce] = useDebounce(itemContabilDescricaoBusca, delayDebounce);
  const [naturezaDebouce] = useDebounce(naturezaBusca, delayDebounce);
  const [naturezaDescricaoDebouce] = useDebounce(naturezaDescricaoBusca, delayDebounce);
  const [dmedDebouce] = useDebounce(dmedBusca, delayDebounce);
  const [idProdutoDebounce] = useDebounce(idProdutoBusca, delayDebounce);
  const [idSistemaDebounce] = useDebounce(idSistemaBusca, delayDebounce);
  const [produtoDebounce] = useDebounce(produtoBusca, delayDebounce);
  const [codProdutoProtheusDebounce] = useDebounce(codProdutoProtheusBusca, delayDebounce);
  const [statusDebounce] = useDebounce(statusBusca, delayDebounce);
  const { setVisaoProdutoContabil } = useVisaoProdutoContabilStore();

  const [validouPermissao, setValidouPermissao] = useState(false);

  useValidaAcessos([Acessos.PRODUTO_CONTA_CONTABIL], () => {
    setValidouPermissao(true);
    queryClient
      .invalidateQueries({ queryKey: ["fetchVisaoProdutoContabilPaginadas"] })
      .then(() => setInvalidando(false));
  });

  const { data, error, isError, isFetching } = useQuery({
    queryKey: [
      "fetchVisaoProdutoContabilPaginadas",
      currentPage,
      idProdutoDadoContabilDebouce,
      idDadoContabilDebouce,
      idProdutoDebounce,
      idSistemaDebounce,
      produtoDebounce,
      codProdutoProtheusDebounce,
      codContaContabilDebouce,
      contaContabilDescricaoDebouce,
      itemContabilDebouce,
      descrItemContabilDebouce,
      naturezaDebouce,
      naturezaDescricaoDebouce,
      dmedDebouce,
      statusDebounce,
    ],
    keepPreviousData: true,
    enabled: !invalidando && validouPermissao,
    queryFn: () => {
      return fetchVisaoProdutoContabilPaginadas(
        {
          page: currentPage - 1,
          pageSize: itemsPerPage,
          idProdutoDadoContabil: idProdutoDadoContabilDebouce ?? undefined,
          idDadoContabil: idDadoContabilDebouce ?? undefined,
          idProduto: idProdutoDebounce ?? undefined,
          idSistema: idSistemaDebounce ?? undefined,
          produto: produtoDebounce ?? undefined,
          codProdutoProtheus: codProdutoProtheusDebounce ?? undefined,
          contaContabil: codContaContabilDebouce ?? undefined,
          contaContabilDescricao: contaContabilDescricaoDebouce ?? undefined,
          itemContabil: itemContabilDebouce ?? undefined,
          itemContabilDescricao: descrItemContabilDebouce ?? undefined,
          natureza: naturezaDebouce ?? undefined,
          naturezaDescricao: naturezaDescricaoDebouce ?? undefined,
          dmed: dmedDebouce ?? undefined,
          status: statusDebounce ?? undefined,
        },
        axios
      );
    },
  });

  function atualizaValorPesquisa<T>(setter: (arg: T) => void, valor: T): void {
    setter(valor);
    setCurrentPage(1);
  }

  const notificacao = useFuncaoNotificacao();
  const tituloTela = "Produto / Dados Contábil";
  const subtitulo = "Gerencie os vínculos dos produtos com as contas contábeis";

  return (
    <>
      <CabecalhoPages titulo={tituloTela} subtitulo={subtitulo} />
      <Grid
        w="100%"
        templateColumns={{ base: "¨100%", md: "repeat(12, 1fr)" }}
        mt={4}
        pb={{ base: 4, md: 1 }}
      >
        <InputControl
          numeroColunasMd={4}
          numeroColunasLg={2}
          label="Código"
          id="idProdutoDadoContabil"
          value={idProdutoDadoContabilBusca?.toString() || ""}
          onChange={(event) =>
            trataValorInputNumberVazio(
              (val) => atualizaValorPesquisa(setIdProdutoDadoContabilBusca, val),
              event
            )
          }
        />
        <InputControl
          numeroColunasMd={4}
          numeroColunasLg={2}
          label="Código Dado/Contábil"
          id="idDadoContabil"
          value={idDadoContabilBusca?.toString() || ""}
          onChange={(event) =>
            trataValorInputNumberVazio(
              (val) => atualizaValorPesquisa(setIdDadoContabilBusca, val),
              event
            )
          }
        />
        <InputControl
          numeroColunasMd={4}
          numeroColunasLg={2}
          label="Código Produto"
          id="idProduto"
          value={idProdutoBusca?.toString() || ""}
          onChange={(event) =>
            trataValorInputNumberVazio(
              (val) => atualizaValorPesquisa(setIdProdutoBusca, val),
              event
            )
          }
        />
        <InputControl
          numeroColunasMd={6}
          numeroColunasLg={4}
          label="Nome Produto"
          id="nome"
          value={produtoBusca}
          onChange={(e) => atualizaValorPesquisa(setProdutoBusca, e.target.value)}
        />
        <DropBoxSistemas
          numeroColunasMd={6}
          numeroColunasLg={2}
          onChange={(idSistema) => atualizaValorPesquisa(setIdSistemaBusca, idSistema)}
          value={idSistemaBusca}
        />
        <SelectControl
          label="DMED"
          id="dmed"
          options={[
            { value: "S", label: "Sim" },
            { value: "N", label: "Não" },
          ]}
          onChange={(event) =>
            atualizaValorPesquisa(
              setDmedBusca,
              (event.length === 0 ? undefined : event) as "S" | "N" | undefined
            )
          }
          numeroColunasMd={4}
          numeroColunasLg={2}
        />

        <InputControl
          numeroColunasMd={4}
          numeroColunasLg={2}
          label="Código Protheus"
          id="codProdutoProtheus"
          value={codProdutoProtheusBusca}
          onChange={(e) => atualizaValorPesquisa(setCodProdutoProtheusBusca, e.target.value)}
        />
        <InputControl
          numeroColunasMd={4}
          numeroColunasLg={2}
          label="Conta Contábil"
          id="contaContabil"
          value={contaContabilBusca}
          onChange={(e) => atualizaValorPesquisa(setContaContabilBusca, e.target.value)}
        />
        <InputControl
          numeroColunasMd={6}
          numeroColunasLg={4}
          label="Descrição Conta Contábi"
          id="contaContabilDescricao"
          value={contaContabilDescricaoBusca}
          onChange={(e) =>
            atualizaValorPesquisa(
              setContaContabilDescricaoBusca,
              e.target.value.toLocaleUpperCase()
            )
          }
        />
        <InputControl
          label="Item Contábil"
          id="itemContabil"
          value={itemContabilBusca}
          onChange={(e) =>
            atualizaValorPesquisa(setItemContabilBusca, e.target.value.toLocaleUpperCase())
          }
          numeroColunasMd={6}
          numeroColunasLg={2}
        />
        <InputControl
          numeroColunasMd={6}
          numeroColunasLg={4}
          label="Descrição Item Contábil"
          id="itemContabilDescricao"
          value={itemContabilDescricaoBusca}
          onChange={(e) => atualizaValorPesquisa(setItemContabilDescricaoBusca, e.target.value)}
        />
        <InputControl
          numeroColunasMd={6}
          numeroColunasLg={2}
          label="Nº Cod. Natureza"
          id="codigoNatureza"
          value={naturezaBusca}
          onChange={(e) =>
            atualizaValorPesquisa(setNaturezaBusca, e.target.value.toLocaleUpperCase())
          }
        />
        <InputControl
          numeroColunasMd={6}
          numeroColunasLg={4}
          label="Descrição Cod. Natureza"
          id="naturezaDescricao"
          value={naturezaDescricaoBusca}
          onChange={(e) =>
            atualizaValorPesquisa(setNaturezaDescricaoBusca, e.target.value.toLocaleUpperCase())
          }
        />
        <SelectControl
          label="Status"
          id="status"
          value={statusBusca}
          options={[
            { value: "A", label: "Ativo" },
            { value: "I", label: "Inativo" },
          ]}
          onChange={(event) =>
            atualizaValorPesquisa(
              setStatusBusca,
              (event.length === 0 ? undefined : event) as "A" | "I" | undefined
            )
          }
          numeroColunasMd={6}
          numeroColunasLg={2}
        />
      </Grid>
      <ButtonControl />
      <Box overflowX="auto">
        <Box p={1} mt={2}>
          <TabelaCR5
            cabecalhos={[
              { titulo: "CÓDIGO PRODUTO", dadoBuilder: (item) => item.idProduto.toString() },
              {
                titulo: "PRODUTO",
                dadoBuilder: (item) => item.produto.toUpperCase() || "-",
                alinhamento: "left",
              },
              {
                titulo: "SISTEMA",
                dadoBuilder: (item) =>
                  `${item.idSistema?.toString()} - ${item.sistema?.toString()}`,
              },
              {
                titulo: "CÓDIGO DADO CONTÁBIL",
                dadoBuilder: (item) => item.idDadoContabil?.toString() || "-",
              },
              {
                titulo: "CONTA CONTÁBIL",
                dadoBuilder: (item) => item.contaContabil || "-",
              },
              {
                titulo: "ITEM CONTÁBIL",
                dadoBuilder: (item) => item.itemContabil || "-",
              },
              {
                titulo: "NATUREZA",
                dadoBuilder: (item) => item.natureza || "-",
              },
              {
                titulo: "DMED",
                dadoBuilder: (item) => (item.dmed ? (item.dmed === "N" ? "NÃO" : "SIM") : "-"),
              },
            ]}
            detalhesBuilder={{
              type: "Expandir",
              buildDetalhes: (item) => [
                [{ nome: "Código Produto Protheus", valor: item.codProdutoProtheus || "-" }],
                [
                  {
                    nome: "Desc. Item Contábil",
                    valor: item.itemContabilDescricao?.toUpperCase(),
                  },
                ],
                [
                  {
                    nome: "Desc. Código Contábil",
                    valor: item.contaContabilDescricao?.toUpperCase(),
                  },
                ],
                [
                  { nome: "Desc. Código Natureza", valor: item.naturezaDescricao?.toUpperCase() },
                  {
                    nome: "Status Produto Dado Contábil",
                    valor: item.status === "A" ? "Ativo" : "Inativo",
                  },
                ],
              ],
            }}
            onEdit={(item) => {
              if (item.idDadoContabil !== null) {
                setVisaoProdutoContabil(item);
                navigate("./editar");
              } else {
                notificacao({
                  tipo: "warning",
                  titulo: "Aviso",
                  message: "Produto não contém nenhum vínculo Contábil",
                });
              }
            }}
            data={data?.result || []}
            keybuilder={(item) => item.idProduto}
            isFetching={isFetching}
            isError={isError}
            error={error}
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
