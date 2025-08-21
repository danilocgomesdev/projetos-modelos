export type FiltroPagamento =
  | { dataPagamentoInicial?: Date; dataPagamentoFinal?: Date }
  | { isPago: boolean };
