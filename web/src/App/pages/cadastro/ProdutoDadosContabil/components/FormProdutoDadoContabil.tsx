import { AbsoluteCenter, Box, Divider, Grid, GridItem, useColorModeValue } from "@chakra-ui/react";
import { useQuery } from "@tanstack/react-query";
import { FiSearch } from "react-icons/fi";
import { DadoContabilDTO } from "../../../../../models/DTOs/Contabil/DadoContabilDTO";
import { fetchAllSistemas } from "../../../../../requests/requestsSistema";
import { TemposCachePadrao } from "../../../../../utils/constantes";
import { InputForm } from "../../../../components/InputControl";
import { SelectForm } from "../../../../components/SelectControl";
import useCR5Axios from "../../../../hooks/useCR5Axios";

interface ProdutoDadoContabilProps {
  dadoContabil?: DadoContabilDTO | null | undefined;
  onOpen: () => void;
  onOpenVisaoServicos?: () => void;
}

export function FormProdutoDadoContabil({
  dadoContabil,
  onOpen,
  onOpenVisaoServicos,
}: ProdutoDadoContabilProps) {
  const { axios } = useCR5Axios();
  const { data: sistemas } = useQuery({
    queryKey: ["todasOsSistemas"],
    staleTime: TemposCachePadrao.ESTATICO,
    cacheTime: TemposCachePadrao.ESTATICO,
    queryFn: () => {
      return fetchAllSistemas(axios);
    },
  });

  return (
    <Grid w="100%" templateColumns={{ base: "¨100%", md: "repeat(4, 1fr)" }} mt={4}>
      {onOpenVisaoServicos ? (
        <InputForm
          name="idProduto"
          size="sm"
          label="Código Produto"
          naoEditavel={true}
          icon={<FiSearch />}
          cursor="pointer"
          onClick={onOpenVisaoServicos}
          pl={10}
          obrigatorio
        />
      ) : (
        <InputForm
          name="idProduto"
          size="sm"
          label="Código Produto"
          naoEditavel={true}
          obrigatorio
        />
      )}
      <InputForm
        name="produto"
        size="sm"
        label="Produto"
        naoEditavel={true}
        textTransform="uppercase"
        numeroColunasMd={3}
        obrigatorio
      />
      <SelectForm
        name="idSistema"
        label="Sistema"
        w="100%"
        numeroColunasMd={2}
        naoEditavel={true}
        obrigatorio
        options={(sistemas || []).map((sistema) => {
          return {
            value: sistema.idSistema.toString(),
            label: `${sistema.idSistema} - ${sistema.descricaoReduzida}`,
          };
        })}
      />
      <SelectForm
        name="status"
        label="Status"
        id="status"
        w="100%"
        obrigatorio
        options={[
          {
            value: "A",
            label: "ATIVO",
          },
          {
            value: "I",
            label: "INATIVO",
          },
        ]}
      />
      <SelectForm
        name="dmed"
        label="Produto DMED"
        w="100%"
        obrigatorio
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
            Dados Contábeis
          </AbsoluteCenter>
        </Box>
      </GridItem>
      <InputForm
        name="itemContabil"
        size="sm"
        label="Item Contábil"
        naoEditavel={true}
        icon={<FiSearch />}
        cursor="pointer"
        onClick={onOpen}
        pl={10}
        value={dadoContabil?.itemContabil ?? ""}
        numeroColunasMd={2}
        obrigatorio
      />
      <InputForm
        name="itemContabilDescricao"
        size="sm"
        label="Descrição Item Contábil"
        textTransform="uppercase"
        value={dadoContabil?.itemContabilDescricao ?? ""}
        numeroColunasMd={2}
        onClick={onOpen}
        obrigatorio
      />
      <InputForm
        name="contaContabil"
        size="sm"
        label="Conta Contábil"
        textTransform="uppercase"
        naoEditavel
        value={dadoContabil?.contaContabil ?? ""}
        numeroColunasMd={2}
        onClick={onOpen}
        obrigatorio
      />
      <InputForm
        name="contaContabilDescricao"
        size="sm"
        label="Descrição Conta Contábil"
        textTransform="uppercase"
        value={dadoContabil?.contaContabilDescricao ?? ""}
        naoEditavel
        numeroColunasMd={2}
        onClick={onOpen}
        obrigatorio
      />
      <InputForm
        name="codigoNatureza"
        size="sm"
        label="Código Natureza"
        textTransform="uppercase"
        naoEditavel={true}
        numeroColunasMd={2}
        value={dadoContabil?.natureza ?? ""}
        onClick={onOpen}
      />
      <InputForm
        name="descricaoNatureza"
        size="sm"
        label="Descricão Código Natureza"
        textTransform="uppercase"
        naoEditavel={true}
        numeroColunasMd={2}
        value={dadoContabil?.naturezaDescricao ?? ""}
        onClick={onOpen}
      />
    </Grid>
  );
}
