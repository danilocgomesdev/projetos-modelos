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
import { useMemo } from "react";
import { fetchInformacoesOrigemCadin } from "../../../../../requests/requestsCadin";
import { TemposCachePadrao } from "../../../../../utils/constantes";
import { DadosContasReceberProtheus } from "../../../../components/DadosContasReceberProtheus";
import { DadosNaoEncontrados } from "../../../../components/DadosNaoEncontrados";
import { Loading } from "../../../../components/Loading";
import { ModalBase } from "../../../../components/ModalBase";
import useCR5Axios from "../../../../hooks/useCR5Axios";
import { DadosContratoCadin } from "./DadosContratoCadin";
import { DadosParcelaOrigem } from "./DadosParcelaOrigem";

interface ModalDetalhesCobrancaCadinProps {
  isOpen: boolean;
  onClose: () => void;
  idCobrancaCliente: number | null;
}

export function ModalDetalhesCobrancaCadin({
  isOpen,
  onClose,
  idCobrancaCliente,
}: ModalDetalhesCobrancaCadinProps) {
  const { axios } = useCR5Axios();

  const { data, isFetching, isError } = useQuery({
    queryKey: ["fetchParcelasCadin", idCobrancaCliente],
    enabled: !!idCobrancaCliente,
    staleTime: TemposCachePadrao.CURTO,
    cacheTime: TemposCachePadrao.CURTO,
    queryFn: () => {
      return fetchInformacoesOrigemCadin(
        {
          idCobrancaCliente: idCobrancaCliente || 0, // valor está sempre presente
        },
        axios
      );
    },
  });

  const mensagemTipoBaixa = useMemo<string>(() => {
    if (!data) {
      return "";
    }
    if (
      data.informacoesOrigem.every(
        (it) => it.acordoRateado?.amortizaBoletoPago.baixaParcial === "1"
      )
    ) {
      return "Título de Origem será baixado no Protheus";
    } else {
      return "Será criado título no Protheus";
    }
  }, [data]);

  const corCabecalho = useColorModeValue("light.cabecalho", "dark.cabecalho");

  return (
    <ModalBase
      isOpen={isOpen}
      onClose={onClose}
      titulo="Detalhes da Cobrança"
      size="6xl"
      centralizado={true}
      overflowY="auto"
    >
      <Box maxH="70vh" w="100%">
        {isFetching ? (
          <Loading mensagem="Carregando dados!" altura="50vh" />
        ) : isError || !data ? (
          <DadosNaoEncontrados
            altura="50vh"
            isError
            mensagem="Não foi possível carregar a aplicação. Procure o suporte."
          />
        ) : (
          <>
            <Text fontSize="sm" fontWeight="bold" p={2}>
              ⚠️ Atenção: {mensagemTipoBaixa}
            </Text>
            <Accordion defaultIndex={[0, 1, 2]} allowMultiple>
              <AccordionItem>
                <h2>
                  <AccordionButton bg={corCabecalho}>
                    <Box as="span" flex="1" textAlign="center" fontSize="sm" fontWeight="bold">
                      DADOS DO CONTRATO 25
                    </Box>
                    <AccordionIcon />
                  </AccordionButton>
                </h2>
                <AccordionPanel pb={4} w="100%">
                  <DadosContratoCadin parcela={data.parcelaCadin.parcelaDTO} />
                </AccordionPanel>
              </AccordionItem>

              <AccordionItem>
                <h2>
                  <AccordionButton bg={corCabecalho}>
                    <Box
                      as="span"
                      flex="1"
                      textAlign="center"
                      fontSize="sm"
                      fontWeight="bold"
                      pl={8}
                    >
                      CONTAS A RECEBER PROTHEUS - PARCELA CADIN
                    </Box>
                    <AccordionIcon />
                  </AccordionButton>
                </h2>
                <AccordionPanel pb={4} w="100%">
                  {data.parcelaCadin.contasAReceberProtheus ? (
                    <DadosContasReceberProtheus dados={data.parcelaCadin.contasAReceberProtheus} />
                  ) : (
                    <Text fontSize="sm">Não foi criada Contas a Receber no Protheus!</Text>
                  )}
                </AccordionPanel>
              </AccordionItem>

              <AccordionItem>
                <h2>
                  <AccordionButton bg={corCabecalho}>
                    <Box
                      as="span"
                      flex="1"
                      textAlign="center"
                      fontSize="sm"
                      fontWeight="bold"
                      pl={8}
                    >
                      PARCELAS DE ORIGEM
                    </Box>
                    <AccordionIcon />
                  </AccordionButton>
                </h2>
                <AccordionPanel pb={4} w="100%">
                  <DadosParcelaOrigem dados={data} />
                </AccordionPanel>
              </AccordionItem>
            </Accordion>
          </>
        )}
      </Box>
    </ModalBase>
  );
}
