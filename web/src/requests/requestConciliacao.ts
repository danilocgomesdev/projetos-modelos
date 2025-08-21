import { AxiosInstance } from "axios";
import { ConciliacaoHitsDTO } from "../models/DTOs/Hits/ConciliacaoHitsDTO.js";
import { PageQuery } from "../models/request/PageQuery";
import { PageResult } from "../models/response/PageResult";
import { removeValoresVazios } from "../utils/requestUtil.js";

export interface FetchConciliacaoPaginadoParams extends PageQuery {
  idPagamento?: number | null;
  contId?: number | null;
  numeroParcela?: number | null;
  criadoCr5?: boolean | null;
  criadoProtheus?: boolean | null;
  baixadoProtheus?: boolean | null;
  dataPagamento?: Date | null;
}

export async function fetchConciliacaoPaginado(
  params: FetchConciliacaoPaginadoParams,
  axios: AxiosInstance
): Promise<PageResult<ConciliacaoHitsDTO>> {
  const res = await axios.get<PageResult<ConciliacaoHitsDTO>>("/conciliacao/hits/paginado", {
    params: removeValoresVazios(params),
  });
  return res.data;
}

export interface FetchConciliacaoHitsParams {
  data: string;
}

export function fetchConciliacaoHits(
  params: FetchConciliacaoHitsParams,
  axios: AxiosInstance
): Promise<void> {
  return axios
    .post(`/conciliacao/hits/contrato/pre-validar-gerar-receber?data=${params.data}`)
    .then(() => Promise.resolve());
}
