import { AxiosInstance } from "axios";
import { ContaContabilProtheusDTO } from "../models/DTOs/Contabil/ContaContabilProtheusDTO";
import { PageQuery } from "../models/request/PageQuery";
import { PageResult } from "../models/response/PageResult";

export interface FetchContaContabilProtheusPaginadoParams extends PageQuery {
  contaContabil?: string;
  contaContabilDescricao?: string;
  entidade?: string;
}

export function fetchContaContabilProtheusPaginado(
  params: FetchContaContabilProtheusPaginadoParams,
  axios: AxiosInstance
): Promise<PageResult<ContaContabilProtheusDTO>> {
  return axios
    .get<PageResult<ContaContabilProtheusDTO>>("/protheus/conta-contabil/paginado", { params })
    .then((res) => res.data);
}
