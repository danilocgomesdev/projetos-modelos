import { AbsoluteCenter, Box, Divider, Text, useColorModeValue } from "@chakra-ui/react";
import { ParcelaCadinDTO } from "../../../../../models/DTOs/Cadin/ParcelaCadinDTO";
import { DadosContasReceberProtheus } from "../../../../components/DadosContasReceberProtheus";
import { DadosParcela } from "../../../../components/DadosParcela";
import { AcordoRateado } from "./AcordoRateado";

interface DadosParcelaOrigemProps {
  dados?: ParcelaCadinDTO | null;
}

export function DadosParcelaOrigem({ dados }: DadosParcelaOrigemProps) {
  const parcelas = dados;
  if (!parcelas) {
    return null;
  }

  const corFundoDivisoes = useColorModeValue("light.modalBackground", "dark.modalBackground");
  const corFundoAlternativo = useColorModeValue("light.tabela", "dark.tabela");

  return (
    <>
      {parcelas.informacoesOrigem.map((informacaoOrigem, index) => {
        const corFundo = index % 2 == 0 ? corFundoDivisoes : corFundoAlternativo;

        return (
          <Box
            key={informacaoOrigem.parcelaDeOrigem.parcelaDTO.idCobrancaCliente}
            m={2}
            bg={corFundo}
            w="100%"
            pt={2}
          >
            <Box position="relative" p={2}>
              <Divider />
              <AbsoluteCenter fontSize="md" fontWeight="bold" bgColor={corFundo} px={2} py={1}>
                Dados Parcela Origem
              </AbsoluteCenter>
            </Box>

            <DadosParcela parcela={informacaoOrigem.parcelaDeOrigem.parcelaDTO} />

            <Box position="relative" p={5}>
              <Divider />
              <AbsoluteCenter fontSize="sm" fontWeight="bold" bgColor={corFundo} px={2} py={1}>
                Dados Contas a Receber Protheus
              </AbsoluteCenter>
            </Box>
            {informacaoOrigem.parcelaDeOrigem.contasAReceberProtheus ? (
              <DadosContasReceberProtheus
                dados={informacaoOrigem.parcelaDeOrigem.contasAReceberProtheus}
                bgLabelBaixas={corFundo}
              />
            ) : (
              <Text fontSize="sm">Não foi criada Contas a Receber no Protheus!</Text>
            )}

            <Box position="relative" p={2}>
              <Divider />
              <AbsoluteCenter fontSize="sm" fontWeight="bold" bgColor={corFundo} px={2} py={1}>
                Acordo Rateado
              </AbsoluteCenter>
            </Box>
            {informacaoOrigem.acordoRateado ? (
              <AcordoRateado dados={informacaoOrigem.acordoRateado} />
            ) : (
              <Text fontSize="sm">Acordo não foi rateado!</Text>
            )}
          </Box>
        );
      })}
    </>
  );
}
