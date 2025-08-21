import {
  Flex,
  FormControl,
  FormErrorMessage,
  FormHelperText,
  FormLabel,
  GridItem,
  Text,
  useColorMode,
} from "@chakra-ui/react";
import { ChangeEvent, ReactNode } from "react";

interface TextControlProps {
  isError?: boolean;
  label: string;
  messagemSucesso?: string;
  messagemErro?: string | undefined;
  cursor?: string;
  icon?: ReactNode;
  children?: ReactNode;
  naoEditavel?: boolean;
  onClick?: () => void;
  onChange?: (e: ChangeEvent<HTMLInputElement>) => void;
  width?: string;
  id?: string;
  value?: string;
  type?: string;
  mascara?: string;
  name?: string;
  ref?: string;
  maxLength?: number;
  numeroColunasMd?: number;
  numeroColunasLg?: number;
}

export function TextControl({
  isError,
  label,
  messagemSucesso,
  messagemErro,
  children,
  cursor,
  onClick,
  onChange,
  width,
  id,
  value,
  type,
  numeroColunasMd,
  numeroColunasLg,
}: TextControlProps) {
  return (
    <GridItem
      colSpan={{ base: 1, md: numeroColunasMd ?? 1, lg: numeroColunasLg ?? numeroColunasMd }}
      m={1}
    >
      <FormControl
        isInvalid={isError}
        borderRadius="md"
        border={"1px solid "}
        borderColor={useColorMode().colorMode === "light" ? "gray.200" : "gray.600"}
      >
        <Flex alignItems="center" ml={2}>
          <FormLabel fontSize="xs" mb={0}>
            {label}
          </FormLabel>
          <Text
            cursor={cursor}
            size="sm"
            width={width}
            id={id}
            onClick={onClick} // Mantendo a ação de clique, se necessário
            onChange={onChange} // Você pode remover essa linha se não precisar dela
            textTransform={type === "text" ? "uppercase" : "none"}
            pt={1} // Pequeno padding superior para alinhar com o rótulo
            ml={1} // Margem esquerda para espaçar do rótulo
          >
            {value}
          </Text>
          {children}
        </Flex>
        {!isError ? (
          <FormHelperText>{messagemSucesso}</FormHelperText>
        ) : (
          <FormErrorMessage>{messagemErro}</FormErrorMessage>
        )}
      </FormControl>
    </GridItem>
  );
}
