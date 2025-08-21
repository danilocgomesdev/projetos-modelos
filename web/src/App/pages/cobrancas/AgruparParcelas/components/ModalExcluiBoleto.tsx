import { ReactNode, useEffect } from "react";
import { ModalBase } from "../../../../components/ModalBase";

interface ModalConfirmaProps<T> {
  titulo: string;
  texto: ReactNode;
  isOpen: boolean;
  onClose: () => void;
  onConfirm: (item: T) => void;
  item: T;
  isLoading?: boolean;
}

export function ModalExluiBoleto<T>({
  titulo,
  texto,
  isOpen,
  onClose,
  onConfirm,
  item,
  isLoading,
}: ModalConfirmaProps<T>) {
  useEffect(() => {
    if (isLoading === false) {
      onClose();
    }
  }, [isLoading, onClose]);

  function confirmar() {
    onConfirm(item); // Passa o item para a função ao confirmar
  }

  function handleClose() {
    onClose();
  }

  return (
    <ModalBase
      isOpen={isOpen}
      onClose={isLoading ? () => undefined : handleClose}
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
