import { Box, Divider } from "@chakra-ui/react";
import { zodResolver } from "@hookform/resolvers/zod";
import { useMutation } from "@tanstack/react-query";
import { FormProvider, useForm } from "react-hook-form";
import { useNavigate } from "react-router-dom";
import * as zod from "zod";
import { fetchCriarEstrangeiro } from "../../../../requests/requestsPessoasCr5.ts";
import Acessos from "../../../../utils/Acessos.ts";
import { getMensagemDeErroOuProcureSuporte } from "../../../../utils/errors.ts";
import { ButtonControlForm } from "../../../components/ButoonControl/index.tsx";
import { CabecalhoPages } from "../../../components/CabecalhoPages/index.tsx";
import useCR5Axios from "../../../hooks/useCR5Axios.ts";
import { useFuncaoNotificacao } from "../../../hooks/useFuncaoNotificacao.tsx";
import { useValidaAcessos } from "../../../hooks/useValidaAcessos.ts";
import { EstrangeiroValidateSchema } from "./components/ClientesValidateSchema.ts";
import { FormClienteEstrangeiro } from "./components/FormClienteEstrangeiro.tsx";

export function CriarClienteEstangeiro() {
  const { axios } = useCR5Axios();
  type CriandoClientePessoaFisicaFormData = zod.infer<typeof EstrangeiroValidateSchema>;
  const navigate = useNavigate();

  const notificacao = useFuncaoNotificacao();

  useValidaAcessos([Acessos.PESSOAS]);

  const methods = useForm<CriandoClientePessoaFisicaFormData>({
    criteriaMode: "all",
    mode: "all",
    resolver: zodResolver(EstrangeiroValidateSchema),
  });

  const { mutate, isLoading } = useMutation(
    () => {
      const params = {
        idEstrangeiro: methods.getValues("idEstrangeiro").toUpperCase(),
        descricao: methods.getValues("descricao").toUpperCase(),
        dataNascimento: methods.getValues("dataNascimento") || undefined,
        logradouro: methods.getValues("logradouro"),
        complemento: methods.getValues("complemento") || undefined,
        codigoPostal: methods.getValues("codigoPostal"),
        bairro: methods.getValues("bairro"),
        cidade: methods.getValues("cidade"),
        estado: methods.getValues("estado"),
        codigoPais: Number(methods.getValues("codigoPais")),
        pais: methods.getValues("pais"),
        telefone: methods.getValues("telefone"),
        telefone2: methods.getValues("telefone2") || undefined,
        celular: methods.getValues("celular"),
        celular2: methods.getValues("celular2") || undefined,
        numeroResidencia: methods.getValues("numeroResidencia"),
        email: methods.getValues("email"),
      };
      return fetchCriarEstrangeiro(params, axios);
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

  function handleClienteEstrangeiro() {
    mutate();
  }

  return (
    <Box display="flex" alignItems="center" flexDirection="column">
      <CabecalhoPages titulo="Cadastrar Cliente Estangeiro" />
      <Box w={{ base: "100%", md: "auto", lg: "55%" }}>
        <FormProvider {...methods}>
          <form onSubmit={methods.handleSubmit(handleClienteEstrangeiro)}>
            <FormClienteEstrangeiro />
            <Divider mt={5} />
            <ButtonControlForm isLoading={isLoading} />
          </form>
        </FormProvider>
      </Box>
    </Box>
  );
}
