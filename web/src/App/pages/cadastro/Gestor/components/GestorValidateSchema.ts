import * as zod from "zod";
import { emailValid, numberValid, stringValid } from "../../../../../utils/zodUtil.ts";

export const GestorValidateSchema = zod.object({
  nome: stringValid("Informe o Nome"),
  email: emailValid("Informe um e-mail válido"),
  matricula: numberValid("Informe a Matrícula"),
  descricao: stringValid("Informe a Descrição"),
  idCiPessoas: numberValid("Informe o ID CI Pessoas"),
  idUnidade: numberValid("Informe o ID Unidade"),
});
