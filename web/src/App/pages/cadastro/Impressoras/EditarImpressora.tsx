import { Box, Divider } from "@chakra-ui/react";
import { zodResolver } from "@hookform/resolvers/zod";
import { useMutation } from "@tanstack/react-query";
import { useEffect, useState } from "react";
import { FormProvider, useForm } from "react-hook-form";
import { useNavigate } from "react-router-dom";
import * as zod from "zod";
import { ImpressorasDTO } from "../../../../models/DTOs/Outros/ImpressorasDTO.ts";
import { fetchAlterarImpressora } from "../../../../requests/resquestImpressoras.ts";
import Acessos from "../../../../utils/Acessos.ts";
import { getMensagemDeErroOuProcureSuporte } from "../../../../utils/errors.ts";
import { ButtonControlForm } from "../../../components/ButoonControl/index.tsx";
import { CabecalhoPages } from "../../../components/CabecalhoPages";
import useCR5Axios from "../../../hooks/useCR5Axios.ts";
import { useFuncaoNotificacao } from "../../../hooks/useFuncaoNotificacao.tsx";
import { useValidaAcessos } from "../../../hooks/useValidaAcessos.ts";
import { useUnidadeStore } from "../../../stores/UnidadeStore.tsx";
import { FormImpressora } from "./components/FormImpressora.tsx";
import { ImpressorasValidateSchema } from "./components/ImpressorasValidateSchema.ts";
import { useImpressoraStore } from "./store/ImpressoraStore.tsx";

export function EditarImpressora() {
  const { axios } = useCR5Axios();
  type EditandoImpressoraFormData = zod.infer<typeof ImpressorasValidateSchema>;
  const navigate = useNavigate();
  const { impressora, setImpressora } = useImpressoraStore();
  const { unidadeAtual } = useUnidadeStore();

  const notificacao = useFuncaoNotificacao();
  const [validouPermissao, setValidouPermissao] = useState(false);

  useValidaAcessos([Acessos.CADASTRO_DE_IMPRESSORAS], () => {
    setValidouPermissao(true);
  });

  useEffect(() => {
    if (!impressora) {
      navigate("./..");
    }
  }, [impressora, navigate]);

  const methods = useForm<EditandoImpressoraFormData>({
    criteriaMode: "all",
    mode: "all",
    resolver: zodResolver(ImpressorasValidateSchema),
  });

  function handleImpressoraSelecionado(impressora: ImpressorasDTO) {
    setImpressora(impressora);
    methods.setValue("modelo", impressora.modelo);
    methods.setValue("descricao", impressora.descricao);
    methods.setValue("ipMaquina", impressora.ipMaquina);
    methods.setValue("tipoPorta", impressora.tipoPorta);
    methods.setValue("porta", impressora.porta.toString());
    methods.setValue("gaveta", impressora.gaveta ? "S" : "N");
    methods.setValue("guilhotina", impressora.guilhotina ? "S" : "N");
  }

  useEffect(() => {
    if (impressora) {
      handleImpressoraSelecionado(impressora);
    }
  }, [impressora]);

  const { mutate, isLoading } = useMutation(
    (idImpressora: number) => {
      const params = {
        idImpressora: idImpressora,
        idUnidade: unidadeAtual.id,
        descricao: methods.getValues("descricao").toUpperCase(),
        modelo: methods.getValues("modelo"),
        ipMaquina: methods.getValues("ipMaquina"),
        gaveta: methods.getValues("gaveta") === "S" ? true : false,
        guilhotina: methods.getValues("guilhotina") === "S" ? true : false,
        tipoPorta: methods.getValues("tipoPorta"),
        porta: Number(methods.getValues("porta")),
      };

      return fetchAlterarImpressora(params, axios);
    },
    {
      onSuccess: () => {
        notificacao({ message: "Atualizado com sucesso!", tipo: "success", titulo: "SUCESSO!" });
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

  const handleAtualizarImpressora = (): void => {
    if (impressora) {
      mutate(impressora.idImpressora);
    }
  };

  if (!impressora || !validouPermissao) {
    return <></>;
  }

  const tituloTela = "Editar Impressora";
  const subtitulo = `Esta edição será apenas para Impressora: ${impressora.idImpressora}, da Unidade: ${unidadeAtual.codigo}`;

  return (
    <Box display="flex" alignItems="center" flexDirection="column">
      <CabecalhoPages titulo={tituloTela} subtitulo={subtitulo} />
      <Box w={{ base: "100%", md: "auto", lg: "55%" }}>
        <FormProvider {...methods}>
          <form onSubmit={methods.handleSubmit(handleAtualizarImpressora)}>
            <FormImpressora />
            <Divider mt={5} />
            <ButtonControlForm isLoading={isLoading} />
          </form>
        </FormProvider>
      </Box>
    </Box>
  );
}
