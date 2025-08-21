import { Box, Divider } from "@chakra-ui/react";
import { zodResolver } from "@hookform/resolvers/zod";
import { useMutation } from "@tanstack/react-query";
import { FormProvider, useForm } from "react-hook-form";
import { useNavigate } from "react-router-dom";
import * as zod from "zod";
import { fetchCriarPessoaJuridica } from "../../../../requests/requestsPessoasCr5.ts";
import Acessos from "../../../../utils/Acessos.ts";
import { getMensagemDeErroOuProcureSuporte } from "../../../../utils/errors.ts";
import { ButtonControlForm } from "../../../components/ButoonControl/index.tsx";
import { CabecalhoPages } from "../../../components/CabecalhoPages/index.tsx";
import useCR5Axios from "../../../hooks/useCR5Axios.ts";
import { useFuncaoNotificacao } from "../../../hooks/useFuncaoNotificacao.tsx";
import { useValidaAcessos } from "../../../hooks/useValidaAcessos.ts";
import { PessoaJuridicaValidateSchema } from "./components/ClientesValidateSchema.ts";
import { FormClientePessoaJuridica } from "./components/FormClientePessoaJuridica.tsx";

export function CriarClientePessoaJuridica() {
  const { axios } = useCR5Axios();
  type CriandoClientePessoaJuridicaFormData = zod.infer<typeof PessoaJuridicaValidateSchema>;
  const navigate = useNavigate();

  const notificacao = useFuncaoNotificacao();

  useValidaAcessos([Acessos.PESSOAS]);

  const methods = useForm<CriandoClientePessoaJuridicaFormData>({
    criteriaMode: "all",
    mode: "all",
    resolver: zodResolver(PessoaJuridicaValidateSchema),
  });

  const { mutate, isLoading } = useMutation(
    () => {
      const params = {
        inscricaoEstadual: methods.getValues("inscricaoEstadual"),
        cnpj: methods.getValues("cnpj"),
        razaoSocial: methods.getValues("razaoSocial"),
        logradouro: methods.getValues("logradouro"),
        complemento: methods.getValues("complemento") || undefined,
        bairro: methods.getValues("bairro"),
        cidade: methods.getValues("cidade"),
        estado: methods.getValues("estado"),
        cep: methods.getValues("cep"),
        telefone: methods.getValues("telefone"),
        telefone2: methods.getValues("telefone2") || undefined,
        celular: methods.getValues("celular"),
        celular2: methods.getValues("celular2") || undefined,
        numeroResidencia: methods.getValues("numeroResidencia"),
        email: methods.getValues("email"),
      };
      return fetchCriarPessoaJuridica(params, axios);
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

  function handleClientePessoaJuridica() {
    mutate();
  }

  const tituloTela = "Cadastrar Cliente Pessoa Jurídica";
  const subtitulo = "Para buscar endreço pelo CEP, clique fora do campo após preencher o CEP";

  return (
    <Box display="flex" alignItems="center" flexDirection="column">
      <CabecalhoPages titulo={tituloTela} subtitulo={subtitulo} />
      <Box w={{ base: "100%", md: "auto", lg: "55%" }}>
        <FormProvider {...methods}>
          <form onSubmit={methods.handleSubmit(handleClientePessoaJuridica)}>
            <FormClientePessoaJuridica />
            <Divider mt={5} />
            <ButtonControlForm isLoading={isLoading} />
          </form>
        </FormProvider>
      </Box>
    </Box>
  );
}
