import { Button, GridItem } from "@chakra-ui/react";
import { FiSearch, FiTrash } from "react-icons/fi";

interface ButtonPesquisarLimparProps {
  handlePesquisar?: () => void;
  handleLimparCampo?: () => void;
  isLimpar?: boolean;
  numeroColunasMd?: number;
  numeroColunasLg?: number;
  marginTopLg?: number;
  marginTopMd?: number;
}

export function ButtonPesquisarLimpar({
  handlePesquisar,
  handleLimparCampo,
  isLimpar,
  numeroColunasMd,
  numeroColunasLg,
  marginTopLg,
  marginTopMd,
}: ButtonPesquisarLimparProps) {
  return (
    <>
      {handlePesquisar && (
        <GridItem
          colSpan={{ base: 1, md: numeroColunasMd ?? 1, lg: numeroColunasLg ?? numeroColunasMd }}
          m={1}
          mb={2}
          mt={{ base: 0, md: marginTopMd ?? 0, lg: marginTopLg ?? marginTopMd }}
          alignContent={"end"}
        >
          <Button colorScheme="blue" size="sm" w="100%" onClick={() => handlePesquisar()}>
            <FiSearch style={{ marginRight: "0.2rem" }} />
            Pesquisar
          </Button>
        </GridItem>
      )}
      {handleLimparCampo && (
        <GridItem
          colSpan={{ base: 1, md: numeroColunasMd ?? 1, lg: numeroColunasLg ?? numeroColunasMd }}
          m={1}
          mb={2}
          alignContent={"end"}
          mt={{ base: 2, md: marginTopMd ?? 2, lg: marginTopLg ?? marginTopMd }}
        >
          <Button
            isDisabled={isLimpar}
            colorScheme="blue"
            variant="outline"
            size="sm"
            w="100%"
            onClick={() => handleLimparCampo()}
          >
            <FiTrash style={{ marginRight: "0.2rem" }} />
            Limpar
          </Button>
        </GridItem>
      )}
    </>
  );
}
