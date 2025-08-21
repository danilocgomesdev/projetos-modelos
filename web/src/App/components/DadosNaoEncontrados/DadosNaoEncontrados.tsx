import { Box, Text } from "@chakra-ui/react";
import { ReactNode } from "react";

interface DadosNaoEncontradosParams {
  mensagem: string;
  altura: string;
  isError: boolean;
  children?: ReactNode;
}

export function DadosNaoEncontrados({
  mensagem,
  isError,
  children,
  altura,
}: DadosNaoEncontradosParams) {
  return (
    <Box
      display="flex"
      justifyContent="center"
      alignItems="center"
      height={altura}
      flexDirection="column"
    >
      {isError && <Text fontSize="2rem">❌</Text>}
      <Text>{mensagem}</Text>
      {children}
    </Box>
  );
}
