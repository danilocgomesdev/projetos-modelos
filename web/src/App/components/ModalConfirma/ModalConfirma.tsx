import { ReactNode, useEffect } from "react";
import { ModalBase } from "../ModalBase";

interface ModalConfirmaProps {
  titulo: "Atenção" | "Confirma";
  texto: ReactNode;
  isOpen: boolean;
  onClose: () => void;
  onConfirm: () => void;
  isLoading?: boolean;
}

export function ModalConfirma({
  titulo,
  texto,
  isOpen,
  onClose,
  onConfirm,
  isLoading,
}: ModalConfirmaProps) {
  useEffect(() => {
    if (isLoading === false) {
      onClose();
    }
  }, [isLoading, onClose]);

  function confirmar() {
    onConfirm();
  }
  return (
    <ModalBase
      isOpen={isOpen}
      onClose={isLoading ? () => undefined : onClose}
      titulo={titulo === "Atenção" ? "⚠️ " + titulo : "ℹ️ " + titulo}
      centralizado={true}
      isLoading={isLoading}
      loadingText="Aguarde..."
      botaoConfirma={{ onClick: confirmar }}
    >
      {texto}
    </ModalBase>
  );
}
