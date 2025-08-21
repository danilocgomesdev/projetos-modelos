import { FormaPagamentoSimplificado } from "./FormasDePagamentoSimplificas";
import { UnidadeDTO } from "./UnidadeDTO";

export interface PagamentoNaoBaixadoComUnidadeDTO {
  pagamentoNaoBaixado: PagamentoNaoBaixadoDTO;
  unidade: UnidadeDTO;
}

export interface PagamentoNaoBaixadoDTO {
  formaPagamento: FormaPagamentoSimplificado;
  dataPagamento: Date;
  sacadoCpfCnpj: string;
  sacadoNome: string;
  idUnidade: number;
  valorOperacao: number;
  parcelamento: number;
  idOperadorRecebimento: number | null;
  idPedido: number | null;

  cobrancas: InfoCobrancaPagamentoNaoBaixadoDTO[];
}

export interface InfoCobrancaPagamentoNaoBaixadoDTO {
  idCobrancaCliente: number;
  contId: number;
  idSistema: number;
  numeroParcela: number;
  valorCobranca: number;
  situacaoParcela: string;
  statusContrato: string;
}
