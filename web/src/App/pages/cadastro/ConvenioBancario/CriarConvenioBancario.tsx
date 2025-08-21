import { Box, Divider, useDisclosure } from "@chakra-ui/react";
import { zodResolver } from "@hookform/resolvers/zod";
import { useMutation } from "@tanstack/react-query";
import { useEffect, useRef, useState } from "react";
import { FormProvider, useForm } from "react-hook-form";
import { useNavigate } from "react-router-dom";
import { ContaCorrenteDTO } from "../../../../models/DTOs/AgenciaBancoConta/ContaCorrenteDTO";
import { FaixaNossoNumeroDTO } from "../../../../models/DTOs/ConvenioBancario";
import { ErroCancelaAcao } from "../../../../models/errors/ErroCancelaAcao";
import {
  CriarConvenioBancarioParams,
  FaixaNossoNumeroCRUDDTO,
  postCriarConvenioBancario,
} from "../../../../requests/requestsConveniosBancarios";
import Acessos from "../../../../utils/Acessos";
import { getMensagemDeErroOuProcureSuporte } from "../../../../utils/errors";
import { SimpleObservable } from "../../../../utils/simpleObservable";
import { ButtonControlForm } from "../../../components/ButoonControl";
import { CabecalhoPages } from "../../../components/CabecalhoPages";
import useCR5Axios from "../../../hooks/useCR5Axios";
import { useFuncaoNotificacao } from "../../../hooks/useFuncaoNotificacao";
import { useValidaAcessos } from "../../../hooks/useValidaAcessos";
import { useUnidadeStore } from "../../../stores/UnidadeStore";
import { ModalPesquisaContaCorrente } from "../ContaCorrente/components/ModalPesquisaContaCorrente";
import { ConvenioBancarioValidateSchema } from "./components/ConvenioBancarioValidateSchema";
import { FormConvenioBancario } from "./components/FormConvenioBancario";
import { ModalCriaFaixaNossoNumero } from "./components/ModalCriaFaixaNossoNumero";
import { ModalEditarFaixaNossoNumero } from "./components/ModalEditarFaixaNossoNumero";

export function CriarConvenioBancario() {
  const { axios } = useCR5Axios();
  type EditandoConvenioBancarioFormData = Zod.infer<typeof ConvenioBancarioValidateSchema>;
  const navigate = useNavigate();
  const { unidadeAtual } = useUnidadeStore();
  const { isOpen: isModalCCOpen, onOpen: onModalCCOpen, onClose: onModalCCClose } = useDisclosure();
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

  const [contaCorrenteFiltrada, setContaCorrenteFiltrada] = useState<ContaCorrenteDTO>();
  const [faixaNossoNumero, setFaixaNossoNumero] = useState<FaixaNossoNumeroDTO>();
  const notificacao = useFuncaoNotificacao();

  useValidaAcessos([Acessos.CONVENIOS_BANCARIOS]);

  const methods = useForm<EditandoConvenioBancarioFormData>({
    criteriaMode: "all",
    mode: "all",
    resolver: zodResolver(ConvenioBancarioValidateSchema),
  });

  const [aceite, setAceite] = useState(false);
  const [unidadeCentralizadora, setUnidadeCentralizadora] = useState(false);

  useEffect(() => {
    methods.setValue("carteira", "RG");
    methods.setValue("sistemaBancario", "SINCO");
    methods.clearErrors();
  }, []);

  const { mutate, isLoading } = useMutation(
    () => {
      if (!faixaNossoNumero) {
        return Promise.reject(
          new ErroCancelaAcao("Um Convênio deve ter uma faixa de nosso número! Cadastre na aba")
        );
      }

      if (!contaCorrenteFiltrada) {
        return Promise.reject(new ErroCancelaAcao("Favor, selecionar uma conta corrente"));
      }

      const params: CriarConvenioBancarioParams = {
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
        idUnidade: unidadeAtual.id,
        criarFaixaNossoNumeroDTO: {
          nossoNumeroAtual: faixaNossoNumero.nossoNumeroAtual,
          nossoNumeroInicial: faixaNossoNumero.nossoNumeroInicial,
          nossoNumeroFinal: faixaNossoNumero.nossoNumeroFinal,
        },
      };

      return postCriarConvenioBancario(params, axios);
    },
    {
      onSuccess: () => {
        notificacao({ message: "Criado com sucesso!", tipo: "success", titulo: "SUCESSO!" });
        navigate("./..");
      },
      onError: (e) => {
        notificacao({
          message: getMensagemDeErroOuProcureSuporte(e),
          tipo: "error",
          titulo: "Erro ao criar convênio",
        });
      },
    }
  );

  function handleCriarConvenioBancario() {
    mutate();
  }

  function handleNovaFaixaNossoNumero(faixaCrud: FaixaNossoNumeroCRUDDTO) {
    const faixa: FaixaNossoNumeroDTO = {
      id: -1,
      dataInclusao: new Date(),
      idOperadorInclusao: -1,
      dataAlteracao: null,
      idOperadorAlteracao: null,
      ativo: true,
      ...faixaCrud,
    };
    setFaixaNossoNumero(faixa);
    onModalCriaFNNClose();
  }

  const observableFaixa = useRef(new SimpleObservable<FaixaNossoNumeroDTO>());

  function handleOpenEditarFaixaNossoNumero(faixa: FaixaNossoNumeroDTO) {
    observableFaixa.current.notify(faixa);
    onModalEditaFNNOpen();
  }

  function handleEditarFaixaNossoNumero(params: {
    faixa: FaixaNossoNumeroCRUDDTO;
    idFaixa: number;
  }) {
    if (faixaNossoNumero) {
      // Sempre vai estar presente
      const faixaAnteior = { ...faixaNossoNumero };

      faixaAnteior.nossoNumeroAtual = params.faixa.nossoNumeroAtual;
      faixaAnteior.nossoNumeroInicial = params.faixa.nossoNumeroInicial;
      faixaAnteior.nossoNumeroFinal = params.faixa.nossoNumeroFinal;

      setFaixaNossoNumero(faixaAnteior);
      onModalEditaFNNClose();
    }
  }

  return (
    <Box display="flex" alignItems="center" flexDirection="column">
      <CabecalhoPages
        titulo="Criar Convênio Bancário"
        subtitulo={`Você está criando um Convênio para a unidade ${unidadeAtual.nome}-${unidadeAtual.codigo}`}
      />
      <Box w={{ base: "100%", md: "60%" }}>
        <FormProvider {...methods}>
          <form onSubmit={methods.handleSubmit(handleCriarConvenioBancario)}>
            <FormConvenioBancario
              onOpenContaCorrente={onModalCCOpen}
              contaCorrenteFiltrada={contaCorrenteFiltrada}
              onAtivarFaixaNossoNumero={undefined}
              onEditFaixaNossoNumero={handleOpenEditarFaixaNossoNumero}
              onCriarFaixaNossoNumero={onModalCriaFNNOpen}
              faixasNossoNumero={faixaNossoNumero ? [faixaNossoNumero] : []}
              editando={false}
              aceite={aceite}
              setAceite={setAceite}
              unidadeCentralizadora={unidadeCentralizadora}
              setUnidadeCentralizadora={setUnidadeCentralizadora}
              editFaixaNossoNumeroLoading={false}
            />
            <Divider mt={5} />
            <ButtonControlForm isLoading={isLoading} />
          </form>
        </FormProvider>
      </Box>
      <ModalPesquisaContaCorrente
        isOpen={isModalCCOpen}
        onClose={onModalCCClose}
        setContaCorrenteFiltrada={setContaCorrenteFiltrada}
      />

      <ModalCriaFaixaNossoNumero
        isOpen={isModalCriaFNNOpen}
        onClose={onModalCriaFNNClose}
        titulo={`Criando Faixa de Nosso Número para o novo convênio da unidade ${unidadeAtual.codigo}`}
        onCreate={handleNovaFaixaNossoNumero}
      />

      <ModalEditarFaixaNossoNumero
        isOpen={isModalEditaFNNOpen}
        onClose={onModalEditaFNNClose}
        titulo={`Editando Faixa de Nosso Número para o novo convênio da unidade ${unidadeAtual.codigo}`}
        faixaNossoNumero={faixaNossoNumero}
        onEdit={handleEditarFaixaNossoNumero}
      />
    </Box>
  );
}
