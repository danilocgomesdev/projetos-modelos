import {
  IntegraProtheus,
  integraProtheusValues,
} from "../../../models/DTOs/Protheus/IntegraProtheus";
import { SelectMultipleControl } from "../SelectControl";

interface DropBoxIntegraProtheusParams {
  onChange: (integraProtheus: IntegraProtheus[]) => void;
  valorInicial?: string[];
  numeroColunasMd?: number;
  numeroColunasLg?: number;
}

export function DropBoxIntegraProtheus({
  onChange,
  valorInicial,
  numeroColunasMd,
  numeroColunasLg,
}: DropBoxIntegraProtheusParams) {
  return (
    <SelectMultipleControl
      label="Integra Protheus"
      numeroColunasMd={numeroColunasMd}
      numeroColunasLg={numeroColunasLg}
      valorInicial={valorInicial}
      options={Object.keys(integraProtheusValues).map((chave) => {
        const integraProtheus = integraProtheusValues[chave as IntegraProtheus];
        return {
          value: chave,
          label: integraProtheus.descricao,
        };
      })}
      id="idIntegraProtheus"
      onChange={(integraProtheus) => {
        onChange(integraProtheus as IntegraProtheus[]);
      }}
    />
  );
}
