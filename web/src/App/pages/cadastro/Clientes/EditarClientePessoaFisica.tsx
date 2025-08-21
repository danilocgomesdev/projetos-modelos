import { Box, Divider } from "@chakra-ui/react";
import { zodResolver } from "@hookform/resolvers/zod";
import { useMutation } from "@tanstack/react-query";
import { useEffect, useState } from "react";
import { FormProvider, useForm } from "react-hook-form";
import { useNavigate } from "react-router-dom";
import * as zod from "zod";
import { PessoaCr5DTO } from "../../../../models/DTOs/Outros/PessoaCr5DTO.ts";
import { fetchAtualizarPessoaFisica } from "../../../../requests/requestsPessoasCr5.ts";
import Acessos from "../../../../utils/Acessos.ts";
import { getMensagemDeErroOuProcureSuporte } from "../../../../utils/errors.ts";
import { formatarDataHttp } from "../../../../utils/mascaras.ts";
import { ButtonControlForm } from "../../../components/ButoonControl/ButtonControlForm.tsx";
import { CabecalhoPages } from "../../../components/CabecalhoPages";
import useCR5Axios from "../../../hooks/useCR5Axios.ts";
import { useFuncaoNotificacao } from "../../../hooks/useFuncaoNotificacao.tsx";
import { useValidaAcessos } from "../../../hooks/useValidaAcessos.ts";
import { PessoaFisicaValidateSchema } from "./components/ClientesValidateSchema.ts";
import { FormClientePessoaFisica } from "./components/FormClientePessoaFisica.tsx";
import { useClientesStore } from "./store/ClientesStore.tsx";

export function EditarClientePessoaFisica() {
  const { axios } = useCR5Axios();
  type EditandoClientePessoaFisicaFormData = zod.infer<typeof PessoaFisicaValidateSchema>;
  const navigate = useNavigate();

  const notificacao = useFuncaoNotificacao();
  const [validouPermissao, setValidouPermissao] = useState(false);

  useValidaAcessos([Acessos.PESSOAS], () => {
    setValidouPermissao(true);
  });

  const { cliente, setCliente } = useClientesStore();

  const methods = useForm<EditandoClientePessoaFisicaFormData>({
    criteriaMode: "all",
    mode: "all",
    resolver: zodResolver(PessoaFisicaValidateSchema),
  });

  function handleProdutoExternoSelecionado(cliente: PessoaCr5DTO) {
    setCliente(cliente);
    methods.setValue("cpf", cliente.cpfCnpj);
    methods.setValue("rg", cliente.rg);
    methods.setValue("descricao", cliente.descricao.toString());
    methods.setValue("dataNascimento", formatarDataHttp(new Date(cliente.dataNascimento)));
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
    methods.setValue("emancipado", cliente.emancipado === true ? "S" : "N");
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
        cpf: methods.getValues("cpf"),
        rg: methods.getValues("rg"),
        descricao: methods.getValues("descricao").toUpperCase(),
        dataNascimento: methods.getValues("dataNascimento"),
        logradouro: methods.getValues("logradouro"),
        complemento: methods.getValues("complemento"),
        bairro: methods.getValues("bairro"),
        numeroResidencia: methods.getValues("numeroResidencia"),
        cidade: methods.getValues("cidade"),
        estado: methods.getValues("estado"),
        cep: methods.getValues("cep"),
        telefone: methods.getValues("telefone"),
        telefone2: methods.getValues("telefone2"),
        celular: methods.getValues("celular"),
        celular2: methods.getValues("celular2"),
        emancipado: methods.getValues("emancipado") === "S" ? true : false,
        email: methods.getValues("email"),
      };

      return fetchAtualizarPessoaFisica(params, axios);
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
      <CabecalhoPages titulo="Editar Cliente Pessoa Física" subtitulo={subtitulo} />
      <Box w={{ base: "100%", md: "auto", lg: "55%" }}>
        <FormProvider {...methods}>
          <form onSubmit={methods.handleSubmit(handleAtualizarProdutoExterno)}>
            <FormClientePessoaFisica />
            <Divider mt={5} />
            <ButtonControlForm isLoading={isLoading} />
          </form>
        </FormProvider>
      </Box>
    </Box>
  );
}
