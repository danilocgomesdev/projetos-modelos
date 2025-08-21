import { DadoContabilDTO } from "../Contabil/DadoContabilDTO";

export interface ProdutoDadoContabilDTO {
  dadoContabil: DadoContabilDTO;
  idProdutoDadoContabil: number;
  produto: string;
  status: string;
  codProdutoProtheus: string;
  dataInativacao: number | null;
  preco: number;
  idProduto: number;
  idSistema: number;
  dmed: string;
}
