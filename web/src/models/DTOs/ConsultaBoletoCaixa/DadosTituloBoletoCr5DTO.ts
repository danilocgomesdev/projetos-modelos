import { StatusInterface } from "../Cobrancas/StatusInterface";

export interface DadosTituloBoletoCr5DTO {
  entidade: string;
  vinculo: "REMOVIDO" | "ATIVO";
  unidade: number;
  responsavelFinanceiro: string;
  cpfCnpj: string;
  protheus: string;
  status: StatusInterface;
  contrato: number;
  parcela: number;
  situacao: string;
  cobranca: number;
  descVenc: number;
  abatimento: number;
  fiegOvg: number;
  juros: number;
  multa: number;
  pago: number;
  vencimento: string;
  pagamento: string;
  credito: string;
  idCbc: number;
  recno: number;
  grupo: number;
  grupoCancelado: number | null;
  boletoAtivo: string;
}
