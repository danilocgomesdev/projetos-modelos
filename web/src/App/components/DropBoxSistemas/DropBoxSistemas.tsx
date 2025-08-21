import { GridItem } from "@chakra-ui/react";
import { useQuery } from "@tanstack/react-query";

import { fetchAllSistemas } from "../../../requests/requestsSistema";

import { TemposCachePadrao } from "../../../utils/constantes";

import useCR5Axios from "../../hooks/useCR5Axios";

import { SelectControl } from "../SelectControl";

interface DropBoxSistemasParamas {
  value?: number | null;
  onChange: (idUnidade: number | null) => void;
  numeroColunasMd?: number;
  numeroColunasLg?: number;
}

export function DropBoxSistemas({
  value,
  onChange,
  numeroColunasMd,
  numeroColunasLg,
}: DropBoxSistemasParamas) {
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
    <GridItem
      colSpan={{ base: 1, md: numeroColunasMd ?? 1, lg: numeroColunasLg ?? numeroColunasMd }}
      mb={0}
    >
      <SelectControl
        label="Sistema"
        options={(sistemas || []).map((sistema) => {
          return {
            value: sistema.idSistema.toString(),
            label: `${sistema.idSistema} - ${sistema.descricaoReduzida}`,
          };
        })}
        id="idSistema"
        value={value ? value.toString() : ""}
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
