export interface StatusInterfaceDTO {
  descricao: string;
  nomeEnum: string;
}

export interface StatusInterfaceType {
  ABERTO: StatusInterfaceDTO;
  MEDICAO: StatusInterfaceDTO;
  COBRADO: StatusInterfaceDTO;
  AGRUPADO: StatusInterfaceDTO;
  PAGO: StatusInterfaceDTO;
  CANCELADO: StatusInterfaceDTO;
  CANCELAR: StatusInterfaceDTO;
  EXCLUIDO: StatusInterfaceDTO;
  EXTINGUIDO: StatusInterfaceDTO;
  EXTINGUIR: StatusInterfaceDTO;
  NAO_ADM_CR5: StatusInterfaceDTO;
  SUBSTITUIDO: StatusInterfaceDTO;
  SUBSTITUIR: StatusInterfaceDTO;
  FINANCIADO: StatusInterfaceDTO;
  NAO_EFETIVADO: StatusInterfaceDTO;
}

export type StatusInterface = keyof StatusInterfaceType;

/**
 * Aqui, label é um nome amigável e value é o valor no Enum StatusInterface no cr5-webservices-v2.
 * Constante feita para ser usada como opção de SelectControl
 */
export const formasDePagamentoValues: StatusInterfaceType = {
  ABERTO: {
    nomeEnum: "ABERTO",
    descricao: "Aberto",
  },
  MEDICAO: {
    nomeEnum: "MEDICAO",
    descricao: "Medicao",
  },
  COBRADO: {
    nomeEnum: "COBRADO",
    descricao: "Cobrado",
  },
  AGRUPADO: {
    nomeEnum: "AGRUPADO",
    descricao: "Agrupado",
  },
  PAGO: {
    nomeEnum: "PAGO",
    descricao: "Pago",
  },
  CANCELADO: {
    nomeEnum: "CANCELADO",
    descricao: "Cancelado",
  },
  CANCELAR: {
    nomeEnum: "CANCELAR",
    descricao: "Cancelar",
  },
  EXCLUIDO: {
    nomeEnum: "EXCLUIDO",
    descricao: "Excluido",
  },
  EXTINGUIDO: {
    nomeEnum: "EXTINGUIDO",
    descricao: "Extinguido",
  },
  EXTINGUIR: {
    nomeEnum: "EXTINGUIR",
    descricao: "Extinguir",
  },
  NAO_ADM_CR5: {
    nomeEnum: "NAO_ADM_CR5",
    descricao: "Não Administrado CR5",
  },
  SUBSTITUIDO: {
    nomeEnum: "SUBSTITUIDO",
    descricao: "Substituido",
  },
  SUBSTITUIR: {
    nomeEnum: "SUBSTITUIR",
    descricao: "Substituir",
  },
  FINANCIADO: {
    nomeEnum: "FINANCIADO",
    descricao: "Financiado",
  },
  NAO_EFETIVADO: {
    nomeEnum: "NAO_EFETIVADO",
    descricao: "Não Efetivado",
  },
};
