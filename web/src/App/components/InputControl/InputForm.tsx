import {
  FormControl,
  FormErrorMessage,
  FormLabel,
  GridItem,
  Input,
  InputGroup,
  InputLeftElement,
  InputProps,
  Text,
  useColorModeValue,
} from "@chakra-ui/react";
import React, { ReactNode } from "react";
import { Controller, useFormContext } from "react-hook-form";
import { IMaskInput } from "react-imask";

type ContextInputProps = InputProps & {
  name: string;
  label?: string;
  icon?: ReactNode;
  mascara?: string;
  naoEditavel?: boolean;
  defaultValue?: number;
  obrigatorio?: boolean;
  numeroColunasMd?: number;
  numeroColunasLg?: number;
  type?: string;
};

export const InputForm: React.FC<ContextInputProps> = ({
  name,
  label,
  icon,
  mascara,
  obrigatorio,
  naoEditavel,
  defaultValue,
  numeroColunasMd,
  numeroColunasLg,
  type,
  ...props
}) => {
  const {
    control,
    formState: { errors },
  } = useFormContext();

  return (
    <GridItem
      colSpan={{ base: 1, md: numeroColunasMd ?? 1, lg: numeroColunasLg ?? numeroColunasMd }}
      m={1}
    >
      <FormControl isInvalid={errors[name] !== undefined}>
        {!!label && (
          <FormLabel fontSize="xs" display={"flex"}>
            {label}
            {obrigatorio && (
              <Text ml={0.5} color={useColorModeValue("red", "red.300")}>
                *
              </Text>
            )}
          </FormLabel>
        )}
        <InputGroup borderColor="gray">
          {!!icon && <InputLeftElement pointerEvents="none">{icon}</InputLeftElement>}
          <Controller
            name={name}
            control={control}
            render={({ field }) => {
              // Evitar valor undefined
              field.value = field.value ?? "";

              return (
                <Input
                  {...field}
                  {...props}
                  defaultValue={defaultValue}
                  borderRadius={3}
                  as={mascara ? IMaskInput : undefined}
                  mask={mascara}
                  readOnly={naoEditavel}
                  textTransform={type === "text" ? "uppercase" : "none"}
                />
              );
            }}
          />
        </InputGroup>
        {errors[name] && (
          <FormErrorMessage
            maxW={"100%"}
            mt={2}
            whiteSpace="normal" // Permite quebras de linha normais
            wordBreak="break-word" // Quebra a palavra se for muito longa
          >
            {errors[name]?.message?.toString()}
          </FormErrorMessage>
        )}
      </FormControl>
    </GridItem>
  );
};
