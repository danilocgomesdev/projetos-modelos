import { zodResolver } from "@hookform/resolvers/zod";
import { useEffect } from "react";
import { FormProvider, useForm } from "react-hook-form";
import * as zod from "zod";
import { FaixaNossoNumeroDTO } from "../../../../../models/DTOs/ConvenioBancario";
import { FaixaNossoNumeroCRUDDTO } from "../../../../../requests/requestsConveniosBancarios";
import { ModalBase } from "../../../../components/ModalBase";
import { FaixaNossoNumeroValidateSchema } from "./FaixaNossoNumeroValidateSchema";
import { FormNossoNumero } from "./FormNossoNumero";

interface ModalEditarFaixaNossoNumeroProps {
  isOpen: boolean;
  onClose: () => void;
  onEdit: (params: { faixa: FaixaNossoNumeroCRUDDTO; idFaixa: number }) => void;
  titulo: string;
  faixaNossoNumero: FaixaNossoNumeroDTO | undefined;
  isLoading?: boolean;
}

export function ModalEditarFaixaNossoNumero({
  isOpen,
  onClose,
  onEdit,
  titulo,
  faixaNossoNumero,
  isLoading,
}: ModalEditarFaixaNossoNumeroProps) {
  type FaixaNossoNumeroFormData = zod.infer<typeof FaixaNossoNumeroValidateSchema>;

  useEffect(() => {
    if (faixaNossoNumero) {
      methods.setValue("nossoNumeroAtual", faixaNossoNumero.nossoNumeroAtual);
      methods.setValue("nossoNumeroInicial", faixaNossoNumero.nossoNumeroInicial);
      methods.setValue("nossoNumeroFinal", faixaNossoNumero.nossoNumeroFinal);
    }
  }, [faixaNossoNumero]);

  const methods = useForm<FaixaNossoNumeroFormData>({
    criteriaMode: "all",
    mode: "all",
    resolver: zodResolver(FaixaNossoNumeroValidateSchema),
  });

  function handleEditarFaixaNossoNumero() {
    onEdit({
      faixa: {
        nossoNumeroAtual: methods.getValues("nossoNumeroAtual"),
        nossoNumeroInicial: methods.getValues("nossoNumeroInicial"),
        nossoNumeroFinal: methods.getValues("nossoNumeroFinal"),
      },
      idFaixa: faixaNossoNumero?.id ?? -1, // Sempre deve existir
    });
  }

  return (
    <ModalBase
      isOpen={isOpen}
      onClose={onClose}
      titulo={titulo}
      size="2xl"
      centralizado={true}
      isLoading={isLoading}
      botaoConfirma={{
        label: "Salvar Faixa",
        onClick: methods.handleSubmit(handleEditarFaixaNossoNumero),
      }}
    >
      <FormProvider {...methods}>
        <FormNossoNumero />
      </FormProvider>
    </ModalBase>
  );
}
