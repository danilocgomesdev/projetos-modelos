import { Button, Grid, GridItem } from "@chakra-ui/react";
import { ReactNode } from "react";
import { FiPlusCircle, FiPrinter } from "react-icons/fi";
import { useNavigate } from "react-router-dom";
import { useFuncaoNotificacao } from "../../hooks/useFuncaoNotificacao";

interface CustomButton {
  label: string;
  icon?: ReactNode;
  onClick: () => void;
  colorScheme?: string;
}

interface ButtonControlProps {
  onNovoClick?: () => void;
  onRelatorioClick?: () => void;
  customButtons?: CustomButton[];
}

export function ButtonControl({
  onNovoClick,
  onRelatorioClick,
  customButtons = [],
}: ButtonControlProps) {
  const navigate = useNavigate();
  const notificacao = useFuncaoNotificacao();

  return (
    <Grid
      w="100%"
      templateColumns={{ base: "1fr", md: `repeat(${2 + customButtons.length}, auto)` }}
      justifyContent={{ base: "stretch", md: "flex-end" }}
      mb={2}
      mt={2}
    >
      {customButtons.map((btn, idx) => (
        <GridItem key={idx} minW="180px" m={1}>
          <Button colorScheme="blue" size="sm" w="100%" onClick={btn.onClick}>
            {btn.icon && <span style={{ marginRight: "0.2rem" }}>{btn.icon}</span>}
            {btn.label}
          </Button>
        </GridItem>
      ))}

      <GridItem minW="180px" m={1}>
        {onNovoClick ? (
          <Button colorScheme="blue" size="sm" w="100%" onClick={() => onNovoClick()}>
            <FiPlusCircle style={{ marginRight: "0.2rem" }} />
            Novo
          </Button>
        ) : (
          <Button colorScheme="blue" size="sm" w="100%" onClick={() => navigate("./novo")}>
            <FiPlusCircle style={{ marginRight: "0.2rem" }} />
            Novo
          </Button>
        )}
      </GridItem>

      {onRelatorioClick && (
        <GridItem minW="180px" m={1}>
          <Button
            colorScheme="blue"
            size="sm"
            w="100%"
            onClick={
              onRelatorioClick ||
              (() =>
                notificacao({
                  titulo: "Relatório não disponível",
                  message: "Por enquanto, esse relatório não está disponível",
                  tipo: "warning",
                }))
            }
          >
            <FiPrinter style={{ marginRight: "0.2rem" }} />
            Relatório
          </Button>
        </GridItem>
      )}
    </Grid>
  );
}
