import {
  Accordion,
  AccordionButton,
  AccordionIcon,
  AccordionItem,
  AccordionPanel,
  Box,
  useColorModeValue,
} from "@chakra-ui/react";

import { DadosTituloBoletoCr5DTO } from "../../../../../models/DTOs/ConsultaBoletoCaixa/DadosTituloBoletoCr5DTO";

import { DadosTitulo } from "./DadosTitulo";

interface DadosTituloCR5Props {
  dadosTituloCR5: DadosTituloBoletoCr5DTO[] | undefined;
  isFetchingCR5: boolean;
  isErrorCR5: boolean;
  errorCR5: unknown;
}

export function DadosTituloCR5({
  dadosTituloCR5,
  isFetchingCR5,
  isErrorCR5,
  errorCR5,
}: DadosTituloCR5Props) {
  return (
    <Box>
      <Accordion defaultIndex={[0, 1]} allowMultiple>
        <AccordionItem>
          <h2>
            <AccordionButton bg={useColorModeValue("light.cabecalho", "dark.cabecalho")}>
              <Box as="span" flex="1" textAlign="center" fontSize="sm" fontWeight="bold">
                Dados Título CR5
              </Box>
              <AccordionIcon />
            </AccordionButton>
          </h2>
          <AccordionPanel w="100%">
            <Box maxH="70vh" w="100%">
              <DadosTitulo
                dadosTituloCR5={dadosTituloCR5}
                isFetching={isFetchingCR5}
                isError={isErrorCR5}
                errorCR5={errorCR5}
              />
            </Box>
          </AccordionPanel>
        </AccordionItem>
      </Accordion>
    </Box>
  );
}
