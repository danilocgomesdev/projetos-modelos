export interface FormasDePagamentoSimplificadaDTO {
  descricao: string;
  nomeEnum: string;
}

export interface FormasDePagamentoType {
  BOLETO: FormasDePagamentoSimplificadaDTO;
  DEPOSITO: FormasDePagamentoSimplificadaDTO;
  DINHEIRO: FormasDePagamentoSimplificadaDTO;
  ECOMMERCE: FormasDePagamentoSimplificadaDTO;
  CARTAO_CREDITO: FormasDePagamentoSimplificadaDTO;
  CARTAO_DEBITO: FormasDePagamentoSimplificadaDTO;
  PIX: FormasDePagamentoSimplificadaDTO;
  PGCADIN: FormasDePagamentoSimplificadaDTO;
  NAO_IDENTIFICADO: FormasDePagamentoSimplificadaDTO;
}

export type FormaPagamentoSimplificado = keyof FormasDePagamentoType;

/**
 * Aqui, label é um nome amigável e value é o valor no Enum FormaPagamentoSimplificado no cr5-webservices-v2.
 * Constante feita para ser usada como opção de SelectControl para selecionar forma de pagamento
 */
export const formasDePagamentoValues: FormasDePagamentoType = {
  BOLETO: {
    descricao: "Boleto",
    nomeEnum: "BOLETO",
  },
  DEPOSITO: {
    descricao: "Depósito",
    nomeEnum: "DEPOSITO",
  },
  DINHEIRO: {
    descricao: "Dinheiro",
    nomeEnum: "DINHEIRO",
  },
  ECOMMERCE: {
    descricao: "Ecommerce",
    nomeEnum: "ECOMMERCE",
  },
  CARTAO_CREDITO: {
    descricao: "Cartão de Crédito",
    nomeEnum: "CARTAO_CREDITO",
  },
  CARTAO_DEBITO: {
    descricao: "Cartão de Débito",
    nomeEnum: "CARTAO_DEBITO",
  },
  PIX: {
    descricao: "Pix",
    nomeEnum: "PIX",
  },
  PGCADIN: {
    descricao: "Pago Cadin",
    nomeEnum: "PGCADIN",
  },
  NAO_IDENTIFICADO: {
    descricao: "Não identificado",
    nomeEnum: "NAO_IDENTIFICADO",
  },
};
