import { Box, Divider } from "@chakra-ui/react";
import { zodResolver } from "@hookform/resolvers/zod";
import { useMutation } from "@tanstack/react-query";
import { useState } from "react";
import { FormProvider, useForm } from "react-hook-form";
import { useNavigate } from "react-router-dom";
import * as zod from "zod";
import { fetchCriarProdutoDadoContabil } from "../../../../requests/requestsProdutoDadoContabil.ts";
import Acessos from "../../../../utils/Acessos.ts";
import { DefaultSistema } from "../../../../utils/constantes.ts";
import { getMensagemDeErroOuProcureSuporte } from "../../../../utils/errors.ts";
import { ButtonControlForm } from "../../../components/ButoonControl/index.tsx";
import { CabecalhoPages } from "../../../components/CabecalhoPages/index.tsx";
import useCR5Axios from "../../../hooks/useCR5Axios.ts";
import { useFuncaoNotificacao } from "../../../hooks/useFuncaoNotificacao.tsx";
import { useValidaAcessos } from "../../../hooks/useValidaAcessos.ts";
import { FormNovoProdutoServico } from "./components/FormNovoProdutoServico.tsx";
import { ProdutoServicoValidateSchema } from "./components/ProdutoServicoValidateSchema.ts";

export function CriarProdutoServico() {
  const { axios } = useCR5Axios();
  type CriandoProdutoServicoFormData = zod.infer<typeof ProdutoServicoValidateSchema>;
  const navigate = useNavigate();

  const notificacao = useFuncaoNotificacao();

  useValidaAcessos([Acessos.PRODUTO_SERVICO_AVULSO]);

  const methods = useForm<CriandoProdutoServicoFormData>({
    criteriaMode: "all",
    mode: "all",
    resolver: zodResolver(ProdutoServicoValidateSchema),
  });
  const [precoFormatoMoeda, setPrecoFormatoMoeda] = useState<string | null>();

  const { mutate, isLoading } = useMutation(
    () => {
      const params = {
        produto: methods.getValues("produto").toUpperCase(),
        preco: Number((Number(methods.getValues("preco")?.replace(/\D+/g, "")) / 100).toFixed(2)),
        idSistema: DefaultSistema.CR5,
        status: methods.getValues("status"),
        dataInativacao: methods.getValues("status") === "A" ? null : new Date().getTime(),
        codProdutoProtheus: methods.getValues("codProdutoProtheus"),
      };

      return fetchCriarProdutoDadoContabil(params, axios);
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

  function handleProdutoServico() {
    mutate();
  }

  return (
    <Box display="flex" alignItems="center" flexDirection="column">
      <CabecalhoPages titulo="Cadastrar Produto / Serviço" />
      <Box w={{ base: "100%", md: "auto", lg: "55%" }}>
        <FormProvider {...methods}>
          <form onSubmit={methods.handleSubmit(handleProdutoServico)}>
            <FormNovoProdutoServico
              setPrecoFormatoMoeda={setPrecoFormatoMoeda}
              precoFormatoMoeda={precoFormatoMoeda}
            />
            <Divider mt={5} />
            <ButtonControlForm isLoading={isLoading} />
          </form>
        </FormProvider>
      </Box>
    </Box>
  );
}
