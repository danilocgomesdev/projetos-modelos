import { AxiosInstance } from "axios";
import { ExportarParaCadinDTO } from "../models/DTOs/Cadin/ExportarParaCadinDTO.ts";
import { PageQuery } from "../models/request/PageQuery";
import { PageResult } from "../models/response/PageResult";
import { removeValoresVazios } from "../utils/requestUtil.js.ts";

export interface FetchExportarParaCadinPaginadoParams extends PageQuery {
  contrato?: number | null;
  nome?: string;
  cpfCnpj?: string;
  nossoNumero?: string;
  dtVencimentoInicio?: string;
  dtVencimentoFim?: string;
  sistema?: number | null;
}

export function fetchExportarParaCadinPaginado(
  params: FetchExportarParaCadinPaginadoParams,
  axios: AxiosInstance
): Promise<PageResult<ExportarParaCadinDTO>> {
  return axios
    .get<PageResult<ExportarParaCadinDTO>>("/cadin/consulta-exportar-para-cadin", {
      params: removeValoresVazios(params),
    })
    .then((res) => res.data);
}

export interface FetchExportarIdCobrancasClientesParams {
  idCobrancasClientes: number;
}

export function fetchExportarIdCobrancasClientes(
  params: FetchExportarIdCobrancasClientesParams,
  axios: AxiosInstance
): Promise<void> {
  return axios
    .put(`/cadin/exportar-para-cadin/${params.idCobrancasClientes}`)
    .then(() => Promise.resolve());
}
