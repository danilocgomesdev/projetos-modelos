import { Box, CircularProgress, Text } from "@chakra-ui/react";

interface LoadingParams {
  mensagem: string;
  altura: string;
}

export function Loading({ mensagem, altura }: LoadingParams) {
  return (
    <Box
      display="flex"
      justifyContent="center"
      alignItems="center"
      h={altura}
      flexDirection="column"
      m={2}
    >
      <CircularProgress isIndeterminate size="80px" color="#2B6CB0" />
      <Text fontSize="md" pt={2}>
        {mensagem}
      </Text>
    </Box>
  );
}
