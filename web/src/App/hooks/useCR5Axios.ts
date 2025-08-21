import { AxiosInstance } from "axios";
import { cr5AxiosClient } from "../../singletons/axiosCR5";
import { useKeycloak } from "./useKeyCloak";

export default function useCR5Axios(): { axios: AxiosInstance } {
  const { keycloak } = useKeycloak();
  cr5AxiosClient.defaults.headers.common["Authorization"] = `Bearer ${keycloak.token}`;

  return {
    axios: cr5AxiosClient,
  };
}
