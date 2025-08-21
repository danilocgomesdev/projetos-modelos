import { AxiosInstance } from "axios";
import { SistemaDTO } from "../models/DTOs/Outros/SistemaDTO.ts";
import { PageQuery } from "../models/request/PageQuery.ts";
import { PageResult } from "../models/response/PageResult.ts";
import { removeValoresVazios } from "../utils/requestUtil.js.ts";

export interface FetchSistemaParams extends PageQuery {
  sistema?: string;
  descricao?: string;
  descricaoReduzida?: string;
}

export function fetchSistemasPaginadas(
  params: FetchSistemaParams,
  axios: AxiosInstance
): Promise<PageResult<SistemaDTO>> {
  return axios
    .get<PageResult<SistemaDTO>>("/sistemas/paginado", { params: removeValoresVazios(params) })
    .then((res) => res.data);
}

export function fetchAllSistemas(axios: AxiosInstance): Promise<SistemaDTO[]> {
  return axios.get<SistemaDTO[]>("/sistemas").then((res) => res.data);
}
