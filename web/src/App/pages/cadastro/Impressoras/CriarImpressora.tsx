import { Box, Divider } from "@chakra-ui/react";
import { zodResolver } from "@hookform/resolvers/zod";
import { useMutation } from "@tanstack/react-query";
import { FormProvider, useForm } from "react-hook-form";
import { useNavigate } from "react-router-dom";
import * as zod from "zod";
import { fetchCriarImpressora } from "../../../../requests/resquestImpressoras.ts";
import Acessos from "../../../../utils/Acessos.ts";
import { getMensagemDeErroOuProcureSuporte } from "../../../../utils/errors.ts";
import { ButtonControlForm } from "../../../components/ButoonControl/index.tsx";
import { CabecalhoPages } from "../../../components/CabecalhoPages/index.tsx";
import useCR5Axios from "../../../hooks/useCR5Axios.ts";
import { useFuncaoNotificacao } from "../../../hooks/useFuncaoNotificacao.tsx";
import { useValidaAcessos } from "../../../hooks/useValidaAcessos.ts";
import { useUnidadeStore } from "../../../stores/UnidadeStore.tsx";
import { FormImpressora } from "./components/FormImpressora.tsx";
import { ImpressorasValidateSchema } from "./components/ImpressorasValidateSchema.ts";

export function CriarImpressora() {
  const { axios } = useCR5Axios();
  type CriandoImpressoraFormData = zod.infer<typeof ImpressorasValidateSchema>;
  const navigate = useNavigate();
  const { unidadeAtual } = useUnidadeStore();

  const methods = useForm<CriandoImpressoraFormData>({
    criteriaMode: "all",
    mode: "all",
    resolver: zodResolver(ImpressorasValidateSchema),
  });

  const notificacao = useFuncaoNotificacao();

  useValidaAcessos([Acessos.CADASTRO_DE_IMPRESSORAS]);

  const { mutate, isLoading } = useMutation(
    () => {
      const params = {
        idUnidade: unidadeAtual.id,
        descricao: methods.getValues("descricao").toUpperCase(),
        modelo: methods.getValues("modelo"),
        ipMaquina: methods.getValues("ipMaquina"),
        gaveta: methods.getValues("gaveta") === "S" ? true : false,
        guilhotina: methods.getValues("guilhotina") === "S" ? true : false,
        tipoPorta: methods.getValues("tipoPorta"),
        porta: Number(methods.getValues("porta")),
      };

      return fetchCriarImpressora(params, axios);
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

  function handleImpressora() {
    mutate();
  }

  const tituloTela = "Cadastro Nova Impressora";
  const subtitulo = "Este cadastro será apenas para Unidade Atual";

  return (
    <Box display="flex" alignItems="center" flexDirection="column">
      <CabecalhoPages titulo={tituloTela} subtitulo={subtitulo} />
      <Box w={{ base: "100%", md: "auto", lg: "55%" }}>
        <FormProvider {...methods}>
          <form onSubmit={methods.handleSubmit(handleImpressora)}>
            <FormImpressora />
            <Divider mt={5} />
            <ButtonControlForm isLoading={isLoading} />
          </form>
        </FormProvider>
      </Box>
    </Box>
  );
}
