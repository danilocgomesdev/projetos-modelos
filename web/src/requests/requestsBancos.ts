import { AxiosError, AxiosInstance } from "axios";
import { BancoDTO } from "../models/DTOs/AgenciaBancoConta/BancoDTO.ts";
import { PageQuery } from "../models/request/PageQuery";
import { PageResult } from "../models/response/PageResult";
import { removeValoresVazios } from "../utils/requestUtil.js.ts";

export interface FetchBancosPaginadoParams extends PageQuery {
  id?: number;
  nome?: string;
  numero?: string;
  abreviatura?: string;
}

export async function fetchBancosPaginado(
  params: FetchBancosPaginadoParams,
  axios: AxiosInstance
): Promise<PageResult<BancoDTO>> {
  const res = await axios.get<PageResult<BancoDTO>>("/bancos", {
    params: removeValoresVazios(params),
  });
  return res.data;
}

export async function fetchBancoById(
  idBanco: number,
  axios: AxiosInstance
): Promise<BancoDTO | null> {
  try {
    const res = await axios.get<BancoDTO>(`/bancos/${idBanco}`);
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

interface CriarBancoDTO {
  numero: string;
  nome: string;
  abreviatura: string;
}

export async function postCriarBanco(params: CriarBancoDTO, axios: AxiosInstance): Promise<void> {
  await axios.post("/bancos", params);
}

interface EditarBancoDTO extends CriarBancoDTO {
  id: number;
}

export async function putEditarBanco(params: EditarBancoDTO, axios: AxiosInstance): Promise<void> {
  const { id, ...rest } = params;
  await axios.put(`/bancos/${id}`, rest);
}

export async function deleteBanco(idbanco: number, axios: AxiosInstance): Promise<void> {
  await axios.delete(`/bancos/${idbanco}`);
}
