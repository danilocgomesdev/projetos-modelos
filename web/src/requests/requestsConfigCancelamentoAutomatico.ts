import { AxiosInstance } from "axios";
import { ConfigCancelamentoAutomaticoDTO } from "../models/DTOs/Outros/ConfigCancelamentoAutomaticoDTO.ts";
import { PageQuery } from "../models/request/PageQuery";
import { PageResult } from "../models/response/PageResult";
import { removeValoresVazios } from "../utils/requestUtil.js.ts";

export interface FetchConfigCancelamentoAutomaticoPaginadoParams extends PageQuery {
  idSistema?: number | null;
}

export function fetchConfigCancelamentoAutomaticoPaginado(
  params: FetchConfigCancelamentoAutomaticoPaginadoParams,
  axios: AxiosInstance
): Promise<PageResult<ConfigCancelamentoAutomaticoDTO>> {
  return axios
    .get<PageResult<ConfigCancelamentoAutomaticoDTO>>(
      "/configCancelamentoAutomaticoContrato/consultaConfiguracao",
      {
        params: removeValoresVazios(params),
      }
    )
    .then((res) => res.data);
}

export interface FetchAlterarConfigParams {
  id?: number;
  cancelamentoAutomatico?: boolean;
}

export function fetchAlterarConfig(
  params: FetchAlterarConfigParams,
  axios: AxiosInstance
): Promise<void> {
  return axios
    .put(`/configCancelamentoAutomaticoContrato/alterarConfig/${params.id}`, params)
    .then(() => Promise.resolve());
}
