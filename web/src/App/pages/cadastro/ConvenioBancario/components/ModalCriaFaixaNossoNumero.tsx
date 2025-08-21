import { zodResolver } from "@hookform/resolvers/zod";
import { FormProvider, useForm } from "react-hook-form";
import * as zod from "zod";
import { FaixaNossoNumeroCRUDDTO } from "../../../../../requests/requestsConveniosBancarios";
import { ModalBase } from "../../../../components/ModalBase";
import { FaixaNossoNumeroValidateSchema } from "./FaixaNossoNumeroValidateSchema";
import { FormNossoNumero } from "./FormNossoNumero";

interface ModalCriaFaixaNossoNumeroProps {
  isOpen: boolean;
  onClose: () => void;
  onCreate: (faixa: FaixaNossoNumeroCRUDDTO) => void;
  titulo: string;
}

export function ModalCriaFaixaNossoNumero({
  isOpen,
  onClose,
  onCreate,
  titulo,
}: ModalCriaFaixaNossoNumeroProps) {
  type FaixaNossoNumeroFormData = zod.infer<typeof FaixaNossoNumeroValidateSchema>;

  const methods = useForm<FaixaNossoNumeroFormData>({
    criteriaMode: "all",
    mode: "all",
    resolver: zodResolver(FaixaNossoNumeroValidateSchema),
  });

  function handleCriaFaixaNossoNumero() {
    onCreate({
      nossoNumeroAtual: methods.getValues("nossoNumeroAtual"),
      nossoNumeroInicial: methods.getValues("nossoNumeroInicial"),
      nossoNumeroFinal: methods.getValues("nossoNumeroFinal"),
    });
  }

  return (
    <ModalBase
      isOpen={isOpen}
      onClose={onClose}
      titulo={titulo}
      size="2xl"
      centralizado={true}
      botaoConfirma={{
        label: "Criar Faixa",
        onClick: methods.handleSubmit(handleCriaFaixaNossoNumero),
      }}
    >
      <FormProvider {...methods}>
        <FormNossoNumero />
      </FormProvider>
    </ModalBase>
  );
}
