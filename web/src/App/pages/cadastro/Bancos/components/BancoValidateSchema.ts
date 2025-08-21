import * as zod from "zod";
import { numberValid, stringValid } from "../../../../../utils/zodUtil.ts";

export const BancoValidateSchema = zod.object({
  numero: numberValid("Informe o Número"),
  abreviatura: stringValid("Informe a Abreviatura"),
  nome: stringValid("Informe o Nome"),
});
