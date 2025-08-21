import { Box, Divider, useDisclosure } from "@chakra-ui/react";
import { zodResolver } from "@hookform/resolvers/zod";
import { useMutation } from "@tanstack/react-query";
import { useEffect, useRef, useState } from "react";
import { FormProvider, useForm } from "react-hook-form";
import { useNavigate } from "react-router-dom";
import * as zod from "zod";
import { AgenciaDTO } from "../../../../models/DTOs/AgenciaBancoConta/AgenciaDTO.ts";
import {
  AlterarContaCorrenteDTO,
  putEditarContaCorrente,
} from "../../../../requests/requestsContasCorrente.ts";
import Acessos from "../../../../utils/Acessos.ts";
import { getMensagemDeErroOuProcureSuporte } from "../../../../utils/errors.ts";
import { ButtonControlForm } from "../../../components/ButoonControl/index.tsx";
import { CabecalhoPages } from "../../../components/CabecalhoPages/index.tsx";
import useCR5Axios from "../../../hooks/useCR5Axios.ts";
import { useFuncaoNotificacao } from "../../../hooks/useFuncaoNotificacao.tsx";
import { useValidaAcessos } from "../../../hooks/useValidaAcessos.ts";
import { useUnidadeStore } from "../../../stores/UnidadeStore.tsx";
import { ModalPesquisaAgencia } from "../Agencias/components/ModalPesquisaAgencia.tsx";
import { ContaCorrenteValidateSchema } from "./components/ContaCorrenteValidateSchema.ts";
import { FormContaCorrente } from "./components/FormContaCorrente.tsx";
import { useContaCorrenteStore } from "./store/ContaCorrenteStore.ts";

export function EditarContaCorrente() {
  const { axios } = useCR5Axios();
  const navigate = useNavigate();

  const { contaCorrente } = useContaCorrenteStore();
  const [validouPermissao, setValidouPermissao] = useState(false);

  const notificacao = useFuncaoNotificacao();

  useValidaAcessos([Acessos.CONTAS_CORRENTES], () => {
    setValidouPermissao(true);
  });

  useEffect(() => {
    if (!contaCorrente) {
      navigate("./..");
    }
  }, [contaCorrente, navigate]);

  const { unidadeAtual } = useUnidadeStore();
  const unidadeInicial = useRef(unidadeAtual);

  useEffect(() => {
    if (unidadeAtual.id !== unidadeInicial.current.id) {
      navigate("./..");
    }
  }, [unidadeAtual]);

  type CriandoContaCorrenteFormData = zod.infer<typeof ContaCorrenteValidateSchema>;

  const methods = useForm<CriandoContaCorrenteFormData>({
    criteriaMode: "all",
    mode: "all",
    resolver: zodResolver(ContaCorrenteValidateSchema),
  });

  const { isOpen, onOpen, onClose } = useDisclosure();

  const [agenciaFiltrada, setAgenciaFiltrada] = useState<AgenciaDTO>();

  function handleAgenciaSelecionado(agencia: AgenciaDTO): void {
    setAgenciaFiltrada(agencia);
    if (agencia.id) {
      methods.setValue("idAgencia", agencia.id.toString());
    }
  }

  useEffect(() => {
    if (contaCorrente) {
      methods.setValue("idAgencia", contaCorrente.agencia.id.toString());
      methods.setValue("numeroOperacao", contaCorrente.numeroOperacao);
      methods.setValue("numeroConta", contaCorrente.numeroConta);
      methods.setValue("digitoConta", contaCorrente.digitoConta);
      methods.setValue("contaBanco", contaCorrente.contaBanco ?? "");
      methods.setValue("contaCliente", contaCorrente.contaCliente ?? "");
      methods.setValue("contaCaixa", contaCorrente.contaCaixa ?? "");
      methods.setValue("contaJuros", contaCorrente.contaJuros ?? "");
      methods.setValue("contaDescontos", contaCorrente.contaDescontos ?? "");
      methods.setValue("cofreBanco", contaCorrente.cofreBanco ?? "");
      methods.setValue("cofreAgencia", contaCorrente.cofreAgencia ?? "");
      methods.setValue("cofreConta", contaCorrente.cofreConta ?? "");
      methods.clearErrors();

      setAgenciaFiltrada(contaCorrente.agencia);
    }
  }, [contaCorrente]);

  const { mutate, isLoading } = useMutation(
    () => {
      const params: AlterarContaCorrenteDTO = {
        id: contaCorrente?.id ?? -1,
        idAgencia: Number(methods.getValues("idAgencia")),
        numeroOperacao: methods.getValues("numeroOperacao"),
        numeroConta: methods.getValues("numeroConta"),
        digitoConta: methods.getValues("digitoConta"),
        contaBanco: methods.getValues("contaBanco"),
        contaCliente: methods.getValues("contaCliente"),
        contaCaixa: methods.getValues("contaCaixa"),
        contaJuros: methods.getValues("contaJuros"),
        contaDescontos: methods.getValues("contaDescontos"),
        cofreBanco: methods.getValues("cofreBanco"),
        cofreAgencia: methods.getValues("cofreAgencia"),
        cofreConta: methods.getValues("cofreConta"),
        idUnidade: unidadeAtual.id,
      };

      return putEditarContaCorrente(params, axios);
    },
    {
      onSuccess: () => {
        notificacao({ message: "Alterado com sucesso!", tipo: "success", titulo: "SUCESSO!" });
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

  function handleEditarContaCorrete() {
    mutate();
  }

  if (!contaCorrente || !validouPermissao) {
    return <></>;
  }

  const tituloTela = "Editar Conta Corrente";
  const subtitulo = `Essa Conta Corrente: ${contaCorrente.numeroConta},  pertence à unidade ${unidadeAtual.codigo}`;

  return (
    <Box display="flex" alignItems="center" flexDirection="column">
      <CabecalhoPages titulo={tituloTela} subtitulo={subtitulo} />
      <Box w={{ base: "100%", md: "auto", lg: "55%" }}>
        <FormProvider {...methods}>
          <form onSubmit={methods.handleSubmit(handleEditarContaCorrete)}>
            <FormContaCorrente onOpen={onOpen} agenciaFiltrada={agenciaFiltrada} />
            <Divider mt={5} />
            <ButtonControlForm isLoading={isLoading} />
          </form>
        </FormProvider>
      </Box>
      <ModalPesquisaAgencia
        isOpen={isOpen}
        onClose={onClose}
        setAgenciaFiltrado={handleAgenciaSelecionado}
      />
    </Box>
  );
}
