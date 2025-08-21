import { UnidadeDTO } from "../Outros/UnidadeDTO";
export interface SituacaoClienteComUnidadeDTO {
  situacaoCliente: SituacaoClienteDTO;
  unidade: UnidadeDTO;
}

export interface SituacaoClienteDTO {
  statusInterface: string;
  idSistema: number;
  clienteDescricao: string;
  cpfCnpj: string;
  codUnidade: string;
  unidadeDescricao: string;
  entidade: string;
  dtInicioCobranca: string;
  dtFimCobranca: string;
  contrato: number;
  parcela: number;
  vlCobranca: number;
  vlPago: number | null;
  vlEstorno: number | null;
  dtVencimento: string;
  dtPagamento: string | null;
  dtCredito: string | null;
  dtEstorno: string | null;
  dtCancelamento: string | null;
  cbcSituacao: string;
  objetoContrato: string | null;
  idCobrancaCliente: number;
  nossoNumero: string;
  rede: string;

  consumidorNome: string;
}
