import { Box, Divider } from "@chakra-ui/react";
import { zodResolver } from "@hookform/resolvers/zod";
import { useMutation } from "@tanstack/react-query";
import { useEffect, useState } from "react";
import { FormProvider, useForm } from "react-hook-form";
import { useNavigate } from "react-router-dom";
import * as zod from "zod";
import { PessoaCr5DTO } from "../../../../models/DTOs/Outros/PessoaCr5DTO.ts";
import { fetchAtualizarPessoaJuridica } from "../../../../requests/requestsPessoasCr5.ts";
import Acessos from "../../../../utils/Acessos.ts";
import { getMensagemDeErroOuProcureSuporte } from "../../../../utils/errors.ts";
import { ButtonControlForm } from "../../../components/ButoonControl/ButtonControlForm.tsx";
import { CabecalhoPages } from "../../../components/CabecalhoPages";
import useCR5Axios from "../../../hooks/useCR5Axios.ts";
import { useFuncaoNotificacao } from "../../../hooks/useFuncaoNotificacao.tsx";
import { useValidaAcessos } from "../../../hooks/useValidaAcessos.ts";
import { PessoaJuridicaValidateSchema } from "./components/ClientesValidateSchema.ts";
import { FormClientePessoaJuridica } from "./components/FormClientePessoaJuridica.tsx";
import { useClientesStore } from "./store/ClientesStore.tsx";

export function EditarClientePessoaJuridica() {
  const { axios } = useCR5Axios();
  type EditandoClientePessoaJuridicaFormData = zod.infer<typeof PessoaJuridicaValidateSchema>;
  const navigate = useNavigate();

  const notificacao = useFuncaoNotificacao();
  const [validouPermissao, setValidouPermissao] = useState(false);

  useValidaAcessos([Acessos.PESSOAS], () => {
    setValidouPermissao(true);
  });

  const { cliente, setCliente } = useClientesStore();

  const methods = useForm<EditandoClientePessoaJuridicaFormData>({
    criteriaMode: "all",
    mode: "all",
    resolver: zodResolver(PessoaJuridicaValidateSchema),
  });

  function handleProdutoExternoSelecionado(cliente: PessoaCr5DTO) {
    setCliente(cliente);
    methods.setValue("razaoSocial", cliente.descricao);
    methods.setValue("cnpj", cliente.cpfCnpj);
    methods.setValue("inscricaoEstadual", cliente.inscricaoEstadual);
    methods.setValue("logradouro", cliente.logradouro);
    methods.setValue("complemento", cliente.complemento ?? "");
    methods.setValue("bairro", cliente.bairro);
    methods.setValue("numeroResidencia", cliente.numeroResidencia ?? "");
    methods.setValue("cep", cliente.cep);
    methods.setValue("cidade", cliente.cidade);
    methods.setValue("estado", cliente.estado ?? "");
    methods.setValue("telefone", cliente.telefone);
    methods.setValue("telefone2", cliente.telefone2 ?? "");
    methods.setValue("celular", cliente.celular);
    methods.setValue("celular2", cliente.celular2 ?? "");
    methods.setValue("email", cliente.email);
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
        inscricaoEstadual: methods.getValues("inscricaoEstadual"),
        cnpj: methods.getValues("cnpj"),
        razaoSocial: methods.getValues("razaoSocial"),
        logradouro: methods.getValues("logradouro"),
        complemento: methods.getValues("complemento") || undefined,
        bairro: methods.getValues("bairro"),
        cidade: methods.getValues("cidade"),
        estado: methods.getValues("estado"),
        cep: methods.getValues("cep"),
        telefone: methods.getValues("telefone"),
        telefone2: methods.getValues("telefone2") || undefined,
        celular: methods.getValues("celular"),
        celular2: methods.getValues("celular2") || undefined,
        numeroResidencia: methods.getValues("numeroResidencia"),
        email: methods.getValues("email"),
      };

      return fetchAtualizarPessoaJuridica(params, axios);
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
      <CabecalhoPages titulo="Editar Cliente Pessoa Jurídica" subtitulo={subtitulo} />
      <Box w={{ base: "100%", md: "auto", lg: "55%" }}>
        <FormProvider {...methods}>
          <form onSubmit={methods.handleSubmit(handleAtualizarProdutoExterno)}>
            <FormClientePessoaJuridica />
            <Divider mt={5} />
            <ButtonControlForm isLoading={isLoading} />
          </form>
        </FormProvider>
      </Box>
    </Box>
  );
}
