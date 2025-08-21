export interface SituacaoAgrupadoDTO {
  descricao: string;
  nomeEnum: string;
}

export interface SituacaoAgrupadoType {
  EM_ABERTO: SituacaoAgrupadoDTO;
  PAGO: SituacaoAgrupadoDTO;
  CANCELADO: SituacaoAgrupadoDTO;
  ADMINISTRADO_CADIN: SituacaoAgrupadoDTO;
  PAGO_CADIN: SituacaoAgrupadoDTO;
}

export type SituacaoAgrupado = keyof SituacaoAgrupadoType;

/**
 * Aqui, label é um nome amigável e value é o valor no Enum SituacaoAgrupado no cr5-webservices-v2.
 * Constante feita para ser usada como opção de SelectControl
 */
export const situacoesAgrupadoValues: SituacaoAgrupadoType = {
    EM_ABERTO: {
        nomeEnum: "EM_ABERTO",
        descricao: "Em Aberto",
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
    ADMINISTRADO_CADIN: {
        nomeEnum: "ADMINISTRADO_CADIN",
        descricao: "Administrado Cadin",
    },
};


