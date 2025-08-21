import { GridItem } from "@chakra-ui/react";
import { formasDePagamentoValues } from "../../../models/DTOs/Outros/FormasDePagamentoSimplificas";
import { SelectControl } from "../SelectControl";

interface DropBoxFormaPagamentoSimplificadaParams {
  onChange: (formaPagamento: string | null) => void;
  numeroColunasMd?: number;
  numeroColunasLg?: number;
}

export function DropBoxFormaPagamentoSimplificada({
  onChange,
  numeroColunasMd,
  numeroColunasLg,
}: DropBoxFormaPagamentoSimplificadaParams) {
  return (
    <GridItem
      colSpan={{ base: 1, md: numeroColunasMd ?? 1, lg: numeroColunasLg ?? numeroColunasMd }}
      mb={0}
    >
      <SelectControl
        label="Forma Pagamento"
        options={Object.values(formasDePagamentoValues).map((forma) => {
          return {
            value: forma.nomeEnum,
            label: forma.descricao,
          };
        })}
        id="formaPagamento"
        onChange={(e) => {
          onChange(e);
        }}
      />
    </GridItem>
  );
}
