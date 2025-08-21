import { Box, Divider, useDisclosure } from "@chakra-ui/react";
import { zodResolver } from "@hookform/resolvers/zod";
import { useMutation } from "@tanstack/react-query";
import { useState } from "react";
import { FormProvider, useForm } from "react-hook-form";
import { useNavigate } from "react-router-dom";
import * as zod from "zod";
import { PessoaCIDTO } from "../../../../models/DTOs/Outros/Gestor.ts";
import { UnidadeDTO } from "../../../../models/DTOs/Outros/UnidadeDTO.ts";
import { postCriarGestor } from "../../../../requests/requestsGestor.ts";
import Acessos from "../../../../utils/Acessos.ts";
import { getMensagemDeErroOuProcureSuporte } from "../../../../utils/errors";
import { ButtonControlForm } from "../../../components/ButoonControl/index.tsx";
import { CabecalhoPages } from "../../../components/CabecalhoPages";
import { ModalControlUnidade } from "../../../components/ModalControlUnidade/ModalControlUnidade.tsx";
import { ModalPesquisaPessoaCI } from "../../../components/ModalPesquisaPessoaCI/ModalPesquisaPessoaCI.tsx";
import useCR5Axios from "../../../hooks/useCR5Axios";
import { useFuncaoNotificacao } from "../../../hooks/useFuncaoNotificacao.tsx";
import { useValidaAcessos } from "../../../hooks/useValidaAcessos.ts";
import { FormGestor } from "./components/FormGestor.tsx";
import { GestorValidateSchema } from "./components/GestorValidateSchema.ts";

export function CriarGestor() {
  const { axios } = useCR5Axios();
  type CriandoGestorFormData = zod.infer<typeof GestorValidateSchema>;
  const navigate = useNavigate();
  const [unidadeFiltrada, setUnidadeFiltrada] = useState<UnidadeDTO>();
  const [pessoaFiltrada, setPessoaFiltrada] = useState<PessoaCIDTO>();

  const {
    isOpen: isOpenModalUnidade,
    onOpen: onOpenModalUnidade,
    onClose: onCloseModalUnidade,
  } = useDisclosure();
  const {
    isOpen: isOpenModalCIPessoas,
    onOpen: onOpenModalCIPessoas,
    onClose: onCloseModalCIPessoas,
  } = useDisclosure();
  const methods = useForm<CriandoGestorFormData>({
    criteriaMode: "all",
    mode: "all",
    resolver: zodResolver(GestorValidateSchema),
  });

  const notificacao = useFuncaoNotificacao();

  function handleUnidadeSelecionado(unidade: UnidadeDTO): void {
    setUnidadeFiltrada(unidade);
    if (unidade.id) {
      methods.setValue("idUnidade", unidade.id.toString());
    }
  }

  function handlePessoaSelecionado(pessoa: PessoaCIDTO): void {
    setPessoaFiltrada(pessoa);
    if (pessoa.id) {
      methods.setValue("nome", pessoa.nome);
      methods.setValue("matricula", pessoa.matricula);
      methods.setValue("email", pessoa.email);
      methods.setValue("idCiPessoas", pessoa.id.toString());
    }
  }

  useValidaAcessos([Acessos.ADMINISTRADOR_CR5]);

  const { mutate, isLoading } = useMutation(
    () => {
      const params = {
        nome: methods.getValues("nome").toUpperCase(),
        email: methods.getValues("email"),
        matricula: Number(methods.getValues("matricula")),
        descricao: methods.getValues("descricao").toUpperCase(),
        idCiPessoas: Number(methods.getValues("idCiPessoas")),
        idUnidade: Number(methods.getValues("idUnidade")),
      };

      return postCriarGestor(params, axios);
    },
    {
      onSuccess: () => {
        notificacao({
          message: "Gestor cadastrado com sucesso!",
          tipo: "success",
          titulo: "SUCESSO!",
        });
        navigate("./.."); // Volta para a página anterior
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

  function handleCriarGestor() {
    mutate();
  }

  return (
    <Box display="flex" alignItems="center" flexDirection="column">
      <CabecalhoPages titulo="Cadastrar Gestor" />
      <Box w={{ base: "100%", md: "auto", lg: "55%" }}>
        <FormProvider {...methods}>
          <form onSubmit={methods.handleSubmit(handleCriarGestor)}>
            <FormGestor
              onOpenModalUnidade={onOpenModalUnidade}
              onOpenModalCIPessoas={onOpenModalCIPessoas}
              unidadeFiltrado={unidadeFiltrada}
              pessoaFiltrada={pessoaFiltrada}
            />
            <Divider mt={5} />
            <ButtonControlForm isLoading={isLoading} />
          </form>
        </FormProvider>
      </Box>
      <ModalControlUnidade
        isOpen={isOpenModalUnidade}
        onClose={onCloseModalUnidade}
        setUnidadeFiltrada={handleUnidadeSelecionado}
      />
      <ModalPesquisaPessoaCI
        isOpen={isOpenModalCIPessoas}
        onClose={onCloseModalCIPessoas}
        setPessoaFiltrada={handlePessoaSelecionado}
      />
    </Box>
  );
}
