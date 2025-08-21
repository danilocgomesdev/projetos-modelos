import { AxiosInstance } from "axios";
import { PagamentoNaoBaixadoComUnidadeDTO } from "../models/DTOs/Outros/PagamentoNaoBaixadoDTO.js";
import { removeValoresVazios } from "../utils/requestUtil.js";

interface BuscaPagamentosNaoBaixadosParams {
  idUnidade: number | null;
  valorMaximo: number | null;
  dataInicial: Date | null;
  dataFinal: Date | null;
}

export async function buscaPagamentosNaoBaixados(
  params: BuscaPagamentosNaoBaixadosParams,
  axios: AxiosInstance
): Promise<PagamentoNaoBaixadoComUnidadeDTO[]> {
  const res = await axios.get<PagamentoNaoBaixadoComUnidadeDTO[]>("/pagamento-nao-baixado", {
    params: removeValoresVazios(params),
  });

  return res.data;
}
