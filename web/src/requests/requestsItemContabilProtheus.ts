import { AxiosInstance } from "axios";
import { ItemContabilProtheusDTO } from "../models/DTOs/Contabil/ItemContabilProtheusDTO";
import { PageQuery } from "../models/request/PageQuery";
import { PageResult } from "../models/response/PageResult";

export interface FetchItemContabilProtheusPaginadoParams extends PageQuery {
  itemContabil?: string;
  itemContabilDescricao?: string;
  entidade?: string;
}

export function fetchItemContabilProtheusPaginado(
  params: FetchItemContabilProtheusPaginadoParams,
  axios: AxiosInstance
): Promise<PageResult<ItemContabilProtheusDTO>> {
  return axios
    .get<PageResult<ItemContabilProtheusDTO>>("/protheus/item-contabil/paginado", {
      params,
    })
    .then((res) => res.data);
}
