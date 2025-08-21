import {
  FormControl,
  FormErrorMessage,
  FormLabel,
  GridItem,
  Input,
  InputGroup,
  InputLeftElement,
  InputProps,
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
  numeroColunasMd?: number;
  numeroColunasLg?: number;
};

export const InputFormMoeda: React.FC<ContextInputProps> = ({
  name,
  label,
  icon,
  mascara,
  naoEditavel,
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
      colSpan={{ base: 1, md: numeroColunasMd ?? 1, lg: numeroColunasLg ?? numeroColunasMd }}
      m={1}
    >
      <FormControl isInvalid={errors[name] !== undefined}>
        {!!label && <FormLabel fontSize="xs">{label}</FormLabel>}
        <InputGroup borderColor="gray">
          {!!icon && <InputLeftElement pointerEvents="none">{icon}</InputLeftElement>}
          <Controller
            name={name}
            control={control}
            render={({ field }) => {
              const onChangeFilds = field.onChange;
              const onChange = props.onChange;
              return (
                <Input
                  {...field}
                  {...props}
                  borderRadius={3}
                  as={mascara ? IMaskInput : undefined}
                  mask={mascara}
                  readOnly={naoEditavel}
                  onChange={(e) => {
                    if (onChange) {
                      onChange(e);
                    }
                    onChangeFilds(e);
                  }}
                />
              );
            }}
          />
        </InputGroup>
        {errors[name] && <FormErrorMessage>{errors[name]?.message?.toString()}</FormErrorMessage>}
      </FormControl>
    </GridItem>
  );
};
