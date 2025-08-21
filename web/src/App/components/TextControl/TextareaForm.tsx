import {
  FormControl,
  FormErrorMessage,
  FormLabel,
  GridItem,
  Text,
  Textarea,
  useColorModeValue,
} from "@chakra-ui/react";
import React, { ReactNode } from "react";
import { Controller, useFormContext } from "react-hook-form";

type ContextTextareaProps = {
  name: string;
  label?: string;
  icon?: ReactNode;
  obrigatorio?: boolean;
  naoEditavel?: boolean;
  defaultValue?: string;
  numeroColunasMd?: number;
  numeroColunasLg?: number;
};

export const TextareaForm: React.FC<ContextTextareaProps> = ({
  name,
  label,
  icon,
  obrigatorio,
  naoEditavel,
  defaultValue,
  numeroColunasMd,
  numeroColunasLg,
  ...props
}) => {
  const {
    control,
    formState: { errors },
  } = useFormContext();

  return (
    <GridItem
      colSpan={{
        base: 1,
        md: numeroColunasMd ?? 1,
        lg: numeroColunasLg ?? numeroColunasMd,
      }}
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
        <Controller
          name={name}
          control={control}
          render={({ field }) => {
            field.value = field.value ?? "";

            return (
              <Textarea
                {...field}
                {...props}
                defaultValue={defaultValue}
                isReadOnly={naoEditavel}
                borderRadius={3}
                resize="vertical"
              />
            );
          }}
        />
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
