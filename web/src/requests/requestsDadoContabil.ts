import { AxiosInstance } from "axios";
import { DadoContabilDTO } from "../models/DTOs/Contabil/DadoContabilDTO";
import { PageQuery } from "../models/request/PageQuery";
import { PageResult } from "../models/response/PageResult";

export interface FetchDadoContabilPaginadoParams extends PageQuery {
  idDadoContabil?: number | null;
  contaContabil?: string;
  contaContabilDescricao?: string;
  itemContabil?: string;
  itemContabilDescricao?: string;
  natureza?: string;
  naturezaDescricao?: string;
  idEntidade?: number | null;
  idOperadorInclusao?: number;
  status?: "ATIVO" | "INATIVO";
}

export function fetchDadoContabilPaginado(
  params: FetchDadoContabilPaginadoParams,
  axios: AxiosInstance
): Promise<PageResult<DadoContabilDTO>> {
  return axios
    .get<PageResult<DadoContabilDTO>>("/dado-contabil/paginado", { params })
    .then((res) => res.data);
}

interface CriarDadoContabilDTO {
  contaContabil: string;
  contaContabilDescricao: string;
  itemContabil: string;
  itemContabilDescricao: string;
  natureza: string;
  naturezaDescricao: string;
  idEntidade: number;
  idOperadorInclusao: number;
  idOperadorAlteracao?: number;
}

export function fetchCriarDadoContabil(
  params: CriarDadoContabilDTO,
  axios: AxiosInstance
): Promise<void> {
  return axios.post("/dado-contabil", params).then(() => Promise.resolve());
}

interface AlterarDadoContabilDTO {
  idDadoContabil: number;
  contaContabil: string;
  contaContabilDescricao: string;
  itemContabil: string;
  itemContabilDescricao: string;
  natureza: string;
  naturezaDescricao: string;
  idEntidade: number;
  idOperadorInclusao?: number;
  idOperadorAlteracao?: number;
}

export function fetchAtualizarDadoContabil(
  params: AlterarDadoContabilDTO,
  axios: AxiosInstance
): Promise<void> {
  return axios.put(`/dado-contabil/${params.idDadoContabil}`, params).then(() => Promise.resolve());
}

export function fetchDeleteDadoContabil(
  idDadoContabil: number,
  axios: AxiosInstance
): Promise<void> {
  return axios.delete(`/dado-contabil/${idDadoContabil}`).then(() => Promise.resolve());
}
