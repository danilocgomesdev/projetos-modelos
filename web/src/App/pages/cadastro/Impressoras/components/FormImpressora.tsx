import { Grid } from "@chakra-ui/react";
import { InputForm } from "../../../../components/InputControl";
import { SelectForm } from "../../../../components/SelectControl";

export function FormImpressora() {
  return (
    <Grid w="100%" templateColumns={{ base: "100%", md: "repeat(4, 1fr)" }} mt={4}>
      <SelectForm
        name="modelo"
        label="Modelo"
        id="modelo"
        w="100%"
        options={[
          {
            value: "Térmica",
            label: "TÉRMICA",
          },
        ]}
      />
      <InputForm name="ipMaquina" size="sm" label="Endereço IP" />
      <SelectForm
        name="tipoPorta"
        label="Tipo Porta"
        id="tipoPorta"
        w="100%"
        options={[
          {
            value: "COM",
            label: "COM",
          },
        ]}
      />
      <InputForm name="porta" size="sm" type="number" label="Porta" />
      <InputForm
        name="descricao"
        size="sm"
        label="Descrição"
        textTransform="uppercase"
        numeroColunasMd={2}
      />
      <SelectForm
        name="gaveta"
        label="Gaveta"
        id="gaveta"
        w="100%"
        options={[
          {
            value: "S",
            label: "SIM",
          },
          {
            value: "N",
            label: "NÃO",
          },
        ]}
      />
      <SelectForm
        name="guilhotina"
        label="Guilhotina"
        id="guilhotina"
        w="100%"
        options={[
          {
            value: "S",
            label: "SIM",
          },
          {
            value: "N",
            label: "NÃO",
          },
        ]}
      />
    </Grid>
  );
}
