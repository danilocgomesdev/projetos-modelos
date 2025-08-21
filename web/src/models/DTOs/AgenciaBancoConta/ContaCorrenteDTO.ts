import { UnidadeDTO } from "../Outros/UnidadeDTO";
import { AgenciaDTO } from "./AgenciaDTO";

export interface ContaCorrenteDTO {
  id: number;
  agencia: AgenciaDTO;
  numeroOperacao: string;
  numeroConta: string;
  digitoConta: string;
  idUnidade: number;
  dataInclusao: Date;
  idOperadorInclusao: number;
  dataAlteracao: Date | null;
  idOperadorAlteracao: number | null;
  contaBanco: string | null;
  contaCliente: string | null;
  contaCaixa: string | null;
  contaJuros: string | null;
  contaDescontos: string | null;
  cofreBanco: string | null;
  cofreAgencia: string | null;
  cofreConta: string | null;
  unidade: UnidadeDTO;
}
