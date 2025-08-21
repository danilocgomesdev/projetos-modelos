import { AxiosInstance } from "axios";
import { SituacaoClienteDTO } from "../models/DTOs/Cobrancas/SituacaoClienteDTO.ts";
import { removeValoresVazios } from "../utils/requestUtil.js";

import { PageQuery } from "../models/request/PageQuery.ts";
import { PageResult } from "../models/response/PageResult";

interface BuscaSituacaoClienteParams extends PageQuery {
  cpfCnpj: string;
  dtInicioCobranca: Date | null;
  dtFimCobranca: Date | null;
  idUnidade: number | null;
  idSistema: number | null;
  contrato: number | null;
  parcela: number | null;
  nossoNumero: string | null;
  status: string | null;
  consumidorNome: string | null;
  produto: string | null;
}

export function buscaSituacaoCliente(
  params: BuscaSituacaoClienteParams,
  axios: AxiosInstance
): Promise<PageResult<SituacaoClienteDTO>> {
  return axios
    .get<PageResult<SituacaoClienteDTO>>("/cliente/situacaoCliente", {
      params: removeValoresVazios(params),
    })
    .then((res) => res.data);
}
