import { BancoDTO } from "./BancoDTO.ts";

export interface AgenciaDTO {
  id: number;
  banco: BancoDTO;
  cnpj: string;
  numero: string;
  digitoVerificador: string;
  nome: string;
  cidade: string;
  dataInclusao: string;
  idOperadorInclusao: number;
  dataAlteracao: string;
  idOperadorAlteracao: number;
}
