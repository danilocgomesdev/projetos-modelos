import {
  Box,
  FormControl,
  FormErrorMessage,
  FormLabel,
  GridItem,
  Select,
  SelectProps,
  Text,
  useColorModeValue,
} from "@chakra-ui/react";
import React from "react";
import { Controller, useFormContext } from "react-hook-form";

type ContextSelectProps = SelectProps & {
  name: string;
  label?: string;
  options: { value: string; label: string }[];
  naoEditavel?: boolean;
  aceitaVazio?: boolean;
  numeroColunasMd?: number;
  numeroColunasLg?: number;
  obrigatorio?: boolean;
};

export const SelectForm: React.FC<ContextSelectProps> = ({
  name,
  label,
  options,
  naoEditavel,
  aceitaVazio,
  numeroColunasMd,
  numeroColunasLg,
  obrigatorio,
  ...props
}) => {
  const {
    control,
    formState: { errors },
  } = useFormContext();

  aceitaVazio = aceitaVazio ?? true;

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
        <Box>
          <Controller
            name={name}
            control={control}
            render={({ field }) => (
              <Select
                {...field}
                {...props}
                size="sm"
                borderColor="#808080"
                borderRadius={3}
                isReadOnly={naoEditavel}
                w={"100%"}
              >
                {aceitaVazio && <option disabled={naoEditavel}>{""}</option>}
                {options.map((option) => (
                  <option key={option.value} value={option.value} disabled={naoEditavel}>
                    {option.label}
                  </option>
                ))}
              </Select>
            )}
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
        </Box>
      </FormControl>
    </GridItem>
  );
};
