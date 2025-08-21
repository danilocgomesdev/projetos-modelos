import { Box, Heading, Text } from "@chakra-ui/react";
import { VisaoOperadorDTO } from "../../../models/DTOs/Visoes/VisaoOperadorDTO";
import { formatarDataHoraBrasil } from "../../../utils/mascaras";

export interface InfoData {
  titulo: string;
  data: string | number | Date;
}

export function DetalhesOperador({
  titulo,
  operador,
  infoData,
}: {
  titulo: string;
  operador: VisaoOperadorDTO;
  infoData: InfoData;
}) {
  return (
    <Box fontSize="sm">
      <Heading size="xs" textTransform="uppercase" mb={3}>
        {titulo}
      </Heading>
      <Text>
        <strong>Nome:</strong> {operador.nome}
      </Text>
      <Text>
        <strong>Usuário:</strong> {operador.usuario}
      </Text>
      <Text>
        <strong>Matricula:</strong> {operador.matricula}
      </Text>
      <Text>
        <strong>Id Operador:</strong> {operador.idOperador}
      </Text>
      <Text>
        <strong>{infoData.titulo}:</strong> {formatarDataHoraBrasil(infoData.data)}
      </Text>
    </Box>
  );
}
