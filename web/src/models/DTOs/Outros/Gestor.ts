import { UnidadeDTO } from "./UnidadeDTO";

export interface GestorDTO {
  idGestor: number;
  nome: string;
  email: string;
  matricula: number;
  descricao: string;
  idCiPessoas: number;
  idUnidade: number;
  idOperadorInclusao: number;
  idOperadorAlteracao: number;
  dataInclusao: string;
  dataAlteracao: string;
  unidade: UnidadeDTO;
}

export interface PessoaCIDTO {
  id: number;
  nome: string;
  email: string;
  matricula: string;
}
