import { ConsultaBoletoDTO } from "./ConsultaBoletoDTO";
import { ControleNegocialDTO } from "./ControleNegocialDTO";
import { HeaderDTO } from "./HeaderDTO";

export interface ServicoSaidaDTO {
  header: HeaderDTO;
  codRetorno: string;
  origemRetorno: string;
  msgRetorno: string;
  dados: {
    controleNegocial: ControleNegocialDTO;
    consultaBoleto: ConsultaBoletoDTO;
  };
}