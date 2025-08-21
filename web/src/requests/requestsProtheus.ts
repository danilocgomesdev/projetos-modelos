import { AxiosInstance } from "axios";
import { ContasAReceberProtheusDTO } from "../models/DTOs/Protheus/ContasAReceberProtheusDTO";

interface FetchContasAReceberProtheusDaCobrancaParams {
  idCobrancaCliente: number;
}

export async function fetchContasAReceberProtheusDaCobranca(
  { idCobrancaCliente }: FetchContasAReceberProtheusDaCobrancaParams,
  axios: AxiosInstance
): Promise<ContasAReceberProtheusDTO> {
  const res = await axios.get<ContasAReceberProtheusDTO>(
    `/protheus/contas-a-receber/cobranca-cliente/${idCobrancaCliente}`
  );

  return res.data;
}
