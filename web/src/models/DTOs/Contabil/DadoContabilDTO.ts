export interface DadoContabilDTO {
  idDadoContabil: number;
  contaContabil: string;
  contaContabilDescricao: string;
  itemContabil: string;
  itemContabilDescricao: string;
  natureza: string;
  naturezaDescricao: string;
  idEntidade: number;
  idOperadorInclusao: number;
  dataInativacao: string | null;
}
