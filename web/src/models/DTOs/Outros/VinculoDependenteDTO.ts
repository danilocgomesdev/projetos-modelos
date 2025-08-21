import { PessoaCr5DTO } from "./PessoaCr5DTO";

export interface DependenteDTO {
  idDependente: number;
  nomeDependente: string;
  cpfCnpjDependente: string;
  dataNascimentoDependente: Date;
  dataInclusao: string;
  idOperadorInclusao: number;
  dataAlteracao: string;
  idOperadorAlteracao: number;
}

export interface VinculoDependenteDTO {
  idDependenteResponsavel: number;
  dependente: DependenteDTO;
  pessoaResponsavel: PessoaCr5DTO;
}

interface VinculoDependenteResponsavelDTO {
  idAluno: number;
  nomeAluno: string;
  cpfAluno: string;
  statusAluno: string;
  idResponsavelAluno: number;
  statusDoVinculo: string;
  idResponsavelFinanceiro: number;
  nomeResponsavelFinanceiro: string;
  cpfResponsavelFinanceiro: string;
  statusResponsavel: string;
  sistema: string;
}

export interface ClienteResponsavelDTO {
  list: VinculoDependenteResponsavelDTO[];
  amount: number;
  skip: number;
}
