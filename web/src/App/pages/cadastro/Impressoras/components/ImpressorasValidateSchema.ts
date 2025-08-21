import * as zod from "zod";
import { numberValid, stringValid, validaSubconjuntoString } from "../../../../../utils/zodUtil";

export const ImpressorasValidateSchema = zod.object({
  modelo: validaSubconjuntoString("Modelo deve ser (Térmica)", isTermica),
  descricao: stringValid("Informe a Descrição"),
  ipMaquina: stringValid("Informe o Endereço IP"),
  porta: numberValid("Informe o número da Porta"),
  gaveta: validaSubconjuntoString("Gaveta deve ser S (SIM) ou N (NÃO)", isSimOrNao),
  guilhotina: validaSubconjuntoString("Guilhotina deve ser S (SIM) ou N (NÃO)", isSimOrNao),
  tipoPorta: validaSubconjuntoString("Tipo de Porta deve ser (COM)", isCOM),
});

function isSimOrNao(value: string): value is "S" | "N" {
  return value === "S" || value === "N";
}

function isCOM(value: string): value is "COM" {
  return value === "COM";
}

function isTermica(value: string): value is "Térmica" {
  return value === "Térmica";
}
