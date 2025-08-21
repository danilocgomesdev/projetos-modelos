import { AbsoluteCenter, Box, Divider, Grid, GridItem, useColorModeValue } from "@chakra-ui/react";
import { useQuery } from "@tanstack/react-query";
import { useFormContext } from "react-hook-form";
import { PaisesDTO } from "../../../../../models/DTOs/Outros/PaisesDTO";
import { fetchAllPais } from "../../../../../requests/requestsPaises";
import { TemposCachePadrao } from "../../../../../utils/constantes";
import { InputForm } from "../../../../components/InputControl";
import { SelectForm } from "../../../../components/SelectControl";
import useCR5Axios from "../../../../hooks/useCR5Axios";

export function FormClienteEstrangeiro() {
  const { axios } = useCR5Axios();
  const { data: paises } = useQuery({
    queryKey: ["todosOsPaises"],
    staleTime: TemposCachePadrao.ESTATICO,
    cacheTime: TemposCachePadrao.ESTATICO,
    queryFn: () => {
      return fetchAllPais(axios);
    },
  });

  const { setValue } = useFormContext();

  function handleSetarCodigoPais(pais: string) {
    const paisFiltrado: PaisesDTO | undefined = paises?.find((x) => x.descricao === pais);
    setValue("codigoPais", paisFiltrado?.codigoPais.toString());
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
      <InputForm
        name="idEstrangeiro"
        size="sm"
        label="Identificador Estrangeiro"
        numeroColunasMd={2}
      />
      <InputForm
        name="dataNascimento"
        type="date"
        size="sm"
        label="Data Nascimento"
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
      <InputForm name="codigoPostal" size="sm" label="Código Postal" />
      <InputForm
        name="logradouro"
        size="sm"
        label="Logradouro"
        textTransform="uppercase"
        numeroColunasMd={2}
      />
      <InputForm
        name="numeroResidencia"
        size="sm"
        label="Número"
        type="number"
        textTransform="uppercase"
      />
      <InputForm name="complemento" label="Complemento" size="sm" textTransform="uppercase" />
      <InputForm name="bairro" size="sm" label="Bairro" textTransform="uppercase" />
      <InputForm
        name="cidade"
        size="sm"
        label="Cidade"
        textTransform="uppercase"
        numeroColunasMd={2}
      />
      <InputForm name="estado" size="sm" label="Estado" numeroColunasMd={2} />
      <SelectForm
        name="pais"
        label="País"
        id="pais"
        w="100%"
        numeroColunasMd={2}
        options={(paises || []).map((pais) => {
          return {
            value: pais.descricao.toString(),
            label: `${pais.descricao}`,
          };
        })}
        onBlur={(e) => handleSetarCodigoPais(e.target.value)}
      />
      <InputForm name="telefone" size="sm" label="Telefone 1" type="number" />
      <InputForm name="telefone2" size="sm" label="Telefone 2" type="number" />
      <InputForm name="celular" size="sm" label="Celular 1" type="number" />
      <InputForm name="celular2" size="sm" label="Celular 2" type="number" />
    </Grid>
  );
}
