import { AxiosError, AxiosInstance } from "axios";
import { ContaCorrenteDTO } from "../models/DTOs/AgenciaBancoConta/ContaCorrenteDTO.js";
import { PageQuery } from "../models/request/PageQuery";
import { PageResult } from "../models/response/PageResult";
import { removeValoresVazios } from "../utils/requestUtil.js";

export interface FetchContasCorrentePaginadoParams extends PageQuery {
  id?: number;
  numeroConta?: string;
  nomeAgencia?: string;
  nomeBanco?: string;
  idEntidade?: number;
  idUnidade?: number;
}

export async function fetchContasCorrentePaginado(
  params: FetchContasCorrentePaginadoParams,
  axios: AxiosInstance
): Promise<PageResult<ContaCorrenteDTO>> {
  const res = await axios.get<PageResult<ContaCorrenteDTO>>("/contas-corrente", {
    params: removeValoresVazios(params),
  });
  return res.data;
}

export async function fetchContaCorrenteById(
  idContaCorrente: number,
  axios: AxiosInstance
): Promise<ContaCorrenteDTO | null> {
  try {
    const res = await axios.get<ContaCorrenteDTO>(`/contas-corrente/${idContaCorrente}`);
    return res.data;
  } catch (err) {
    if (err instanceof AxiosError) {
      if (err.status === 404) {
        return null;
      }
    }

    throw err;
  }
}

export interface CriarContaCorrenteDTO {
  idAgencia: number;
  numeroOperacao: string;
  numeroConta: string;
  digitoConta: string;
  idUnidade: number;
  contaBanco?: string;
  contaCliente?: string;
  contaCaixa?: string;
  contaJuros?: string;
  contaDescontos?: string;
  cofreBanco?: string;
  cofreAgencia?: string;
  cofreConta?: string;
}

export async function postCriarContaCorrente(
  params: CriarContaCorrenteDTO,
  axios: AxiosInstance
): Promise<void> {
  await axios.post("/contas-corrente", params);
}

export interface AlterarContaCorrenteDTO extends CriarContaCorrenteDTO {
  id: number;
}

export async function putEditarContaCorrente(
  params: AlterarContaCorrenteDTO,
  axios: AxiosInstance
): Promise<void> {
  const { id, ...rest } = params;
  await axios.put(`/contas-corrente/${id}`, rest);
}

export async function deleteContaCorrente(
  idContaCorrente: number,
  axios: AxiosInstance
): Promise<void> {
  await axios.delete(`/contas-corrente/${idContaCorrente}`);
}
