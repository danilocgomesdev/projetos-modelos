import { useQuery } from "@tanstack/react-query";
import { useState } from "react";
import { ContaCorrenteDTO } from "../../../../../models/DTOs/AgenciaBancoConta/ContaCorrenteDTO.ts";
import { fetchContasCorrentePaginado } from "../../../../../requests/requestsContasCorrente.ts";
import { TemposCachePadrao } from "../../../../../utils/constantes.ts";
import { ModalBase } from "../../../../components/ModalBase";
import { TabelaCR5 } from "../../../../components/Tabelas/TabelaCR5.tsx";
import useCR5Axios from "../../../../hooks/useCR5Axios.ts";
import { useUnidadeStore } from "../../../../stores/UnidadeStore.tsx";

interface ModalPesquisaContaCorrenteProps {
  isOpen: boolean;
  onClose: () => void;
  setContaCorrenteFiltrada: (contaCorrente: ContaCorrenteDTO) => void;
}

export function ModalPesquisaContaCorrente({
  isOpen,
  onClose,
  setContaCorrenteFiltrada,
}: ModalPesquisaContaCorrenteProps) {
  const { axios } = useCR5Axios();
  const { unidadeAtual } = useUnidadeStore();
  const [currentPage, setCurrentPage] = useState(1);

  const itemsPerPage = 10;

  const { data, isFetching, isError, error } = useQuery({
    queryKey: ["fetchContasCorrentePaginado", unidadeAtual],
    keepPreviousData: true,
    staleTime: TemposCachePadrao.MODERADO,
    cacheTime: TemposCachePadrao.MODERADO,
    queryFn: () => {
      return fetchContasCorrentePaginado(
        {
          page: currentPage - 1,
          pageSize: itemsPerPage,
          idUnidade: unidadeAtual.id,
        },
        axios
      );
    },
  });

  function atualizarContaCorrente(contaCorrente: ContaCorrenteDTO) {
    setContaCorrenteFiltrada(contaCorrente);
    onClose();
  }

  return (
    <ModalBase
      isOpen={isOpen}
      onClose={onClose}
      titulo="Pesquisar Conta Corrente"
      subtitulo={`Contas Corrente da unidade ${unidadeAtual.nome}-${unidadeAtual.codigo}`}
      size="3xl"
      centralizado={true}
    >
      <TabelaCR5
        cabecalhos={[
          { titulo: "CÓDIGO", dadoBuilder: (item) => item.id.toString() },
          { titulo: "AGÊNCIA", dadoBuilder: (item) => item.agencia.nome.toUpperCase() },
          {
            titulo: "NÚMERO - DIGITO",
            dadoBuilder: (item) => `${item.numeroConta}-${item.digitoConta}`,
          },
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
        onRowClick={(item) => atualizarContaCorrente(item)}
      />
    </ModalBase>
  );
}
