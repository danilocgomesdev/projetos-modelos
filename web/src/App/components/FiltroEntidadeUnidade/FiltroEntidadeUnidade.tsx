import { Box, GridItem, Radio, RadioGroup, Stack } from "@chakra-ui/react";
import { useEffect, useState } from "react";
import { DropBoxEntidades } from "../DropBoxEntidades";
import { DropBoxUnidades } from "../DropBoxUnidades";

type OnChangeEntidadeUnidade = (
  arg: { tipo: "ENTIDADE" | "UNIDADE"; id: number } | { tipo: "LIMPAR" }
) => void;

interface FiltroEntidadeUnidadeProps {
  onChange: OnChangeEntidadeUnidade;
  width?: string;
  numeroColunasMd?: number;
  numeroColunasLg?: number;
}

export function FiltroEntidadeUnidade({
  width,
  onChange,
  numeroColunasMd,
  numeroColunasLg,
}: FiltroEntidadeUnidadeProps) {
  const [tipoFiltro, setTipoFiltro] = useState("entidade");
  const [idEntidade, setIdEntidade] = useState<number | null>(null);
  const [idUnidade, setIdUnidade] = useState<number | null>(null);

  useEffect(() => {
    if (tipoFiltro === "entidade" && idEntidade) {
      onChange({ tipo: "ENTIDADE", id: idEntidade });
    } else if (tipoFiltro === "unidade" && idUnidade) {
      onChange({ tipo: "UNIDADE", id: idUnidade });
    } else {
      onChange({ tipo: "LIMPAR" });
    }
  }, [tipoFiltro]);

  return (
    <GridItem
      colSpan={{ base: 1, md: numeroColunasMd ?? 1, lg: numeroColunasLg ?? numeroColunasMd }}
      mb={1}
      mt={{ base: 2, md: 0 }}
    >
      <Box width={width}>
        <RadioGroup size="sm" onChange={setTipoFiltro} value={tipoFiltro}>
          <Stack direction="row">
            <Radio value="entidade">Entidade</Radio>
            <Radio value="unidade">Unidade</Radio>
          </Stack>
        </RadioGroup>

        <Box width={width}>
          <Box display={tipoFiltro === "entidade" ? "block" : "none"}>
            <DropBoxEntidades
              mostrarLabel={false}
              onChange={(id) => {
                setIdEntidade(id);

                if (id) {
                  onChange({ tipo: "ENTIDADE", id });
                } else {
                  onChange({ tipo: "LIMPAR" });
                }
              }}
            />
          </Box>
          <Box display={tipoFiltro === "unidade" ? "block" : "none"}>
            <DropBoxUnidades
              mostrarLabel={false}
              onChange={(id) => {
                setIdUnidade(id);

                if (id) {
                  onChange({ tipo: "UNIDADE", id });
                } else {
                  onChange({ tipo: "LIMPAR" });
                }
              }}
            />
          </Box>
        </Box>
      </Box>
    </GridItem>
  );
}
