import { Box, Divider, useDisclosure } from "@chakra-ui/react";
import { zodResolver } from "@hookform/resolvers/zod";
import { useMutation } from "@tanstack/react-query";
import { useEffect, useState } from "react";
import { FormProvider, useForm } from "react-hook-form";
import { useNavigate } from "react-router-dom";
import * as zod from "zod";
import { AgenciaDTO } from "../../../../models/DTOs/AgenciaBancoConta/AgenciaDTO.ts";
import { BancoDTO } from "../../../../models/DTOs/AgenciaBancoConta/BancoDTO.ts";
import { fetchAtualizarAgencias } from "../../../../requests/requestsAgencias";
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
import { useAgenciaStore } from "./store/AgenciaStore.tsx";

export function EditarAgencia() {
  const { axios } = useCR5Axios();
  type EditandoAgenciaFormData = zod.infer<typeof AgenciaValidateSchema>;
  const navigate = useNavigate();
  const { agencia, setAgencia } = useAgenciaStore();
  const { isOpen, onOpen, onClose } = useDisclosure();
  const [bancoFiltrado, setBancoFiltrado] = useState<BancoDTO>();

  const notificacao = useFuncaoNotificacao();
  const [validouPermissao, setValidouPermissao] = useState(false);

  useValidaAcessos([Acessos.AGENCIAS], () => {
    setValidouPermissao(true);
  });

  useEffect(() => {
    if (!agencia) {
      navigate("./..");
    }
  }, [agencia, navigate]);

  const methods = useForm<EditandoAgenciaFormData>({
    criteriaMode: "all",
    mode: "all",
    resolver: zodResolver(AgenciaValidateSchema),
  });

  function handleBancoSelecionado(banco: BancoDTO) {
    setBancoFiltrado(banco);
    if (banco.id) {
      methods.setValue("idBanco", banco.id.toString());
      methods.setValue("banco", banco.nome);
    }
  }

  function handleAgenciaSelecionada(agencia: AgenciaDTO) {
    setAgencia(agencia);
    methods.setValue("numero", agencia.numero);
    methods.setValue("digitoVerificador", agencia.digitoVerificador);
    methods.setValue("nome", agencia.nome);
    methods.setValue("cnpj", agencia.cnpj);
    methods.setValue("cidade", agencia.cidade);
    handleBancoSelecionado(agencia.banco);
  }

  useEffect(() => {
    if (agencia) {
      handleAgenciaSelecionada(agencia);
    }
  }, [agencia]);

  const { mutate, isLoading } = useMutation(
    (idAgencia: number) => {
      const params = {
        idAgencia: idAgencia,
        idBanco: Number(methods.getValues("idBanco")),
        cnpj: methods.getValues("cnpj").replace(/\D/g, ""),
        digitoVerificador: methods.getValues("digitoVerificador"),
        cidade: methods.getValues("cidade").toUpperCase(),
        numero: methods.getValues("numero"),
        nome: methods.getValues("nome").toUpperCase(),
      };

      return fetchAtualizarAgencias(params, axios);
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

  if (!agencia || !validouPermissao) {
    return <></>;
  }

  const subtitulo = `Editando a agência: ${agencia.id} - ${agencia.nome}`;

  return (
    <Box display="flex" alignItems="center" flexDirection="column">
      <CabecalhoPages titulo="Editar Agência" subtitulo={subtitulo} />
      <Box w={{ base: "100%", md: "auto", lg: "55%" }}>
        <FormProvider {...methods}>
          <form onSubmit={methods.handleSubmit(() => mutate(agencia.id))}>
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
