import { AxiosInstance } from "axios";
import { ImpressorasDTO } from "../models/DTOs/Outros/ImpressorasDTO.ts";
import { PageQuery } from "../models/request/PageQuery.ts";
import { PageResult } from "../models/response/PageResult.ts";
import { removeValoresVazios } from "../utils/requestUtil.js.ts";

export interface FetchImpressorasParams extends PageQuery {
  idImpressora?: number | null;
  descricao?: string;
  modelo?: string;
  ipMaquina?: string;
  idUnidade: number;
}

export function fetchImpressorasPaginado(
  params: FetchImpressorasParams,
  axios: AxiosInstance
): Promise<PageResult<ImpressorasDTO>> {
  return axios
    .get<PageResult<ImpressorasDTO>>("/impressora/paginado", {
      params: removeValoresVazios(params),
    })
    .then((res) => res.data);
}

export interface FetchCriarImpressoraParams {
  idUnidade: number;
  descricao: string;
  modelo: string;
  ipMaquina: string;
  gaveta: boolean;
  guilhotina: boolean;
  tipoPorta: string;
  porta: number;
}

export function fetchCriarImpressora(
  params: FetchCriarImpressoraParams,
  axios: AxiosInstance
): Promise<void> {
  return axios.post("/impressora", params).then(() => Promise.resolve());
}

export interface FetchAlterarImpressoraParams {
  idImpressora: number;
  idUnidade: number;
  descricao: string;
  modelo: string;
  ipMaquina: string;
  gaveta: boolean;
  guilhotina: boolean;
  tipoPorta: string;
  porta: number;
}

export function fetchAlterarImpressora(
  params: FetchAlterarImpressoraParams,
  axios: AxiosInstance
): Promise<void> {
  return axios.put(`/impressora/${params.idImpressora}`, params).then(() => Promise.resolve());
}

export interface FetchDeleteImpressoraParams {
  idImpressora: number;
}

export function fetchDeleteImpressora(
  params: FetchDeleteImpressoraParams,
  axios: AxiosInstance
): Promise<void> {
  return axios.delete(`/impressora/${params.idImpressora}`).then(() => Promise.resolve());
}
