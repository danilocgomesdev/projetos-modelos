export interface BancoDTO {
  id: number;
  numero: string;
  nome: string;
  abreviatura: string;
  dataInclusao: string;
  idOperadorInclusao: number | null;
  dataAlteracao: string;
  idOperadorAlteracao: number | null;
}
