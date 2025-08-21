import { useQuery } from "@tanstack/react-query";

import { Box } from "@chakra-ui/react";

import { fetchDadosDoGrupo } from "../../../../../requests/requestCobrancasAgrupadas";

import { TemposCachePadrao } from "../../../../../utils/constantes";

import { formatarMoedaBrasil } from "../../../../../utils/mascaras";

import { ModalBase } from "../../../../components/ModalBase";

import { TabelaCR5 } from "../../../../components/Tabelas";

import { situacoesCobrancaClienteValues } from "../../../../../models/DTOs/Cobrancas/SituacaoCobrancaCliente";
import useCR5Axios from "../../../../hooks/useCR5Axios";

interface ModalDadosGrupoProps {
  isOpen: boolean;
  onClose: () => void;
  idCobrancaAgrupada?: number;
}

export default function ModalDadosGrupo({
  isOpen,
  onClose,
  idCobrancaAgrupada,
}: ModalDadosGrupoProps) {
  const { axios } = useCR5Axios();

  const { data, isFetching, isError, error } = useQuery({
    queryKey: ["fetchDadosCobrancaGrupo", idCobrancaAgrupada],
    enabled: !!idCobrancaAgrupada,
    staleTime: TemposCachePadrao.CURTO,
    cacheTime: TemposCachePadrao.CURTO,
    queryFn: () => {
      return fetchDadosDoGrupo(idCobrancaAgrupada!, axios);
    },
  });

  return (
    <ModalBase
      size="6xl"
      isOpen={isOpen}
      onClose={onClose}
      titulo={`Dados do Grupo ${idCobrancaAgrupada}`}
      centralizado={true}
      overflowY="auto"
    >
      <TabelaCR5
        cabecalhos={[
          {
            titulo: "Unidade",
            dadoBuilder: (item) => item.idUnidade.toString(),
          },
          {
            titulo: "Contrato",
            dadoBuilder: (item) => item.contrato.toString(),
          },
          {
            titulo: "Protheus Contrato",
            dadoBuilder: (item) => item.protheusContrato,
          },
          {
            titulo: "Sistema",
            dadoBuilder: (item) => item.idSistema.toString(),
          },
          {
            titulo: "Situação",
            dadoBuilder: (item) => situacoesCobrancaClienteValues[item.situacao]?.descricao,
          },
          {
            titulo: "Parcela",
            dadoBuilder: (item) => item.parcela.toString(),
          },
          {
            titulo: "Consumidor",
            dadoBuilder: (item) => item.consumidorNome,
          },
          {
            titulo: "COBRANÇA",
            dadoBuilder: (item) => formatarMoedaBrasil(item.valorCobranca),
          },
          {
            titulo: "Boleto",
            dadoBuilder: (item) => (item.nossoNumero ? "Sim" : "Não"),
          },
        ]}
        data={data}
        isFetching={isFetching}
        isError={isError}
        error={error}
        keybuilder={(item) => item.contrato + item.parcela + item.protheusContrato}
      />
      <Box p={4} />
    </ModalBase>
  );
}
