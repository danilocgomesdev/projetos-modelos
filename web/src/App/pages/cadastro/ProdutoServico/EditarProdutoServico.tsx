import { Box, Divider } from "@chakra-ui/react";
import { zodResolver } from "@hookform/resolvers/zod";
import { useMutation } from "@tanstack/react-query";
import { useEffect, useState } from "react";
import { FormProvider, useForm } from "react-hook-form";
import { useNavigate } from "react-router-dom";
import * as zod from "zod";
import { ProdutoDadoContabilDTO } from "../../../../models/DTOs/Produtos/ProdutoDadoContabilDTO.ts";
import {
  FetchAtualizarProdutoDadoContabilParams,
  fetchAtualizarProdutoDadoContabil,
} from "../../../../requests/requestsProdutoDadoContabil.ts";
import Acessos from "../../../../utils/Acessos.ts";
import { getMensagemDeErroOuProcureSuporte } from "../../../../utils/errors.ts";
import { formatarMoedaBrasil } from "../../../../utils/mascaras.ts";
import { ButtonControlForm } from "../../../components/ButoonControl/ButtonControlForm.tsx";
import { CabecalhoPages } from "../../../components/CabecalhoPages/index.tsx";
import useCR5Axios from "../../../hooks/useCR5Axios.ts";
import { useFuncaoNotificacao } from "../../../hooks/useFuncaoNotificacao.tsx";
import { useValidaAcessos } from "../../../hooks/useValidaAcessos.ts";
import { useProdutoDadoContabilStore } from "../ProdutoDadosContabil/store/ProdutoDadoContabilStore.tsx";
import { FormNovoProdutoServico } from "./components/FormNovoProdutoServico.tsx";
import { AlterarProdutoServicoValidateSchema } from "./components/ProdutoServicoValidateSchema.ts";

export function EditarProdutoServico() {
  const { axios } = useCR5Axios();

  type EditandoProdutoServicoFormData = zod.infer<typeof AlterarProdutoServicoValidateSchema>;
  const navigate = useNavigate();

  const [validouPermissao, setValidouPermissao] = useState(false);

  useValidaAcessos([Acessos.PRODUTO_SERVICO_AVULSO], () => {
    setValidouPermissao(true);
  });

  const { produtoDadoContabil, setProdutoDadoContabil } = useProdutoDadoContabilStore();

  const [precoFormatoMoeda, setPrecoFormatoMoeda] = useState<string | null>();

  useEffect(() => {
    if (!produtoDadoContabil) {
      navigate("./..");
    }
  }, [produtoDadoContabil, navigate]);

  const methods = useForm<EditandoProdutoServicoFormData>({
    criteriaMode: "all",
    mode: "all",
    resolver: zodResolver(AlterarProdutoServicoValidateSchema),
  });

  const notificacao = useFuncaoNotificacao();

  function handleProdutoServicoSelecionado(produtoDadoContabil: ProdutoDadoContabilDTO) {
    setProdutoDadoContabil(produtoDadoContabil);
    setPrecoFormatoMoeda(formatarMoedaBrasil(produtoDadoContabil.preco));

    methods.setValue("produto", produtoDadoContabil.produto);
    methods.setValue("status", produtoDadoContabil.status == "A" ? "A" : "I");
    methods.setValue("preco", formatarMoedaBrasil(produtoDadoContabil.preco) || "");
    methods.setValue("idSistema", produtoDadoContabil.idSistema?.toString());
    methods.setValue("codProdutoProtheus", produtoDadoContabil.codProdutoProtheus);
  }

  useEffect(() => {
    if (produtoDadoContabil) {
      handleProdutoServicoSelecionado(produtoDadoContabil);
    }
  }, [produtoDadoContabil]);

  const { mutate, isLoading } = useMutation(
    (idProdutoDadoContabil: number) => {
      const params: FetchAtualizarProdutoDadoContabilParams = {
        idProdutoDadoContabil: idProdutoDadoContabil,
        produto: methods.getValues("produto").toUpperCase(),
        preco: Number(
          (Number(methods.getValues("preco")?.replace(/[^\d]+/g, "")) / 100).toFixed(2)
        ),
        status: methods.getValues("status"),
        codProdutoProtheus: methods.getValues("codProdutoProtheus"),
        dataInativacao: methods.getValues("status") === "A" ? null : new Date().getTime(),
        idSistema: Number(methods.getValues("idSistema")),
      };
      return fetchAtualizarProdutoDadoContabil(params, axios);
    },
    {
      onSuccess: () => {
        notificacao({ message: "Editado com sucesso!", tipo: "success", titulo: "SUCESSO!" });
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

  function handleProdutoServico() {
    if (produtoDadoContabil) {
      mutate(produtoDadoContabil.idProdutoDadoContabil);
    }
  }

  if (!produtoDadoContabil || !validouPermissao) {
    return <></>;
  }
  const subtitulo = `Editando o produto/serviço: ${produtoDadoContabil.idProduto} - ${produtoDadoContabil.produto}`;

  return (
    <Box display="flex" alignItems="center" flexDirection="column">
      <CabecalhoPages titulo="Editar Produto / Serviço" subtitulo={subtitulo} />
      <Box w={{ base: "100%", md: "auto", lg: "55%" }}>
        <FormProvider {...methods}>
          <form onSubmit={methods.handleSubmit(handleProdutoServico)}>
            <FormNovoProdutoServico
              setPrecoFormatoMoeda={setPrecoFormatoMoeda}
              precoFormatoMoeda={precoFormatoMoeda}
            />
            <Divider mt={5} />
            <ButtonControlForm isLoading={isLoading} />
          </form>
        </FormProvider>
      </Box>
    </Box>
  );
}
