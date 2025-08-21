import * as zod from "zod";
import { optionalString, stringValid } from "../../../../../utils/zodUtil";

export const AgenciaValidateSchema = zod.object({
  idBanco: stringValid("Informe o Banco"),
  banco: stringValid("Informe o Banco"),
  cnpj: stringValid("Informe o CNPJ"),
  cidade: stringValid("Informe a cidade"),
  digitoVerificador: optionalString(),
  nome: stringValid("Informe o nome da agência"),
  numero: stringValid("Informe a agência"),
});
