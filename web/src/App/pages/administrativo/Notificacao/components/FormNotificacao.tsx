import { Grid } from "@chakra-ui/react";
import { TextareaForm } from "../../../../components/TextControl/TextareaForm.tsx";

export function FormNotificacao() {
  return (
    <Grid w="100%" templateColumns={{ base: "100%", md: "repeat(4, 1fr)" }} mt={4} p={1}>
      <TextareaForm name="mensagem" label="Mensagem" numeroColunasMd={4} obrigatorio />
    </Grid>
  );
}
