import { AxiosInstance } from "axios";
import { OperadorEDireitosDTO } from "../models/DTOs/Acessos/AcessosDTOs";

export function fetchAcessos(axios: AxiosInstance): Promise<OperadorEDireitosDTO> {
  return axios.get<OperadorEDireitosDTO>("/acessos").then((res) => res.data);
}
