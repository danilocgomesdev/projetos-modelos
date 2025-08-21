import { Box, Divider, useDisclosure } from "@chakra-ui/react";
import { zodResolver } from "@hookform/resolvers/zod";
import { useMutation } from "@tanstack/react-query";
import { useEffect, useState } from "react";
import { FormProvider, useForm } from "react-hook-form";
import { useNavigate } from "react-router-dom";
import * as zod from "zod";
import { ContaContabilProtheusDTO } from "../../../../models/DTOs/Contabil/ContaContabilProtheusDTO.ts";
import { ItemContabilProtheusDTO } from "../../../../models/DTOs/Contabil/ItemContabilProtheusDTO.ts";
import { NaturezaProtheusDTO } from "../../../../models/DTOs/Protheus/NaturezaProtheusDTO.ts";
import { fetchCriarDadoContabil } from "../../../../requests/requestsDadoContabil.ts";
import queryClient from "../../../../singletons/reactQueryClient.ts";
import Acessos from "../../../../utils/Acessos.ts";
import { getMensagemDeErroOuProcureSuporte } from "../../../../utils/errors.ts";
import { ButtonControlForm } from "../../../components/ButoonControl/ButtonControlForm.tsx";
import { CabecalhoPages } from "../../../components/CabecalhoPages/index.tsx";
import useCR5Axios from "../../../hooks/useCR5Axios.ts";
import { useFuncaoNotificacao } from "../../../hooks/useFuncaoNotificacao.tsx";
import { useValidaAcessos } from "../../../hooks/useValidaAcessos.ts";
import { DadoContabilValidateSchema } from "./components/DadoContabilValidateSchema.ts";
import { FormDadoContabil } from "./components/FormDadoContabil.tsx";
import { ModalPesquisaContaContabilProtheus } from "./components/ModalPesquisaContaContabilProtheus.tsx";
import { ModalPesquisaItemContabilProtheus } from "./components/ModalPesquisaItemContabilProtheus.tsx";
import { ModalPesquisaNaturezaProtheus } from "./components/ModalPesquisaNaturezaProtheus.tsx";

export function CriarDadoContabil() {
  const { axios } = useCR5Axios();
  const navigate = useNavigate();
  const notificacao = useFuncaoNotificacao();
  const {
    isOpen: isOpenContaContabil,
    onOpen: onOpenContaContabil,
    onClose: onCloseContaContabil,
  } = useDisclosure();

  const {
    isOpen: isOpenItemContabil,
    onOpen: onOpenItemContabil,
    onClose: onCloseItemContabil,
  } = useDisclosure();

  const {
    isOpen: isOpenNatureza,
    onOpen: onOpenNatureza,
    onClose: onCloseNatureza,
  } = useDisclosure();

  const [contaContabilProtheusFiltrado, setContaContabilProtheusFiltrado] =
    useState<ContaContabilProtheusDTO>();
  const [itemContabilProtheusFiltrado, setItemContabilProtheusFiltrado] =
    useState<ItemContabilProtheusDTO>();
  const [naturezaProtheusFiltrado, setNaturezaProtheusFiltrado] = useState<NaturezaProtheusDTO>();

  useValidaAcessos([Acessos.PRODUTO_CONTA_CONTABIL]);

  type CriandoDadoContabilFormData = zod.infer<typeof DadoContabilValidateSchema>;
  const methods = useForm<CriandoDadoContabilFormData>({
    criteriaMode: "all",
    mode: "all",
    resolver: zodResolver(DadoContabilValidateSchema),
  });

  const { mutate, isLoading } = useMutation(
    () => {
      const params = {
        contaContabil: methods.getValues("contaContabil").trim(),
        contaContabilDescricao: methods.getValues("contaContabilDescricao").trim(),
        itemContabil: methods.getValues("itemContabil").trim(),
        itemContabilDescricao: methods.getValues("itemContabilDescricao").trim(),
        idEntidade: Number(methods.getValues("idEntidade")),
        dataInativacao:
          methods.getValues("dataInativacao") === "A" ? undefined : new Date().getTime(),
        natureza: methods.getValues("natureza"),
        naturezaDescricao: methods.getValues("naturezaDescricao"),
        idOperadorInclusao: 1,
      };

      return fetchCriarDadoContabil(params, axios);
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

  function handleItemContabilSelecionado(itemContabil: ItemContabilProtheusDTO): void {
    setItemContabilProtheusFiltrado(itemContabil);
    if (itemContabil.itemContabil) {
      methods.setValue("itemContabil", itemContabil.itemContabil);
      methods.setValue("itemContabilDescricao", itemContabil.itemContabilDescricao);
    }
  }

  function handleContaContabilSelecionado(contaContabil: ContaContabilProtheusDTO): void {
    setContaContabilProtheusFiltrado(contaContabil);
    if (contaContabil.contaContabil) {
      methods.setValue("contaContabil", contaContabil.contaContabil);
      methods.setValue("contaContabilDescricao", contaContabil.contaContabilDescricao);
    }
  }

  function handleNaturezaSelecionado(natureza: NaturezaProtheusDTO): void {
    setNaturezaProtheusFiltrado(natureza);
    if (natureza.natureza) {
      methods.setValue("natureza", natureza.natureza);
      methods.setValue("naturezaDescricao", natureza.naturezaDescricao);
    }
  }

  useEffect(() => {
    queryClient.invalidateQueries({
      queryKey: ["fetchItemContabilProtheusModal", "fetchContaContabilProtheusModal"],
    });
  }, [isOpenContaContabil, isOpenItemContabil, onCloseContaContabil, onCloseItemContabil]);

  function handleDadoContabil() {
    mutate();
  }

  useEffect(() => {
    queryClient.invalidateQueries({
      queryKey: ["fetchItemContabilProtheusModal", "fetchContaContabilProtheusModal"],
    });
    const itemContabil: ItemContabilProtheusDTO = {
      itemContabil: "",
      itemContabilDescricao: "",
      entidade: Number(methods.getValues("idEntidade")),
    };
    setItemContabilProtheusFiltrado(itemContabil);

    const contaContabil: ContaContabilProtheusDTO = {
      contaContabil: "",
      contaContabilDescricao: "",
      entidade: Number(methods.getValues("idEntidade")),
    };
    setContaContabilProtheusFiltrado(contaContabil);

    methods.setValue("itemContabil", "");
    methods.setValue("itemContabilDescricao", "");
    methods.setValue("contaContabil", "");
    methods.setValue("contaContabilDescricao", "");
  }, [methods.watch("idEntidade")]);

  const titulo = "Cadastrar Dado Contábil";
  const subtitulo = "Para cadastrar um novo dado contábil, primeiro selecione a entidade.";

  return (
    <Box display="flex" alignItems="center" flexDirection="column">
      <CabecalhoPages titulo={titulo} subtitulo={subtitulo} />
      <Box w={{ base: "100%", md: "auto", lg: "55%" }}>
        <FormProvider {...methods}>
          <form onSubmit={methods.handleSubmit(handleDadoContabil)}>
            <FormDadoContabil
              onOpenContaContabil={onOpenContaContabil}
              onOpenItemContabil={onOpenItemContabil}
              onOpenNatureza={onOpenNatureza}
              contaContabilProtheusFiltrado={contaContabilProtheusFiltrado}
              itemContabilProtheusFiltrado={itemContabilProtheusFiltrado}
              naturezaProtheusFiltrado={naturezaProtheusFiltrado}
            />
            <Divider mt={5} />
            <ButtonControlForm isLoading={isLoading} />
          </form>
        </FormProvider>
      </Box>
      <ModalPesquisaContaContabilProtheus
        isOpen={isOpenContaContabil}
        onClose={onCloseContaContabil}
        setContaContabilProtheusFiltrado={handleContaContabilSelecionado}
        entidade={methods.getValues("idEntidade")}
      />
      <ModalPesquisaItemContabilProtheus
        isOpen={isOpenItemContabil}
        onClose={onCloseItemContabil}
        setItemContabilProtheusFiltrado={handleItemContabilSelecionado}
        entidade={methods.getValues("idEntidade")}
      />
      <ModalPesquisaNaturezaProtheus
        isOpen={isOpenNatureza}
        onClose={onCloseNatureza}
        setNaturezaProtheusFiltrado={handleNaturezaSelecionado}
      />
    </Box>
  );
}
