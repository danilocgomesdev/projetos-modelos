import * as zod from "zod";
import { numberValid, optionalString, stringValid } from "../../../../../utils/zodUtil";

export const ContaCorrenteValidateSchema = zod.object({
  idAgencia: numberValid("Informe a Agência"),
  numeroOperacao: stringValid("informe o N° Operação"),
  numeroConta: stringValid("informe o N° da Conta"),
  digitoConta: stringValid("informe o Dígito da Conta"),
  contaBanco: optionalString(),
  contaCliente: optionalString(),
  contaCaixa: optionalString(),
  contaJuros: optionalString(),
  contaDescontos: optionalString(),
  cofreBanco: optionalString(),
  cofreAgencia: optionalString(),
  cofreConta: optionalString(),
});
