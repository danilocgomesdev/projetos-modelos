import { AbsoluteCenter, Box, Divider, Grid, GridItem, useColorModeValue } from "@chakra-ui/react";
import { useState } from "react";
import { useFormContext } from "react-hook-form";
import { FiSearch } from "react-icons/fi";
import { EnderecoDTO } from "../../../../../models/DTOs/Outros/EnderecoDTO";
import { fetchEndereco } from "../../../../../requests/requestsPessoasCr5";
import { EstadosBrasil } from "../../../../../utils/constantes";
import { getMensagemDeErroOuProcureSuporte } from "../../../../../utils/errors";
import { InputForm } from "../../../../components/InputControl";
import { SelectForm } from "../../../../components/SelectControl";
import useCR5Axios from "../../../../hooks/useCR5Axios";
import { useFuncaoNotificacao } from "../../../../hooks/useFuncaoNotificacao";

export function FormClientePessoaFisica() {
  const { axios } = useCR5Axios();
  const [editavel, setEditavel] = useState<boolean>(true);
  const { setValue } = useFormContext();
  const notificacao = useFuncaoNotificacao();

  function BuscarEndereco(cep: string) {
    fetchEndereco({ cep }, axios)
      .then((endereco: EnderecoDTO) => {
        const { cep, logradouro, cidade, bairro, uf } = endereco;
        setValue("cep", cep);
        setValue("logradouro", logradouro);
        setValue("cidade", cidade);
        setValue("bairro", bairro);
        setValue("estado", uf);
        setEditavel(logradouro.length > 0);
      })
      .catch((e) => {
        setValue("cep", "");
        setValue("logradouro", "");
        setValue("cidade", "");
        setValue("bairro", "");
        setValue("estado", "");
        setEditavel(true);

        notificacao({
          message: getMensagemDeErroOuProcureSuporte(e),
          tipo: "warning",
          titulo: "INFO",
        });
      });
  }

  return (
    <Grid w="100%" templateColumns={{ base: "¨100%", md: "repeat(4, 1fr)" }} mt={4}>
      <InputForm
        name="descricao"
        size="sm"
        label="Nome"
        textTransform="uppercase"
        numeroColunasMd={2}
      />

      <InputForm name="email" size="sm" label="E-mail" numeroColunasMd={2} />

      <InputForm name="rg" type="number" size="sm" label="RG" />

      <InputForm name="cpf" type="text" size="sm" label="CPF" mascara="000.000.000-00" />

      <InputForm name="dataNascimento" type="date" size="sm" label="Data Nascimento" />

      <SelectForm
        name="emancipado"
        label="Emancipado"
        w="100%"
        options={[
          {
            value: "S",
            label: "SIM",
          },
          {
            value: "N",
            label: "NÃO",
          },
        ]}
      />
      <GridItem colSpan={{ base: 1, md: 4 }} m={1}>
        <Box position="relative" padding="5">
          <Divider />
          <AbsoluteCenter
            bg={useColorModeValue("light.text", "dark.primary")}
            px="4"
            fontSize={"sm"}
          >
            Endereço
          </AbsoluteCenter>
        </Box>
      </GridItem>
      <InputForm
        name="cep"
        size="sm"
        label="CEP"
        mascara="00000-000"
        icon={<FiSearch />}
        pl={10}
        onBlur={(e) => {
          BuscarEndereco(e.target.value);
        }}
      />
      <InputForm
        name="logradouro"
        size="sm"
        label="Logradouro"
        textTransform="uppercase"
        naoEditavel={editavel}
        numeroColunasMd={2}
      />
      <InputForm
        name="numeroResidencia"
        size="sm"
        label="Número"
        type="number"
        textTransform="uppercase"
      />
      <InputForm
        name="cidade"
        size="sm"
        label="Cidade"
        textTransform="uppercase"
        naoEditavel={true}
      />

      <SelectForm
        name="estado"
        label="Estado"
        id="estado"
        w="100%"
        textTransform="uppercase"
        options={EstadosBrasil}
        naoEditavel={true}
      />

      <InputForm
        name="bairro"
        size="sm"
        label="Bairro"
        textTransform="uppercase"
        naoEditavel={editavel}
      />

      <InputForm name="complemento" label="Complemento" size="sm" textTransform="uppercase" />

      <InputForm name="telefone" size="sm" mascara="(00) 0000-0000" label="Telefone 1" />

      <InputForm name="telefone2" size="sm" mascara="(00) 0000-0000" label="Telefone 2" />

      <InputForm name="celular" size="sm" mascara="(00) 00000-0000" label="Celular 1" />

      <InputForm name="celular2" size="sm" mascara="(00) 00000-0000" label="Celular 2" />
    </Grid>
  );
}
