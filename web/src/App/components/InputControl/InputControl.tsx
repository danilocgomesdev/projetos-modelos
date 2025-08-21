import {
  FormControl,
  FormErrorMessage,
  FormHelperText,
  FormLabel,
  GridItem,
  Input,
  InputGroup,
  InputLeftElement,
} from "@chakra-ui/react";

import { ChangeEvent, ReactNode } from "react";

import { IMaskInput } from "react-imask";

interface InputControlProps {
  isError?: boolean;
  label?: string;
  messagemSucesso?: string;
  messagemErro?: string | undefined;
  cursor?: string;
  icon?: ReactNode;
  children?: ReactNode;
  naoEditavel?: boolean;
  onClick?: () => void;
  onBlur?: (e: ChangeEvent<HTMLInputElement>) => void;
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
  bgColor?: string;
  maxDate?: string;
  minDate?: string;
  disabled?: boolean;
}

export function InputControl({
  isError,
  label,
  messagemSucesso,
  messagemErro,
  icon,
  children,
  cursor,
  naoEditavel,
  onClick,
  onBlur,
  onChange,
  width,
  id,
  value,
  type,
  mascara,
  name,
  ref,
  maxLength,
  numeroColunasMd,
  numeroColunasLg,
  bgColor,
  maxDate,
  minDate,
  disabled,
}: InputControlProps) {
  return (
    <GridItem
      colSpan={{ base: 1, md: numeroColunasMd ?? 1, lg: numeroColunasLg ?? numeroColunasMd }}
      m={1}
      mb={0}
    >
      <FormControl isInvalid={isError}>
        <FormLabel fontSize="xs">{label}</FormLabel>
        <InputGroup borderColor="#E0E1E7">
          {icon ? <InputLeftElement pointerEvents="none">{icon}</InputLeftElement> : null}
          <Input
            type={type}
            cursor={cursor}
            size="sm"
            ref={ref}
            readOnly={naoEditavel}
            borderRadius={3}
            onClick={onClick}
            textTransform={type === "text" ? "uppercase" : "none"}
            width={width}
            id={id}
            name={name}
            value={value}
            onChange={onChange}
            as={mascara ? IMaskInput : undefined}
            mask={mascara}
            onBlur={onBlur}
            maxLength={maxLength}
            bgColor={bgColor}
            max={maxDate}
            min={minDate}
            disabled={disabled}
          />
          {children}
        </InputGroup>
        {!isError ? (
          <FormHelperText>{messagemSucesso}</FormHelperText>
        ) : (
          <FormErrorMessage>{messagemErro}</FormErrorMessage>
        )}
      </FormControl>
    </GridItem>
  );
}
