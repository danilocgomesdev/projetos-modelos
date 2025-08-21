import { SituacaoCobrancaCliente } from "../Cobrancas/SituacaoCobrancaCliente.ts";

import { CobrancaUnidadeDTO } from "./CobrancaUnidadeDTO.ts";

export interface CobrancaParaContratoEmRedeDTO {
  idCobrancaCliente: number;
  idCobrancaAgrupada: number;
  idInterface: number;
  cor: string;
  filialERP: string;
  descricaoUnidade: string;
  contratoProtheus: string;
  contratoProducao: number;
  dataVencimento: Date;
  parcela: number;
  situacao: SituacaoCobrancaCliente;
  cpfCnpjResponsavelFinanceiro: string;
  nomeResponsavelFinanceiro: string;
  nomeConsumidor: string;
  valorCobranca: number;
  jurosEMultas: number;
  juros: number;
  multa: number;
  descontos: number;
  valorPromocao: number;
  valorBolsa: number;
  valorDescontoComercial: number;
  cbcValorAgente: number;
  valorTotalParcela: number;
  valorPago: number;

  unidade: CobrancaUnidadeDTO;
}
