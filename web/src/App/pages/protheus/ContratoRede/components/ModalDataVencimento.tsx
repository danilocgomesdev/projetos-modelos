import { useEffect, useState } from "react";

import { InputControl } from "../../../../components/InputControl";

import { getTodayISODate } from "../../../../../utils/date";

import { ModalBase } from "../../../../components/ModalBase";

interface ModalDataVencimentoProps {
  isOpen: boolean;
  onClose: () => void;
  onDataSelecionada: (data: string) => void;
  dataMax: Date;
  isLoanding: boolean;
}

export default function ModalDataVencimento({
  isOpen,
  onClose,
  onDataSelecionada,
  dataMax,
  isLoanding,
}: ModalDataVencimentoProps) {
  const [dataSelecionada, setDataSelecionada] = useState(getTodayISODate());

  useEffect(() => {
    if (isOpen) {
      setDataSelecionada(new Date().toISOString().split("T")[0]);
    }
  }, [isOpen]);

  async function handleConfirmarData() {
    if (dataSelecionada) {
      onDataSelecionada(dataSelecionada);
    } else {
      alert("Por favor, selecione uma data.");
    }
  }

  return (
    <>
      <ModalBase
        isOpen={isOpen}
        onClose={onClose}
        size="sm"
        titulo="Data do Novo Vencimento"
        centralizado={true}
        botaoConfirma={{
          onClick: handleConfirmarData,
        }}
        isLoading={isLoanding}
        loadingText={"Salvando o grupo..."}
      >
        <InputControl
          label="Data Vencimento"
          type="date"
          value={dataSelecionada}
          maxDate={dataMax.toISOString().split("T")[0]}
          minDate={getTodayISODate()}
          onChange={(e) => setDataSelecionada(e.target.value)}
        />
      </ModalBase>
    </>
  );
}
