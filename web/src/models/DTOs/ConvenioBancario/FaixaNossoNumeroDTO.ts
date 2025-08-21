export interface FaixaNossoNumeroDTO {
  id: number;
  ativo: boolean;
  nossoNumeroInicial: string;
  nossoNumeroFinal: string;
  nossoNumeroAtual: string;
  dataInclusao: Date;
  dataAlteracao: Date | null;
  idOperadorAlteracao: number | null;
  idOperadorInclusao: number;
}
