import { GridItem } from "@chakra-ui/react";
import { useQuery } from "@tanstack/react-query";
import { fetchAllUnidades } from "../../../requests/requestsUnidades";
import { TemposCachePadrao } from "../../../utils/constantes";
import useCR5Axios from "../../hooks/useCR5Axios";
import { SelectControl } from "../SelectControl";

interface DropBoxUnidadesParamas {
  onChange: (idUnidade: number | null) => void;
  mostrarLabel?: boolean;
  numeroColunasMd?: number;
  numeroColunasLg?: number;
}

export function DropBoxUnidades({
  onChange,
  mostrarLabel,
  numeroColunasMd,
  numeroColunasLg,
}: DropBoxUnidadesParamas) {
  const { axios } = useCR5Axios();

  const { data: unidades } = useQuery({
    queryKey: ["todasAsUnidades"],
    staleTime: TemposCachePadrao.ESTATICO,
    cacheTime: TemposCachePadrao.ESTATICO,
    queryFn: () => {
      return fetchAllUnidades(axios);
    },
  });

  return (
    <GridItem
      colSpan={{ base: 1, md: numeroColunasMd ?? 1, lg: numeroColunasLg ?? numeroColunasMd }}
      mb={0}
    >
      <SelectControl
        w="100%"
        label={mostrarLabel ?? true ? "Unidade" : ""}
        options={(unidades || []).map((unidade) => {
          return {
            value: unidade.id.toString(),
            label: `${unidade.codigo} - ${unidade.nome}`,
          };
        })}
        id="idUnidade"
        onChange={(e) => {
          const numero = Number.parseInt(e);
          if (!numero || Number.isNaN(numero)) {
            onChange(null);
          } else {
            onChange(numero);
          }
        }}
      />
    </GridItem>
  );
}
