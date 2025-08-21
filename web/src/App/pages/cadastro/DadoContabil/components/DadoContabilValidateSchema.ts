import * as zod from "zod";
import {
  numberValid,
  optionalString,
  stringValid,
  validaSubconjuntoString,
} from "../../../../../utils/zodUtil";

export const DadoContabilValidateSchema = zod.object({
  idDadoContabil: optionalString(),
  contaContabil: stringValid("Informe a Conta Contábil"),
  contaContabilDescricao: stringValid("Informe a Descricão Conta Contábil"),
  itemContabil: stringValid("Informe o Item Contábil"),
  itemContabilDescricao: stringValid("Informe a Descricão Item Contábil"),
  idEntidade: numberValid("Informe a Entidade"),
  dataInativacao: validaSubconjuntoString("Status deve ser A (Ativo) ou F (Inativo)", isAOrF),
  natureza: stringValid("Informe o Código Natureza"),
  naturezaDescricao: stringValid("Informe a Descricão Código Natureza"),
});

function isAOrF(value: string): value is "A" | "F" {
  return value === "A" || value === "F";
}
