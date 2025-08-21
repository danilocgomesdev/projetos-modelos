import * as zod from "zod";
import {
  numberValid,
  optionalString,
  stringValid,
  validaSubconjuntoString,
} from "../../../../../utils/zodUtil";

export const ProdutoDadoContabilValidateSchema = zod.object({
  idProdutoDadoContabil: numberValid("Informe o Código Produto Dado Contábil"),
  dadoContabil: numberValid("Informe o Dado Produto"),
  idProduto: numberValid("Informe o Código Produto"),
  produto: stringValid("Informe o Produto"),
  preco: optionalString(),
  idSistema: numberValid("Informe o Sistema"),
  itemContabil: stringValid("Informe o Item Contábil"),
  itemContabilDescricao: stringValid("Informe a Descrição Item Contábil"),
  contaContabil: stringValid("Informe a Conta Contábil"),
  contaContabilDescricao: stringValid("Informe a Descrição Conta Contábil"),
  status: validaSubconjuntoString("Status deve ser A (Ativo) ou I (Inativo)", isAOrI),
  dmed: stringValid("Informe o Declaração de Serviços Médicos e de Saúde - Dmed"),
  codigoNatureza: optionalString(),
  descricaoNatureza: optionalString(),
  dataInativacao: optionalString(),
  codProdutoProtheus: optionalString(),
});

function isAOrI(value: string): value is "A" | "I" {
  return value === "A" || value === "I";
}
