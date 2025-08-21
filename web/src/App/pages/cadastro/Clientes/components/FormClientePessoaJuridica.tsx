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

export function FormClientePessoaJuridica() {
  const { axios } = useCR5Axios();
  const [editavel, setEditavel] = useState<boolean>(true);

  const { setValue } = useFormContext();
  const notificacao = useFuncaoNotificacao();

  async function BuscarEndereco(cep: string) {
    try {
      const endereco: EnderecoDTO | undefined = await fetchEndereco({ cep }, axios);
      if (endereco) {
        setValue("cep", endereco.cep);
        setValue("logradouro", endereco.logradouro);
        setEditavel(false);
        if (endereco.logradouro.length > 0) {
          setEditavel(true);
        }
        setValue("cidade", endereco.cidade);
        setValue("bairro", endereco.bairro);
        setValue("estado", endereco.uf);
      } else {
        notificacao({
          message: "Endereço não encontrado",
          tipo: "info",
          titulo: "INFO",
        });
        setEditavel(true);
      }
    } catch (error) {
      notificacao({
        message: getMensagemDeErroOuProcureSuporte(error),
        tipo: "error",
        titulo: "ERRO!",
      });
    }
  }

  return (
    <Grid w="100%" templateColumns={{ base: "¨100%", md: "repeat(4, 1fr)" }} mt={4}>
      <InputForm
        name="razaoSocial"
        size="sm"
        label="Razão Social"
        textTransform="uppercase"
        numeroColunasMd={2}
      />
      <InputForm name="email" size="sm" label="E-mail" numeroColunasMd={2} />
      <InputForm
        name="cnpj"
        type="text"
        size="sm"
        label="CNPJ"
        mascara="00.000.000/0000-00"
        numeroColunasMd={2}
      />
      <InputForm
        name="inscricaoEstadual"
        size="sm"
        label="Inscrição Estadual"
        numeroColunasMd={2}
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
        numeroColunasMd={2}
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
        name="cidade"
        size="sm"
        label="Cidade"
        textTransform="uppercase"
        naoEditavel={true}
        numeroColunasMd={2}
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
        name="numeroResidencia"
        size="sm"
        label="Número"
        type="number"
        textTransform="uppercase"
      />
      <InputForm
        name="bairro"
        size="sm"
        label="Bairro"
        textTransform="uppercase"
        naoEditavel={editavel}
        numeroColunasMd={2}
      />

      <InputForm
        name="complemento"
        label="Complemento"
        size="sm"
        textTransform="uppercase"
        numeroColunasMd={2}
      />

      <InputForm name="telefone" size="sm" mascara="(00) 0000-0000" label="Telefone 1" />
      <InputForm name="telefone2" size="sm" mascara="(00) 0000-0000" label="Telefone 2" />

      <InputForm name="celular" size="sm" mascara="(00) 00000-0000" label="Celular 1" />

      <InputForm name="celular2" size="sm" mascara="(00) 00000-0000" label="Celular 2" />
    </Grid>
  );
}
