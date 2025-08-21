import { AxiosInstance } from "axios";
import { ProdutoDadoContabilDTO } from "../models/DTOs/Produtos/ProdutoDadoContabilDTO.js";
import { PageQuery } from "../models/request/PageQuery.js";
import { PageResult } from "../models/response/PageResult.js";
import { removeValoresVazios } from "../utils/requestUtil.js.js";

export interface FetchProdutoDadoContabilParams extends PageQuery {
  idProdutoDadoContabil?: number | null;
  produto?: string | null;
  preco?: number | null;
  status?: string | null;
  dadoContabil?: number | null;
  idProduto?: number | null;
  idSistema?: number | null;
  dmed?: string | null;
  dataInativacao?: number | null;
  codProdutoProtheus?: string | null;
}

export function fetchProdutoDadoContabilPaginado(
  params: FetchProdutoDadoContabilParams,
  axios: AxiosInstance
): Promise<PageResult<ProdutoDadoContabilDTO>> {
  return axios
    .get<PageResult<ProdutoDadoContabilDTO>>("/produto-dado-contabil/paginado", {
      params: removeValoresVazios(params),
    })
    .then((res) => res.data);
}

export interface FetchCriarProdutoDadoContabilParams {
  produto: string;
  preco: number;
  idSistema: number;
  status: string;
  dataInativacao: number | null;
  codProdutoProtheus?: string;
}

export function fetchCriarProdutoDadoContabil(
  params: FetchCriarProdutoDadoContabilParams,
  axios: AxiosInstance
): Promise<number> {
  return axios.post<number>("/produto-dado-contabil", params).then((res) => res.data);
}

export interface FetchCriarVinculoProdutoDadoContabilParams {
  idProdutoDadoContabil: number;
  dadoContabil: number;
  idProduto: number;
  idSistema: number;
  dmed: string;
  dataInativacao?: number;
}

export function fetchCriarVinculoProdutoDadoContabil(
  params: FetchCriarVinculoProdutoDadoContabilParams,
  axios: AxiosInstance
): Promise<number> {
  return axios.post<number>("/produto-dado-contabil/vinculo", params).then((res) => res.data);
}

export interface FetchAtualizarProdutoDadoContabilParams {
  idProdutoDadoContabil: number;
  produto?: string;
  preco?: number;
  status: string;
  dadoContabil?: number;
  idProduto?: number;
  idSistema: number;
  dmed?: string;
  dataInativacao: number | null;
  codProdutoProtheus?: string;
}

export function fetchAtualizarProdutoDadoContabil(
  params: FetchAtualizarProdutoDadoContabilParams,
  axios: AxiosInstance
): Promise<ProdutoDadoContabilDTO> {
  return axios
    .put<ProdutoDadoContabilDTO>("/produto-dado-contabil", params)
    .then((res) => res.data);
}
