import { Box, Collapse, Td, Tr, useColorModeValue } from "@chakra-ui/react";
import React, { ReactNode, useCallback, useState } from "react";

type Expandido =
  | { type: "Expandido"; componente: ReactNode }
  | {
      type: "OnClick";
      onClick: () => void;
    };

type Selecionavel = {
  type: "linha";
  onClick: () => void;
};

interface LinhaDeTabelaProps {
  children: ReactNode;
  index: number;
  componenteExpandido?: Expandido;
  onClickRow?: Selecionavel;
}

// Componente de linha de tabela personalizado
export function LinhaDeTabela({
  children,
  index,
  componenteExpandido,
  onClickRow,
}: LinhaDeTabelaProps): JSX.Element {
  const [estaExpandido, setEstaExpandido] = useState(false);
  const corTabela = useColorModeValue("light.tabela", "dark.tabela");

  const handleClick = useCallback(() => {
    if (componenteExpandido?.type === "Expandido") {
      setEstaExpandido((prev) => !prev);
    } else if (componenteExpandido?.type === "OnClick") {
      componenteExpandido.onClick();
    }
    if (onClickRow?.type === "linha") {
      onClickRow.onClick();
    }
  }, [componenteExpandido, onClickRow]);

  const background = index % 2 === 0 ? "" : corTabela;

  return (
    <>
      <Tr
        onClick={handleClick}
        cursor={componenteExpandido || onClickRow?.type === "linha" ? "pointer" : "default"}
        bg={background}
      >
        {children}
      </Tr>
      {componenteExpandido?.type === "Expandido" && (
        <Tr>
          <Td colSpan={React.Children.count(children)} height={0} p={0}>
            <Collapse in={estaExpandido} animateOpacity>
              <Box p="5px" mt="1" rounded="md" bg={background} shadow="md">
                {componenteExpandido.componente}
              </Box>
            </Collapse>
          </Td>
        </Tr>
      )}
    </>
  );
}
