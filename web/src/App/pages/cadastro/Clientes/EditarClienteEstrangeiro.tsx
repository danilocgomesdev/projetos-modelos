import { Box, Divider } from "@chakra-ui/react";
import { zodResolver } from "@hookform/resolvers/zod";
import { useMutation } from "@tanstack/react-query";
import { useEffect, useState } from "react";
import { FormProvider, useForm } from "react-hook-form";
import { useNavigate } from "react-router-dom";
import * as zod from "zod";
import { PessoaCr5DTO } from "../../../../models/DTOs/Outros/PessoaCr5DTO.ts";
import { fetchAtualizarEstrangeiro } from "../../../../requests/requestsPessoasCr5.ts";
import Acessos from "../../../../utils/Acessos.ts";
import { getMensagemDeErroOuProcureSuporte } from "../../../../utils/errors.ts";
import { formatarDataHttp } from "../../../../utils/mascaras.ts";
import { ButtonControlForm } from "../../../components/ButoonControl";
import { CabecalhoPages } from "../../../components/CabecalhoPages";
import useCR5Axios from "../../../hooks/useCR5Axios.ts";
import { useFuncaoNotificacao } from "../../../hooks/useFuncaoNotificacao.tsx";
import { useValidaAcessos } from "../../../hooks/useValidaAcessos.ts";
import { EstrangeiroValidateSchema } from "./components/ClientesValidateSchema.ts";
import { FormClienteEstrangeiro } from "./components/FormClienteEstrangeiro.tsx";
import { useClientesStore } from "./store/ClientesStore.tsx";

export function EditarClienteEstrangeiro() {
  const { axios } = useCR5Axios();
  type EditandoClienteEstrangeiroFormData = zod.infer<typeof EstrangeiroValidateSchema>;
  const navigate = useNavigate();

  const [validouPermissao, setValidouPermissao] = useState(false);

  useValidaAcessos([Acessos.PESSOAS], () => {
    setValidouPermissao(true);
  });

  const { cliente, setCliente } = useClientesStore();

  const methods = useForm<EditandoClienteEstrangeiroFormData>({
    criteriaMode: "all",
    mode: "all",
    resolver: zodResolver(EstrangeiroValidateSchema),
  });

  const notificacao = useFuncaoNotificacao();

  function handleProdutoExternoSelecionado(cliente: PessoaCr5DTO) {
    setCliente(cliente);
    methods.setValue("descricao", cliente.descricao.toString());
    if (cliente.dataNascimento != null) {
      methods.setValue("dataNascimento", formatarDataHttp(new Date(cliente.dataNascimento)));
    }
    methods.setValue("logradouro", cliente.logradouro);
    methods.setValue("complemento", cliente.complemento ?? "");
    methods.setValue("bairro", cliente.bairro);
    methods.setValue("numeroResidencia", cliente.numeroResidencia ?? "");
    methods.setValue("cidade", cliente.cidade);
    methods.setValue("estado", cliente.estado ?? "");
    methods.setValue("pais", cliente.pais);
    methods.setValue("codigoPostal", cliente.codigoPostal);
    methods.setValue("codigoPais", cliente.codigoPais.toString());
    methods.setValue("telefone", cliente.telefone);
    methods.setValue("telefone2", cliente.telefone2 ?? "");
    methods.setValue("celular", cliente.celular);
    methods.setValue("celular2", cliente.celular2 ?? "");
    methods.setValue("email", cliente.email);
    methods.setValue("idEstrangeiro", cliente.idEstrangeiro);
  }

  useEffect(() => {
    if (cliente) {
      handleProdutoExternoSelecionado(cliente);
    }
  }, [cliente]);

  const { mutate, isLoading } = useMutation(
    (idPessoa: number) => {
      const params = {
        idPessoa: idPessoa,
        descricao: methods.getValues("descricao").toUpperCase(),
        dataNascimento: methods.getValues("dataNascimento"),
        logradouro: methods.getValues("logradouro"),
        complemento: methods.getValues("complemento"),
        bairro: methods.getValues("bairro"),
        numeroResidencia: methods.getValues("numeroResidencia"),
        cidade: methods.getValues("cidade"),
        estado: methods.getValues("estado"),
        pais: methods.getValues("pais"),
        codigoPostal: methods.getValues("codigoPostal"),
        codigoPais: Number(methods.getValues("codigoPais")),
        telefone: methods.getValues("telefone"),
        telefone2: methods.getValues("telefone2"),
        celular: methods.getValues("celular"),
        celular2: methods.getValues("celular2"),
        email: methods.getValues("email"),
        idEstrangeiro: methods.getValues("idEstrangeiro"),
      };

      return fetchAtualizarEstrangeiro(params, axios);
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

  const handleAtualizarProdutoExterno = (): void => {
    if (cliente) {
      mutate(cliente.idPessoa);
    }
  };

  if (!cliente || !validouPermissao) {
    return <></>;
  }

  const subtitulo = `Editando o cliente ${cliente.idPessoa} - ${cliente.descricao}`;

  return (
    <Box display="flex" alignItems="center" flexDirection="column">
      <CabecalhoPages titulo="Editar Cliente Estrangeiro" subtitulo={subtitulo} />
      <Box w={{ base: "100%", md: "auto", lg: "55%" }}>
        <FormProvider {...methods}>
          <form onSubmit={methods.handleSubmit(handleAtualizarProdutoExterno)}>
            <FormClienteEstrangeiro />
            <Divider mt={5} />
            <ButtonControlForm isLoading={isLoading} />
          </form>
        </FormProvider>
      </Box>
    </Box>
  );
}
