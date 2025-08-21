import { AxiosInstance } from "axios";
import { VisaoProdutoContabilDTO } from "../models/DTOs/Visoes/VisaoProdutoContabilDTOs.ts";
import { PageQuery } from "../models/request/PageQuery.ts";
import { PageResult } from "../models/response/PageResult.ts";
import { removeValoresVazios } from "../utils/requestUtil.js.ts";

export interface FetchVisaoProdutoContabilParams extends PageQuery {
  idProdutoDadoContabil?: number;
  idDadoContabil?: number;
  idSistema?: number;
  dmed?: string;
  idProduto?: number;
  produto?: string;
  codProdutoProtheus?: string;
  contaContabil?: string;
  contaContabilDescricao?: string;
  itemContabil?: string;
  itemContabilDescricao?: string;
  natureza?: string;
  naturezaDescricao?: string;
  status?: string;
}

export function fetchVisaoProdutoContabilPaginadas(
  params: FetchVisaoProdutoContabilParams,
  axios: AxiosInstance
): Promise<PageResult<VisaoProdutoContabilDTO>> {
  return axios
    .get<PageResult<VisaoProdutoContabilDTO>>("/visao-produto-contabil/paginado", {
      params: removeValoresVazios(params),
    })
    .then((res) => res.data);
}

export function fetchVisaoProdutoContabilByIdAndSistema(
  idProduto: number,
  idSistema: number,
  axios: AxiosInstance
): Promise<VisaoProdutoContabilDTO> {
  return axios
    .get<VisaoProdutoContabilDTO>(
      `/visao-produto-contabil/sistema/${idSistema}/produto/${idProduto}`
    )
    .then((res) => res.data);
}
