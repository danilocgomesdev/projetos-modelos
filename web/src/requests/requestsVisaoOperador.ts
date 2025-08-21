import { AxiosInstance } from "axios";
import { VisaoOperadorDTO } from "../models/DTOs/Visoes/VisaoOperadorDTO.ts";
import { PageQuery } from "../models/request/PageQuery.ts";
import { PageResult } from "../models/response/PageResult.ts";
import { removeValoresVazios } from "../utils/requestUtil.js.ts";

export interface FetchVisaoOperadorPaginadoParams extends PageQuery {
  idOperador?: number;
  usuario?: string;
  nome?: string;
  email?: string;
  matricula?: number;
  entidade?: number;
  idPessoa?: number;
}

export function fetchVisaoOperadorPaginado(
  params: FetchVisaoOperadorPaginadoParams,
  axios: AxiosInstance
): Promise<PageResult<VisaoOperadorDTO>> {
  return axios
    .get<PageResult<VisaoOperadorDTO>>("/visao-operador/paginado", {
      params: removeValoresVazios(params),
    })
    .then((res) => res.data);
}

export function fetchVisaoOperadorList(
  params: number[],
  axios: AxiosInstance
): Promise<VisaoOperadorDTO[]> {
  return axios
    .post<VisaoOperadorDTO[]>("/visao-operador/paginado/list", params)
    .then((res) => res.data);
}
