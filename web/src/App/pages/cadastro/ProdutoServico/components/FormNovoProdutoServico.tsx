import { Grid } from "@chakra-ui/react";
import { useQuery } from "@tanstack/react-query";
import { fetchAllSistemas } from "../../../../../requests/requestsSistema";
import { DefaultSistema, TemposCachePadrao } from "../../../../../utils/constantes";
import { mascaraMoeda } from "../../../../../utils/mascaras";
import { InputForm } from "../../../../components/InputControl";
import { InputFormMoeda } from "../../../../components/InputControl/InputFormMoeda";
import { SelectForm } from "../../../../components/SelectControl";
import useCR5Axios from "../../../../hooks/useCR5Axios";

interface ProdutoServicoProps {
  setPrecoFormatoMoeda: (value: string) => void;
  precoFormatoMoeda?: string | null;
}

export function FormNovoProdutoServico({
  setPrecoFormatoMoeda,
  precoFormatoMoeda,
}: ProdutoServicoProps) {
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
    <>
      <Grid w="100%" templateColumns={{ base: "¨100%", md: "repeat(4, 1fr)" }} mt={4}>
        <InputForm
          numeroColunasMd={4}
          numeroColunasLg={3}
          name="produto"
          size="sm"
          label="Nome / Descrição Produto"
          textTransform="uppercase"
        />
        <InputFormMoeda
          numeroColunasMd={2}
          numeroColunasLg={1}
          name="preco"
          size="sm"
          label="Preço Sugerido"
          textTransform="uppercase"
          value={precoFormatoMoeda || ""}
          onChange={(e) => setPrecoFormatoMoeda(mascaraMoeda(e) || "")}
        />
        <SelectForm
          name="idSistema"
          label="Sistema"
          w="100%"
          numeroColunasMd={2}
          numeroColunasLg={1}
          defaultChecked
          defaultValue={DefaultSistema.CR5}
          obrigatorio
          naoEditavel
          options={(sistemas || []).map((sistema) => {
            return {
              value: sistema.idSistema.toString(),
              label: `${sistema.idSistema} - ${sistema.descricaoReduzida}`,
            };
          })}
        />
        <SelectForm
          numeroColunasMd={2}
          numeroColunasLg={1}
          name="status"
          label="Status"
          id="status"
          w="100%"
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
        <InputForm
          numeroColunasMd={2}
          numeroColunasLg={2}
          name="codProdutoProtheus"
          size="sm"
          label="Código Produto Protheus"
        />
      </Grid>
    </>
  );
}
