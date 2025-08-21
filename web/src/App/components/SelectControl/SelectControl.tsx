import { FormControl, FormLabel, GridItem, Select, useColorModeValue } from "@chakra-ui/react";

export type OptionsType = { value: string; label: string };

interface SelectControlParams {
  options: OptionsType[];
  label: string;
  onChange: (value: string) => void;
  id?: string;
  w?: string;
  colorborda?: string;
  value?: string;
  numeroColunasMd?: number;
  numeroColunasLg?: number;
}

export function SelectControl({
  options,
  label,
  onChange,
  id,
  w,
  colorborda,
  value,
  numeroColunasMd,
  numeroColunasLg,
  ...props
}: SelectControlParams) {
  return (
    <GridItem
      colSpan={{ base: 1, md: numeroColunasMd ?? 1, lg: numeroColunasLg ?? numeroColunasMd }}
      m={1}
      mb={0}
    >
      <FormControl>
        <FormLabel fontSize="xs">{label}</FormLabel>
        <Select
          {...props}
          size="sm"
          width={w}
          value={value}
          id={id}
          onChange={(e) => onChange(e.target.value)}
          borderRadius={3}
          borderColor={colorborda ? colorborda : useColorModeValue("gray.300", "white")}
          placeholder="-"
          w={"100%"}
        >
          {options.map((x) => (
            <option key={x.value} value={x.value}>
              {x.label}
            </option>
          ))}
        </Select>
      </FormControl>
    </GridItem>
  );
}
