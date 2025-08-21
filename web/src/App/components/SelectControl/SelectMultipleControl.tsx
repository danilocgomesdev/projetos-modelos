import { FormControl, FormLabel, GridItem } from "@chakra-ui/react";
import { GroupBase, OptionBase, Select } from "chakra-react-select";
import { useEffect, useState } from "react";

interface OptionsType extends OptionBase {
  value: string;
  label: string;
}

interface SelectMultipleControlParams {
  options: OptionsType[];
  label: string;
  onChange: (values: string[]) => void;
  id?: string;
  valorInicial?: string[];
  numeroColunasMd?: number;
  numeroColunasLg?: number;
}

export function SelectMultipleControl({
  options,
  label,
  onChange,
  id,
  valorInicial,
  numeroColunasMd,
  numeroColunasLg,
}: SelectMultipleControlParams) {
  const [val, setVal] = useState<OptionsType[] | undefined>();

  useEffect(() => {
    if (valorInicial) {
      setVal(options.filter((op) => valorInicial.indexOf(op.value) !== -1));
    }
  }, []);

  return (
    <GridItem
      colSpan={{ base: 1, md: numeroColunasMd ?? 1, lg: numeroColunasLg ?? numeroColunasMd }}
      m={1}
      mb={0}
    >
      <FormControl>
        <FormLabel fontSize="xs">{label}</FormLabel>
        <Select<OptionsType, true, GroupBase<OptionsType>>
          name={label}
          size="sm"
          isMulti
          placeholder="-"
          // value={values && options.filter((op) => values.indexOf(op.label) !== -1)}
          value={val}
          id={id}
          onChange={(newValues) => {
            if (newValues) {
              const optionValues = [...newValues.entries()].map(([, option]) => option);
              const notification = optionValues.map((it) => it.value);
              onChange(notification);
              setVal(optionValues);
            } else {
              setVal([]);
            }
          }}
          // onChange={console.log}
          options={options}
        />
      </FormControl>
    </GridItem>
  );
}
