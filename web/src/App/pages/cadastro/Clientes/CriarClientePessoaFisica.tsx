import { Box, Divider } from "@chakra-ui/react";
import { zodResolver } from "@hookform/resolvers/zod";
import { useMutation } from "@tanstack/react-query";
import { FormProvider, useForm } from "react-hook-form";
import { useNavigate } from "react-router-dom";
import * as zod from "zod";
import { fetchCriarPessoaFisica } from "../../../../requests/requestsPessoasCr5.ts";
import Acessos from "../../../../utils/Acessos.ts";
import { getMensagemDeErroOuProcureSuporte } from "../../../../utils/errors.ts";
import { ButtonControlForm } from "../../../components/ButoonControl/index.tsx";
import { CabecalhoPages } from "../../../components/CabecalhoPages/index.tsx";
import useCR5Axios from "../../../hooks/useCR5Axios.ts";
import { useFuncaoNotificacao } from "../../../hooks/useFuncaoNotificacao.tsx";
import { useValidaAcessos } from "../../../hooks/useValidaAcessos.ts";
import { PessoaFisicaValidateSchema } from "./components/ClientesValidateSchema.ts";
import { FormClientePessoaFisica } from "./components/FormClientePessoaFisica.tsx";

export function CriarClientePessoaFisica() {
  const { axios } = useCR5Axios();
  type CriandoClientePessoaFisicaFormData = zod.infer<typeof PessoaFisicaValidateSchema>;
  const navigate = useNavigate();

  const notificacao = useFuncaoNotificacao();

  useValidaAcessos([Acessos.PESSOAS]);

  const methods = useForm<CriandoClientePessoaFisicaFormData>({
    criteriaMode: "all",
    mode: "all",
    resolver: zodResolver(PessoaFisicaValidateSchema),
  });

  const { mutate, isLoading } = useMutation(
    () => {
      const params = {
        cpf: methods.getValues("cpf"),
        rg: methods.getValues("rg"),
        descricao: methods.getValues("descricao").toUpperCase(),
        dataNascimento: methods.getValues("dataNascimento"),
        logradouro: methods.getValues("logradouro"),
        complemento: methods.getValues("complemento") || undefined,
        bairro: methods.getValues("bairro"),
        cidade: methods.getValues("cidade"),
        estado: methods.getValues("estado"),
        cep: methods.getValues("cep"),
        telefone: methods.getValues("telefone"),
        telefone2: methods.getValues("telefone2") || undefined,
        idResponsavelFinanceiro: Number(methods.getValues("idResponsavelFinanceiro")),
        emancipado: methods.getValues("emancipado") === "S" ? true : false,
        celular: methods.getValues("celular"),
        celular2: methods.getValues("celular2") || undefined,
        numeroResidencia: methods.getValues("numeroResidencia") || undefined,
        email: methods.getValues("email"),
      };
      return fetchCriarPessoaFisica(params, axios);
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

  function handleClientePessoaFisica() {
    mutate();
  }

  const tituloTela = "Cadastrar Cliente Pessoa Física";
  const subtitulo = "Para buscar endreço pelo CEP, clique fora do campo após preencher o CEP";

  return (
    <Box display="flex" alignItems="center" flexDirection="column">
      <CabecalhoPages titulo={tituloTela} subtitulo={subtitulo} />
      <Box w={{ base: "100%", md: "auto", lg: "55%" }}>
        <FormProvider {...methods}>
          <form onSubmit={methods.handleSubmit(handleClientePessoaFisica)}>
            <FormClientePessoaFisica />
            <Divider mt={5} />
            <ButtonControlForm isLoading={isLoading} />
          </form>
        </FormProvider>
      </Box>
    </Box>
  );
}
