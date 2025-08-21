import { SituacaoCobrancaCliente } from "../Cobrancas/SituacaoCobrancaCliente";
import { StatusInterface } from "../Cobrancas/StatusInterface";

export interface CobrancasGrupoDTO {
  contrato?: number;
  dataVencimento: Date;
  dataPagamento: Date;
  parcela?: number;
  situacao: SituacaoCobrancaCliente;
  nossoNumero: string;
  cpfCnpj: string;
  sacadoNome: string;
  valorCobranca: number;
  totalDebito: number;
  totalDesconto: number;
  valorTotalParcela: number;
  notaFiscal: string;
  dataEmissaoNotaFiscal: Date;
  avisoLancamentoNota: string;
  dataAvisoLancamentoNota: Date;
  status: StatusInterface;
  idSistema: number;
  idUnidadeContrato: number;
  idCobrancaAgrupada: number;
  idCobrancaCliente: number;
  idInterface: number;
  terminoVigenciaCobranca: Date;
  idGrupoCancelado: number;
}
