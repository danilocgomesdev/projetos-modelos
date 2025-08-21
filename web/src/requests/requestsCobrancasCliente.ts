import { AxiosInstance } from "axios";
import { CobrancaClienteAdicionarDTO } from "../models/DTOs/CobrancaCliente/CobrancaClienteAdicionarDTO";
import { ParcelaDTO } from "../models/DTOs/Cobrancas/ParcelaDTO";
import { FormaPagamentoSimplificado } from "../models/DTOs/Outros/FormasDePagamentoSimplificas";
import { FiltroIntegraProtheus } from "../models/request/FiltroIntegraProtheus";
import { FiltroPagamento } from "../models/request/FiltroPagamento";
import { PageQuery } from "../models/request/PageQuery";
import { PageResult } from "../models/response/PageResult";

export interface FetchPesquisaParcelasParams extends PageQuery {
  filtroPagamento?: FiltroPagamento;
  dataVencimentoInicial?: Date;
  dataVencimentoFinal?: Date;
  sacadoNome?: string;
  sacadoCpfCnpj?: string;
  numeroParcela?: number;
  contId?: number;
  idSistema?: number;
  nossoNumero?: string;
  contratoProtheus?: string;
  recno?: number;
  baixaIntegrada?: boolean;
  idUnidade?: number;
  idEntidade?: number;
  formaPagamento?: FormaPagamentoSimplificado;
  filtroIntegraProtheus?: FiltroIntegraProtheus;

  // filtros do Protheus
  saldoZeradoProtheus?: boolean;
}

export async function fetchPesquisaParcelas(
  params: FetchPesquisaParcelasParams,
  axios: AxiosInstance
): Promise<PageResult<ParcelaDTO>> {
  const { page, pageSize, ...otherParams } = params;

  const res = await axios.post<PageResult<ParcelaDTO>>(
    "/cobranca-cliente/pesquisar-parcelas",
    otherParams,
    {
      params: {
        page,
        pageSize,
      },
    }
  );

  return res.data;
}

export interface FiltroCobrancasDTO extends PageQuery {
  filtroPagamento?: FiltroPagamento;
  dataVencimentoInicial?: Date;
  dataVencimentoFinal?: Date;
  sacadoNome?: string;
  sacadoCpfCnpj?: string;
  numeroParcela?: number;
  contId?: number;
  idSistema?: number;
  nossoNumero?: string;
  contratoProtheus?: string;
  recno?: number;
  baixaIntegrada?: boolean;
  idUnidade?: number;
  idEntidade?: number;
  formaPagamento?: FormaPagamentoSimplificado;
  filtroIntegraProtheus?: FiltroIntegraProtheus;
  saldoZeradoProtheus?: boolean;
}

export async function fetchParcelasPaginado(
  params: FiltroCobrancasDTO,
  axios: AxiosInstance
): Promise<PageResult<ParcelaDTO>> {
  const { page, pageSize, ...body } = params;

  const res = await axios.post<PageResult<ParcelaDTO>>(
    "/cobranca-cliente/pesquisar-parcelas",
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

export interface FiltroCobrancaClienteAdicionarDTO extends PageQuery {
  contId?: number;
  idSistema?: number;
}

export function fetchBuscaDadosContratoPaginado(
  params: FiltroCobrancaClienteAdicionarDTO,
  axios: AxiosInstance
): Promise<PageResult<CobrancaClienteAdicionarDTO>> {
  return axios
    .get<PageResult<CobrancaClienteAdicionarDTO>>("/cobranca-cliente/pesquisar-parcelas-contrato", {
      params,
    })
    .then((res) => res.data);
}

export interface AdicionaParcelaProps {
  idCobrancaCliente: number;
  idInterfaceCobranca: number;
  qtdParcela: number;
}

export function fetchAdicionarParcela(
  params: AdicionaParcelaProps,
  axios: AxiosInstance
): Promise<void> {
  return axios.post("/cobranca-cliente/adicionar-parcela", params).then(() => Promise.resolve());
}

export interface FetchDeleteCobrancaClienteParams {
  idCobrancaCliente: number;
  idOperador?: number | null;
}

export function fetchDeletaCobrancaCliente(
  params: FetchDeleteCobrancaClienteParams,
  axios: AxiosInstance
): Promise<void> {
  return axios
    .delete(`/cobranca-cliente/${params.idCobrancaCliente}`)
    .then(() => Promise.resolve());
}
