import { AxiosInstance } from "axios";
import { ParcelaCadinDTO } from "../models/DTOs/Cadin/ParcelaCadinDTO";
import { ParcelaDTO } from "../models/DTOs/Cobrancas/ParcelaDTO";
import { PageQuery } from "../models/request/PageQuery";
import { PageResult } from "../models/response/PageResult";

export interface FetchParcelasCadinParams {
  idCobrancaCliente: number;
}

export function fetchInformacoesOrigemCadin(
  params: FetchParcelasCadinParams,
  axios: AxiosInstance
): Promise<ParcelaCadinDTO> {
  return axios
    .get<ParcelaCadinDTO>(`/parcela-cadin/informacoes-origem/${params.idCobrancaCliente}`)
    .then((res) => res.data);
}

export interface FetchProtheusCadinPaginadoParams extends PageQuery {
  dataInicial: string | null;
  dataFinal: string | null;
  dataVencimentoInicial?: Date;
  dataVencimentoFinal?: Date;
  sacadoNome: string | null;
  sacadoCpfCnpj: string | null;
  numeroParcela: number | null;
  contId: number | null;
  nossoNumero: string | null;
  baixaIntegrada: boolean | null;
  idUnidade: number | null;
  idEntidade: number | null;
  formaPagamento: string | null;
  integraProtheus: string | null;
}

export function fetchParcelasPagasCadinPaginado(
  params: FetchProtheusCadinPaginadoParams,
  axios: AxiosInstance
): Promise<PageResult<ParcelaDTO>> {
  const { page, pageSize, ...otherParams } = params;
  return axios
    .post<PageResult<ParcelaDTO>>("/parcela-cadin/busca-pagas-no-intervalo", otherParams, {
      params: {
        page,
        pageSize,
      },
    })
    .then((res) => res.data);
}
