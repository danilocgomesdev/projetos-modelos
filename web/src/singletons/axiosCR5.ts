import axios from "axios";

// TODO talvez melhorar esse processo de pegar variáveis de ambiente
const urlAuthMiddleware = import.meta.env.VITE_URL_AUTH_MIDDLEWARE_CR5;

if (!urlAuthMiddleware) {
  throw new Error("É necessário configurar VITE_URL_AUTH_MIDDLEWARE_CR5");
}

const baseURL = urlAuthMiddleware + "/api";

export const cr5AxiosClient = axios.create({
  baseURL,
  headers: {
    "Access-Control-Allow-Origin": "*",
  },
});
