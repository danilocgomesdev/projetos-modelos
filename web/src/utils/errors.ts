import { AxiosError } from "axios";
import { TipoNotificacao } from "../App/hooks/useFuncaoNotificacao";
import { ErroCancelaAcao } from "../models/errors/ErroCancelaAcao";

export function getMensagemDeErroOuProcureSuporte(error: unknown): string {
  const procureOSuporte = "Ocorreu um erro inesperado! Procure o suporte.";

  if (error instanceof ErroCancelaAcao) {
    return error.message;
  }

  if (ehErroDoAxios(error) && error.response && temCampoDescricao(error.response.data)) {
    return error.response.data.description.substring(0, 500);
  } else {
    if (error instanceof Error) {
      return `${procureOSuporte}: ${error.message.substring(0, 500)}`;
    } else {
      return procureOSuporte;
    }
  }
}

export function ehErroDoAxios(obj: unknown): obj is AxiosError {
  return obj instanceof AxiosError;
}

function temCampoDescricao(obj: unknown): obj is { description: string } {
  return (
    typeof obj === "object" &&
    obj !== null &&
    "description" in obj &&
    typeof obj.description === "string"
  );
}


export function getTipoPorCodigoRetorno(error: unknown): TipoNotificacao {
  
  if (ehErroDoAxios(error) && error.response) {
    const status = error.response?.status;

    if (status === 422) {
      return "warning";
    } else {
      return "error";
    }

  }
    
  return "error";
}

export function getTituloPorCodigoRetorno(error: unknown): string {
  
  if (ehErroDoAxios(error) && error.response) {
    const status = error.response?.status;

    if (status === 422) {
      return "AVISO";
    } else {
      return "ERRO!";
    }

  }
    
  return "ERRO!";
}
