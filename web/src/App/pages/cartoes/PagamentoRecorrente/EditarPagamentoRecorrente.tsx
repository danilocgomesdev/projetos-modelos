import { Box, Button, Divider, Grid, GridItem, useDisclosure } from "@chakra-ui/react";
import { zodResolver } from "@hookform/resolvers/zod";
import { useMutation } from "@tanstack/react-query";
import { useEffect, useRef, useState } from "react";
import { FormProvider, useForm } from "react-hook-form";
import { useNavigate } from "react-router-dom";
import * as zod from "zod";
import { RecorrenciaDTO } from "../../../../models/DTOs/Recorrencia/RecorrenciaDTO.ts";
import {
  DeletarRecorrenciaParams,
  cancelarRecorrencia,
} from "../../../../requests/requestsRecorrencia.ts";
import Acessos from "../../../../utils/Acessos.ts";
import { mascaraCpfCnpj } from "../../../../utils/mascaras.ts";
import { CabecalhoPages } from "../../../components/CabecalhoPages";
import { ModalConfirma } from "../../../components/ModalConfirma";
import useCR5Axios from "../../../hooks/useCR5Axios.ts";
import { useFuncaoNotificacao } from "../../../hooks/useFuncaoNotificacao.tsx";
import { useValidaAcessos } from "../../../hooks/useValidaAcessos.ts";
import { useUnidadeStore } from "../../../stores/UnidadeStore.tsx";
import { FormPagamentoRecorrente } from "../../cartoes/PagamentoRecorrente/components/FormPagamentoRecorrente.tsx";
import { ListaRecorrenciaSelecionada } from "../../cartoes/PagamentoRecorrente/components/ListaRecorrenciaSelecionada.tsx";
import { PagamentoRecorrenteValidateSchema } from "./components/PagamentoRecorrenteValidateSchema.ts";
import { usePagamentoRecorrenteStore } from "./store/PagamentoRecorrenteStore.ts";

export function EditarPagamentoRecorrente() {
  const { axios } = useCR5Axios();
  const navigate = useNavigate();

  const { pagamentoRecorrente } = usePagamentoRecorrenteStore();
  const [validouPermissao, setValidouPermissao] = useState(false);

  const notificacao = useFuncaoNotificacao();

  useValidaAcessos([Acessos.RECORRENCIA], () => {
    setValidouPermissao(true);
  });

  useEffect(() => {
    if (!pagamentoRecorrente) {
      navigate("./..");
    }
  }, [pagamentoRecorrente, navigate]);

  const { unidadeAtual } = useUnidadeStore();
  const unidadeInicial = useRef(unidadeAtual);

  useEffect(() => {
    if (unidadeAtual.id !== unidadeInicial.current.id) {
      navigate("./..");
    }
  }, [unidadeAtual]);

  type CriandoPagamentoRecorrenteFormData = zod.infer<typeof PagamentoRecorrenteValidateSchema>;
  const methods = useForm<CriandoPagamentoRecorrenteFormData>({
    criteriaMode: "all",
    mode: "all",
    resolver: zodResolver(PagamentoRecorrenteValidateSchema),
  });

  const { isOpen, onOpen, onClose } = useDisclosure();

  const [recorrenciaFiltrada] = useState<RecorrenciaDTO>();

  useEffect(() => {
    if (pagamentoRecorrente) {
      methods.setValue("unidade", pagamentoRecorrente.unidade.toString());
      methods.setValue("cpfCnpj", mascaraCpfCnpj(pagamentoRecorrente.cpfCnpj.toString()));
      methods.setValue("responsavelFinanceiro", pagamentoRecorrente.responsavelFinanceiro);
      methods.setValue("statusRecorrencia", pagamentoRecorrente.statusRecorrencia);
      methods.setValue("idRecorrencia", pagamentoRecorrente.idRecorrencia.toString());
      methods.setValue("dataInicioRecorrencia", pagamentoRecorrente.dataInicioRecorrencia ?? "");
      methods.setValue("dataFimRecorrencia", pagamentoRecorrente.dataFimRecorrencia ?? "");
      methods.clearErrors();
    }
  }, [pagamentoRecorrente]);

  const { mutate, isLoading } = useMutation(
    () => {
      const params: DeletarRecorrenciaParams = {
        idRecorrencia: pagamentoRecorrente?.idRecorrencia.toString(),
        idEntidade: pagamentoRecorrente?.idEntidade,
      };

      return cancelarRecorrencia(params, axios);
    },
    {
      onSuccess: () => {
        notificacao({ message: "Cancelado com sucesso!", tipo: "success", titulo: "SUCESSO!" });

        methods.setValue("statusRecorrencia", "Cancelada");

        onClose();
      },
      onError: (e) => {
        onClose();
        notificacao({
          message: "Erro. Transação não efetivada ." + e,
          tipo: "error",
          titulo: "ERRO!",
        });
      },
    }
  );

  function handleEditarPagamentoRecorrente() {
    console.log("Entrou para deletar ....: " + pagamentoRecorrente?.idRecorrencia.toString());
    mutate();
  }

  if (!pagamentoRecorrente || !validouPermissao) {
    return <></>;
  }

  const tituloTela = "Dados da Recorrência";
  const subtitulo = `Recorrência: ${pagamentoRecorrente.idRecorrencia}`;

  return (
    <Box display="flex" alignItems="center" flexDirection="column">
      <CabecalhoPages titulo={tituloTela} subtitulo={subtitulo} />
      <Box w={{ base: "100%", md: "auto", lg: "65%" }}>
        <FormProvider {...methods}>
          <form>
            <FormPagamentoRecorrente
              onOpen={onOpen}
              pagamentoRecorrenteFiltrada={recorrenciaFiltrada}
            />
            <ListaRecorrenciaSelecionada
              onOpen={onOpen}
              pagamentoRecorrenteFiltradaItem={pagamentoRecorrente.idRecorrencia.toString()}
            />
            <Divider mt={5} />
          </form>
          <Grid w={"100%"} templateColumns={{ base: "1fr", md: "repeat(4, 1fr)" }} pt={5} pb={5}>
            <GridItem colSpan={{ base: 1, md: 2 }} p={1}>
              <Button
                colorScheme="blue"
                w="100%"
                type="submit"
                isLoading={isLoading}
                loadingText="Salvando"
                onClick={onOpen}
              >
                Cancelar Recorrência
              </Button>
            </GridItem>
            <GridItem colSpan={{ base: 1, md: 2 }} p={1}>
              <Button
                variant="outline"
                colorScheme="blue"
                w="100%"
                onClick={() => {
                  navigate("./..");
                }}
              >
                Voltar
              </Button>
            </GridItem>
          </Grid>
          <ModalConfirma
            titulo="Atenção"
            texto={`Deseja Excluir Recorrência:  ${pagamentoRecorrente.idRecorrencia.toString()}`}
            onClose={onClose}
            isOpen={isOpen}
            onConfirm={handleEditarPagamentoRecorrente}
          />
        </FormProvider>
      </Box>
    </Box>
  );
}
