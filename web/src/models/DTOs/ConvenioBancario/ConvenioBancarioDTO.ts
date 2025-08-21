import { MoedaDTO } from ".";
import { ContaCorrenteDTO } from "../AgenciaBancoConta/ContaCorrenteDTO";
import { UnidadeDTO } from "../Outros/UnidadeDTO";
import { FaixaNossoNumeroDTO } from "./FaixaNossoNumeroDTO";

export type CarteiraConvenioBancario = "RG" | "SR";
enum CarteiraConvenioBancarioEnum {
  RG = "RG",
  SR = "SR",
}
export const carteiraConvenioBancarioValues = Object.values(CarteiraConvenioBancarioEnum);

export type SistemaBancario = "SINCO" | "SIGCB";
enum SistemaBancarioEnum {
  SINCO = "SINCO",
  SIGCB = "SIGCB",
}
export const sistemaBancarioValues = Object.values(SistemaBancarioEnum);

export interface ConvenioBancarioDTO {
  id: number;
  contaCorrente: ContaCorrenteDTO;
  nomeCedente: string;
  numero: string;
  carteira: CarteiraConvenioBancario;
  moeda: MoedaDTO;
  tituloEspecie: string;
  tipoEmissao: string;
  aceite: boolean;
  indiceMulta: number;
  indiceJuros: number;
  sistemaBancario: SistemaBancario;
  observacao1: string | null;
  observacao2: string | null;
  observacao3: string | null;
  observacao4: string | null;
  observacao5: string | null;
  localPagamento: string;
  idUnidade: number;
  dataInativo: Date | null;
  dataInclusao: Date;
  idOperadorInclusao: number;
  dataAlteracao: Date | null;
  idOperadorAlteracao: number | null;
  utilizaUnCentralizadora: boolean;
  faixasNossoNumero: FaixaNossoNumeroDTO[];

  unidade: UnidadeDTO;
}
