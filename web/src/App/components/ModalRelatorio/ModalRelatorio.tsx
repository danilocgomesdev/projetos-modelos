import { Radio, RadioGroup, Stack } from "@chakra-ui/react";
import { useEffect, useState } from "react";
import { EntidadesEnum } from "../../../utils/constantes";
import { useOperadorEDireitosStore } from "../../stores/OperadorEDireitosStore";
import { useUnidadeStore } from "../../stores/UnidadeStore";
import { ModalBase } from "../ModalBase";
import { useFetchRelatorio } from "./hook/useFetchRelatorio";

interface ModalRelatorioProps {
  isOpen: boolean;
  onClose: () => void;
  nomeRelatorio: string;
  rota: string;
  itemsPerPage?: number;
  currentPage?: number;
  entidade?: number;
  params?: Record<string, any>;
}

export function ModalRelatorio({
  isOpen,
  onClose,
  nomeRelatorio,
  rota,
  entidade,
  params,
}: ModalRelatorioProps) {
  const { operador } = useOperadorEDireitosStore();
  const { unidadeAtual } = useUnidadeStore();

  const idEntidade = entidade ?? EntidadesEnum[unidadeAtual.entidade as keyof typeof EntidadesEnum];

  const [value, setValue] = useState("");

  const { fetchRelatorio, isLoading, relatorio } = useFetchRelatorio();

  const handleRelatorio = () => {
    if (!value || !idEntidade) return;

    const baseUrl = `${rota}/entidade/${idEntidade}/relatorio`;
    let url = "";

    switch (value) {
      case "1":
        url = `${baseUrl}/pdf?operador=${operador?.nome}`;
        break;
      case "2":
        url = `${baseUrl}/filtro/pdf?operador=${operador?.nome}`;
        break;
      case "3":
        url = `${baseUrl}/xls?operador=${operador?.nome}`;
        break;
      case "4":
        url = `${baseUrl}/filtro/xls?operador=${operador?.nome}`;
        break;
      default:
        return;
    }

    fetchRelatorio(url, params);
  };

  const handleDownload = () => {
    if (relatorio) {
      const url = window.URL.createObjectURL(relatorio);
      const extension = relatorio.type === "application/pdf" ? ".pdf" : ".xlsx";

      if (extension === ".pdf") {
        window.open(url, "_blank");
      } else {
        const link = document.createElement("a");
        link.href = url;
        link.download = `${nomeRelatorio}${extension}`;
        link.click();
        window.URL.revokeObjectURL(url);
      }
    }
    onClose();
  };

  useEffect(() => {
    if (relatorio) {
      handleDownload();
    }
  }, [relatorio]);

  return (
    <ModalBase
      isOpen={isOpen}
      onClose={onClose}
      titulo="Gerar Relatório"
      size="xl"
      centralizado
      botaoConfirma={{
        label: isLoading ? "Gerando..." : "Gerar Relatório",
        onClick: handleRelatorio,
      }}
      isLoading={isLoading}
      loadingText="Gerando..."
    >
      <RadioGroup onChange={(val) => setValue(val)} value={value}>
        <Stack direction="row" justifyContent="space-between">
          <Radio value="1">PDF</Radio>
          <Radio value="2">Página Atual - PDF</Radio>
          <Radio value="3">Excel</Radio>
          <Radio value="4">Página Atual - Excel</Radio>
        </Stack>
      </RadioGroup>
    </ModalBase>
  );
}
