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
import { fetchCriarVinculoProdutoDadoContabil } from "../../../../requests/requestsProdutoDadoContabil.ts";
import queryClient from "../../../../singletons/reactQueryClient.ts";
import Acessos from "../../../../utils/Acessos.ts";
import { getMensagemDeErroOuProcureSuporte } from "../../../../utils/errors.ts";
import { ButtonControlForm } from "../../../components/ButoonControl/index.tsx";
import { CabecalhoPages } from "../../../components/CabecalhoPages/index.tsx";
import { ModalPesquisaVisaoProdutoContabil } from "../../../components/VisaoProdutoContabil/ModalPesquisaVisaoProdutoContabil.tsx";
import useCR5Axios from "../../../hooks/useCR5Axios.ts";
import { useFuncaoNotificacao } from "../../../hooks/useFuncaoNotificacao.tsx";
import { useValidaAcessos } from "../../../hooks/useValidaAcessos.ts";
import { ModalPesquisaDadoContabil } from "../DadoContabil/components/ModalPesquisaDadoContabil.tsx";
import { FormProdutoDadoContabil } from "./components/FormProdutoDadoContabil.tsx";
import { ProdutoDadoContabilValidateSchema } from "./components/ProdutoDadoContabilValidateSchema.ts";

export function CriarProdutoDadosContabil() {
  const { axios } = useCR5Axios();
  type CriandoProdutoDadoContabilFormData = zod.infer<typeof ProdutoDadoContabilValidateSchema>;
  const navigate = useNavigate();

  useState<ItemContabilProtheusDTO>();
  useState<ContaContabilProtheusDTO>();
  const notificacao = useFuncaoNotificacao();

  const { isOpen, onOpen, onClose } = useDisclosure();
  const {
    isOpen: isOpenModalVisaoServicos,
    onOpen: onOpenModalVisaoServicos,
    onClose: onCloseModalVisaoServicos,
  } = useDisclosure();

  const [dadoContabilFiltrado, setDadoContabilFiltrado] = useState<DadoContabilDTO>();

  useValidaAcessos([Acessos.PRODUTO_CONTA_CONTABIL]);

  const methods = useForm<CriandoProdutoDadoContabilFormData>({
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

  function handleVisaoServicoSelecionado(visaoProdutoDadoContabil: VisaoProdutoContabilDTO) {
    methods.setValue("idProduto", visaoProdutoDadoContabil.idProduto.toString());
    methods.setValue("produto", visaoProdutoDadoContabil.produto);
    methods.setValue("idSistema", visaoProdutoDadoContabil.idSistema.toString());
    methods.setValue(
      "idProdutoDadoContabil",
      visaoProdutoDadoContabil.idProdutoDadoContabil.toString()
    );
  }

  const { mutate, isLoading } = useMutation(
    () => {
      const params = {
        idProdutoDadoContabil: Number(methods.getValues("idProdutoDadoContabil")),
        dadoContabil: Number(methods.getValues("dadoContabil")),
        idProduto: Number(methods.getValues("idProduto")),
        idSistema: Number(methods.getValues("idSistema")),
        dmed: methods.getValues("dmed"),
        dataInativacao:
          methods.getValues("dataInativacao") === "A" ? undefined : new Date().getTime(),
      };
      return fetchCriarVinculoProdutoDadoContabil(params, axios);
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

  function handleCriarProdutoDadoContabil() {
    mutate();
  }

  useEffect(() => {
    queryClient.invalidateQueries({ queryKey: ["fetchItemContabilProtheusModal", "fetch"] });
  }, [onClose, isOpen]);

  return (
    <Box display="flex" alignItems="center" flexDirection="column">
      <CabecalhoPages titulo="Criar Vínculo Produto / Dado Contábil" />
      <Box w={{ base: "100%", md: "auto", lg: "55%" }}>
        <FormProvider {...methods}>
          <form onSubmit={methods.handleSubmit(handleCriarProdutoDadoContabil)}>
            <FormProdutoDadoContabil
              onOpen={onOpen}
              dadoContabil={dadoContabilFiltrado}
              onOpenVisaoServicos={onOpenModalVisaoServicos}
            />
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
      <ModalPesquisaVisaoProdutoContabil
        abrirModal={isOpenModalVisaoServicos}
        onClose={onCloseModalVisaoServicos}
        setVisaoProdutoContabilFiltrado={handleVisaoServicoSelecionado}
      />
    </Box>
  );
}
