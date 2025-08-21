import { AxiosInstance } from "axios";

import { ConsultaBoletoCaixaResponseDTO } from "../models/DTOs/ConsultaBoletoCaixa/ConsultaBoletoCaixaResponseDTO.ts";
import { DadosBoletoCr5DTO } from "../models/DTOs/ConsultaBoletoCaixa/DadosBoletoCr5DTO.ts";
import { PageQuery } from "../models/request/PageQuery";

export interface FetchConsultaBoletoCaixaPaginadoParam extends PageQuery {
  nossoNumero?: string;
}

export function fetchConsultaBoletoCaixaPaginado(
  params: FetchConsultaBoletoCaixaPaginadoParam,
  axios: AxiosInstance
): Promise<ConsultaBoletoCaixaResponseDTO> {
  return axios
    .post<ConsultaBoletoCaixaResponseDTO>("/boleto/consulta-boleto-caixa", params)
    .then((res) => res.data);
}

export function fetchConsultaBoletoCR5Paginado(
  params: FetchConsultaBoletoCaixaPaginadoParam,
  axios: AxiosInstance
): Promise<DadosBoletoCr5DTO> {
  return axios
    .post("/boleto/consulta-boleto-cr5", params)
    .then((res) => res.data);
}

export function fetchRetirarVinculoBoleto(
  idBoleto: number ,
  axios: AxiosInstance
): Promise<void> {
  return axios
    .post(`/boleto/retirar-vinculo/${idBoleto}`)
    .then((res) => res.data);
}