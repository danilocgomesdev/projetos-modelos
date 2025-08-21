import { MovimentacaoBancariaProtheusDTO } from "./MovimentacaoBancariaProtheusDTO";

export interface ContasAReceberProtheusDTO {
  recno: number;
  filial: string;
  cliente: string;
  historico: string;
  contrato: string;
  titulo: string;
  parcela: string;
  sistema: string;
  idInterface: string;
  dataEmissao: string;
  cobranca: number;
  valorLiquidado: number;
  saldo: number;
  dataBaixa: string | null;
  revisaoDeContrato: string;
  excluido: boolean;
  tipo: string;
  baixas: MovimentacaoBancariaProtheusDTO[];
}
