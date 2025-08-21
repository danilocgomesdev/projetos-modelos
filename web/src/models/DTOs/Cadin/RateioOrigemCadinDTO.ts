export interface RateioOrigemCadinDTO {
  id: number;
  dataInclusao: string;
  idCobrancaClienteCadin: number;
  idCobrancaClienteOrigem: number;
  codigoAmortizaBoletoPago: number;
  formaPagamento: string;
  valorTotalCobranca: number;
  porcentagemRateio: number;
  desconto: number;
  juros: number;
  multa: number;
  custo: number;
  valorPago: number;
  dataBaixaProtheus: string | null;
}
