import { AxiosInstance } from "axios";
import { UnidadeDTO } from "../models/DTOs/Outros/UnidadeDTO.ts";
import { PageQuery } from "../models/request/PageQuery";
import { PageResult } from "../models/response/PageResult";
import { removeValoresVazios } from "../utils/requestUtil.js.ts";

export function fetchAllUnidades(axios: AxiosInstance): Promise<UnidadeDTO[]> {
  return axios.get<UnidadeDTO[]>("/unidades/todas-as-unidades").then((res) => res.data);
}

export interface FetchUnidadesPaginadoParams extends PageQuery {
  nome?: string;
  cidade?: string;
  codigo?: string;
}

export function fetchUnidadesPaginadas(
  params: FetchUnidadesPaginadoParams,
  axios: AxiosInstance
): Promise<PageResult<UnidadeDTO>> {
  return axios
    .get<PageResult<UnidadeDTO>>("/unidades", { params: removeValoresVazios(params) })
    .then((res) => res.data);
}

export function fetchUnidade(axios: AxiosInstance, idUnidade: number): Promise<UnidadeDTO> {
  if (idUnidade < 0) {
    return Promise.reject(() => new Error("Não existe unidade com id negativo"));
  }

  return axios.get<UnidadeDTO>(`/unidades/${idUnidade}`).then((res) => res.data);
}
