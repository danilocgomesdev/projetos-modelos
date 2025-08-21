import { Grid } from "@chakra-ui/react";
import { FiSearch } from "react-icons/fi";
import { BancoDTO } from "../../../../../models/DTOs/AgenciaBancoConta/BancoDTO";
import { InputForm } from "../../../../components/InputControl";

interface FormAgenciaProps {
  onOpen: () => void;
  bancoFiltrado: BancoDTO | undefined;
}

export function FormAgencia({ onOpen, bancoFiltrado }: FormAgenciaProps) {
  return (
    <Grid w="100%" templateColumns={{ base: "100%", md: "repeat(4, 1fr)" }} mt={4} p={1}>
      <InputForm
        name="idBanco"
        size="sm"
        label="Número Banco"
        textTransform="uppercase"
        naoEditavel
        value={bancoFiltrado?.numero}
        obrigatorio
        numeroColunasMd={1}
        onClick={onOpen}
        cursor="pointer"
      />
      <InputForm
        name="banco"
        size="sm"
        label="Banco"
        textTransform="uppercase"
        naoEditavel
        icon={<FiSearch />}
        cursor="pointer"
        onClick={onOpen}
        value={bancoFiltrado?.nome}
        pl={10}
        obrigatorio
        numeroColunasMd={3}
      />
      <InputForm
        name="numero"
        type="number"
        size="sm"
        label="N° Agência"
        textTransform="uppercase"
        numeroColunasMd={2}
        obrigatorio
      />
      <InputForm
        name="digitoVerificador"
        size="sm"
        label="Dígito Verificador"
        textTransform="uppercase"
        maxLength={1}
        numeroColunasMd={2}
      />
      <InputForm
        name="nome"
        size="sm"
        label="Nome"
        textTransform="uppercase"
        numeroColunasMd={3}
        obrigatorio
      />
      <InputForm
        name="cnpj"
        type="text"
        size="sm"
        label="CNPJ"
        textTransform="uppercase"
        mascara="00.000.000/0000-00"
        numeroColunasMd={1}
        obrigatorio
      />
      <InputForm
        name="cidade"
        size="sm"
        label="Cidade"
        textTransform="uppercase"
        numeroColunasMd={4}
        obrigatorio
      />
    </Grid>
  );
}
