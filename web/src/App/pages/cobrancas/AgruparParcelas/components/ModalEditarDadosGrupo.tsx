import { useEffect, useState } from "react";
import { CobrancasGrupoDTO } from "../../../../../models/DTOs/CobrancasAgrupadas/CobrancasGrupoDTO";
import { converteDataStringDate } from "../../../../../utils/date";
import { InputControl } from "../../../../components/InputControl";
import { ModalBase } from "../../../../components/ModalBase";
import { useFuncaoNotificacao } from "../../../../hooks/useFuncaoNotificacao";

interface ModalEditarCobrancaProps {
  isOpen: boolean;
  onClose: () => void;
  onSalvar: (dadosEditados: Partial<CobrancasGrupoDTO>) => void;
  isLoading: boolean;
  onItemSelecionada: CobrancasGrupoDTO | null;
}

export default function ModalEditarDadosGrupo({
  isOpen,
  onClose,
  onSalvar,
  isLoading,
  onItemSelecionada,
}: ModalEditarCobrancaProps) {
  const [dadosEditados, setDadosEditados] = useState<Partial<CobrancasGrupoDTO>>({});

  const notificacao = useFuncaoNotificacao();
  useEffect(() => {
    if (!isOpen) {
      // Limpa os dados quando o modal for fechado
      setDadosEditados({});
    } else if (isOpen && onItemSelecionada) {
      setDadosEditados({
        idCobrancaAgrupada: onItemSelecionada.idCobrancaAgrupada,
        nossoNumero: onItemSelecionada.nossoNumero,
        dataVencimento: onItemSelecionada.dataVencimento,
        notaFiscal: onItemSelecionada.notaFiscal ?? "",
        dataEmissaoNotaFiscal: onItemSelecionada.dataEmissaoNotaFiscal,
        avisoLancamentoNota: onItemSelecionada.avisoLancamentoNota ?? "",
        dataAvisoLancamentoNota: onItemSelecionada.dataAvisoLancamentoNota,
      });
    }
  }, [isOpen, onItemSelecionada]);

  function validaBoleto() {
    if (onItemSelecionada?.nossoNumero) {
      notificacao({
        message: "Para alterar a data de vencimento, é necessário excluir o boleto existente!",
        tipo: "warning",
        titulo: "AVISO!",
      });
    }
  }

  function handleChange(event: React.ChangeEvent<HTMLInputElement>) {
    const { name, value, type } = event.target;
    setDadosEditados((prev) => ({
      ...prev,
      [name]: type === "date" ? converteDataStringDate(value) : value.toUpperCase(),
    }));
  }

  function handleSalvar() {
    onSalvar(dadosEditados);
  }

  function handleClose() {
    setDadosEditados({});
    onClose();
  }

  return (
    <ModalBase
      isOpen={isOpen}
      onClose={handleClose}
      size="sm"
      titulo="Editar Cobrança"
      centralizado={true}
      botaoConfirma={{
        onClick: handleSalvar,
        label: "Salvar",
      }}
      isLoading={isLoading}
      loadingText={"Salvando..."}
    >
      <InputControl
        label="Data Vencimento"
        type="date"
        name="dataVencimento"
        value={
          dadosEditados.dataVencimento
            ? new Date(dadosEditados.dataVencimento).toISOString().split("T")[0]
            : ""
        }
        onChange={(event) => {
          validaBoleto();
          handleChange(event);
        }}
      />

      <InputControl
        label="NF"
        type="number"
        name="notaFiscal"
        value={dadosEditados.notaFiscal?.toString() || ""}
        onChange={handleChange}
      />

      <InputControl
        label="DT Emissão NF"
        type="date"
        name="dataEmissaoNotaFiscal"
        value={
          dadosEditados.dataEmissaoNotaFiscal &&
          !isNaN(new Date(dadosEditados.dataEmissaoNotaFiscal).getTime())
            ? new Date(dadosEditados.dataEmissaoNotaFiscal).toISOString().split("T")[0]
            : ""
        }
        onChange={handleChange}
      />

      <InputControl
        label="AL"
        name="avisoLancamentoNota"
        value={dadosEditados.avisoLancamentoNota?.toString().toUpperCase() || ""}
        onChange={handleChange}
      />

      <InputControl
        label="Dt. Emissão AL"
        type="date"
        name="dataAvisoLancamentoNota"
        value={
          dadosEditados.dataAvisoLancamentoNota &&
          !isNaN(new Date(dadosEditados.dataAvisoLancamentoNota).getTime())
            ? new Date(dadosEditados.dataAvisoLancamentoNota).toISOString().split("T")[0]
            : ""
        }
        onChange={handleChange}
      />
    </ModalBase>
  );
}
