import { AxiosInstance } from "axios";
import { CobrancasGrupoDTO } from "../models/DTOs/CobrancasAgrupadas/CobrancasGrupoDTO";
import { CobrancaParaContratoEmRedeDTO } from "../models/DTOs/CobrancasAgrupadas/CobrancasParaContratoRedeDTO";
import { DadosCobrancaGrupoDTO } from "../models/DTOs/CobrancasAgrupadas/DadosCobrancaGrupoDTO";
import { PageQuery } from "../models/request/PageQuery";
import { PageResult } from "../models/response/PageResult";


export interface FetchCobrancaParaContratoEmRedeParams extends PageQuery {
    proposta: string,
    cpfCnpj: string
}

 export async function fetchCobrancaParaContratoEmRedePaginado(
  params: FetchCobrancaParaContratoEmRedeParams,
  axios: AxiosInstance
): Promise<PageResult<CobrancaParaContratoEmRedeDTO>> {
    const { page, pageSize, ...otherParams } = params;
  return axios
    .post<PageResult<CobrancaParaContratoEmRedeDTO>>("/agrupadas/pesquisar-cobrancas-protheus", otherParams, {
      params: {
        page,
        pageSize,
      },
    })
    .then((res) => res.data);
}

export interface FetchAgruparParcelasProtheusParams{
    listaIdInterfaces: number[],
    dataVencimento: Date
}

export async function fetchAgruparParcelasProtheus(
  params: FetchAgruparParcelasProtheusParams,
  axios: AxiosInstance
): Promise<void> {
  return axios
    .post("/agrupadas/agrupadas-protheus", params)
    .then(() => Promise.resolve());
}

export interface FiltroCobrancasAgruparParams extends PageQuery {
  nomeCliente?: string,
  cpfCnpj?: string,
  dataInicial?: Date;
  dataFinal?: Date;
  contIdInicial?: number,
  contIdFinal?: number,
  contProtheusInicial?: string,
  contProtheusFinal?: string,
  parcela?: number,
  produto?: string,
  nomeConsumidor?: string,
  isContratoRede?: string,
  nomePainel: string,
  idGrupo?: number
}

export async function fetchCobrancaParaAgruparPaginado(
  params: FiltroCobrancasAgruparParams,
  axios: AxiosInstance
): Promise<PageResult<CobrancasGrupoDTO>> {
  const { page, pageSize, ...body } = params;
  const res = await axios.post<PageResult<CobrancasGrupoDTO>>(
    "/agrupadas/cobrancas",
    body,
    {
      params: {
        page,
        pageSize,
      },
    }
  );

  return res.data;
}

export interface AgrupamentoParams {
    listaIdCobrancaCliente: number[],
    dataVencimento: Date
}

export async function fetchAgruparParcelas(
  params: AgrupamentoParams,
  axios: AxiosInstance
): Promise<void> {
  return axios
    .post("/agrupadas/agrupada-parcelas", params)
    .then(() => Promise.resolve());
}

export interface AlteraDadosGrupoParams {
  dataVencimento?: Date,
  notaFiscal?: number | string,
  dataEmissaoNotaFiscal?: Date,
  avisoLancamento?: string,
  dataAvisoLancamento?: Date
}


export async function fetchAlteraDadosGrupo(
  params: AlteraDadosGrupoParams,
  idGrupo: number,
  axios: AxiosInstance
) {
  await axios.put(`/agrupadas/altera-dados-grupo/${idGrupo}`, params);
}


export async function fetchDesagrupar(
  idGrupo: number,
  axios: AxiosInstance
) {
  await axios.put(`/agrupadas/desfazer-grupo/${idGrupo}`);
}

export async function fetchDadosDoGrupo(
  idGrupo: number,
  axios: AxiosInstance
) {
  const res = await axios.get<DadosCobrancaGrupoDTO[]>(`/agrupadas/cobrancas-do-grupo/${idGrupo}`);
  return res.data;
}

export async function fetchExcluirBoletoGrupo(
    idGrupo: number,
    axios: AxiosInstance
): Promise<void> {
return axios.delete(`/agrupadas/exclui-boleto-grupo/${idGrupo}`)
  .then(() => Promise.resolve());
}

export async function fetchExcluirBoleto(
    idCobrancaCliente: number,
    axios: AxiosInstance
): Promise<void> {
return axios.delete(`/agrupadas/exclui-boleto/${idCobrancaCliente}`)
  .then(() => Promise.resolve());
}
