export interface ConciliacaoHitsDTO {
  sistema: number;
  entidade: string;
  criadoCr5: boolean;
  motivoFalha: string;
  dataPagamento: number; // timestamp em milissegundos
  numeroParcela: number;
  valorPago: number;
  contratoProtheus: string;
  contrato: number;
  sacadoNome: string;
  sacadoCpfCnpj: string;
  dataInclusaoProtheus: number;
  dataAlteracaoProtheus: number | null;
}
