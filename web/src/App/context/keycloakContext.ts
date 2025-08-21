import { KeycloakInstance } from "keycloak-js";
import React, { createContext } from "react";

export type IAuthContextProps = {
  client?: KeycloakInstance;
  initialized: boolean;
};

export function createAuthContext(
  initialContext?: Partial<IAuthContextProps>
): React.Context<IAuthContextProps> {
  return createContext({
    initialized: false,
    ...initialContext,
  });
}

export default createAuthContext;

export const keycloakContext = createAuthContext();
