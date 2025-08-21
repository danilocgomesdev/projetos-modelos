import { Center, Divider, Flex, List, ListItem } from "@chakra-ui/react";
import { ReactNode } from "react";

export interface Detalhe {
  nome: string;
  valor: string | null | undefined;
}

export interface DetalhesLinhaTabelaProps {
  detalhes: Detalhe[][];
  children?: ReactNode;
}

export function DetalhesLinhaTabela({ detalhes, children }: DetalhesLinhaTabelaProps) {
  const colunas = detalhes.length;

  const width = (100 / colunas).toFixed(2) + "%";

  return (
    <Flex flexDirection="row" padding={1} margin={2} justifyContent="space-around" fontSize="sm">
      {detalhes.map((valores, index) => (
        <Flex
          w={width}
          key={valores.map((det) => det.nome).reduce((acc, cur) => acc + "#" + cur, "")}
        >
          {index != 0 && (
            <Center height="100%" mr={5}>
              <Divider orientation="vertical" />
            </Center>
          )}
          <List>
            {valores.map((detalhe) => (
              <ListItem key={detalhe.nome}>
                <strong>{detalhe.nome}:</strong> {detalhe.valor ?? ""}
              </ListItem>
            ))}
          </List>
        </Flex>
      ))}
      {children}
    </Flex>
  );
}
