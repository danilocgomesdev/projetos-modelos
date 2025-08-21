import { ChakraProvider } from "@chakra-ui/react";

import { QueryClientProvider } from "@tanstack/react-query";
import { KeycloakInitOptions } from "keycloak-js";
import React from "react";
import ReactDOM from "react-dom/client";
import { App } from "./App/App";
import { Secured } from "./App/components/Secured/Secured";
import { theme } from "./App/layout/theme";
import KeycloakProvider from "./App/providers/KeyCloakProvider";
import { keycloak } from "./singletons/keycloak";
import queryClient from "./singletons/reactQueryClient";

const keycloakOptions: KeycloakInitOptions = {
  onLoad: "login-required",
  checkLoginIframe: false,
  responseMode: "query",
};

ReactDOM.createRoot(document.getElementById("root") as HTMLElement).render(
  <ChakraProvider theme={theme}>
    <KeycloakProvider client={keycloak} initOptions={keycloakOptions}>
      <QueryClientProvider client={queryClient}>
        <Secured>
          <React.StrictMode>
            <App />
          </React.StrictMode>
        </Secured>
      </QueryClientProvider>
    </KeycloakProvider>
  </ChakraProvider>
);
