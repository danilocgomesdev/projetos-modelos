import { Box, Divider } from "@chakra-ui/react";
import { zodResolver } from "@hookform/resolvers/zod";
import { useMutation } from "@tanstack/react-query";
import { FormProvider, useForm } from "react-hook-form";
import { useNavigate } from "react-router-dom";
import * as zod from "zod";
import { postCriarBanco } from "../../../../requests/requestsBancos.ts";
import Acessos from "../../../../utils/Acessos.ts";
import { getMensagemDeErroOuProcureSuporte } from "../../../../utils/errors";
import { ButtonControlForm } from "../../../components/ButoonControl/index.tsx";
import { CabecalhoPages } from "../../../components/CabecalhoPages";
import useCR5Axios from "../../../hooks/useCR5Axios";
import { useFuncaoNotificacao } from "../../../hooks/useFuncaoNotificacao.tsx";
import { useValidaAcessos } from "../../../hooks/useValidaAcessos.ts";
import { BancoValidateSchema } from "./components/BancoValidateSchema.ts";
import { FormBanco } from "./components/FormBanco.tsx";

export function CriarBanco() {
  const { axios } = useCR5Axios();
  type CriandoBancoFormData = zod.infer<typeof BancoValidateSchema>;
  const navigate = useNavigate();

  const methods = useForm<CriandoBancoFormData>({
    criteriaMode: "all",
    mode: "all",
    resolver: zodResolver(BancoValidateSchema),
  });

  const notificacao = useFuncaoNotificacao();

  useValidaAcessos([Acessos.BANCOS]);

  const { mutate, isLoading } = useMutation(
    () => {
      const params = {
        numero: methods.getValues("numero"),
        abreviatura: methods.getValues("abreviatura").toUpperCase(),
        nome: methods.getValues("nome").toUpperCase(),
      };

      return postCriarBanco(params, axios);
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

  function handleCriarBanco() {
    mutate();
  }

  return (
    <Box display="flex" alignItems="center" flexDirection="column">
      <CabecalhoPages titulo="Cadastrar Bancos" />
      <Box w={{ base: "100%", md: "auto", lg: "55%" }}>
        <FormProvider {...methods}>
          <form onSubmit={methods.handleSubmit(handleCriarBanco)}>
            <FormBanco />
            <Divider mt={5} />
            <ButtonControlForm isLoading={isLoading} />
          </form>
        </FormProvider>
      </Box>
    </Box>
  );
}
