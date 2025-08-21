import { MensagemDTO } from "./MensagemDTO";

export interface ControleNegocialDTO {
  origemRetorno: string;
  codRetorno: string;
  mensagens: MensagemDTO[];
}