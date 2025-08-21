import { AxiosInstance } from "axios";
import { CobrancasDTO } from "../models/DTOs/Cobrancas/CobrancasDTO.js";
import { PageQuery } from "../models/request/PageQuery";
import { PageResult } from "../models/response/PageResult";
import { removeValoresVazios } from "../utils/requestUtil.js";

export interface FetchCobrancasPaginadoParams extends PageQuery {
  idSistema?: number | null;
  idUnidade?: number | null;
  ano?: number | null;
  numeroParcela?: string;
  statusInterface?: string;
  dataInicioCobranca?: string; // LocalDate → string no formato "yyyy-MM-dd"
  dataFimCobranca?: string;
  dataInicioVigencia?: string;
  dataFimVigencia?: string;
  contratoInicio?: string;
  contratoFim?: string;
  contratoProtheusInicio?: string;
  contratoProtheusFim?: string;
}

export function fetchCobrancasPaginado(
  params: FetchCobrancasPaginadoParams,
  axios: AxiosInstance
): Promise<PageResult<CobrancasDTO>> {
  return axios
    .get<PageResult<CobrancasDTO>>("/cobrancas/paginado", {
      params: removeValoresVazios(params),
    })
    .then((res) => res.data);
}
