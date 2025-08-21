import { AxiosError, AxiosInstance } from "axios";
import { GestorDTO, PessoaCIDTO } from "../models/DTOs/Outros/Gestor.js";
import { PageQuery } from "../models/request/PageQuery";
import { PageResult } from "../models/response/PageResult";
import { removeValoresVazios } from "../utils/requestUtil.js";

export interface FetchGestoresPaginadoParams extends PageQuery {
  idGestor?: number;
  nome?: string;
  email?: string;
  matricula?: number | null;
  idUnidade?: number;
}

export async function fetchGestoresPaginado(
  params: FetchGestoresPaginadoParams,
  axios: AxiosInstance
): Promise<PageResult<GestorDTO>> {
  const res = await axios.get<PageResult<GestorDTO>>("/gestores", {
    params: removeValoresVazios(params),
  });
  return res.data;
}

export async function fetchGestorById(
  idGestor: number,
  axios: AxiosInstance
): Promise<GestorDTO | null> {
  try {
    const res = await axios.get<GestorDTO>(`/gestores/${idGestor}`);
    return res.data;
  } catch (err) {
    if (err instanceof AxiosError && err.response?.status === 404) {
      return null;
    }
    throw err;
  }
}

interface CriarGestorDTO {
  nome: string;
  email: string;
  matricula: number;
  descricao: string;
  idCiPessoas: number;
  idUnidade: number;
}

export async function postCriarGestor(params: CriarGestorDTO, axios: AxiosInstance): Promise<void> {
  await axios.post("/gestores", params);
}

interface EditarGestorDTO extends CriarGestorDTO {
  idGestor: number;
}

export async function putEditarGestor(
  params: EditarGestorDTO,
  axios: AxiosInstance
): Promise<void> {
  const { idGestor, ...rest } = params;
  await axios.put(`/gestores/${idGestor}`, rest);
}

export async function deleteGestor(idGestor: number, axios: AxiosInstance): Promise<void> {
  await axios.delete(`/gestores/${idGestor}`);
}

export interface FetchPessoasCIPaginadoParams extends PageQuery {
  idPessoa?: number;
  nome?: string;
  email?: string;
  matricula?: number | null;
  idUnidade?: number;
}

export async function fetchPessoasCIPaginado(
  params: FetchPessoasCIPaginadoParams,
  axios: AxiosInstance
): Promise<PageResult<PessoaCIDTO>> {
  const res = await axios.get<PageResult<PessoaCIDTO>>("/gestores/pessoas-paginado", {
    params: removeValoresVazios(params),
  });
  return res.data;
}
