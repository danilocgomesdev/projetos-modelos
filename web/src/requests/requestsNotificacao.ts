import { AxiosInstance } from "axios";

interface FetchEnviarNotificaoParams {
  mensagem: string;
}

export function fetchEnviarNotificao(
  params: FetchEnviarNotificaoParams,
  axios: AxiosInstance
): Promise<void> {
  return axios.post("/notificacoes/enviar", params).then(() => Promise.resolve());
}
