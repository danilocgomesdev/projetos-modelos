export interface SituacaoCobrancaClienteDTO {
  descricao: string;
  nomeEnum: string;
}

export interface SituacaoCobrancaClienteType {
  PROVISORIO: SituacaoCobrancaClienteDTO;
  DEPOSITO: SituacaoCobrancaClienteDTO;
  ISENTO: SituacaoCobrancaClienteDTO;
  PAGO_CADIN: SituacaoCobrancaClienteDTO;
  ADM_CADIN: SituacaoCobrancaClienteDTO;
  DELETADO: SituacaoCobrancaClienteDTO;
  AGRUPADO: SituacaoCobrancaClienteDTO;
  EM_ABERTO: SituacaoCobrancaClienteDTO;
  PAGO_MANUAL: SituacaoCobrancaClienteDTO;
  PAGO_RETORNO_BANCO: SituacaoCobrancaClienteDTO;
  PAGO_PIX: SituacaoCobrancaClienteDTO;
  NAO_ADM_CR5: SituacaoCobrancaClienteDTO;
  ESTORNADO: SituacaoCobrancaClienteDTO;
  EMABERTO: SituacaoCobrancaClienteDTO;
}

export type SituacaoCobrancaCliente = keyof SituacaoCobrancaClienteType;

/**
 * Aqui, label é um nome amigável e value é o valor no Enum SituacaoCobrancaCliente no cr5-webservices-v2.
 * Constante feita para ser usada como opção de SelectControl
 */
export const situacoesCobrancaClienteValues: SituacaoCobrancaClienteType = {
  PROVISORIO: {
    nomeEnum: "PROVISORIO",
    descricao: "Provisorio",
  },
  DEPOSITO: {
    nomeEnum: "DEPOSITO",
    descricao: "Deposito",
  },
  ISENTO: {
    nomeEnum: "ISENTO",
    descricao: "Isento",
  },
  PAGO_CADIN: {
    nomeEnum: "PAGO_CADIN",
    descricao: "Pago Cadin",
  },
  ADM_CADIN: {
    nomeEnum: "ADM_CADIN",
    descricao: "Administrado Cadin",
  },
  DELETADO: {
    nomeEnum: "DELETADO",
    descricao: "Deletado",
  },
  AGRUPADO: {
    nomeEnum: "AGRUPADO",
    descricao: "Agrupado",
  },
  EM_ABERTO: {
    nomeEnum: "EM_ABERTO",
    descricao: "Em Aberto",
  },
  PAGO_MANUAL: {
    nomeEnum: "PAGO_MANUAL",
    descricao: "Pago Manual",
  },
  PAGO_RETORNO_BANCO: {
    nomeEnum: "PAGO_RETORNO_BANCO",
    descricao: "Pago Retorno Banco",
  },
  PAGO_PIX: {
    nomeEnum: "PAGO_PIX",
    descricao: "Pago Pix",
  },
  NAO_ADM_CR5: {
    nomeEnum: "NAO_ADM_CR5",
    descricao: "Nao administrado CR5",
  },
  ESTORNADO: {
    nomeEnum: "ESTORNADO",
    descricao: "Estornado",
  },
  EMABERTO: {
  nomeEnum: "EM ABERTO",
  descricao: "Em Aberto",
  }
};
