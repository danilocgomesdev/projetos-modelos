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
import { NaturezaProtheusDTO } from "../../../../models/DTOs/Protheus/NaturezaProtheusDTO.ts";
import { fetchAtualizarDadoContabil } from "../../../../requests/requestsDadoContabil.ts";
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
import { useDadoContabilStore } from "./store/DadoContabilStore.tsx";

export function EditarDadoContabil() {
  const { axios } = useCR5Axios();
  type EditandoDadoContabilFormData = zod.infer<typeof DadoContabilValidateSchema>;
  const navigate = useNavigate();
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
  const { dadoContabil, adicionarDadoContabil } = useDadoContabilStore();

  const notificacao = useFuncaoNotificacao();
  const [validouPermissao, setValidouPermissao] = useState(false);

  useValidaAcessos([Acessos.PRODUTO_CONTA_CONTABIL], () => {
    setValidouPermissao(true);
  });

  useEffect(() => {
    if (!dadoContabil) {
      navigate("./..");
    }
  }, [dadoContabil, navigate]);

  const methods = useForm<EditandoDadoContabilFormData>({
    criteriaMode: "all",
    mode: "all",
    resolver: zodResolver(DadoContabilValidateSchema),
  });

  function handleDadoContabilSelecionada(dadoContabil: DadoContabilDTO) {
    adicionarDadoContabil(dadoContabil);
    methods.setValue("idEntidade", dadoContabil.idEntidade.toString());
    methods.setValue("dataInativacao", dadoContabil.dataInativacao == null ? "A" : "F");
    methods.setValue("idDadoContabil", dadoContabil.idDadoContabil.toString());
    const natureza: NaturezaProtheusDTO = {
      natureza: dadoContabil.natureza,
      naturezaDescricao: dadoContabil.naturezaDescricao,
    };
    handleNaturezaSelecionado(natureza);

    const contaContabil: ContaContabilProtheusDTO = {
      contaContabil: dadoContabil.contaContabil,
      contaContabilDescricao: dadoContabil.contaContabilDescricao,
      entidade: dadoContabil.idEntidade,
    };

    handleContaContabilSelecionado(contaContabil);

    const itemContabil: ItemContabilProtheusDTO = {
      itemContabil: dadoContabil.itemContabil,
      itemContabilDescricao: dadoContabil.itemContabilDescricao,
      entidade: dadoContabil.idEntidade,
    };

    handleItemContabilSelecionado(itemContabil);
  }

  useEffect(() => {
    if (dadoContabil) {
      handleDadoContabilSelecionada(dadoContabil);
    }
  }, [dadoContabil]);

  useEffect(() => {
    const subscription = methods.watch((value, { name }) => {
      if (name === "idEntidade" && value.idEntidade) {
        queryClient.invalidateQueries({
          queryKey: ["fetchItemContabilProtheusModal", "fetchContaContabilProtheusModal"],
        });
        methods.setValue("itemContabil", "");
        methods.setValue("itemContabilDescricao", "");
        methods.setValue("contaContabil", "");
        methods.setValue("contaContabilDescricao", "");

        setContaContabilProtheusFiltrado({
          contaContabil: "",
          contaContabilDescricao: "",
          entidade: Number(value.idEntidade),
        });

        setItemContabilProtheusFiltrado({
          itemContabil: "",
          itemContabilDescricao: "",
          entidade: Number(value.idEntidade),
        });
      }
    });
    return () => subscription.unsubscribe();
  }, [methods]);

  const { mutate, isLoading } = useMutation(
    (idDadoContabil: number) => {
      const params = {
        idDadoContabil: idDadoContabil,
        contaContabil: methods.getValues("contaContabil"),
        contaContabilDescricao: methods.getValues("contaContabilDescricao"),
        itemContabil: methods.getValues("itemContabil"),
        itemContabilDescricao: methods.getValues("itemContabilDescricao"),
        idEntidade: Number(methods.getValues("idEntidade")),
        dataInativacao:
          methods.getValues("dataInativacao") === "A" ? undefined : new Date().getTime(),
        natureza: methods.getValues("natureza"),
        naturezaDescricao: methods.getValues("naturezaDescricao"),
      };
      return fetchAtualizarDadoContabil(params, axios);
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

  if (!dadoContabil || !validouPermissao) {
    return <></>;
  }

  const subtitulo = `Editar Dado Contábil: ${dadoContabil.idDadoContabil}`;

  return (
    <Box display="flex" alignItems="center" flexDirection="column">
      <CabecalhoPages titulo="Editar Dado Contábil" subtitulo={subtitulo} />
      <Box w={{ base: "100%", md: "auto", lg: "55%" }}>
        <FormProvider {...methods}>
          <form onSubmit={methods.handleSubmit(() => mutate(dadoContabil.idDadoContabil))}>
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
