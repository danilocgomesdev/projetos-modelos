import { GridItem } from "@chakra-ui/react";
import { Entidades } from "../../../models/DTOs/Outros/EntidadeDTO";
import { SelectControl } from "../SelectControl";

interface DropBoxEntidadesParams {
  onChange: (idEntidade: number | null) => void;
  mostrarLabel?: boolean;
  numeroColunasMd?: number;
  numeroColunasLg?: number;
}

export function DropBoxEntidades({
  onChange,
  mostrarLabel,
  numeroColunasMd,
  numeroColunasLg,
}: DropBoxEntidadesParams) {
  return (
    <GridItem
      colSpan={{ base: 1, md: numeroColunasMd ?? 1, lg: numeroColunasLg ?? numeroColunasMd }}
      mb={0}
    >
      <SelectControl
        label={mostrarLabel ?? true ? "Entidade" : ""}
        options={Object.values(Entidades).map((entidade) => {
          return {
            value: entidade.codigo.toString(),
            label: entidade.descReduzida,
          };
        })}
        id="idEntidade"
        onChange={(e) => {
          const codigo = Number.parseInt(e);
          if (!codigo || Number.isNaN(codigo)) {
            onChange(null);
          } else {
            onChange(codigo);
          }
        }}
      />
    </GridItem>
  );
}
