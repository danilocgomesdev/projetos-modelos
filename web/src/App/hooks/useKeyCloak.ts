import { useContext } from "react";
import { keycloakContext } from "../context/keycloakContext";

export function useKeycloak() {
  const context = useContext(keycloakContext);

  if (!context.client) {
    throw new Error(
      "useKeycloak deve ser usado dentro de um KeycloakProvider configurado corretamente"
    );
  }

  const { client, initialized } = context;

  return {
    initialized,
    keycloak: client,
  };
}
