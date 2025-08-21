import { DescontoDTO } from "./DescontoDTO";
import { JurosMoraDTO } from "./JurosMoraDTO";
import { MultaDTO } from "./MultaDTO";
import { PagadorDTO } from "./PagadorDTO";
import { PagamentoDTO } from "./PagamentoDTO";
import { PosVencimentoDTO } from "./PosVencimentoDTO";

export interface TituloDTO {
  numeroDocumento: string;
  dataVencimento: string;
  valor: string;
  tipoEspecie: string;
  flagAceite: string;
  dataEmissao: string;
  jurosMora: JurosMoraDTO;
  valorAbatimento: string;
  posVencimento: PosVencimentoDTO;
  codigoMoeda: string;
  pagador: PagadorDTO;
  multa: MultaDTO;
  descontos: DescontoDTO[];
  valorIof: string;
  identificacaoEmpresa: string;
  pagamento: PagamentoDTO;
  codigoBarras: string;
  linhaDigitavel: string;
  url: string;
}