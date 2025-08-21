import { Box, Grid, useDisclosure } from "@chakra-ui/react";
import { useMutation, useQuery } from "@tanstack/react-query";
import { useState } from "react";
import {
  fetchAlterarConfig,
  fetchConfigCancelamentoAutomaticoPaginado,
} from "../../../../requests/requestsConfigCancelamentoAutomatico.ts";
import queryClient from "../../../../singletons/reactQueryClient.ts";
import Acessos from "../../../../utils/Acessos.ts";
import { DelaysDebouncePadrao, TemposCachePadrao } from "../../../../utils/constantes.ts";
import { getMensagemDeErroOuProcureSuporte } from "../../../../utils/errors.ts";
import { formatarDataBrasil_2 } from "../../../../utils/mascaras.ts";
import { CabecalhoPages } from "../../../components/CabecalhoPages/index.tsx";
import { DropBoxSistemas } from "../../../components/DropBoxSistemas/index.ts";
import { ModalConfirma } from "../../../components/ModalConfirma/index.tsx";
import { TabelaCR5 } from "../../../components/Tabelas/index.ts";
import useCR5Axios from "../../../hooks/useCR5Axios.ts";
import useDebounce from "../../../hooks/useDebounce.ts";
import { useFuncaoNotificacao } from "../../../hooks/useFuncaoNotificacao.tsx";
import { useValidaAcessos } from "../../../hooks/useValidaAcessos.ts";
import { useCancelamentoAutomaticoStore } from "./store/CancelamentoAutomaticoStore.tsx";

export function CancelamentoAutomatico() {
  const { axios } = useCR5Axios();
  const itemsPerPage = 7;
  const delayDebounce = DelaysDebouncePadrao.PADRAO;
  const { isOpen, onOpen, onClose } = useDisclosure();

  const [invalidando, setInvalidando] = useState(true);
  const [currentPage, setCurrentPage] = useState(1);

  const [idSistemaBusca, setIdSistemaBusca] = useState<number | null>(null);

  const [idSistemaDebounce] = useDebounce(idSistemaBusca, delayDebounce);
  const notificacao = useFuncaoNotificacao();
  const [validouPermissao, setValidouPermissao] = useState(false);

  const { cancelamentoAutomatico, setCancelamentoAutomatico } = useCancelamentoAutomaticoStore();

  useValidaAcessos([Acessos.CANCELAR_CONTRATO_NO_7o_DIA_APOS_VENCIMENTO], () => {
    setValidouPermissao(true);
    queryClient
      .invalidateQueries({ queryKey: ["fetchConfigCancelamentoAutomaticoPaginado"] })
      .then(() => setInvalidando(false));
  });

  const { data, refetch, error, isError, isFetching } = useQuery({
    queryKey: ["fetchConfigCancelamentoAutomaticoPaginado", currentPage, idSistemaDebounce],
    keepPreviousData: true,
    enabled: !invalidando && validouPermissao,
    staleTime: TemposCachePadrao.CURTO,
    cacheTime: TemposCachePadrao.CURTO,
    queryFn: () => {
      return fetchConfigCancelamentoAutomaticoPaginado(
        {
          page: currentPage - 1,
          pageSize: itemsPerPage,
          idSistema: idSistemaDebounce,
        },
        axios
      );
    },
  });

  const { mutate, isLoading: isLoadingAlteraConfig } = useMutation(
    (id: number) => {
      const params = {
        id: id,
        cancelamentoAutomatico: cancelamentoAutomatico?.cancelamentoAutomatico,
      };

      return fetchAlterarConfig(params, axios);
    },
    {
      onSuccess: () => {
        notificacao({ message: "Alterado com sucesso!", tipo: "success", titulo: "SUCESSO!" });
        queryClient
          .invalidateQueries({ queryKey: ["fetchConfigCancelamentoAutomaticoPaginado"] })
          .then(() => refetch());
        onClose();
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

  function handleAltera() {
    if (cancelamentoAutomatico) {
      mutate(cancelamentoAutomatico.id);
      close();
    }
  }

  return (
    <>
      <CabecalhoPages titulo="Configuração Cancelamento Automático" />
      <Box gap={2} m={4} fontStyle={"italic"} fontSize="sm">
        <Box>
          - Se habilitado, os contratos do sistema informado serão cancelados após 7 dias do não
          pagamento da primeira parcela.
        </Box>
        <Box>
          - Na tabela abaixo, configure quais sistemas deverão realizar o cancelamento automático.
        </Box>
        <Box>- Novos sistemas por padrão não irão realizar o cancelamento automático.</Box>
      </Box>

      <Grid w="100%" templateColumns={{ base: "¨100%", md: "repeat(12, 1fr)" }} mt={4} p={1}>
        <DropBoxSistemas
          onChange={setIdSistemaBusca}
          numeroColunasMd={6}
          numeroColunasLg={4}
          value={idSistemaBusca}
        />
      </Grid>

      <TabelaCR5
        cabecalhos={[
          { titulo: "ID", dadoBuilder: (item) => item.id.toString() },
          {
            titulo: "CANCELA AUTOMÁTICO",
            dadoBuilder: (item) => (item.cancelamentoAutomatico ? "SIM" : "NÃO"),
          },
          { titulo: "ID SISTEMA", dadoBuilder: (item) => item.idSistema.toString() },
          { titulo: "SISTEMA", dadoBuilder: (item) => item.sistema.toString() },
          {
            titulo: "DATA ALTERAÇÃO",
            dadoBuilder: (item) => formatarDataBrasil_2(item.dataAlteracao),
          },
          { titulo: "OPERADOR", dadoBuilder: (item) => item.nomeOperador },
        ]}
        data={data?.result}
        keybuilder={(item) => item.id}
        isFetching={isFetching}
        isError={isError}
        error={error}
        onEdit={(item) => {
          setCancelamentoAutomatico(item), onOpen();
        }}
        totalPages={data?.pageTotal ?? 0}
        itemsPerPage={itemsPerPage}
        currentPage={currentPage}
        setCurrentPage={setCurrentPage}
        totalItems={data?.total ?? 0}
      />

      <ModalConfirma
        titulo="Atenção"
        texto={`Deseja Alterar configuração sistema:  ${cancelamentoAutomatico?.sistema}?`}
        onClose={onClose}
        isOpen={isOpen}
        onConfirm={handleAltera}
        isLoading={isLoadingAlteraConfig}
      />
    </>
  );
}
