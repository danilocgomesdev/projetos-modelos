export interface MovimentacaoBancariaProtheusDTO {
  recno: number;
  filial: string;
  data: string;
  valor: number;
  valorJuros: number;
  valorMulta: number;
  valorDesconto: number;
  numero: string;
  cienteFornecedor: string;
  beneficiario: string;
  parcela: string;
  prefixo: string;
  motivoBaixa: string;
  formaPagamento: string;
  tipo: string;
  loja: string;
  natureza: string;
  banco: string;
  agencia: string;
  conta: string;
  origem: string;
  historico: string;
  tipoBaixa: string;
  conciliacaoGefin: string;
  excluido: boolean;
}
