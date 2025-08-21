import { Grid } from "@chakra-ui/react";
import { useQuery } from "@tanstack/react-query";
import { useState } from "react";
import { DadoContabilDTO } from "../../../../../models/DTOs/Contabil/DadoContabilDTO";
import { fetchDadoContabilPaginado } from "../../../../../requests/requestsDadoContabil";
import { getEntidadeNome, trataValorInputNumberVazio } from "../../../../../utils/componentUtil";
import { DelaysDebouncePadrao, TemposCachePadrao } from "../../../../../utils/constantes";
import { DropBoxEntidades } from "../../../../components/DropBoxEntidades";
import { InputControl } from "../../../../components/InputControl";
import { ModalBase } from "../../../../components/ModalBase";
import { SelectControl } from "../../../../components/SelectControl";
import { TabelaCR5 } from "../../../../components/Tabelas";
import useCR5Axios from "../../../../hooks/useCR5Axios";
import useDebounce from "../../../../hooks/useDebounce";

interface ModalPesquisaDadoContabilProps {
  isOpen: boolean;
  onClose: () => void;
  setDadoContabilFiltrado: (DadoContabil: DadoContabilDTO) => void;
}

export function ModalPesquisaDadoContabil({
  isOpen,
  onClose,
  setDadoContabilFiltrado,
}: ModalPesquisaDadoContabilProps) {
  const { axios } = useCR5Axios();
  const itemsPerPage = 7;
  const delayDebounce = DelaysDebouncePadrao.PADRAO;

  const [currentPage, setCurrentPage] = useState(1);
  const [dadoContabilBusca, setIdDadoContabilBusca] = useState<number | null>(null);
  const [idSistemaBusca, setIdSistemaBusca] = useState<number | null>(null);
  const [idEntidadeBusca, setIdEntidadeBusca] = useState<number | null>();
  const [itemContabilBusca, setItemContabilBusca] = useState("");
  const [itemContabilDescricaoBusca, setItemContabilDescricaoBusca] = useState("");
  const [contaContabilBusca, setContaContabilBusca] = useState("");
  const [descrContaBusca, setDescrContaBusca] = useState("");
  const [codNaturezaBusca, setCodNaturezaBusca] = useState("");
  const [descNaturezaBusca, setDescNaturezaBusca] = useState("");
  const [statusBusca, setStatusBusca] = useState<"ATIVO" | "INATIVO" | undefined>("ATIVO");
  const [dadoContabilDebounce] = useDebounce(
    dadoContabilBusca === null ? undefined : dadoContabilBusca,
    delayDebounce
  );
  const [idSistemaDebounce] = useDebounce(idSistemaBusca, delayDebounce);
  const [idEntidadeDebounce] = useDebounce(idEntidadeBusca, delayDebounce);
  const [itemContabilDebounce] = useDebounce(itemContabilBusca, delayDebounce);
  const [itemContabilDescricaoDebounce] = useDebounce(itemContabilDescricaoBusca, delayDebounce);
  const [contaContabilDebounce] = useDebounce(contaContabilBusca, delayDebounce);
  const [descrContaDebounce] = useDebounce(descrContaBusca, delayDebounce);
  const [codNaturezaDebounce] = useDebounce(codNaturezaBusca, delayDebounce);
  const [descNaturezaDebounce] = useDebounce(descNaturezaBusca, delayDebounce);
  const [statusDebounce] = useDebounce(statusBusca, delayDebounce);

  const { data, isFetching, isError, error } = useQuery({
    queryKey: [
      "fetchDadoContabilModal",
      currentPage,
      dadoContabilDebounce,
      idSistemaDebounce,
      idEntidadeDebounce,
      itemContabilDebounce,
      itemContabilDescricaoDebounce,
      contaContabilDebounce,
      descrContaDebounce,
      codNaturezaDebounce,
      descNaturezaDebounce,
      statusDebounce,
    ],
    keepPreviousData: true,
    staleTime: TemposCachePadrao.MODERADO,
    cacheTime: TemposCachePadrao.MODERADO,
    queryFn: () => {
      return fetchDadoContabilPaginado(
        {
          page: currentPage - 1,
          pageSize: itemsPerPage,
          idDadoContabil: dadoContabilDebounce,
          idEntidade: idEntidadeDebounce,
          itemContabil: itemContabilDebounce,
          itemContabilDescricao: itemContabilDescricaoDebounce,
          contaContabil: contaContabilDebounce,
          contaContabilDescricao: descrContaDebounce,
          natureza: codNaturezaDebounce,
          naturezaDescricao: descNaturezaDebounce,
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

  const atualizarDadoContabil = (dadoContabil: DadoContabilDTO) => {
    setDadoContabilFiltrado(dadoContabil);
    setIdDadoContabilBusca(null);
    setIdSistemaBusca(null);
    setItemContabilBusca("");
    setItemContabilDescricaoBusca("");
    setContaContabilBusca("");
    setDescrContaBusca("");
    setCodNaturezaBusca("");
    setDescNaturezaBusca("");
    setIdEntidadeBusca(null);
    onClose();
  };

  return (
    <>
      <ModalBase
        isOpen={isOpen}
        onClose={onClose}
        titulo="Pequisar Dado Contábil"
        size="6xl"
        centralizado={true}
      >
        <Grid w="100%" templateColumns={{ base: "¨100%", md: "repeat(10, 1fr)" }} mt={4} p={1}>
          <InputControl
            label="Código"
            id="dadoContabil"
            type="number"
            value={dadoContabilBusca?.toString() ?? ""}
            onChange={(event) =>
              trataValorInputNumberVazio(
                (val) => atualizaValorPesquisa(setIdDadoContabilBusca, val),
                event
              )
            }
          />
          <DropBoxEntidades
            mostrarLabel={true}
            onChange={(idEntidade) => atualizaValorPesquisa(setIdEntidadeBusca, idEntidade)}
            numeroColunasMd={2}
          />
          <InputControl
            numeroColunasMd={2}
            label="Conta Contábil"
            id="contaContabilBusca"
            value={contaContabilBusca}
            onChange={(e) =>
              atualizaValorPesquisa(setContaContabilBusca, e.target.value.toLocaleUpperCase())
            }
          />
          <InputControl
            numeroColunasMd={3}
            label="Descrição da Conta Contábil"
            id="descrContaBusca"
            value={descrContaBusca}
            onChange={(e) => atualizaValorPesquisa(setDescrContaBusca, e.target.value)}
          />
          <InputControl
            numeroColunasMd={2}
            label="Item Contábil"
            id="itemContabil"
            value={itemContabilBusca}
            onChange={(e) => atualizaValorPesquisa(setItemContabilBusca, e.target.value)}
          />
          <InputControl
            numeroColunasMd={3}
            label="Descrição Item Contábil"
            id="itemContabilDescricaoBusca"
            value={itemContabilDescricaoBusca}
            onChange={(e) =>
              atualizaValorPesquisa(
                setItemContabilDescricaoBusca,
                e.target.value.toLocaleUpperCase()
              )
            }
          />
          <InputControl
            numeroColunasMd={2}
            label="Código Natureza"
            id="codNaturezaBusca"
            value={codNaturezaBusca}
            onChange={(e) =>
              atualizaValorPesquisa(setCodNaturezaBusca, e.target.value.toLocaleUpperCase())
            }
          />
          <InputControl
            numeroColunasMd={3}
            label="Descrição Código Natureza"
            id="descNaturezaBusca"
            value={descNaturezaBusca}
            onChange={(e) =>
              atualizaValorPesquisa(setDescNaturezaBusca, e.target.value.toLocaleUpperCase())
            }
          />
          <SelectControl
            numeroColunasMd={2}
            label="Status"
            id="status"
            w="100%"
            options={[
              {
                value: "ATIVO",
                label: "Ativo",
              },
              {
                value: "INATIVO",
                label: "Inativo",
              },
            ]}
            onChange={(event) =>
              atualizaValorPesquisa(
                setStatusBusca,
                (event.length === 0 ? undefined : event) as "ATIVO" | "INATIVO" | undefined
              )
            }
          />
        </Grid>
        <TabelaCR5
          cabecalhos={[
            { titulo: "CÓDIGO", dadoBuilder: (item) => item.idDadoContabil.toString() },
            {
              titulo: "ENTIDADE",
              dadoBuilder: (item) => getEntidadeNome(item.idEntidade).toUpperCase(),
            },
            { titulo: "CONTA CONTÁBIL", dadoBuilder: (item) => item.contaContabil },
            {
              titulo: "DESC. CONTA CONTÁBIL",
              dadoBuilder: (item) => item.contaContabilDescricao,
              alinhamento: "left",
            },
            { titulo: "ITEM CONTÁBIL", dadoBuilder: (item) => item.itemContabil },
            {
              titulo: "DESC. ITEM CONTÁBIL",
              dadoBuilder: (item) => item.itemContabilDescricao,
              alinhamento: "left",
            },
            { titulo: "NATUREZA", dadoBuilder: (item) => item.natureza },
            {
              titulo: "DESC. NATUREZA",
              dadoBuilder: (item) => item.naturezaDescricao,
              alinhamento: "left",
            },
          ]}
          data={data?.result}
          keybuilder={(item) => item.idDadoContabil}
          isFetching={isFetching}
          isError={isError}
          error={error}
          totalPages={data?.pageTotal ?? 0}
          itemsPerPage={itemsPerPage}
          currentPage={currentPage}
          setCurrentPage={setCurrentPage}
          totalItems={data?.total ?? 0}
          onRowClick={(item) => atualizarDadoContabil(item)}
        />
      </ModalBase>
    </>
  );
}
