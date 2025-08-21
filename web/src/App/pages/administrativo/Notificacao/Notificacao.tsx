import { Box } from "@chakra-ui/react";
import { zodResolver } from "@hookform/resolvers/zod";
import { useMutation } from "@tanstack/react-query";
import { FormProvider, useForm } from "react-hook-form";
import * as zod from "zod";
import { fetchEnviarNotificao } from "../../../../requests/requestsNotificacao.ts";
import Acessos from "../../../../utils/Acessos.ts";
import { getMensagemDeErroOuProcureSuporte } from "../../../../utils/errors";
import { ButtonControlForm } from "../../../components/ButoonControl/ButtonControlForm.tsx";
import { CabecalhoPages } from "../../../components/CabecalhoPages";
import useCR5Axios from "../../../hooks/useCR5Axios";
import { useFuncaoNotificacao } from "../../../hooks/useFuncaoNotificacao.tsx";
import { useValidaAcessos } from "../../../hooks/useValidaAcessos.ts";
import { EnviarNotificacaoValidateSchema } from "./components/EnviarNotificacaoValidateSchema.ts";
import { FormNotificacao } from "./components/FormNotificacao.tsx";

export function Notificacao() {
  const { axios } = useCR5Axios();
  const notificacao = useFuncaoNotificacao();

  useValidaAcessos([Acessos.ADMINISTRADOR_CR5]);

  type EnviarNotificacaoFormData = zod.infer<typeof EnviarNotificacaoValidateSchema>;

  const methods = useForm<EnviarNotificacaoFormData>({
    criteriaMode: "all",
    mode: "all",
    resolver: zodResolver(EnviarNotificacaoValidateSchema),
  });

  const { mutate, isLoading } = useMutation(
    () => {
      const params = {
        mensagem: methods.getValues("mensagem"),
      };

      return fetchEnviarNotificao(params, axios);
    },
    {
      onSuccess: () => {
        notificacao({ message: "Salvo com sucesso!", tipo: "success", titulo: "SUCESSO!" });
        methods.reset();
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

  function handleEnviarNotificao() {
    mutate();
  }

  return (
    <Box display="flex" alignItems="center" flexDirection="column">
      <CabecalhoPages titulo="Enviar Notificação" />
      <Box w={{ base: "100%", md: "auto", lg: "55%" }}>
        <FormProvider {...methods}>
          <form onSubmit={methods.handleSubmit(handleEnviarNotificao)}>
            <FormNotificacao />
            <ButtonControlForm
              isLoading={isLoading}
              textoSubimit="Enviar"
              textoLoadingSubimit="Enviando"
              navigateCancelar="/"
            />
          </form>
        </FormProvider>
      </Box>
    </Box>
  );
}
