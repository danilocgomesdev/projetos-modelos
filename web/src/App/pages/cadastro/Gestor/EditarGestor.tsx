import { Box, Divider, useDisclosure } from "@chakra-ui/react";
import { zodResolver } from "@hookform/resolvers/zod";
import { useMutation } from "@tanstack/react-query";
import { useEffect, useState } from "react";
import { FormProvider, useForm } from "react-hook-form";
import { useNavigate } from "react-router-dom";
import * as zod from "zod";
import { PessoaCIDTO } from "../../../../models/DTOs/Outros/Gestor.ts";
import { UnidadeDTO } from "../../../../models/DTOs/Outros/UnidadeDTO.ts";
import { putEditarGestor } from "../../../../requests/requestsGestor.ts";
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
import { useGestorStore } from "./store/GestorStore.tsx";

export function EditarGestor() {
  const { axios } = useCR5Axios();
  type EditandoGestorFormData = zod.infer<typeof GestorValidateSchema>;
  const navigate = useNavigate();

  const { gestor } = useGestorStore();

  const notificacao = useFuncaoNotificacao();
  const [validouPermissao, setValidouPermissao] = useState(false);
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

  const [unidadeFiltrada, setUnidadeFiltrada] = useState<UnidadeDTO>();
  const [pessoaFiltrada, setPessoaFiltrada] = useState<PessoaCIDTO>();

  useValidaAcessos([Acessos.ADMINISTRADOR_CR5], () => {
    setValidouPermissao(true);
  });

  useEffect(() => {
    if (!gestor) {
      navigate("./..");
    }
  }, [gestor, navigate]);

  const methods = useForm<EditandoGestorFormData>({
    criteriaMode: "all",
    mode: "all",
    resolver: zodResolver(GestorValidateSchema),
  });

  useEffect(() => {
    if (gestor) {
      setarValoresPessoa();
      handleUnidadeSelecionado(gestor.unidade);
      methods.setValue("descricao", gestor.descricao.toUpperCase());
    }
  }, []);

  function setarValoresPessoa() {
    if (gestor) {
      const pessoa: PessoaCIDTO = {
        id: gestor.idCiPessoas,
        nome: gestor.nome,
        matricula: gestor.matricula.toString(),
        email: gestor.email,
      };
      handlePessoaSelecionado(pessoa);
    }
  }

  function handleUnidadeSelecionado(unidade: UnidadeDTO): void {
    setUnidadeFiltrada(unidade);
    if (unidade.id) {
      methods.setValue("idUnidade", unidade.id.toString());
    }
  }

  function handlePessoaSelecionado(pessoa: PessoaCIDTO): void {
    setPessoaFiltrada(pessoa);
    if (pessoa.id) {
      methods.setValue("nome", pessoa.nome.toUpperCase());
      methods.setValue("matricula", pessoa.matricula);
      methods.setValue("email", pessoa.email);
      methods.setValue("idCiPessoas", pessoa.id.toString());
    }
  }

  const { mutate, isLoading } = useMutation(
    () => {
      const params = {
        idGestor: gestor?.idGestor ?? -1,
        nome: methods.getValues("nome").toUpperCase(),
        email: methods.getValues("email"),
        matricula: Number(methods.getValues("matricula")),
        descricao: methods.getValues("descricao").toUpperCase(),
        idCiPessoas: Number(methods.getValues("idCiPessoas")),
        idUnidade: Number(methods.getValues("idUnidade")),
      };

      return putEditarGestor(params, axios);
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

  if (!gestor || !validouPermissao) {
    return <></>;
  }

  const subtitulo = `Editando o gestor ${gestor.idGestor} - ${gestor.nome}`;

  return (
    <Box display="flex" alignItems="center" flexDirection="column">
      <CabecalhoPages titulo="Editar Gestor" subtitulo={subtitulo} />
      <Box w={{ base: "100%", md: "auto", lg: "55%" }}>
        <FormProvider {...methods}>
          <form onSubmit={methods.handleSubmit(() => mutate())}>
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
