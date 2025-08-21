import { Box, Divider, useDisclosure } from "@chakra-ui/react";
import { zodResolver } from "@hookform/resolvers/zod";
import { useMutation } from "@tanstack/react-query";
import { useState } from "react";
import { FormProvider, useForm } from "react-hook-form";
import { useNavigate } from "react-router-dom";
import * as zod from "zod";
import { AgenciaDTO } from "../../../../models/DTOs/AgenciaBancoConta/AgenciaDTO.ts";
import {
  CriarContaCorrenteDTO,
  postCriarContaCorrente,
} from "../../../../requests/requestsContasCorrente.ts";
import Acessos from "../../../../utils/Acessos.ts";
import { getMensagemDeErroOuProcureSuporte } from "../../../../utils/errors.ts";
import { ButtonControlForm } from "../../../components/ButoonControl/ButtonControlForm.tsx";
import { CabecalhoPages } from "../../../components/CabecalhoPages";
import useCR5Axios from "../../../hooks/useCR5Axios.ts";
import { useFuncaoNotificacao } from "../../../hooks/useFuncaoNotificacao.tsx";
import { useValidaAcessos } from "../../../hooks/useValidaAcessos.ts";
import { useUnidadeStore } from "../../../stores/UnidadeStore.tsx";
import { ModalPesquisaAgencia } from "../Agencias/components/ModalPesquisaAgencia.tsx";
import { ContaCorrenteValidateSchema } from "./components/ContaCorrenteValidateSchema.ts";
import { FormContaCorrente } from "./components/FormContaCorrente.tsx";

export function CriarContaCorrente() {
  const { axios } = useCR5Axios();
  const { unidadeAtual } = useUnidadeStore();

  const notificacao = useFuncaoNotificacao();
  const navigate = useNavigate();

  useValidaAcessos([Acessos.CONTAS_CORRENTES]);

  type CriandoContaCorrenteFormData = zod.infer<typeof ContaCorrenteValidateSchema>;

  const methods = useForm<CriandoContaCorrenteFormData>({
    criteriaMode: "all",
    mode: "all",
    resolver: zodResolver(ContaCorrenteValidateSchema),
  });

  const { isOpen, onOpen, onClose } = useDisclosure();

  const [agenciaFiltrada, setAgenciaFiltrada] = useState<AgenciaDTO | undefined>();

  function handleAgenciaSelecionado(agencia: AgenciaDTO): void {
    setAgenciaFiltrada(agencia);
    if (agencia.id) {
      methods.setValue("idAgencia", agencia.id.toString());
    }
  }

  const { mutate, isLoading } = useMutation(
    () => {
      const params: CriarContaCorrenteDTO = {
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

      return postCriarContaCorrente(params, axios);
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

  function handleCriarContaCorrete() {
    mutate();
  }

  const tituloTela = "Cadastrar Conta Corrente";
  const subtitulo = `Será cadastrada na unidade atual ${unidadeAtual.codigo}`;

  return (
    <Box display="flex" alignItems="center" flexDirection="column">
      <CabecalhoPages titulo={tituloTela} subtitulo={subtitulo} />
      <Box w={{ base: "100%", md: "auto", lg: "55%" }}>
        <FormProvider {...methods}>
          <form onSubmit={methods.handleSubmit(handleCriarContaCorrete)}>
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
