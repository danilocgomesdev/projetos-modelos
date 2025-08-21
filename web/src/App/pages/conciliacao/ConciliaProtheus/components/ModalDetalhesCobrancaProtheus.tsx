import {
  Accordion,
  AccordionButton,
  AccordionIcon,
  AccordionItem,
  AccordionPanel,
  Box,
  Text,
  useColorModeValue,
} from "@chakra-ui/react";
import { useQuery } from "@tanstack/react-query";
import { useEffect, useState } from "react";
import { ParcelaDTO } from "../../../../../models/DTOs/Cobrancas/ParcelaDTO";
import { fetchContasAReceberProtheusDaCobranca } from "../../../../../requests/requestsProtheus";
import { TemposCachePadrao } from "../../../../../utils/constantes";
import { SimpleObservable } from "../../../../../utils/simpleObservable";
import { DadosContasReceberProtheus } from "../../../../components/DadosContasReceberProtheus";
import { DadosNaoEncontrados } from "../../../../components/DadosNaoEncontrados";
import { DadosParcela } from "../../../../components/DadosParcela";
import { Loading } from "../../../../components/Loading";
import { ModalBase } from "../../../../components/ModalBase";
import useCR5Axios from "../../../../hooks/useCR5Axios";

interface ModalDetalhesCobrancaProtheusProps {
  isOpen: boolean;
  onClose: () => void;
  idCobrancaCliente: number | null;
  dadosParcelaObservable: SimpleObservable<ParcelaDTO>;
}

export function ModalDetalhesCobrancaProtheus({
  isOpen,
  onClose,
  idCobrancaCliente,
  dadosParcelaObservable,
}: ModalDetalhesCobrancaProtheusProps) {
  const { axios } = useCR5Axios();

  const [parcela, setDadosParcela] = useState<ParcelaDTO | undefined>();

  const observer = dadosParcelaObservable.subscribe(setDadosParcela);

  useEffect(() => {
    return () => {
      dadosParcelaObservable.unsubscribe(observer);
    };
  }, []);

  const { data, isFetching, isError } = useQuery({
    queryKey: ["fetchContasAReceberProtheusDaCobranca", idCobrancaCliente, parcela],
    enabled: !!idCobrancaCliente && !!parcela,
    staleTime: TemposCachePadrao.CURTO,
    cacheTime: TemposCachePadrao.CURTO,
    queryFn: () => {
      return fetchContasAReceberProtheusDaCobranca(
        {
          idCobrancaCliente: idCobrancaCliente || 0, // valor está sempre presente
        },
        axios
      );
    },
  });

  const corCabecalho = useColorModeValue("light.cabecalho", "dark.cabecalho");

  if (!parcela) {
    return <></>;
  }

  return (
    <ModalBase
      isOpen={isOpen}
      onClose={onClose}
      titulo="Detalhes da Cobrança"
      size="6xl"
      centralizado={true}
      overflowY="auto"
    >
      <Accordion defaultIndex={[0, 1]} allowMultiple>
        <AccordionItem>
          <h2>
            <AccordionButton bg={corCabecalho}>
              <Box as="span" flex="1" textAlign="center" fontSize="sm" fontWeight="bold">
                Dados Parcela
              </Box>
              <AccordionIcon />
            </AccordionButton>
          </h2>
          <AccordionPanel pb={4} w="100%">
            <Box maxH="70vh" w="100%">
              <DadosParcela parcela={parcela} />
            </Box>
          </AccordionPanel>
        </AccordionItem>

        <AccordionItem>
          <h2>
            <AccordionButton bg={corCabecalho}>
              <Box as="span" flex="1" textAlign="center" fontSize="sm" fontWeight="bold">
                Dados Contas a Receber Protheus
              </Box>
              <AccordionIcon />
            </AccordionButton>
          </h2>
          <AccordionPanel pb={4} w="100%">
            <>
              {isFetching ? (
                <Loading mensagem="Carregando dados do Protheus!" altura="50vh" />
              ) : isError ? (
                <DadosNaoEncontrados
                  altura="50vh"
                  isError
                  mensagem="Erro inesperado ao buscar dados do Protheus. Procure o suporte."
                />
              ) : !data ? (
                <Text fontSize="sm">Não foi criada Contas a Receber no Protheus!</Text>
              ) : (
                <DadosContasReceberProtheus dados={data} />
              )}
            </>
          </AccordionPanel>
        </AccordionItem>
      </Accordion>
    </ModalBase>
  );
}
