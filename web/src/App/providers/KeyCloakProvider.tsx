import { KeycloakInitOptions, KeycloakInstance } from "keycloak-js";
import React, { FC, ReactNode, useEffect, useState } from "react";
import { IAuthContextProps, keycloakContext } from "../context/keycloakContext";

interface KeycloakAuthProviderProps {
  client: KeycloakInstance;
  initOptions: KeycloakInitOptions;
  children: ReactNode;
}

function createAuthProvider(AuthContext: React.Context<IAuthContextProps>) {
  const KeycloakAuthProvider: FC<KeycloakAuthProviderProps> = (props) => {
    const { client, children, initOptions } = props;
    const [initialized, setInitialized] = useState<boolean>(false);

    useEffect(() => {
      const initialize = () => {
        client
          .init(initOptions)
          .success(() => {
            setInitialized(true);
          })
          .error((err) => {
            console.error("Erro de autenticação!", err);
          });
      };
      initialize();
    }, []);

    return <AuthContext.Provider value={{ initialized, client }}>{children}</AuthContext.Provider>;
  };

  return KeycloakAuthProvider;
}

const KeycloakProvider = createAuthProvider(keycloakContext);
export default KeycloakProvider;
