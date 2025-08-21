import { Box, Center, Divider, Grid, GridItem, Heading, Text } from "@chakra-ui/react";
import { useEffect, useState } from "react";
import { fetchVersao } from "../../../requests/healthcheck";
import { CabecalhoPages } from "../../components/CabecalhoPages";
import useCR5Axios from "../../hooks/useCR5Axios";

export function Sobre() {
  const equipe = [
    { cargo: "Gerente", nome: "Marcos Paulo Fraga" },
    { cargo: "Coordenador", nome: "Wilton Almeida Mendonça" },
    {
      cargo: "Analistas",
      nomes: ["Alex Fernandes de Moura", "Danilo Carlos Gomes", "Denise Oliveira Cruz"],
    },
  ];

  const { axios } = useCR5Axios();
  const [versaoBack, setVersaoBack] = useState("Não determinado");

  useEffect(() => {
    fetchVersao(axios)
      .then(({ versao }) => {
        setVersaoBack(versao);
      })
      .catch((e) => console.error("Erro ao buscar versão do backend", e));
  }, []);

  const renderGrid = (titulo: string, valores: string | string[]) => {
    return (
      <Grid templateColumns={{ base: "1fr", md: "repeat(4, 1fr)" }} gap={2} w="full" mb={5}>
        <GridItem colSpan={{ base: 1, md: 1 }}>
          <Text fontWeight="bold" ml={{ base: 2, md: 0 }}>
            {titulo}:
          </Text>
        </GridItem>
        <GridItem colSpan={{ base: 1, md: 3 }}>
          {Array.isArray(valores) ? (
            valores.map((val) => (
              <Text key={val} ml={{ base: 4, md: 0 }}>
                {val}
              </Text>
            ))
          ) : (
            <Text ml={{ base: 4, md: 0 }}>{valores}</Text>
          )}
        </GridItem>
      </Grid>
    );
  };

  return (
    <Box display="flex" alignItems="center" flexDirection="column">
      <CabecalhoPages titulo="Sobre" />
      <Box w={{ base: "100%", md: "auto", lg: "55%" }} mt={6}>
        <Center mb={5}>
          <Heading size="md">EQUIPE TÉCNICA - GETIN</Heading>
        </Center>
        {equipe.map((item) => renderGrid(item.cargo, item.nomes ?? item.nome))}
        <Divider mt={4} mb={4} />
        {renderGrid("Versão", process.env.APP_VERSION ?? "Não determinado")}
        {renderGrid("Versão do Servidor", versaoBack)}
      </Box>
    </Box>
  );
}
