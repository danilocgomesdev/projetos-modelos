import { Box, Divider, useDisclosure } from "@chakra-ui/react";
import { zodResolver } from "@hookform/resolvers/zod";
import { useMutation } from "@tanstack/react-query";
import { useState } from "react";
import { FormProvider, useForm } from "react-hook-form";
import { useNavigate } from "react-router-dom";
import * as zod from "zod";
import { BancoDTO } from "../../../../models/DTOs/AgenciaBancoConta/BancoDTO.ts";
import { fetchCriarAgencias } from "../../../../requests/requestsAgencias";
import Acessos from "../../../../utils/Acessos.ts";
import { getMensagemDeErroOuProcureSuporte } from "../../../../utils/errors";
import { ButtonControlForm } from "../../../components/ButoonControl/index.tsx";
import { CabecalhoPages } from "../../../components/CabecalhoPages";
import useCR5Axios from "../../../hooks/useCR5Axios";
import { useFuncaoNotificacao } from "../../../hooks/useFuncaoNotificacao.tsx";
import { useValidaAcessos } from "../../../hooks/useValidaAcessos.ts";
import { ModalPesquisaBanco } from "../Bancos/components/ModalPesquisaBanco.tsx";
import { AgenciaValidateSchema } from "./components/AgenciaValidateSchema";
import { FormAgencia } from "./components/FormAgencia.tsx";

export function CriarAgencia() {
  const { axios } = useCR5Axios();
  type CriandoAgenciaFormData = zod.infer<typeof AgenciaValidateSchema>;
  const navigate = useNavigate();

  const methods = useForm<CriandoAgenciaFormData>({
    criteriaMode: "all",
    mode: "all",
    resolver: zodResolver(AgenciaValidateSchema),
  });

  const notificacao = useFuncaoNotificacao();

  useValidaAcessos([Acessos.AGENCIAS]);

  const { isOpen, onOpen, onClose } = useDisclosure();

  const [bancoFiltrado, setBancoFiltrado] = useState<BancoDTO>();

  function handleBancoSelecionado(banco: BancoDTO): void {
    setBancoFiltrado(banco);
    if (banco.id) {
      methods.setValue("idBanco", banco.id.toString());
      methods.setValue("banco", banco.nome);
    }
  }

  const { mutate, isLoading } = useMutation(
    () => {
      const params = {
        idBanco: Number(methods.getValues("idBanco")),
        cnpj: methods.getValues("cnpj").replace(/[^\d]/g, ""),
        digitoVerificador: methods.getValues("digitoVerificador"),
        cidade: methods.getValues("cidade").toUpperCase(),
        numero: methods.getValues("numero"),
        nome: methods.getValues("nome").toUpperCase(),
      };

      return fetchCriarAgencias(params, axios);
    },
    {
      onSuccess: () => {
        notificacao({ message: "Salvo com sucesso!", tipo: "success", titulo: "SUCESSO!" });
        navigate("./..");
      },
      onError: (e) => {
        notificacao({
          message: getMensagemDeErroOuProcureSuporte(e),
          tipo: "error",
          titulo: "ERRO!",
        });
      },
    }
  );

  function handleCriarAgencia() {
    mutate();
  }

  return (
    <Box display="flex" alignItems="center" flexDirection="column">
      <CabecalhoPages titulo="Cadastrar Agências" />
      <Box w={{ base: "100%", md: "auto", lg: "55%" }}>
        <FormProvider {...methods}>
          <form onSubmit={methods.handleSubmit(handleCriarAgencia)}>
            <FormAgencia onOpen={onOpen} bancoFiltrado={bancoFiltrado} />
            <Divider mt={5} />
            <ButtonControlForm isLoading={isLoading} />
          </form>
        </FormProvider>
      </Box>
      <ModalPesquisaBanco
        isOpen={isOpen}
        onClose={onClose}
        setBancoFiltrado={handleBancoSelecionado}
      />
    </Box>
  );
}
