import { AxiosInstance } from "axios";
import { AgenciaDTO } from "../models/DTOs/AgenciaBancoConta/AgenciaDTO.ts";
import { PageQuery } from "../models/request/PageQuery";
import { PageResult } from "../models/response/PageResult";
import { removeValoresVazios } from "../utils/requestUtil.js.ts";

export interface FetchAgenciasPaginadoParams extends PageQuery {
  id?: number | null;
  nome?: string;
  numero?: string;
  cidade?: string;
  cnpj?: string;
  nomeBanco?: string;
}

export function fetchAgenciasPaginado(
  params: FetchAgenciasPaginadoParams,
  axios: AxiosInstance
): Promise<PageResult<AgenciaDTO>> {
  return axios
    .get<PageResult<AgenciaDTO>>("/agencias", { params: removeValoresVazios(params) })
    .then((res) => res.data);
}

export interface FetchDeleteAgenciasParams {
  idAgencia: number;
}

export function fetchDeleteAgencias(
  params: FetchDeleteAgenciasParams,
  axios: AxiosInstance
): Promise<void> {
  return axios.delete(`/agencias/${params.idAgencia}`).then(() => Promise.resolve());
}

interface FetchCriarAgenciasParams {
  idBanco: number;
  cnpj: string;
  digitoVerificador?: string;
  cidade: string;
  numero: string;
  nome: string;
}

export function fetchCriarAgencias(
  params: FetchCriarAgenciasParams,
  axios: AxiosInstance
): Promise<void> {
  return axios.post("/agencias", params).then(() => Promise.resolve());
}

interface FetchAtualizarAgenciasParams {
  idAgencia: number;
  idBanco: number;
  cnpj: string;
  digitoVerificador?: string;
  cidade: string;
  numero: string;
  nome: string;
}

export function fetchAtualizarAgencias(
  params: FetchAtualizarAgenciasParams,
  axios: AxiosInstance
): Promise<void> {
  return axios.put(`/agencias/${params.idAgencia}`, params).then(() => Promise.resolve());
}

// Função para buscar um relatório em PDF
export function fetchRelatorioPDFAgencias(axios: AxiosInstance): Promise<Blob> {
  return axios
    .get("/agencias/relatorio/pdf", {
      responseType: "arraybuffer",
    })
    .then((response) => {
      const blob = new Blob([response.data], { type: "application/pdf" });
      return blob;
    });
}
