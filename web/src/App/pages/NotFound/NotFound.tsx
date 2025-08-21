import { Box, Heading } from "@chakra-ui/react";
export function NotFound() {
  return (
    <Box py={10} px={10} textAlign="center">
      <Heading as="h1" size="4xl" mb={2}>
        ⚠️404
      </Heading>
      <Heading as="h4" size="lg">
        Pagina não encontrada!
      </Heading>
    </Box>
  );
}
