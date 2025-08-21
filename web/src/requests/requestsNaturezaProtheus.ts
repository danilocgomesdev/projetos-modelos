import { AxiosInstance } from "axios";
import { NaturezaProtheusDTO } from "../models/DTOs/Protheus/NaturezaProtheusDTO";
import { PageQuery } from "../models/request/PageQuery";
import { PageResult } from "../models/response/PageResult";

export interface FetchNaturezaProtheusPaginadoParams extends PageQuery {
  natureza?: string;
  descricaoNatureza?: string;
}

export function fetchNaturezaProtheusPaginado(
  params: FetchNaturezaProtheusPaginadoParams,
  axios: AxiosInstance
): Promise<PageResult<NaturezaProtheusDTO>> {
  return axios
    .get<PageResult<NaturezaProtheusDTO>>("/protheus/natureza/paginado", {
      params,
    })
    .then((res) => res.data);
}
