import { Button, Grid, GridItem } from "@chakra-ui/react";
import { useNavigate } from "react-router-dom";

interface ButtonControlFormProps {
  isLoading: boolean;
  textoSubimit?: string;
  textoCancelar?: string;
  textoLoadingSubimit?: string;
  navigateCancelar?: string;
}

export function ButtonControlForm({
  isLoading,
  textoSubimit,
  textoLoadingSubimit,
  navigateCancelar,
  textoCancelar,
}: ButtonControlFormProps) {
  const textoCancelarDefault = textoCancelar ? textoCancelar : "Cancelar";
  const textoSubimitDefault = textoSubimit ? textoSubimit : "Salvar";
  const textoLoadingSubimitDefault = textoLoadingSubimit ? textoLoadingSubimit : "Salvando...";
  const navigate = useNavigate();
  const redirectCancelar = navigateCancelar ? navigateCancelar : "./..";

  return (
    <Grid w={"100%"} templateColumns={{ base: "1fr", md: "repeat(4, 1fr)" }} pt={5} pb={5}>
      <GridItem colSpan={{ base: 1, md: 2 }} p={1}>
        <Button
          colorScheme="blue"
          w="100%"
          type="submit"
          isLoading={isLoading}
          loadingText={textoLoadingSubimitDefault}
        >
          {textoSubimitDefault}
        </Button>
      </GridItem>
      <GridItem colSpan={{ base: 1, md: 2 }} p={1}>
        <Button
          variant="outline"
          colorScheme="blue"
          w="100%"
          onClick={() => {
            navigate(redirectCancelar);
          }}
        >
          {textoCancelarDefault}
        </Button>
      </GridItem>
    </Grid>
  );
}
