import { AxiosInstance } from "axios";
import {
  ClienteResponsavelDTO,
  VinculoDependenteDTO,
} from "../models/DTOs/Outros/VinculoDependenteDTO.ts";
import { PageQuery } from "../models/request/PageQuery.ts";
import { PageResult } from "../models/response/PageResult.ts";
import { removeValoresVazios } from "../utils/requestUtil.js.ts";

export interface FetchVinculoDependenteParams extends PageQuery {
  idDependente?: number | null;
  nomeDependente?: string;
  cpfCnpjDependente?: string;
  nomeResponsavel?: string;
  cpfCnpjResponsavel?: string;
}

export function fetchVinculoDependentePaginado(
  params: FetchVinculoDependenteParams,
  axios: AxiosInstance
): Promise<PageResult<VinculoDependenteDTO>> {
  return axios
    .get<PageResult<VinculoDependenteDTO>>("/vinculo-dependentes/paginado", {
      params: removeValoresVazios(params),
    })
    .then((res) => res.data);
}

export interface FetchClienteResponsavelParams {
  cpf: string;
}

export function fetchClienteResponsavel(
  params: FetchClienteResponsavelParams,
  axios: AxiosInstance
): Promise<ClienteResponsavelDTO> {
  return axios
    .get<ClienteResponsavelDTO>("/vinculo-dependentes/vinculo", {
      params: removeValoresVazios(params),
    })
    .then((res) => res.data);
}

export interface FetchDeleteClienteResponsavelParams {
  idDependente: number;
}

export function fetchDeleteClienteResponsavel(
  params: FetchDeleteClienteResponsavelParams,
  axios: AxiosInstance
): Promise<void> {
  return axios.delete(`/vinculo-dependentes/${params.idDependente}`).then(() => Promise.resolve());
}
