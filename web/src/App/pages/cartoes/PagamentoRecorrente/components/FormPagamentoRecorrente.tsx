import { Grid } from "@chakra-ui/react";
import { RecorrenciaDTO } from "../../../../../models/DTOs/Recorrencia/RecorrenciaDTO.ts";
import { InputForm } from "../../../../components/InputControl";

interface FormPagamentoRecorrenteProps {
  onOpen: () => void;
  pagamentoRecorrenteFiltrada: RecorrenciaDTO | undefined;
}

export function FormPagamentoRecorrente({
  pagamentoRecorrenteFiltrada,
}: FormPagamentoRecorrenteProps) {
  console.log("formulario :" + pagamentoRecorrenteFiltrada?.idRecorrencia.toString());
  return (
    <Grid w="100%" templateColumns={{ base: "100%", md: "repeat(4, 1fr)" }} mt={4}>
      <InputForm name="unidade" size="sm" label="Unidade" numeroColunasMd={2} naoEditavel={true} />
      <InputForm
        name="statusRecorrencia"
        size="sm"
        label="Status"
        numeroColunasMd={2}
        naoEditavel={true}
      />
      <InputForm
        name="responsavelFinanceiro"
        size="sm"
        label="Responsável Financeiro"
        numeroColunasMd={4}
        naoEditavel={true}
      />
      <InputForm name="cpfCnpj" size="sm" label="CPF" numeroColunasMd={2} naoEditavel={true} />
      <InputForm
        name="idRecorrencia"
        size="sm"
        label="Id Recorrência"
        numeroColunasMd={2}
        naoEditavel={true}
      />
      <InputForm
        name="dataInicioRecorrencia"
        size="sm"
        label="Data Inicio Recorrência"
        numeroColunasMd={2}
        naoEditavel={true}
      />
      <InputForm
        name="dataFimRecorrencia"
        size="sm"
        label="Data Fim Recorrência"
        numeroColunasMd={2}
        naoEditavel={true}
      />
    </Grid>
  );
}
