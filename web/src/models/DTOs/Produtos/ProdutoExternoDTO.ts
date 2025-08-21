export interface ProdutoExternoDTO {
  id: number;
  idProduto: number;
  idSistema: number;
  nome: string;
  status: "A" | "F";
  produtoProtheus: string;
  dataInclusao: string;
  idOperadorInclusao: number;
  dataAlteracao: string;
  idOperadorAlteracao: number;
}
