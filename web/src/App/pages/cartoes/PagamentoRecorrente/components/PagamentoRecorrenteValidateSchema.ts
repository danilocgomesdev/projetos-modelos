import * as zod from "zod";
import { numberValid, optionalString } from "../../../../../utils/zodUtil";

export const PagamentoRecorrenteValidateSchema = zod.object({
  unidade: optionalString(),
  cpfCnpj: numberValid("Informe o cpd"),
  responsavelFinanceiro: optionalString(),
  statusRecorrencia: optionalString(),
  idRecorrencia: numberValid("Informe o id da recorrência"),
  dataInicioRecorrencia: optionalString(),
  dataFimRecorrencia: optionalString(),
});
