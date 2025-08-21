import { Box, Button, Divider, Grid, GridItem, useDisclosure } from "@chakra-ui/react";
import { zodResolver } from "@hookform/resolvers/zod";
import { useMutation } from "@tanstack/react-query";
import { useEffect, useState } from "react";
import { FormProvider, useForm } from "react-hook-form";
import { useNavigate } from "react-router-dom";
import { ContaCorrenteDTO } from "../../../../models/DTOs/AgenciaBancoConta/ContaCorrenteDTO";
import { ConvenioBancarioDTO, FaixaNossoNumeroDTO } from "../../../../models/DTOs/ConvenioBancario";
import { ErroCancelaAcao } from "../../../../models/errors/ErroCancelaAcao";
import {
  AlterarConvenioBancarioParams,
  FaixaNossoNumeroCRUDDTO,
  patchAtivarConvenioBancario,
  patchAtivarFaixaNossoNumero,
  patchDesativarConvenioBancario,
  postCriarFaixaNossoNumero,
  putAlterarConvenioBancario,
  putAlterarFaixaNossoNumero,
} from "../../../../requests/requestsConveniosBancarios";
import Acessos from "../../../../utils/Acessos";
import { getMensagemDeErroOuProcureSuporte } from "../../../../utils/errors";
import { CabecalhoPages } from "../../../components/CabecalhoPages";
import useCR5Axios from "../../../hooks/useCR5Axios";
import { useFuncaoNotificacao } from "../../../hooks/useFuncaoNotificacao";
import { useValidaAcessos } from "../../../hooks/useValidaAcessos";
import { ModalPesquisaContaCorrente } from "../ContaCorrente/components/ModalPesquisaContaCorrente";
import { ConvenioBancarioValidateSchema } from "./components/ConvenioBancarioValidateSchema";
import { FormConvenioBancario } from "./components/FormConvenioBancario";
import { ModalCriaFaixaNossoNumero } from "./components/ModalCriaFaixaNossoNumero";
import { ModalEditarFaixaNossoNumero } from "./components/ModalEditarFaixaNossoNumero";
import { ModalPesquisaConvenioBancarioInativo } from "./components/ModalPesquisaConvenioBancarioInativo";
import { useConvenioBancarioStore } from "./store/ConvenioBancarioStore";

export function EditarConvenioBancario() {
  const { axios } = useCR5Axios();
  type EditandoConvenioBancarioFormData = Zod.infer<typeof ConvenioBancarioValidateSchema>;
  const navigate = useNavigate();
  const { convenioBancario } = useConvenioBancarioStore();
  const { isOpen: isModalCCOpen, onOpen: onModalCCOpen, onClose: onModalCCClose } = useDisclosure();
  const { isOpen: isModalBCOpen, onOpen: onModalBCOpen, onClose: onModalBCClose } = useDisclosure();
  const {
    isOpen: isModalCriaFNNOpen,
    onOpen: onModalCriaFNNOpen,
    onClose: onModalCriaFNNClose,
  } = useDisclosure();
  const {
    isOpen: isModalEditaFNNOpen,
    onOpen: onModalEditaFNNOpen,
    onClose: onModalEditaFNNClose,
  } = useDisclosure();

  const notificacao = useFuncaoNotificacao();
  const [validouPermissao, setValidouPermissao] = useState(false);

  useValidaAcessos([Acessos.CONVENIOS_BANCARIOS], () => {
    setValidouPermissao(true);
  });

  useEffect(() => {
    if (!convenioBancario) {
      navigate("./..");
    }
  }, [convenioBancario, navigate]);

  const [contaCorrenteFiltrada, setContaCorrenteFiltrada] = useState<ContaCorrenteDTO>();
  const [faixasNossoNumero, setFaixasNossoNumero] = useState<FaixaNossoNumeroDTO[]>();

  const methods = useForm<EditandoConvenioBancarioFormData>({
    criteriaMode: "all",
    mode: "all",
    resolver: zodResolver(ConvenioBancarioValidateSchema),
  });

  const [aceite, setAceite] = useState(false);
  const [unidadeCentralizadora, setUnidadeCentralizadora] = useState(false);

  useEffect(() => {
    if (convenioBancario) {
      methods.setValue("nomeCedente", convenioBancario.nomeCedente);
      methods.setValue("numero", convenioBancario.numero);
      methods.setValue("carteira", convenioBancario.carteira);
      methods.setValue("indiceMulta", convenioBancario.indiceMulta.toString());
      methods.setValue("indiceJuros", convenioBancario.indiceJuros.toString());
      methods.setValue("sistemaBancario", convenioBancario.sistemaBancario);
      methods.setValue("observacao1", convenioBancario.observacao1 ?? "");
      methods.setValue("observacao2", convenioBancario.observacao2 ?? "");
      methods.setValue("observacao3", convenioBancario.observacao3 ?? "");
      methods.setValue("observacao4", convenioBancario.observacao4 ?? "");
      methods.setValue("observacao5", convenioBancario.observacao5 ?? "");
      methods.setValue("localPagamento", convenioBancario.localPagamento);
      methods.clearErrors();

      setAceite(convenioBancario.aceite);
      setUnidadeCentralizadora(convenioBancario.utilizaUnCentralizadora);

      setContaCorrenteFiltrada(convenioBancario.contaCorrente);
      setFaixasNossoNumero(convenioBancario.faixasNossoNumero);
    }
  }, [convenioBancario]);

  const { mutate: handleEditarConvenioBancario, isLoading: editandoLoading } = useMutation(
    () => {
      if (!convenioBancario) {
        throw new Error(); // sempre vai existir
      }
      if (!contaCorrenteFiltrada) {
        throw new ErroCancelaAcao("Favor, selecionar uma conta corrente.");
      }

      const params: AlterarConvenioBancarioParams = {
        idConvenioBancario: convenioBancario.id,
        idContaCorrente: contaCorrenteFiltrada.id,
        nomeCedente: methods.getValues("nomeCedente"),
        numero: methods.getValues("numero"),
        carteira: methods.getValues("carteira"),
        aceite,
        indiceMulta: Number(methods.getValues("indiceMulta")),
        indiceJuros: Number(methods.getValues("indiceJuros")),
        sistemaBancario: methods.getValues("sistemaBancario"),
        observacao1: methods.getValues("observacao1"),
        observacao2: methods.getValues("observacao2"),
        observacao3: methods.getValues("observacao3"),
        observacao4: methods.getValues("observacao4"),
        observacao5: methods.getValues("observacao5"),
        localPagamento: methods.getValues("localPagamento"),
        utilizaUnCentralizadora: unidadeCentralizadora,
        idUnidade: convenioBancario.idUnidade,
      };

      return putAlterarConvenioBancario(params, axios);
    },
    {
      onSuccess: () => {
        notificacao({ message: "Alterado com sucesso!", tipo: "success", titulo: "SUCESSO!" });
        navigate("./..");
      },
      onError: (e) => {
        notificacao({
          message: getMensagemDeErroOuProcureSuporte(e),
          tipo: "error",
          titulo: "Erro ao alterar convênio",
        });
      },
    }
  );

  const { mutate: handleConvenioInativoSelecionado, isLoading: convenioInativoSelecionadoLoading } =
    useMutation(
      (convenioBancario: ConvenioBancarioDTO | undefined) => {
        if (!convenioBancario) {
          return Promise.reject(new ErroCancelaAcao());
        }

        return patchAtivarConvenioBancario(
          {
            idConvenioBancario: convenioBancario.id,
          },
          axios
        );
      },
      {
        onSuccess: () => {
          navigate("./..");
        },
        onError: (e) => {
          if (e instanceof ErroCancelaAcao) {
            notificacao({
              tipo: "warning",
              titulo: "Selecione um Convênio",
              message:
                "Selelcione um Convênio Bancário clicando no memso e depois confirmando para reativá-lo",
            });
          } else {
            notificacao({
              message: getMensagemDeErroOuProcureSuporte(e),
              tipo: "error",
              titulo: "Erro ao ativar convênio",
            });
          }
        },
      }
    );

  const { mutate: handleDesativarConvenio, isLoading: desativarConvenioLoading } = useMutation(
    (convenioBancario: ConvenioBancarioDTO) => {
      return patchDesativarConvenioBancario(
        {
          idConvenioBancario: convenioBancario.id,
        },
        axios
      );
    },
    {
      onSuccess: () => {
        notificacao({
          tipo: "success",
          titulo: "Sucesso",
          message: "Convênio desativado com sucesso!",
        });
        navigate("./..");
      },
      onError: (e) => {
        notificacao({
          message: getMensagemDeErroOuProcureSuporte(e),
          tipo: "error",
          titulo: "Erro ao editar Faixa de Nosso Número",
        });
      },
    }
  );

  const { mutate: handleAtivarFaixaNossoNumero } = useMutation(
    (faixa: FaixaNossoNumeroDTO) => {
      if (faixa.ativo) {
        notificacao({
          tipo: "warning",
          titulo: "Faixa já ativa",
          message: "Não se pode ativar faixa já ativa!",
        });
      }

      return patchAtivarFaixaNossoNumero(
        {
          idConvenioBancario: convenioBancario?.id ?? -1,
          idFaixa: faixa.id,
        },
        axios
      );
    },
    {
      onSuccess: (novasFaixas) => {
        if (convenioBancario) {
          convenioBancario.faixasNossoNumero = novasFaixas;
        }
      },
      onError: (e) => {
        notificacao({
          message: getMensagemDeErroOuProcureSuporte(e),
          tipo: "error",
          titulo: "Erro ao ativar Faixa de Nosso Número",
        });
      },
    }
  );

  const { mutate: handleNovaFaixaNossoNumero } = useMutation(
    (faixa: FaixaNossoNumeroCRUDDTO) => {
      return postCriarFaixaNossoNumero(
        {
          idConvenioBancario: convenioBancario?.id ?? -1,
          alterarFaixaNossoNumeroDTO: faixa,
        },
        axios
      );
    },
    {
      onSuccess: (novaFaixa) => {
        if (convenioBancario) {
          for (const faixa of convenioBancario.faixasNossoNumero) {
            faixa.ativo = false;
          }
          convenioBancario.faixasNossoNumero.push(novaFaixa);
        }

        onModalCriaFNNClose();
      },
      onError: (e) => {
        notificacao({
          message: getMensagemDeErroOuProcureSuporte(e),
          tipo: "error",
          titulo: "Erro ao criar nova Faixa de Nosso Número",
        });
      },
    }
  );

  const [faixaNossoNumero, setFaixaNossoNumero] = useState<FaixaNossoNumeroDTO>();

  function handleOpenEditarFaixaNossoNumero(faixa: FaixaNossoNumeroDTO) {
    setFaixaNossoNumero(faixa);
    onModalEditaFNNOpen();
    console.log(faixa);
  }

  const { mutate: handleEditarFaixaNossoNumero, isLoading: editarFaixaNossoNumeroLoading } =
    useMutation(
      (params: { faixa: FaixaNossoNumeroCRUDDTO; idFaixa: number }) => {
        return putAlterarFaixaNossoNumero(
          {
            idConvenioBancario: convenioBancario?.id ?? -1,
            idFaixa: params.idFaixa,
            alterarFaixaNossoNumeroDTO: params.faixa,
          },
          axios
        );
      },
      {
        onSuccess: (novaFaixa) => {
          if (convenioBancario) {
            const indexFaixa = convenioBancario.faixasNossoNumero.findIndex(
              (f) => f.id === novaFaixa.id
            );
            convenioBancario.faixasNossoNumero.splice(indexFaixa, 1, novaFaixa);
          }
          onModalEditaFNNClose();
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

  if (!convenioBancario || !validouPermissao) {
    return <></>;
  }

  const subtitulo = `Editando Convênio ativo da unidade ${convenioBancario.unidade.nome}-${convenioBancario.unidade.codigo}`;

  return (
    <Box display="flex" alignItems="center" flexDirection="column">
      <CabecalhoPages titulo="Editar Convênio Bancário" subtitulo={subtitulo} />
      <Box w={{ base: "100%", md: "60%" }}>
        <FormProvider {...methods}>
          <form onSubmit={methods.handleSubmit(() => handleEditarConvenioBancario())}>
            <FormConvenioBancario
              onOpenContaCorrente={onModalCCOpen}
              contaCorrenteFiltrada={contaCorrenteFiltrada}
              onAtivarFaixaNossoNumero={handleAtivarFaixaNossoNumero}
              onEditFaixaNossoNumero={handleOpenEditarFaixaNossoNumero}
              onCriarFaixaNossoNumero={onModalCriaFNNOpen}
              faixasNossoNumero={faixasNossoNumero}
              editando
              editFaixaNossoNumeroLoading={true}
              aceite={aceite}
              setAceite={setAceite}
              unidadeCentralizadora={unidadeCentralizadora}
              setUnidadeCentralizadora={setUnidadeCentralizadora}
            />
            <Divider mt={5} />
            <Grid
              w="100%"
              templateColumns={{ base: "1fr", md: "repeat(1, 1fr)", lg: "repeat(3, 1fr)" }} // 1 coluna no mobile, 2 colunas no md e 3 colunas no lg
              gap={2}
              pt={5}
              pb={5}
            >
              <GridItem colSpan={1} p={1}>
                <Button
                  colorScheme="blue"
                  type="submit"
                  w="100%"
                  isLoading={editandoLoading}
                  loadingText="Salvando"
                >
                  Salvar
                </Button>
              </GridItem>
              <GridItem colSpan={1} p={1}>
                <Button colorScheme="blue" w="100%" onClick={onModalBCOpen}>
                  Gerenciar Convênios Desativados
                </Button>
              </GridItem>
              <GridItem colSpan={1} p={1}>
                <Button
                  colorScheme="blue"
                  w="100%"
                  isLoading={desativarConvenioLoading}
                  loadingText="Desativando"
                  onClick={() => handleDesativarConvenio(convenioBancario)}
                >
                  Desativar Convênio
                </Button>
              </GridItem>
              <GridItem colSpan={1} p={1}>
                <Button colorScheme="blue" w="100%" onClick={() => navigate("./../novo")}>
                  Criar Novo Convênio
                </Button>
              </GridItem>
              <GridItem colSpan={1} p={1}>
                <Button
                  colorScheme="blue"
                  w="100%"
                  onClick={() => navigate("./..")}
                  variant={"outline"}
                >
                  Cancelar
                </Button>
              </GridItem>
            </Grid>
          </form>
        </FormProvider>
      </Box>
      <ModalPesquisaContaCorrente
        isOpen={isModalCCOpen}
        onClose={onModalCCClose}
        setContaCorrenteFiltrada={setContaCorrenteFiltrada}
      />

      <ModalPesquisaConvenioBancarioInativo
        isOpen={isModalBCOpen}
        onClose={onModalBCClose}
        textoBotaoConfirma="Reativar Convênio (desativa o atual)"
        onClickBotaoConfirma={handleConvenioInativoSelecionado}
        isLoading={convenioInativoSelecionadoLoading}
      />

      <ModalCriaFaixaNossoNumero
        isOpen={isModalCriaFNNOpen}
        onClose={onModalCriaFNNClose}
        titulo={`Criando Faixa de Nosso Número para o convênio ${convenioBancario.nomeCedente} número ${convenioBancario.numero}`}
        onCreate={handleNovaFaixaNossoNumero}
      />

      <ModalEditarFaixaNossoNumero
        isOpen={isModalEditaFNNOpen}
        onClose={onModalEditaFNNClose}
        titulo={`Editando Faixa de Nosso Número para o convênio ${convenioBancario.nomeCedente} número ${convenioBancario.numero}`}
        faixaNossoNumero={faixaNossoNumero}
        onEdit={handleEditarFaixaNossoNumero}
        isLoading={editarFaixaNossoNumeroLoading}
      />
    </Box>
  );
}
