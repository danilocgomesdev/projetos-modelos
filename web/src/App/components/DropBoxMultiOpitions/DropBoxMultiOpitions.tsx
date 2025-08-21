import {
  Button,
  Checkbox,
  Divider,
  FormControl,
  FormLabel,
  GridItem,
  Menu,
  MenuButton,
  MenuItem,
  MenuList,
  Text,
  VStack,
  useColorModeValue,
} from "@chakra-ui/react";
import { useState } from "react";
import { FiChevronDown } from "react-icons/fi";

interface Option {
  label: string;
  value: string;
}

interface DropBoxMultiOpitionsProps {
  onChange: (valores: string[]) => void;
  mostrarLabel?: boolean;
  numeroColunasMd?: number;
  numeroColunasLg?: number;
  titulo?: string;
  options: Option[];
}

export function DropBoxMultiOpitions({
  onChange,
  mostrarLabel,
  numeroColunasMd,
  numeroColunasLg,
  titulo,
  options,
}: DropBoxMultiOpitionsProps) {
  const [selectedValues, setSelectedValues] = useState<string[]>([]);
  const allSelected = selectedValues.length === options.length;
  const someSelected = selectedValues.length > 0 && !allSelected;

  const handleToggle = (value: string) => {
    const updated = selectedValues.includes(value)
      ? selectedValues.filter((v) => v !== value)
      : [...selectedValues, value];

    setSelectedValues(updated);
    onChange(updated);
  };

  const handleToggleAll = () => {
    if (allSelected) {
      handleClear();
    } else {
      const allValues = options.map((opt) => opt.value);
      setSelectedValues(allValues);
      onChange(allValues);
    }
  };

  const handleClear = () => {
    setSelectedValues([]);
    onChange([]);
  };

  const getLabel = () => {
    if (selectedValues.length === 0) return "Selecione...";
    return options
      .filter((opt) => selectedValues.includes(opt.value))
      .map((opt) => opt.label)
      .join("; ");
  };

  const borderColor = useColorModeValue("gray.300", "white");
  const bgColor = useColorModeValue("white", "dark.primary");

  return (
    <GridItem
      colSpan={{ base: 1, md: numeroColunasMd ?? 1, lg: numeroColunasLg ?? numeroColunasMd }}
      mb={0}
    >
      <FormControl mt={1} mb={0}>
        {mostrarLabel ?? true ? <FormLabel fontSize="xs">{titulo}</FormLabel> : null}
        <Menu closeOnSelect={false} matchWidth>
          <MenuButton
            as={Button}
            rightIcon={<FiChevronDown />}
            w="100%"
            textAlign="left"
            variant="outline"
            h="32px"
            px="12px"
            fontSize="sm"
            lineHeight="1"
            border="1px solid"
            borderColor={borderColor}
            borderRadius="3"
            bg={bgColor}
            _focus={{ boxShadow: "outline" }}
            _focusVisible={{ boxShadow: "outline" }}
            _hover={{ borderColor: "gray.300" }}
            overflow="hidden"
          >
            <Text isTruncated fontWeight={"normal"}>
              {getLabel()}
            </Text>
          </MenuButton>
          <MenuList w="100%" zIndex={10} maxH="250px" overflowY="auto">
            <MenuItem w="100%" onClick={(e) => e.stopPropagation()}>
              <Checkbox
                isChecked={allSelected}
                isIndeterminate={someSelected}
                onChange={handleToggleAll}
              >
                <Text fontSize="sm" fontWeight="normal">
                  Selecionar todos
                </Text>
              </Checkbox>
            </MenuItem>
            <Divider />
            <VStack align="start" p={2}>
              {options.map((opt) => (
                <MenuItem key={opt.value} w="100%" onClick={(e) => e.stopPropagation()}>
                  <Checkbox
                    isChecked={selectedValues.includes(opt.value)}
                    onChange={() => handleToggle(opt.value)}
                  >
                    <Text fontSize="sm" fontWeight="normal">
                      {opt.label}
                    </Text>
                  </Checkbox>
                </MenuItem>
              ))}
            </VStack>
          </MenuList>
        </Menu>
      </FormControl>
    </GridItem>
  );
}
