import { Grid, GridItem } from "@chakra-ui/react";
import { InputForm } from "../../../../components/InputControl";

export function FormNossoNumero() {
  return (
    <Grid display="flex" flexDirection="column" p={2} w="100%">
      <GridItem>
        <InputForm
          name="nossoNumeroInicial"
          label="Nosso Número Inicial"
          placeholder="00000000000000000"
        />
      </GridItem>
      <GridItem mt={3}>
        <InputForm
          name="nossoNumeroFinal"
          label="Nosso Número Final"
          placeholder="00000000000000000"
        />
      </GridItem>
      <GridItem mt={3}>
        <InputForm
          name="nossoNumeroAtual"
          label="Nosso Número Atual"
          placeholder="00000000000000000"
        />
      </GridItem>
    </Grid>
  );
}
