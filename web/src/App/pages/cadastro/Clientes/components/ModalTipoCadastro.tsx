import { Radio, RadioGroup, Stack } from "@chakra-ui/react";
import { useState } from "react";
import { useNavigate } from "react-router-dom";
import { ModalBase } from "../../../../components/ModalBase";

interface ModalTipoCadastroProps {
  abrirModal: boolean;
  setAbrirModal: (value: boolean) => void;
}

export function ModalTipoCadastro({ abrirModal, setAbrirModal }: ModalTipoCadastroProps) {
  const [value, setValue] = useState("1");
  const navigate = useNavigate();

  function handleNavigateTipo(opcao: string) {
    switch (opcao) {
      case "1":
        navigate("./pessoa-fisica-novo");
        break;
      case "2":
        navigate("./pessoa-juridica-novo");
        break;
      case "3":
        navigate("./estrangeiro-novo");
        break;
      default:
        setAbrirModal(false);
        break;
    }
  }

  return (
    <>
      <ModalBase
        isOpen={abrirModal}
        onClose={() => setAbrirModal(false)}
        titulo="Cadastrar novo cliente"
        size="lg"
        centralizado={true}
        botaoConfirma={{
          label: "Ok",
          onClick: () => handleNavigateTipo(value),
        }}
      >
        <RadioGroup onChange={setValue} value={value}>
          <Stack direction="row">
            <Radio value="1">Pessoa Física</Radio>
            <Radio value="2">Pessoa Juridica</Radio>
            <Radio value="3">Estrangeiro</Radio>
          </Stack>
        </RadioGroup>
      </ModalBase>
    </>
  );
}
