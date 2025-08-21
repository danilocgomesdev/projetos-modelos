import * as zod from "zod";
import { optionalString, stringValid, validaSubconjuntoString } from "../../../../../utils/zodUtil";

export const ProdutoServicoValidateSchema = zod.object({
  produto: stringValid("Informe o nome do Produto"),
  preco: stringValid("Informe o Preço"),
  status: validaSubconjuntoString("Status deve ser A (Ativo) ou I (Inativo)", isAOrI),
  idSistema: optionalString(),
  dataInativo: optionalString(),
  codProdutoProtheus: optionalString(),
});

function isAOrI(value: string): value is "A" | "I" {
  return value === "A" || value === "I";
}

export const AlterarProdutoServicoValidateSchema = ProdutoServicoValidateSchema.extend({
  codProdutoProtheus: stringValid("Informe o Código Produto Protheus"),
});
