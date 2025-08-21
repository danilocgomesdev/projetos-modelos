import { AxiosInstance } from "axios";

export function invalidaCachePermissoes(axios: AxiosInstance, timeout?: number): Promise<void> {
  return axios.post("/verifica-permissoes/invalida-caches", undefined, { timeout });
}
