import * as zod from "zod";
import {
  carteiraConvenioBancarioValues,
  sistemaBancarioValues,
} from "../../../../../models/DTOs/ConvenioBancario";
import {
  checkOptionalString,
  checkString,
  numberValid,
  validaSubconjuntoString,
} from "../../../../../utils/zodUtil.ts";

export const ConvenioBancarioValidateSchema = zod.object({
  nomeCedente: checkString("o nome do cedente", { max: 60 }),
  numero: checkString("o número do Convênio", { max: 10 }),
  carteira: validaSubconjuntoString("Carteira deve ser um valor válido", carteiraValida),
  indiceMulta: numberValid("Informe o índice de multa"),
  indiceJuros: numberValid("Informe o índice de juros"),
  sistemaBancario: validaSubconjuntoString(
    "Sistema bancário deve ser um valor válido",
    sistemaBancarioValido
  ),
  observacao1: checkOptionalString("Observação 1", { max: 100 }),
  observacao2: checkOptionalString("Observação 2", { max: 100 }),
  observacao3: checkOptionalString("Observação 3", { max: 100 }),
  observacao4: checkOptionalString("Observação 4", { max: 100 }),
  observacao5: checkOptionalString("Observação 5", { max: 100 }),
  localPagamento: checkString("o local de pagamento", { max: 100 }),
});

function sistemaBancarioValido(value: string): value is "SINCO" | "SIGCB" {
  return (sistemaBancarioValues as string[]).includes(value);
}

function carteiraValida(value: string): value is "RG" | "SR" {
  return (carteiraConvenioBancarioValues as string[]).includes(value);
}
