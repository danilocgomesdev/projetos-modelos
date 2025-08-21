export interface AmortizaBoletoPagoDTO {
  codigo: number;
  valorJuros: number;
  valorMulta: number;
  valorCusta: number;
  valorPago: number;
  valorPrincial: number;
  valorDesconto: number;
  idCobrancaClienteOrigem: number;
  baixaParcial: string;
  dataBaixaProtheus: string | null;
  dataBaixaCr5: string | null;
  recno: number | null;
  idCobrancaClienteCadin: number;
  codigoEntidade: number;
  numeroParcela: number;
  objetoDeInadiplencia: number;
  statusAcordo: string;
  statusObjetoCadin: string;
}
