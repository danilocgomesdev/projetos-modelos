export interface IntegraProtheusDTO {
  letra: string;
  descricao: string;
  nomeEnum: string;
}

interface IntegraProtheusType {
  CONTRATO_PROTHEUS: IntegraProtheusDTO;
  INTEGRACAO_FINANCEIRA: IntegraProtheusDTO;
  ISENTO: IntegraProtheusDTO;
  VENDA_PARCELADA: IntegraProtheusDTO;
  VENDA_DIRETA: IntegraProtheusDTO;
  VENDA_AVULSA_PARCELADA: IntegraProtheusDTO;
  CONTRATO_PROTHEUS_25: IntegraProtheusDTO;
}

export type IntegraProtheus = keyof IntegraProtheusType;

export const integraProtheusValues: IntegraProtheusType = {
  CONTRATO_PROTHEUS: {
    letra: "C",
    descricao: "Contrato Protheus",
    nomeEnum: "CONTRATO_PROTHEUS",
  },
  INTEGRACAO_FINANCEIRA: {
    letra: "F",
    descricao: "Integração Financeira",
    nomeEnum: "INTEGRACAO_FINANCEIRA",
  },
  ISENTO: {
    letra: "I",
    descricao: "Título isento",
    nomeEnum: "ISENTO",
  },
  VENDA_PARCELADA: {
    letra: "P",
    descricao: "Venda Parcelada",
    nomeEnum: "VENDA_PARCELADA",
  },
  VENDA_DIRETA: {
    letra: "V",
    descricao: "Venda Direta",
    nomeEnum: "VENDA_DIRETA",
  },
  VENDA_AVULSA_PARCELADA: {
    letra: "A",
    descricao: "Venda Avulsa Parcelada",
    nomeEnum: "VENDA_AVULSA_PARCELADA",
  },
  CONTRATO_PROTHEUS_25: {
    letra: "T",
    descricao: "Contrato Protheus 25",
    nomeEnum: "CONTRATO_PROTHEUS_25",
  },
};
