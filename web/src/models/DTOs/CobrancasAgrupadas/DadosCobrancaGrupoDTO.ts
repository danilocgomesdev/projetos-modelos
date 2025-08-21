import { SituacaoCobrancaCliente } from "../Cobrancas/SituacaoCobrancaCliente";

export interface DadosCobrancaGrupoDTO {
  idUnidade: number;
  contrato: number;
  idSistema: number;
  protheusContrato: string;
  parcela: number;
  consumidorNome: string;
  valorCobranca: number;
  nossoNumero: string;
  situacao: SituacaoCobrancaCliente;
}
