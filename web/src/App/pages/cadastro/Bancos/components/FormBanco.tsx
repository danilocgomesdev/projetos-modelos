import { Grid } from "@chakra-ui/react";
import { InputForm } from "../../../../components/InputControl";

export function FormBanco() {
  return (
    <Grid w="100%" templateColumns={{ base: "¨100%", md: "repeat(4, 1fr)" }} mt={4} p={1}>
      <InputForm
        size="sm"
        name="numero"
        label="Número"
        type="number"
        numeroColunasMd={1}
        obrigatorio
      />
      <InputForm size="sm" name="nome" label="Nome" numeroColunasMd={2} obrigatorio type="text" />
      <InputForm
        size="sm"
        name="abreviatura"
        label="Abreviatura"
        type="text"
        numeroColunasMd={1}
        obrigatorio
      />
    </Grid>
  );
}
