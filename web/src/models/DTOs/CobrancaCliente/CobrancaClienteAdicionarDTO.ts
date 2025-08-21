import { SituacaoCobrancaCliente } from "../Cobrancas/SituacaoCobrancaCliente";

export interface CobrancaClienteAdicionarDTO {
  numParcela: number;
  numParcelaProtheus: number;
  situacao: SituacaoCobrancaCliente;
  valorCobranca: number;
  dataVencimento: Date;
  idCobrancaCliente: number;
  codigoUnidade: number;
  idInterface: number;
  idSistema: number;
  nomeEntidade: string;
  sacadoNome: string;
  sacadoCpfCnpj: string;
  idContrato: number;
  idBoleto: number;
  contratoProtheus: string;
}
