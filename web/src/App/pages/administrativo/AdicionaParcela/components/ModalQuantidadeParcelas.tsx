import { useEffect, useState } from "react";

import { InputControl } from "../../../../components/InputControl";

import { ModalBase } from "../../../../components/ModalBase";

import { trataValorInputNumberVazio } from "../../../../../utils/componentUtil";

import { useFuncaoNotificacao } from "../../../../hooks/useFuncaoNotificacao";

interface ModalQuantidadeParcelasProps {
  isOpen: boolean;
  onClose: () => void;
  onQtdParcelaSelecionada: (data: number) => void;
  isLoanding: boolean;
}

export default function ModalQuantidadeParcelas({
  isOpen,
  onClose,
  onQtdParcelaSelecionada,
  isLoanding,
}: ModalQuantidadeParcelasProps) {
  const notificacao = useFuncaoNotificacao();
  const [qtdParcela, setQtdparcela] = useState<number | null>(null);

  useEffect(() => {
    if (isOpen) {
      setQtdparcela(null); // Limpa o campo ao abrir o modal
    }
  }, [isOpen]);

  async function handleConfirma() {
    if (qtdParcela) {
      onQtdParcelaSelecionada(qtdParcela);
    } else {
      notificacao({
        tipo: "warning",
        titulo: "Aviso",
        message: "Favor informar a quantidade!",
      });
    }
  }

  function atualizaValorPesquisa<T>(setter: (arg: T) => void, valor: T): void {
    setter(valor);
  }

  function limpaCampo() {
    setQtdparcela(null);
  }

  return (
    <>
      <ModalBase
        isOpen={isOpen}
        onClose={() => {
          onClose();
          limpaCampo();
        }}
        size="sm"
        titulo="Adicionar Parcela"
        centralizado={true}
        botaoConfirma={{
          onClick: handleConfirma,
        }}
        isLoading={isLoanding}
        loadingText={"Adicionando..."}
      >
        <InputControl
          label="Quantidade de Parcela"
          type="number"
          value={qtdParcela?.toString() ?? ""}
          onChange={(e) =>
            trataValorInputNumberVazio((val) => atualizaValorPesquisa(setQtdparcela, val), e)
          }
        />
      </ModalBase>
    </>
  );
}
