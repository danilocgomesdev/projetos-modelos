import { AxiosInstance } from "axios";

export interface VersaoResponse {
  versao: string;
}

export function fetchVersao(axios: AxiosInstance): Promise<VersaoResponse> {
  return axios.get<VersaoResponse>("/health/versao").then((res) => res.data);
}
