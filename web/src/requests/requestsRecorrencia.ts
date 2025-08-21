import { AxiosInstance } from "axios";
import { RecorrenciaCompletaDTO } from "../models/DTOs/Recorrencia/RecorrenciaCompletaDTO.ts";
import { RecorrenciaDTO } from "../models/DTOs/Recorrencia/RecorrenciaDTO.ts";
import { PageQuery } from "../models/request/PageQuery";
import { PageResult } from "../models/response/PageResult";
import { removeValoresVazios } from "../utils/requestUtil.js.ts";

export interface FetchRecorrenciaPaginadoParams extends PageQuery {
  cpfCnpj?: string | null;
  nome?: string;
  idRecorrencia?: string | null;
  dataPagamento?: string;
  dataInicioRecorrencia?: string;
  dataFimRecorrencia?: string;
  tid?: string;
}

export function fetchRecorrenciaPaginado(
  params: FetchRecorrenciaPaginadoParams,
  axios: AxiosInstance
): Promise<PageResult<RecorrenciaDTO>> {
  return axios
    .get<PageResult<RecorrenciaDTO>>("/cielo-ecommerce/paginado", {
      params: removeValoresVazios(params),
    })
    .then((res) => res.data);
}

export function fetchRecorrenciaPaginadoCompleto(
  params: FetchRecorrenciaPaginadoParams,
  axios: AxiosInstance
): Promise<PageResult<RecorrenciaCompletaDTO>> {
  return axios
    .get<PageResult<RecorrenciaCompletaDTO>>("/cielo-ecommerce/paginado-completo", {
      params: removeValoresVazios(params),
    })
    .then((res) => res.data);
}

export interface DeletarRecorrenciaParams {
  idRecorrencia?: string;
  idEntidade?: number;
}

export async function cancelarRecorrencia(
  params: DeletarRecorrenciaParams,
  axios: AxiosInstance
): Promise<void> {
  await axios
    .delete(`/cielo-ecommerce/entidade/${params.idEntidade}/recorrencia/${params.idRecorrencia}`)
    .then(() => Promise.resolve());
}
