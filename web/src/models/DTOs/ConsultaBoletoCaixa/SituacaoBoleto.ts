export interface SituacaoBoletoDTO {
  descricao: string;
  nomeEnum: string;
}

export interface SituacaoBoletoType {
  REMOVIDO: SituacaoBoletoDTO;
  EM_ABERTO: SituacaoBoletoDTO;
  ATIVO: SituacaoBoletoDTO;
  PAGO_MAIOR: SituacaoBoletoDTO;
  PAGO_MENOR: SituacaoBoletoDTO;
  PAGO: SituacaoBoletoDTO;
  CANCELADO: SituacaoBoletoDTO;
  PAGO_CADIN: SituacaoBoletoDTO;
}

export type SituacaoBoleto = keyof SituacaoBoletoType;

/**
 * Aqui, label é um nome amigável e value é o valor no Enum SituacaoBoleto no cr5-webservices-v2.
 * Constante feita para ser usada como opção de SelectControl
 */
export const situacoesBoletoValues: SituacaoBoletoType = {
    REMOVIDO: {
        nomeEnum: "REMOVIDO",
        descricao: "Removido",
    },
    EM_ABERTO: {
        nomeEnum: "EM_ABERTO",
        descricao: "Em Aberto",
    },
    ATIVO: {
        nomeEnum: "ATIVO",
        descricao: "Ativo",
    },
    PAGO_MAIOR: {
        nomeEnum: "PAGO_MAIOR",
        descricao: "Pago Maior",
    },
    PAGO_MENOR: {
        nomeEnum: "PAGO_MENOR",
        descricao: "Pago Menor",
    },
    PAGO: {
        nomeEnum: "PAGO",
        descricao: "Pago",  
    },
    CANCELADO: {
        nomeEnum: "CANCELADO",
        descricao: "Cancelado",
    },
    PAGO_CADIN: {
        nomeEnum: "PAGO_CADIN", 
        descricao: "Pago Cadin",
    },
};
