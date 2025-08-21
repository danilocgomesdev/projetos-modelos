import { AxiosInstance } from "axios";
import { PaisesDTO } from "../models/DTOs/Outros/PaisesDTO.ts";
import { PageQuery } from "../models/request/PageQuery.ts";
import { PageResult } from "../models/response/PageResult.ts";
import { removeValoresVazios } from "../utils/requestUtil.js.ts";

export interface FetchPaisParams extends PageQuery {
  idPais?: number;
  descricao?: string;
  codigoBCB?: number;
  codigoPais?: number;
}

export function fetchPaisPaginadas(
  params: FetchPaisParams,
  axios: AxiosInstance
): Promise<PageResult<PaisesDTO>> {
  return axios
    .get<PageResult<PaisesDTO>>("/cliente/pais/paginado", { params: removeValoresVazios(params) })
    .then((res) => res.data);
}

export function fetchAllPais(axios: AxiosInstance): Promise<PaisesDTO[]> {
  return axios.get<PaisesDTO[]>("/cliente/pais").then((res) => res.data);
}
