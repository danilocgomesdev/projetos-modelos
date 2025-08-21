import { AxiosError, AxiosInstance } from "axios";
import {
  ConvenioBancarioDTO,
  FaixaNossoNumeroDTO,
  SistemaBancario,
} from "../models/DTOs/ConvenioBancario";
import { PageQuery } from "../models/request/PageQuery";
import { PageResult } from "../models/response/PageResult.js";
import { removeValoresVazios } from "../utils/requestUtil.js";

interface FetchConvenioBancarioByIdParams {
  idConvenioBancario: number;
}

export async function fetchConvenioBancarioById(
  params: FetchConvenioBancarioByIdParams,
  axios: AxiosInstance
): Promise<ConvenioBancarioDTO | null> {
  try {
    const res = await axios.get<ConvenioBancarioDTO>(
      `/convenios-bancarios/${params.idConvenioBancario}`
    );

    return mapeaiaDataConvenio(res.data);
  } catch (err) {
    if (err instanceof AxiosError) {
      if (err.response?.status === 404) {
        return null;
      }
    }
    throw err;
  }
}

export interface FetchConveniosBancariosPaginadoParams extends PageQuery {
  id?: number;
  nomeCedente?: string;
  numero?: string;
  numeroConta?: string;
  nomeAgencia?: string;
  nomeBanco?: string;
  idEntidade?: number;
  idUnidade?: number;
  status?: "ATIVO" | "INATIVO";
}

export async function fetchConveniosBancariosPaginado(
  params: FetchConveniosBancariosPaginadoParams,
  axios: AxiosInstance
): Promise<PageResult<ConvenioBancarioDTO>> {
  const res = await axios.get<PageResult<ConvenioBancarioDTO>>("/convenios-bancarios", {
    params: removeValoresVazios(params),
  });

  return res.data;
}

export async function fetchConvenioBancarioAtivoByUnidade(
  idUnidade: number,
  axios: AxiosInstance
): Promise<ConvenioBancarioDTO | null> {
  try {
    const res = await axios.get<ConvenioBancarioDTO>(`/convenios-bancarios/unidade/${idUnidade}`);

    return mapeaiaDataConvenio(res.data);
  } catch (err) {
    if (err instanceof AxiosError) {
      if (err.response?.status === 404) {
        return null;
      }
    }
    throw err;
  }
}

export interface FetchConveniosBancariosInativosByUnidadeParams {
  idUnidade: number;
}

export function fetchConveniosBancariosInativosByUnidade(
  params: FetchConveniosBancariosInativosByUnidadeParams,
  axios: AxiosInstance
): Promise<PageResult<ConvenioBancarioDTO>> {
  return axios
    .get<PageResult<ConvenioBancarioDTO>>(
      `/convenios-bancarios/unidade/inativos/${params.idUnidade}`
    )
    .then((res) => res.data);
}

export interface DesativarConvenioBancarioParams {
  idConvenioBancario: number;
}

export function patchDesativarConvenioBancario(
  params: DesativarConvenioBancarioParams,
  axios: AxiosInstance
): Promise<void> {
  return axios
    .patch(`/convenios-bancarios/${params.idConvenioBancario}/desativar`)
    .then(() => Promise.resolve());
}

export interface AtivarConvenioBancarioParams {
  idConvenioBancario: number;
}

export function patchAtivarConvenioBancario(
  params: AtivarConvenioBancarioParams,
  axios: AxiosInstance
): Promise<void> {
  return axios
    .patch(`/convenios-bancarios/${params.idConvenioBancario}/ativar`, params)
    .then(() => Promise.resolve());
}

export interface FetchDeleteConvenioBancarioParams {
  idConvenioBancario: number;
}

export function fetchDeleteConvenioBancario(
  params: FetchDeleteConvenioBancarioParams,
  axios: AxiosInstance
): Promise<void> {
  return axios
    .delete(`/convenios-bancarios/${params.idConvenioBancario}`)
    .then(() => Promise.resolve());
}

interface ConvenioBancarioCRUDDTO {
  idContaCorrente: number;
  nomeCedente: string;
  numero: string;
  carteira: string;
  aceite: boolean;
  indiceMulta: number;
  indiceJuros: number;
  sistemaBancario: SistemaBancario;
  observacao1?: string;
  observacao2?: string;
  observacao3?: string;
  observacao4?: string;
  observacao5?: string;
  localPagamento: string;
  idUnidade: number;
  utilizaUnCentralizadora: boolean;
}

export interface FaixaNossoNumeroCRUDDTO {
  nossoNumeroInicial: string;
  nossoNumeroFinal: string;
  nossoNumeroAtual: string;
}

export interface CriarConvenioBancarioParams extends ConvenioBancarioCRUDDTO {
  criarFaixaNossoNumeroDTO: FaixaNossoNumeroCRUDDTO;
}

export async function postCriarConvenioBancario(
  params: CriarConvenioBancarioParams,
  axios: AxiosInstance
): Promise<void> {
  await axios.post("/convenios-bancarios", params);
}

export interface AlterarConvenioBancarioParams extends ConvenioBancarioCRUDDTO {
  idConvenioBancario: number;
}

export async function putAlterarConvenioBancario(
  params: AlterarConvenioBancarioParams,
  axios: AxiosInstance
): Promise<void> {
  const { idConvenioBancario, ...rest } = params;
  await axios.put(`/convenios-bancarios/${idConvenioBancario}`, rest);
}

interface PatchAtivarFaixaNossoNumeroParams {
  idConvenioBancario: number;
  idFaixa: number;
}

export async function patchAtivarFaixaNossoNumero(
  params: PatchAtivarFaixaNossoNumeroParams,
  axios: AxiosInstance
): Promise<FaixaNossoNumeroDTO[]> {
  const { idConvenioBancario, idFaixa } = params;
  const res = await axios.patch<FaixaNossoNumeroDTO[]>(
    `/convenios-bancarios/${idConvenioBancario}/faixas-nn/${idFaixa}/ativar`
  );

  return res.data;
}

interface PostCriarFaixaNossoNumeroParams {
  idConvenioBancario: number;
  alterarFaixaNossoNumeroDTO: FaixaNossoNumeroCRUDDTO;
}

export async function postCriarFaixaNossoNumero(
  params: PostCriarFaixaNossoNumeroParams,
  axios: AxiosInstance
): Promise<FaixaNossoNumeroDTO> {
  const { idConvenioBancario, alterarFaixaNossoNumeroDTO } = params;
  const res = await axios.post<FaixaNossoNumeroDTO>(
    `/convenios-bancarios/${idConvenioBancario}/faixas-nn`,
    alterarFaixaNossoNumeroDTO
  );

  return res.data;
}

interface PatchAlterarFaixaNossoNumeroParams {
  idConvenioBancario: number;
  idFaixa: number;
  alterarFaixaNossoNumeroDTO: FaixaNossoNumeroCRUDDTO;
}

export async function putAlterarFaixaNossoNumero(
  params: PatchAlterarFaixaNossoNumeroParams,
  axios: AxiosInstance
): Promise<FaixaNossoNumeroDTO> {
  const { idConvenioBancario, idFaixa, alterarFaixaNossoNumeroDTO } = params;
  const res = await axios.put<FaixaNossoNumeroDTO>(
    `/convenios-bancarios/${idConvenioBancario}/faixas-nn/${idFaixa}`,
    alterarFaixaNossoNumeroDTO
  );

  return res.data;
}

function mapeaiaDataConvenio(convenioBancario: ConvenioBancarioDTO): ConvenioBancarioDTO {
  const { dataInativo, dataInclusao, dataAlteracao, faixasNossoNumero, ...resto } =
    convenioBancario;

  return {
    dataInativo: dataInativo ? new Date(dataInativo) : null,
    dataInclusao: new Date(dataInclusao),
    dataAlteracao: dataAlteracao ? new Date(dataAlteracao) : null,
    faixasNossoNumero: faixasNossoNumero.map(mapeaiaDataFaixaNN),
    ...resto,
  };
}

function mapeaiaDataFaixaNN(faixa: FaixaNossoNumeroDTO): FaixaNossoNumeroDTO {
  const { dataInclusao, dataAlteracao, ...resto } = faixa;

  return {
    dataInclusao: new Date(dataInclusao),
    dataAlteracao: dataAlteracao ? new Date(dataAlteracao) : null,
    ...resto,
  };
}
