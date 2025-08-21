import { Grid } from "@chakra-ui/react";
import { FiSearch } from "react-icons/fi";
import { AgenciaDTO } from "../../../../../models/DTOs/AgenciaBancoConta/AgenciaDTO";
import { InputForm } from "../../../../components/InputControl";

interface FormContaCorrenteProps {
  onOpen: () => void;
  agenciaFiltrada: AgenciaDTO | undefined;
}

export function FormContaCorrente({ onOpen, agenciaFiltrada }: FormContaCorrenteProps) {
  return (
    <Grid w="100%" templateColumns={{ base: "100%", md: "repeat(4, 1fr)" }} mt={4}>
      <InputForm
        name="numeroAgencia"
        size="sm"
        label="Número Agência"
        textTransform="uppercase"
        naoEditavel={true}
        value={agenciaFiltrada?.numero}
      />
      <InputForm
        name="nomeAgencia"
        size="sm"
        label="Agência"
        textTransform="uppercase"
        naoEditavel={true}
        icon={<FiSearch />}
        cursor="pointer"
        onClick={onOpen}
        value={agenciaFiltrada?.nome}
        pl={10}
        numeroColunasMd={3}
      />

      <InputForm name="numeroConta" size="sm" label="N° Conta" numeroColunasMd={2} />
      <InputForm name="digitoConta" size="sm" label="Dígito" maxLength={1} />
      <InputForm name="numeroOperacao" size="sm" label="N° Operação" />

      <InputForm name="contaBanco" size="sm" label="Conta Banco" numeroColunasMd={2} />
      <InputForm name="contaCliente" size="sm" label="Conta Cliente" numeroColunasMd={2} />

      <InputForm name="contaCaixa" size="sm" label="Conta Caixa" numeroColunasMd={2} />
      <InputForm name="contaJuros" size="sm" label="Conta Juros" numeroColunasMd={2} />

      <InputForm name="contaDescontos" size="sm" label="Conta Descontos" numeroColunasMd={2} />
      <InputForm name="cofreBanco" size="sm" label="Cofre Banco" numeroColunasMd={2} />

      <InputForm name="cofreAgencia" size="sm" label="Cofre Agencia" numeroColunasMd={2} />
      <InputForm name="cofreConta" size="sm" label="Cofre Conta" numeroColunasMd={2} />
    </Grid>
  );
}
