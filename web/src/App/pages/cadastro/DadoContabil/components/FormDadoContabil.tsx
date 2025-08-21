import { AbsoluteCenter, Box, Divider, Grid, GridItem, useColorModeValue } from "@chakra-ui/react";
import { FiSearch } from "react-icons/fi";
import { ContaContabilProtheusDTO } from "../../../../../models/DTOs/Contabil/ContaContabilProtheusDTO";
import { ItemContabilProtheusDTO } from "../../../../../models/DTOs/Contabil/ItemContabilProtheusDTO";
import { NaturezaProtheusDTO } from "../../../../../models/DTOs/Protheus/NaturezaProtheusDTO";
import { OptionsEntidades } from "../../../../../utils/componentUtil";
import { InputForm } from "../../../../components/InputControl";
import { SelectForm } from "../../../../components/SelectControl";

interface FormDadoContabilProps {
  onOpenContaContabil: () => void;
  onOpenItemContabil: () => void;
  onOpenNatureza: () => void;
  contaContabilProtheusFiltrado: ContaContabilProtheusDTO | undefined;
  itemContabilProtheusFiltrado: ItemContabilProtheusDTO | undefined;
  naturezaProtheusFiltrado: NaturezaProtheusDTO | undefined;
}

export function FormDadoContabil({
  onOpenContaContabil,
  onOpenItemContabil,
  onOpenNatureza,
  contaContabilProtheusFiltrado,
  itemContabilProtheusFiltrado,
  naturezaProtheusFiltrado,
}: FormDadoContabilProps) {
  return (
    <>
      <Grid w="100%" templateColumns={{ base: "100%", md: "repeat(4, 1fr)" }} mt={4}>
        <SelectForm
          name="idEntidade"
          label="Entidade"
          w="100%"
          options={OptionsEntidades}
          numeroColunasMd={2}
          obrigatorio
        />
        <SelectForm
          name="dataInativacao"
          label="Status"
          w="100%"
          numeroColunasMd={2}
          obrigatorio
          options={[
            {
              value: "A",
              label: "ATIVO",
            },
            {
              value: "F",
              label: "INATIVO",
            },
          ]}
        />
        <InputForm
          name="contaContabil"
          size="sm"
          label="Conta Contábil"
          textTransform="uppercase"
          icon={<FiSearch />}
          cursor="pointer"
          onClick={onOpenContaContabil}
          pl={10}
          value={contaContabilProtheusFiltrado?.contaContabil}
          obrigatorio
          naoEditavel
        />
        <InputForm
          name="contaContabilDescricao"
          size="sm"
          label="Descrição Conta Contábil"
          textTransform="uppercase"
          numeroColunasMd={3}
          value={contaContabilProtheusFiltrado?.contaContabilDescricao}
          obrigatorio
          naoEditavel
        />
        <InputForm
          name="itemContabil"
          size="sm"
          label="Item Contábil"
          textTransform="uppercase"
          icon={<FiSearch />}
          cursor="pointer"
          onClick={onOpenItemContabil}
          pl={10}
          value={itemContabilProtheusFiltrado?.itemContabil}
          obrigatorio
          naoEditavel
        />
        <InputForm
          name="itemContabilDescricao"
          size="sm"
          label="Descrição Item Contábil"
          textTransform="uppercase"
          numeroColunasMd={3}
          cursor="pointer"
          onClick={onOpenItemContabil}
          value={itemContabilProtheusFiltrado?.itemContabilDescricao}
          obrigatorio
          naoEditavel
        />

        <GridItem colSpan={{ base: 1, md: 4 }} m={1}>
          <Box position="relative" padding="5">
            <Divider />
            <AbsoluteCenter
              bg={useColorModeValue("light.text", "dark.primary")}
              px="4"
              fontSize={"sm"}
            >
              Natureza do Produto
            </AbsoluteCenter>
          </Box>
        </GridItem>
        <InputForm
          name="natureza"
          size="sm"
          label="Código Natureza"
          textTransform="uppercase"
          icon={<FiSearch />}
          cursor="pointer"
          onClick={onOpenNatureza}
          pl={10}
          value={naturezaProtheusFiltrado?.natureza}
          obrigatorio
          naoEditavel
        />
        <InputForm
          numeroColunasMd={3}
          name="naturezaDescricao"
          size="sm"
          label="Descricão Código Natureza"
          textTransform="uppercase"
          obrigatorio
          value={naturezaProtheusFiltrado?.naturezaDescricao}
          naoEditavel
        />
      </Grid>
    </>
  );
}
