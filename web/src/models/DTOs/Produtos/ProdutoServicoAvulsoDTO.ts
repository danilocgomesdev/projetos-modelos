
export interface ProdutoServicoAvulsoDTO {
  idServico: number;
  descricao: string;
  preco: number;
  dataInativo: number | null;
  dataInclusao: number;
  idOperadorInclusao: number;
  dataAlteracao: number;
  idOperadorAlteracao: number;
  idEntidade: number;
  concedeDescontoSindicato: boolean | null;
  codProdutoProtheus: string;
}
