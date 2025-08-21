import { AxiosInstance } from "axios";
import { EnderecoDTO } from "../models/DTOs/Outros/EnderecoDTO.ts";
import { PessoaCr5DTO } from "../models/DTOs/Outros/PessoaCr5DTO.ts";
import { PageQuery } from "../models/request/PageQuery";
import { PageResult } from "../models/response/PageResult";

export interface FetchPessoasCr5PaginadoParams extends PageQuery {
  idPessoa?: number;
  nome?: string;
  cpfCnpj?: string;
  bairro?: string;
  cidade?: string;
  estado?: string;
  cep?: string;
  idEstrangeiro?: string;
}

export function fetchPessoasCr5Paginado(
  params: FetchPessoasCr5PaginadoParams,
  axios: AxiosInstance
): Promise<PageResult<PessoaCr5DTO>> {
  return axios
    .get<PageResult<PessoaCr5DTO>>("/cliente/paginado", { params })
    .then((res) => res.data);
}

export interface FetchCriarPessoaFisicaParams {
  cpf: string;
  rg: string;
  descricao: string;
  dataNascimento: string;
  logradouro: string;
  complemento?: string | null;
  bairro: string;
  cidade: string;
  estado: string;
  cep: string;
  telefone: string;
  telefone2?: string | null;
  idResponsavelFinanceiro?: number | null;
  emancipado: boolean;
  celular: string;
  celular2?: string | null;
  numeroResidencia?: string | null;
  email: string;
}

export function fetchCriarPessoaFisica(
  params: FetchCriarPessoaFisicaParams,
  axios: AxiosInstance
): Promise<void> {
  return axios.post("/cliente/pessoa-fisica", params).then(() => Promise.resolve());
}

interface FetchAtualizarPessoaFisicaParams {
  idPessoa: number;
  cpf: string;
  rg: string;
  descricao: string;
  dataNascimento: string;
  logradouro: string;
  complemento?: string | null;
  bairro: string;
  numeroResidencia?: string | null;
  cidade: string;
  estado: string;
  cep: string;
  telefone: string;
  telefone2?: string | null;
  celular: string;
  celular2?: string | null;
  emancipado: boolean;
  email: string;
}

export function fetchAtualizarPessoaFisica(
  params: FetchAtualizarPessoaFisicaParams,
  axios: AxiosInstance
): Promise<void> {
  return axios
    .put(`/cliente/pessoa-fisica/${params.idPessoa}`, params)
    .then(() => Promise.resolve());
}

export interface FetchCriarPessoaJuridicaParams {
  inscricaoEstadual: string;
  cnpj: string;
  razaoSocial: string;
  logradouro: string;
  complemento?: string | null;
  bairro: string;
  cidade: string;
  estado: string;
  cep: string;
  telefone: string;
  telefone2?: string | null;
  celular: string;
  celular2?: string | null;
  numeroResidencia?: string | null;
  email: string;
}

export function fetchCriarPessoaJuridica(
  params: FetchCriarPessoaJuridicaParams,
  axios: AxiosInstance
): Promise<void> {
  return axios.post("/cliente/pessoa-juridica", params).then(() => Promise.resolve());
}

interface FetchAtualizarPessoaJuridicaParams {
  idPessoa: number;
  inscricaoEstadual: string;
  cnpj: string;
  razaoSocial: string;
  logradouro: string;
  complemento?: string | null;
  bairro: string;
  cidade: string;
  estado: string;
  cep: string;
  telefone: string;
  telefone2?: string | null;
  celular: string;
  celular2?: string | null;
  numeroResidencia?: string | null;
  email: string;
}

export function fetchAtualizarPessoaJuridica(
  params: FetchAtualizarPessoaJuridicaParams,
  axios: AxiosInstance
): Promise<void> {
  return axios
    .put(`/cliente/pessoa-juridica/${params.idPessoa}`, params)
    .then(() => Promise.resolve());
}

export interface FetchCriarEstrangeiroParams {
  descricao: string;
  idEstrangeiro: string;
  logradouro: string;
  complemento?: string | null;
  bairro: string;
  cidade: string;
  codigoPostal: string;
  codigoPais: number;
  estado?: string;
  pais: string;
  telefone: string;
  telefone2?: string | null;
  celular: string;
  celular2?: string | null;
  numeroResidencia?: string | null;
  email: string;
}

export function fetchCriarEstrangeiro(
  params: FetchCriarEstrangeiroParams,
  axios: AxiosInstance
): Promise<void> {
  return axios.post("/cliente/estrangeiro", params).then(() => Promise.resolve());
}

interface FetchAtualizarEstrangeiroParams {
  idPessoa: number;
  descricao: string;
  dataNascimento?: string | null;
  logradouro: string;
  complemento?: string | null;
  bairro: string;
  numeroResidencia?: string | null;
  cidade: string;
  estado?: string;
  pais: string;
  codigoPostal: string;
  codigoPais: number;
  telefone: string;
  telefone2?: string | null;
  celular: string;
  celular2?: string | null;
  email: string;
  idEstrangeiro: string;
}

export function fetchAtualizarEstrangeiro(
  params: FetchAtualizarEstrangeiroParams,
  axios: AxiosInstance
): Promise<void> {
  return axios.put(`/cliente/estrangeiro/${params.idPessoa}`, params).then(() => Promise.resolve());
}

export interface FetchDeletePessoaParams {
  idPessoa: number;
}

export function fetchDeletePessoa(
  params: FetchDeletePessoaParams,
  axios: AxiosInstance
): Promise<void> {
  return axios.delete(`/cliente/${params.idPessoa}`).then(() => Promise.resolve());
}

export interface FetchEnderecoParams {
  cep: string;
}

export async function fetchEndereco(
  params: FetchEnderecoParams,
  axios: AxiosInstance
): Promise<EnderecoDTO> {
  const res = await axios.get<EnderecoDTO>("/cliente/endereco", {
    params,
  });
  return res.data;
}
