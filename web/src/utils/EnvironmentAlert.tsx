// src/components/EnvironmentAlert.tsx
import { Alert, AlertDescription, AlertIcon, AlertTitle, Box } from "@chakra-ui/react";

const environmentMessages: Record<string, string> = {
  dev: "Ambiente de Desenvolvimento.",
  devspace: "Ambiente de Devspace.",
  staging: "Ambiente de Homologação.",
};

export function EnvironmentAlert() {
  const mode = import.meta.env.MODE;

  // Se estiver em produção, não mostra nada
  if (mode === "production") return null;

  const description = environmentMessages[mode] || `Ambiente: ${mode}`;

  return (
    <Box>
      <Alert status="warning" borderRadius="5px" fontSize={{ base: "xs", md: "sm" }}>
        <AlertIcon />
        <Box>
          <AlertTitle>Atenção!</AlertTitle>
          <AlertDescription>{description}</AlertDescription>
        </Box>
      </Alert>
    </Box>
  );
}
