import { AxiosInstance } from "axios";
import { TerminaisTEFDTO } from "../models/DTOs/Tef/TerminaisTEFDTO.ts";
import { PageQuery } from "../models/request/PageQuery";
import { PageResult } from "../models/response/PageResult";
import { removeValoresVazios } from "../utils/requestUtil.js.ts";

export interface FetchTerminaisTEFPaginadoParams extends PageQuery {
  smpVersao?: number | null;
  unidCodigo?: string;
  entidadeIdLocal?: number | null;
  smpDtAtualizacao?: string;
}

export function fetchTerminaisTEFPaginado(
  params: FetchTerminaisTEFPaginadoParams,
  axios: AxiosInstance
): Promise<PageResult<TerminaisTEFDTO>> {
  return axios
    .get<PageResult<TerminaisTEFDTO>>("/cartao/paginado", { params: removeValoresVazios(params) })
    .then((res) => res.data);
}
