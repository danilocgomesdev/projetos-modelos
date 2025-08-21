import { Grid } from "@chakra-ui/react";
import { FiSearch } from "react-icons/fi";
import { PessoaCIDTO } from "../../../../../models/DTOs/Outros/Gestor";
import { UnidadeDTO } from "../../../../../models/DTOs/Outros/UnidadeDTO";
import { InputForm } from "../../../../components/InputControl";

interface FormGestorProps {
  onOpenModalUnidade: () => void;
  unidadeFiltrado: UnidadeDTO | undefined;
  onOpenModalCIPessoas: () => void;
  pessoaFiltrada: PessoaCIDTO | undefined;
}

export function FormGestor({
  onOpenModalUnidade,
  onOpenModalCIPessoas,
  unidadeFiltrado,
  pessoaFiltrada,
}: FormGestorProps) {
  return (
    <Grid w="100%" templateColumns={{ base: "100%", md: "repeat(4, 1fr)" }} mt={4} p={1}>
      {pessoaFiltrada?.nome ? (
        <InputForm
          name="Nome"
          size="sm"
          label="nome"
          naoEditavel
          value={pessoaFiltrada?.nome}
          obrigatorio
          numeroColunasMd={4}
          cursor="pointer"
          icon={<FiSearch />}
          pl={10}
        />
      ) : (
        <InputForm
          name="nome"
          size="sm"
          label="Nome"
          naoEditavel
          value={pessoaFiltrada?.nome}
          obrigatorio
          numeroColunasMd={4}
          onClick={onOpenModalCIPessoas}
          cursor="pointer"
          icon={<FiSearch />}
          pl={10}
        />
      )}
      <InputForm
        size="sm"
        name="email"
        label="E-mail"
        type="email"
        naoEditavel
        value={pessoaFiltrada?.email}
        numeroColunasMd={2}
        obrigatorio
      />
      <InputForm
        size="sm"
        name="matricula"
        label="Matrícula"
        type="number"
        naoEditavel
        value={pessoaFiltrada?.matricula}
        numeroColunasMd={1}
        obrigatorio
      />
      <InputForm
        size="sm"
        name="idCiPessoas"
        label="Código Pessoa"
        type="number"
        naoEditavel
        value={pessoaFiltrada?.id}
        numeroColunasMd={1}
        obrigatorio
      />
      <InputForm
        size="sm"
        name="descricao"
        label="Descrição"
        textTransform="uppercase"
        numeroColunasMd={4}
        obrigatorio
      />
      <InputForm
        name="idUnidade"
        size="sm"
        label="Unidade"
        naoEditavel
        value={
          unidadeFiltrado && unidadeFiltrado.nome
            ? `${unidadeFiltrado?.codigo} - ${unidadeFiltrado?.nome}`
            : unidadeFiltrado && unidadeFiltrado.descricaoUnidade
            ? `${unidadeFiltrado?.codigo} - ${unidadeFiltrado?.descricaoUnidade}`
            : ""
        }
        obrigatorio
        numeroColunasMd={4}
        onClick={onOpenModalUnidade}
        cursor="pointer"
      />
    </Grid>
  );
}
