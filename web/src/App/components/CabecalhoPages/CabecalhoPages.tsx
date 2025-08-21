import { Card, CardBody, Heading, Text, useColorModeValue } from "@chakra-ui/react";

export function CabecalhoPages({ titulo, subtitulo }: { titulo: string; subtitulo?: string }) {
  return (
    <Card w="100%" textAlign="center" bg={useColorModeValue("light.cabecalho", "dark.cabecalho")}>
      <CardBody>
        <Heading size={{ base: "sm", md: "md" }}>{titulo}</Heading>
        {!!subtitulo && (
          <Text fontSize={{ base: "xs", md: "sm" }} fontStyle={"italic"}>
            {subtitulo}
          </Text>
        )}
      </CardBody>
    </Card>
  );
}
