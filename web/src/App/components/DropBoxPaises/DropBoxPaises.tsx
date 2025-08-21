import { useQuery } from "@tanstack/react-query";
import { fetchAllPais } from "../../../requests/requestsPaises";
import { TemposCachePadrao } from "../../../utils/constantes";
import useCR5Axios from "../../hooks/useCR5Axios";
import { SelectControl } from "../SelectControl";

interface DropBoxPaisesParamas {
  onChange: (idPais: number | null) => void;
}

export function DropBoxPaises({ onChange }: DropBoxPaisesParamas) {
  const { axios } = useCR5Axios();

  const { data: paises } = useQuery({
    queryKey: ["todosOsPaises"],
    staleTime: TemposCachePadrao.ESTATICO,
    cacheTime: TemposCachePadrao.ESTATICO,
    queryFn: () => {
      return fetchAllPais(axios);
    },
  });

  return (
    <SelectControl
      label="Pais"
      options={(paises || []).map((pais) => {
        return {
          value: pais.codigoPais.toString(),
          label: `${pais.codigoPais} - ${pais.descricao}`,
        };
      })}
      id="idPais"
      onChange={(e) => {
        const numero = Number.parseInt(e);
        if (!numero || Number.isNaN(numero)) {
          onChange(null);
        } else {
          onChange(numero);
        }
      }}
    />
  );
}
