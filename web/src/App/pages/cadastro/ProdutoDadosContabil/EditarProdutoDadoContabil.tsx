import { Box, Divider, useDisclosure } from "@chakra-ui/react";
import { zodResolver } from "@hookform/resolvers/zod";
import { useMutation } from "@tanstack/react-query";
import { useEffect, useState } from "react";
import { FormProvider, useForm } from "react-hook-form";
import { useNavigate } from "react-router-dom";
import * as zod from "zod";
import { ContaContabilProtheusDTO } from "../../../../models/DTOs/Contabil/ContaContabilProtheusDTO.ts";
import { DadoContabilDTO } from "../../../../models/DTOs/Contabil/DadoContabilDTO.ts";
import { ItemContabilProtheusDTO } from "../../../../models/DTOs/Contabil/ItemContabilProtheusDTO.ts";
import { VisaoProdutoContabilDTO } from "../../../../models/DTOs/Visoes/VisaoProdutoContabilDTOs.ts";
import { fetchAtualizarProdutoDadoContabil } from "../../../../requests/requestsProdutoDadoContabil.ts";
import Acessos from "../../../../utils/Acessos.ts";
import { getMensagemDeErroOuProcureSuporte } from "../../../../utils/errors.ts";
import { ButtonControlForm } from "../../../components/ButoonControl/index.tsx";
import { CabecalhoPages } from "../../../components/CabecalhoPages/index.tsx";
import useCR5Axios from "../../../hooks/useCR5Axios.ts";
import { useFuncaoNotificacao } from "../../../hooks/useFuncaoNotificacao.tsx";
import { useValidaAcessos } from "../../../hooks/useValidaAcessos.ts";
import { ModalPesquisaDadoContabil } from "../DadoContabil/components/ModalPesquisaDadoContabil.tsx";
import { FormProdutoDadoContabil } from "./components/FormProdutoDadoContabil.tsx";
import { ProdutoDadoContabilValidateSchema } from "./components/ProdutoDadoContabilValidateSchema.ts";
import { useVisaoProdutoContabilStore } from "./store/VisaoProdutoContabilStore.tsx";

export function EditarProdutoDadosContabil() {
  const { axios } = useCR5Axios();
  type EditandoProdutoDadoContabilFormData = zod.infer<typeof ProdutoDadoContabilValidateSchema>;
  const navigate = useNavigate();

  useState<ItemContabilProtheusDTO>();
  useState<ContaContabilProtheusDTO>();
  const notificacao = useFuncaoNotificacao();
  const [validouPermissao, setValidouPermissao] = useState(false);
  const { visaoProdutoContabil, setVisaoProdutoContabil } = useVisaoProdutoContabilStore();

  const { isOpen, onOpen, onClose } = useDisclosure();

  const [dadoContabilFiltrado, setDadoContabilFiltrado] = useState<DadoContabilDTO>();

  useValidaAcessos([Acessos.PRODUTO_CONTA_CONTABIL], () => {
    setValidouPermissao(true);
  });

  useEffect(() => {
    if (!visaoProdutoContabil) {
      navigate("./..");
    }
  }, [visaoProdutoContabil, navigate]);

  const methods = useForm<EditandoProdutoDadoContabilFormData>({
    criteriaMode: "all",
    mode: "all",
    resolver: zodResolver(ProdutoDadoContabilValidateSchema),
  });

  function handleDadoContabilSelecionado(dadoContabil: DadoContabilDTO) {
    setDadoContabilFiltrado(dadoContabil);
    if (dadoContabil.idDadoContabil) {
      methods.setValue("dadoContabil", dadoContabil.idDadoContabil.toString());
      methods.setValue("itemContabil", dadoContabil.itemContabil.toString());
      methods.setValue("itemContabilDescricao", dadoContabil.itemContabilDescricao.toString());
      methods.setValue("contaContabil", dadoContabil.contaContabil.toString());
      methods.setValue("contaContabilDescricao", dadoContabil.contaContabilDescricao.toString());
      methods.setValue("codigoNatureza", dadoContabil.natureza.toString());
      methods.setValue("descricaoNatureza", dadoContabil.naturezaDescricao);
    }
  }

  function handleVisaoServicoSelecionado(visaoProdutoContabil: VisaoProdutoContabilDTO) {
    setVisaoProdutoContabil(visaoProdutoContabil);
    methods.setValue("idProduto", visaoProdutoContabil.idProduto.toString());
    methods.setValue("produto", visaoProdutoContabil.produto);
    methods.setValue("preco", visaoProdutoContabil.preco?.toString());
    methods.setValue("idSistema", visaoProdutoContabil.idSistema.toString());
    methods.setValue("dmed", visaoProdutoContabil.dmed == null ? "N" : visaoProdutoContabil.dmed);
    methods.setValue("status", visaoProdutoContabil.status === "A" ? "A" : "I");
    methods.setValue(
      "idProdutoDadoContabil",
      visaoProdutoContabil.idProdutoDadoContabil.toString()
    );
    methods.setValue("codProdutoProtheus", visaoProdutoContabil.codProdutoProtheus?.toString());

    const dadoContabil: DadoContabilDTO = {
      idDadoContabil: visaoProdutoContabil.idDadoContabil,
      itemContabil: visaoProdutoContabil.itemContabil,
      itemContabilDescricao: visaoProdutoContabil.itemContabilDescricao,
      contaContabil: visaoProdutoContabil.contaContabil,
      contaContabilDescricao: visaoProdutoContabil.contaContabilDescricao,
      natureza: visaoProdutoContabil.natureza,
      naturezaDescricao: visaoProdutoContabil.naturezaDescricao,
      idEntidade: Number(visaoProdutoContabil.entidade),
      dataInativacao: visaoProdutoContabil.inativacaoDadoContabil == null ? "A" : "I",
      idOperadorInclusao: 0,
    };
    handleDadoContabilSelecionado(dadoContabil);
  }

  useEffect(() => {
    if (visaoProdutoContabil) {
      handleVisaoServicoSelecionado(visaoProdutoContabil);
    }
  }, [visaoProdutoContabil]);

  const { mutate, isLoading } = useMutation(
    (idProdutoDadoContabil: number) => {
      const params = {
        idProdutoDadoContabil: idProdutoDadoContabil,
        dadoContabil: Number(methods.getValues("dadoContabil")),
        idProduto: Number(methods.getValues("idProduto")),
        produto: methods.getValues("produto"),
        idSistema: Number(methods.getValues("idSistema")),
        dmed: methods.getValues("dmed"),
        status: methods.getValues("status"),
        codProdutoProtheus: methods.getValues("codProdutoProtheus"),
        preco: Number(
          (Number(methods.getValues("preco")?.replace(/[^\d]+/g, "")) / 100).toFixed(2)
        ),
        dataInativacao: methods.getValues("status") === "A" ? null : new Date().getTime(),
      };
      return fetchAtualizarProdutoDadoContabil(params, axios);
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

  if (!visaoProdutoContabil || !validouPermissao) {
    return <></>;
  }

  const subtitulo = `Editando o vínculo do Produto: ${visaoProdutoContabil.idProduto} - ${visaoProdutoContabil.produto}`;

  return (
    <Box display="flex" alignItems="center" flexDirection="column">
      <CabecalhoPages titulo="Editar Vínculo Produto / Dado Contábil" subtitulo={subtitulo} />
      <Box w={{ base: "100%", md: "auto", lg: "55%" }}>
        <FormProvider {...methods}>
          <form
            onSubmit={methods.handleSubmit(() =>
              mutate(visaoProdutoContabil.idProdutoDadoContabil)
            )}
          >
            <FormProdutoDadoContabil onOpen={onOpen} dadoContabil={dadoContabilFiltrado} />
            <Divider mt={5} />
            <ButtonControlForm isLoading={isLoading} />
          </form>
        </FormProvider>
      </Box>
      <ModalPesquisaDadoContabil
        isOpen={isOpen}
        onClose={onClose}
        setDadoContabilFiltrado={handleDadoContabilSelecionado}
      />
    </Box>
  );
}
