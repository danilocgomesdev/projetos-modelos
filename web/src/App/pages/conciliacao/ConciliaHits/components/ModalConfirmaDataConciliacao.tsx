import { useEffect, useState } from "react";
import { InputControl } from "../../../../components/InputControl";
import { ModalBase } from "../../../../components/ModalBase";

interface ModalConfirmaDataConciliacaoProps {
  isOpen: boolean;
  onClose: () => void;
  onDataSelecionada: (data: string) => void; // Alterado de number para string
  isLoanding: boolean;
}

export default function ModalConfirmaDataConciliacao({
  isOpen,
  onClose,
  onDataSelecionada,
  isLoanding,
}: ModalConfirmaDataConciliacaoProps) {
  const [dataSelecionada, setDataSelecionada] = useState<string>("");

  useEffect(() => {
    if (isOpen) {
      setDataSelecionada(new Date().toISOString().split("T")[0]); // formato "yyyy-mm-dd"
    }
  }, [isOpen]);

  async function handleConfirmarData() {
    if (dataSelecionada) {
      // const timestamp = new Date(dataSelecionada).getTime(); // Linha removida
      onDataSelecionada(dataSelecionada); // Passa a string diretamente
    } else {
      alert("Por favor, selecione uma data.");
    }
  }

  return (
    <ModalBase
      isOpen={isOpen}
      onClose={onClose}
      size="sm"
      titulo="Data pagamento para conciliar"
      centralizado={true}
      botaoConfirma={{
        onClick: handleConfirmarData,
      }}
      isLoading={isLoanding}
      loadingText={"Salvando..."}
    >
      <InputControl
        label="Data Pagamento"
        type="date"
        value={dataSelecionada}
        onChange={(e) => setDataSelecionada(e.target.value)}
      />
    </ModalBase>
  );
}
