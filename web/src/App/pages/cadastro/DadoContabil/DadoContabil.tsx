import { Grid, useDisclosure } from "@chakra-ui/react";
import { useMutation, useQuery } from "@tanstack/react-query";
import { useState } from "react";
import { useNavigate } from "react-router-dom";
import {
  fetchDadoContabilPaginado,
  fetchDeleteDadoContabil,
} from "../../../../requests/requestsDadoContabil.ts";
import queryClient from "../../../../singletons/reactQueryClient.ts";
import Acessos from "../../../../utils/Acessos.ts";
import { trataValorInputNumberVazio } from "../../../../utils/componentUtil.ts";
import { DelaysDebouncePadrao, TemposCachePadrao } from "../../../../utils/constantes.ts";
import { getMensagemDeErroOuProcureSuporte } from "../../../../utils/errors.ts";
import { ButtonControl } from "../../../components/ButoonControl/ButtonControl.tsx";
import { CabecalhoPages } from "../../../components/CabecalhoPages/index.tsx";
import { DropBoxEntidades } from "../../../components/DropBoxEntidades/DropBoxEntidades.tsx";
import { InputControl } from "../../../components/InputControl/index.tsx";
import { ModalConfirma } from "../../../components/ModalConfirma/index.tsx";
import { SelectControl } from "../../../components/SelectControl/SelectControl.tsx";
import { TabelaCR5 } from "../../../components/Tabelas/index.ts";
import useCR5Axios from "../../../hooks/useCR5Axios.ts";
import useDebounce from "../../../hooks/useDebounce.ts";
import { useFuncaoNotificacao } from "../../../hooks/useFuncaoNotificacao.tsx";
import { useValidaAcessos } from "../../../hooks/useValidaAcessos.ts";
import { useDadoContabilStore } from "./store/DadoContabilStore.tsx";

export function DadoContabil() {
  const { axios } = useCR5Axios();
  const itemsPerPage = 10;
  const delayDebouce = DelaysDebouncePadrao.PADRAO;
  const navigate = useNavigate();
  const { onOpen, onClose, isOpen } = useDisclosure();

  const [currentPage, setCurrentPage] = useState(1);
  const [idDadoContabilBusca, setIdDadoContabilBusca] = useState<number | null>(null);
  const [idEntidadeBusca, setIdEntidadeBusca] = useState<number | null>();
  const [codContaContabilBusca, setCodContaContabilBusca] = useState("");
  const [descContaContabilBusca, setDescContaContabilBusca] = useState("");
  const [itemContabilBusca, setItemContabilBusca] = useState("");
  const [itemContabilDescricaoBusca, setItemContabilDescricaoBusca] = useState("");
  const [codigoNaturezaBusca, setCodigoNaturezaBusca] = useState("");
  const [descNaturezaBusca, setDescNaturezaBusca] = useState("");
  const [statusBusca, setStatusBusca] = useState<"ATIVO" | "INATIVO" | undefined>("ATIVO");

  const [idDadoContabilDebouce] = useDebounce(idDadoContabilBusca, delayDebouce);
  const [idEntidadeDebouce] = useDebounce(idEntidadeBusca, delayDebouce);
  const [codContaContabilDebouce] = useDebounce(codContaContabilBusca, delayDebouce);
  const [descContaContabilDebouce] = useDebounce(descContaContabilBusca, delayDebouce);
  const [itemContabilDebouce] = useDebounce(itemContabilBusca, delayDebouce);
  const [descrItemContabilDebouce] = useDebounce(itemContabilDescricaoBusca, delayDebouce);
  const [codigoNaturezaDebouce] = useDebounce(codigoNaturezaBusca, delayDebouce);
  const [descNaturezaDebouce] = useDebounce(descNaturezaBusca, delayDebouce);
  const [statusDebounce] = useDebounce(statusBusca, delayDebouce);

  const { dadoContabil, adicionarDadoContabil } = useDadoContabilStore();

  const notificacao = useFuncaoNotificacao();
  const [validouPermissao, setValidouPermissao] = useState(false);

  useValidaAcessos([Acessos.PRODUTO_CONTA_CONTABIL], () => {
    setValidouPermissao(true);
  });

  const { data, refetch, isFetching, isError, error } = useQuery({
    queryKey: [
      "fetchDadoContabilModal",
      currentPage,
      idDadoContabilDebouce,
      idEntidadeDebouce,
      codContaContabilDebouce,
      descContaContabilDebouce,
      itemContabilDebouce,
      descrItemContabilDebouce,
      codigoNaturezaDebouce,
      descNaturezaDebouce,
      statusDebounce,
    ],
    keepPreviousData: true,
    enabled: validouPermissao,
    staleTime: TemposCachePadrao.CURTO,
    cacheTime: TemposCachePadrao.CURTO,
    queryFn: () => {
      return fetchDadoContabilPaginado(
        {
          page: currentPage - 1,
          pageSize: itemsPerPage,
          idDadoContabil: idDadoContabilDebouce ?? undefined,
          idEntidade: idEntidadeDebouce ?? undefined,
          contaContabil: codContaContabilDebouce,
          contaContabilDescricao: descContaContabilDebouce,
          itemContabil: itemContabilDebouce,
          itemContabilDescricao: descrItemContabilDebouce,
          natureza: codigoNaturezaDebouce,
          naturezaDescricao: descNaturezaDebouce,
          status: statusDebounce,
        },
        axios
      );
    },
  });

  function atualizaValorPesquisa<T>(setter: (arg: T) => void, valor: T): void {
    setter(valor);
    setCurrentPage(1);
  }

  const { mutate } = useMutation(
    (idDadoContabil: number) => {
      return fetchDeleteDadoContabil(idDadoContabil, axios);
    },
    {
      onSuccess: () => {
        notificacao({ message: "Excluído com sucesso!", tipo: "success", titulo: "SUCESSO!" });
        queryClient
          .invalidateQueries({ queryKey: ["fetchDeleteDadoContabil"] })
          .then(() => refetch());
      },
      onError: (e) => {
        notificacao({
          message: getMensagemDeErroOuProcureSuporte(e),
          tipo: "error",
          titulo: "ERRO!",
        });
      },
    }
  );

  function handleDeleteDadoContabil() {
    if (dadoContabil) {
      mutate(dadoContabil.idDadoContabil);
    }
  }

  console.log(data);

  return (
    <>
      <CabecalhoPages titulo="Dado Contábil" />
      <Grid
        w="100%"
        templateColumns={{ base: "¨100%", md: "repeat(12, 1fr)" }}
        mt={4}
        pb={{ base: 4, md: 1 }}
      >
        <InputControl
          label="Código"
          id="idDadoContabil"
          type="number"
          value={idDadoContabilBusca?.toString() ?? ""}
          onChange={(event) =>
            trataValorInputNumberVazio(
              (val) => atualizaValorPesquisa(setIdDadoContabilBusca, val),
              event
            )
          }
          numeroColunasMd={2}
          numeroColunasLg={2}
        />
        <DropBoxEntidades
          mostrarLabel={true}
          onChange={(idEntidade) => atualizaValorPesquisa(setIdEntidadeBusca, idEntidade)}
          numeroColunasMd={6}
          numeroColunasLg={2}
        />
        <SelectControl
          label="Status"
          id="status"
          value={statusBusca}
          options={[
            { value: "ATIVO", label: "Ativo" },
            { value: "INATIVO", label: "Inativo" },
          ]}
          onChange={(event) =>
            atualizaValorPesquisa(
              setStatusBusca,
              (event.length === 0 ? undefined : event) as "ATIVO" | "INATIVO" | undefined
            )
          }
          numeroColunasMd={4}
          numeroColunasLg={2}
        />
        <InputControl
          label="Conta Contábil"
          id="codContaContabil"
          value={codContaContabilBusca}
          onChange={(e) => atualizaValorPesquisa(setCodContaContabilBusca, e.target.value)}
          numeroColunasMd={4}
          numeroColunasLg={2}
        />
        <InputControl
          numeroColunasMd={8}
          numeroColunasLg={4}
          label="Descrição Conta Contábi"
          id="descContaContabil"
          value={descContaContabilBusca}
          onChange={(e) =>
            atualizaValorPesquisa(setDescContaContabilBusca, e.target.value.toLocaleUpperCase())
          }
        />
        <InputControl
          label="Item Contábil"
          id="itemContabil"
          value={itemContabilBusca}
          onChange={(e) =>
            atualizaValorPesquisa(setItemContabilBusca, e.target.value.toLocaleUpperCase())
          }
          numeroColunasMd={4}
          numeroColunasLg={2}
        />
        <InputControl
          numeroColunasMd={8}
          numeroColunasLg={4}
          label="Descrição Item Contábil"
          id="itemContabilDescricao"
          value={itemContabilDescricaoBusca}
          onChange={(e) => atualizaValorPesquisa(setItemContabilDescricaoBusca, e.target.value)}
        />
        <InputControl
          numeroColunasMd={4}
          numeroColunasLg={2}
          label="Nº Cod. Natureza"
          id="codNatureza"
          value={codigoNaturezaDebouce}
          onChange={(e) =>
            atualizaValorPesquisa(setCodigoNaturezaBusca, e.target.value.toLocaleUpperCase())
          }
        />
        <InputControl
          numeroColunasMd={8}
          numeroColunasLg={4}
          label="Descrição Cod. Natureza"
          id="descNatureza"
          value={descNaturezaBusca}
          onChange={(e) =>
            atualizaValorPesquisa(setDescNaturezaBusca, e.target.value.toLocaleUpperCase())
          }
        />
      </Grid>
      <ButtonControl />
      <TabelaCR5
        cabecalhos={[
          {
            titulo: "CÓDIGO",
            dadoBuilder: (item) => item.idDadoContabil?.toString(),
          },
          {
            titulo: "CONTA CONTÁBIL",
            dadoBuilder: (item) => item.contaContabil?.toString() || "-",
          },
          {
            titulo: "DESC. CONTA CONTÁBIL",
            dadoBuilder: (item) => item.contaContabilDescricao?.toUpperCase() || "-",
          },
          {
            titulo: "ITEM CONTÁBIL",
            dadoBuilder: (item) => item.itemContabil?.toString() || "-",
          },
          {
            titulo: "DESC. ITEM CONTÁBIL",
            dadoBuilder: (item) => item.itemContabilDescricao?.toUpperCase() || "-",
          },
        ]}
        data={data?.result}
        keybuilder={(item) => item.idDadoContabil}
        isFetching={isFetching}
        isError={isError}
        error={error}
        onDelete={(item) => {
          adicionarDadoContabil(item);
          onOpen();
        }}
        onEdit={(item) => {
          adicionarDadoContabil(item);
          navigate("./editar");
        }}
        detalhesBuilder={{
          type: "Expandir",
          buildDetalhes: (item) => [
            [{ nome: "Código Natureza", valor: item.natureza.toString() }],
            [{ nome: "Desc. Código Natureza", valor: item.naturezaDescricao?.toUpperCase() }],
            [{ nome: "Ativo", valor: item.dataInativacao ? "Não" : "Sim" }],
          ],
        }}
        totalPages={data?.pageTotal ?? 0}
        itemsPerPage={itemsPerPage}
        currentPage={currentPage}
        setCurrentPage={setCurrentPage}
        totalItems={data?.total ?? 0}
      />
      <ModalConfirma
        titulo="Atenção"
        texto={`Deseja realmente excluir:  ${dadoContabil?.idDadoContabil}?`}
        onClose={onClose}
        isOpen={isOpen}
        onConfirm={handleDeleteDadoContabil}
      />
    </>
  );
}
