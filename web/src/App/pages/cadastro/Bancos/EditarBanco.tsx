import { Box, Divider } from "@chakra-ui/react";
import { zodResolver } from "@hookform/resolvers/zod";
import { useMutation } from "@tanstack/react-query";
import { useEffect, useState } from "react";
import { FormProvider, useForm } from "react-hook-form";
import { useNavigate } from "react-router-dom";
import * as zod from "zod";
import { putEditarBanco } from "../../../../requests/requestsBancos.ts";
import Acessos from "../../../../utils/Acessos.ts";
import { getMensagemDeErroOuProcureSuporte } from "../../../../utils/errors";
import { ButtonControlForm } from "../../../components/ButoonControl/index.tsx";
import { CabecalhoPages } from "../../../components/CabecalhoPages";
import useCR5Axios from "../../../hooks/useCR5Axios";
import { useFuncaoNotificacao } from "../../../hooks/useFuncaoNotificacao.tsx";
import { useValidaAcessos } from "../../../hooks/useValidaAcessos.ts";
import { BancoValidateSchema } from "./components/BancoValidateSchema.ts";
import { FormBanco } from "./components/FormBanco.tsx";
import { useBancoStore } from "./store/BancoStore.tsx";

export function EditarBanco() {
  const { axios } = useCR5Axios();
  type EditandoBancoFormData = zod.infer<typeof BancoValidateSchema>;
  const navigate = useNavigate();

  const { banco } = useBancoStore();

  const notificacao = useFuncaoNotificacao();
  const [validouPermissao, setValidouPermissao] = useState(false);

  useValidaAcessos([Acessos.BANCOS], () => {
    setValidouPermissao(true);
  });

  useEffect(() => {
    if (!banco) {
      navigate("./..");
    }
  }, [banco, navigate]);

  const methods = useForm<EditandoBancoFormData>({
    criteriaMode: "all",
    mode: "all",
    resolver: zodResolver(BancoValidateSchema),
  });

  useEffect(() => {
    if (banco) {
      methods.setValue("numero", banco.numero);
      methods.setValue("abreviatura", banco.abreviatura);
      methods.setValue("nome", banco.nome);
    }
  }, []);

  const { mutate, isLoading } = useMutation(
    () => {
      const params = {
        id: banco?.id ?? -1, // Sempre tem
        numero: methods.getValues("numero"),
        abreviatura: methods.getValues("abreviatura").toUpperCase(),
        nome: methods.getValues("nome").toUpperCase(),
      };

      return putEditarBanco(params, axios);
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

  if (!banco || !validouPermissao) {
    return <></>;
  }

  const subtitulo = `Editando o banco ${banco.id} - ${banco.nome}`;

  return (
    <Box display="flex" alignItems="center" flexDirection="column">
      <CabecalhoPages titulo="Editar Banco" subtitulo={subtitulo} />
      <Box w={{ base: "100%", md: "auto", lg: "55%" }}>
        <FormProvider {...methods}>
          <form onSubmit={methods.handleSubmit(() => mutate())}>
            <FormBanco />
            <Divider mt={5} />
            <ButtonControlForm isLoading={isLoading} />
          </form>
        </FormProvider>
      </Box>
    </Box>
  );
}
