import { AxiosInstance } from "axios";
import { VisaoServicosDTO } from "../models/DTOs/Visoes/VisaoServicosDTO.ts";
import { PageQuery } from "../models/request/PageQuery.ts";
import { PageResult } from "../models/response/PageResult.ts";
import { removeValoresVazios } from "../utils/requestUtil.js.ts";

export interface FetchVisaoServicosParams extends PageQuery {
  idProduto?: number;
  idSistema?: number;
  nome?: string;
  codProdutoProtheus?: string;
}

export function fetchVisaoServicosPaginadas(
  params: FetchVisaoServicosParams,
  axios: AxiosInstance
): Promise<PageResult<VisaoServicosDTO>> {
  return axios
    .get<PageResult<VisaoServicosDTO>>("/visao-servicos/paginado", {
      params: removeValoresVazios(params),
    })
    .then((res) => res.data);
}

export function fetchVisaoServicosByIdAndSistema(
  idProduto: number,
  idSistema: number,
  axios: AxiosInstance
): Promise<VisaoServicosDTO> {
  return axios
    .get<VisaoServicosDTO>(`/visao-servicos/sistema/${idSistema}/produto/${idProduto}`)
    .then((res) => res.data);
}
