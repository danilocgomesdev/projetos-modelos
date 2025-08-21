import { Grid, Spinner, useDisclosure } from "@chakra-ui/react";
import { useMutation, useQuery } from "@tanstack/react-query";
import { useEffect, useState } from "react";
import { FiCheckCircle } from "react-icons/fi";
import { useNavigate } from "react-router-dom";
import { ConvenioBancarioDTO } from "../../../../models/DTOs/ConvenioBancario";
import {
  fetchConveniosBancariosPaginado,
  fetchDeleteConvenioBancario,
  patchAtivarConvenioBancario,
} from "../../../../requests/requestsConveniosBancarios";
import queryClient from "../../../../singletons/reactQueryClient";
import Acessos from "../../../../utils/Acessos";
import { trataValorInputNumberVazio } from "../../../../utils/componentUtil";
import { DelaysDebouncePadrao, TemposCachePadrao } from "../../../../utils/constantes";
import { getMensagemDeErroOuProcureSuporte } from "../../../../utils/errors";
import { formatarDataBrasil } from "../../../../utils/mascaras";
import { ButtonControl } from "../../../components/ButoonControl";
import { CabecalhoPages } from "../../../components/CabecalhoPages";
import { FiltroEntidadeUnidade } from "../../../components/FiltroEntidadeUnidade";
import { InputControl } from "../../../components/InputControl";
import { ModalConfirma } from "../../../components/ModalConfirma";
import { SelectControl } from "../../../components/SelectControl";
import { TabelaCR5 } from "../../../components/Tabelas";
import useCR5Axios from "../../../hooks/useCR5Axios";
import useDebounce from "../../../hooks/useDebounce";
import { useFuncaoNotificacao } from "../../../hooks/useFuncaoNotificacao";
import { useValidaAcessos } from "../../../hooks/useValidaAcessos";
import { useConvenioBancarioStore } from "./store/ConvenioBancarioStore";

export function ConvenioBancario() {
  const delayDebounce = DelaysDebouncePadrao.PADRAO;
  const itemsPerPage = 7;

  const { axios } = useCR5Axios();
  const notificacao = useFuncaoNotificacao();
  const { convenioBancario, setConvenioBancario } = useConvenioBancarioStore();
  const navigate = useNavigate();
  const { isOpen: isDeletarOpen, onOpen: onDeletarOpen, onClose: onDeletarClose } = useDisclosure();
  const [invalidando, setInvalidando] = useState(true);

  const [validouPermissao, setValidouPermissao] = useState(false);

  const [currentPage, setCurrentPage] = useState(1);
  const [idBusca, setIdBusca] = useState<number | null>(null);
  const [numeroBusca, setNumeroBusca] = useState("");
  const [nomeCedenteBusca, setNomeCedenteBusca] = useState("");
  const [nomeBancoBusca, setNomeBancoBusca] = useState("");
  const [nomeAgenciaBusca, setNomeAgenciaBusca] = useState("");
  const [numeroContaBusca, setNumeroContaBusca] = useState("");
  const [statusBusca, setStatusBusca] = useState<string | undefined>("ATIVO");
  const [idUnidadeBusca, setIdUnidadeBusca] = useState<number | null>(null);
  const [idEntidadeBusca, setIdEntidadeBusca] = useState<number | null>(null);

  const [idDebounce] = useDebounce(idBusca, delayDebounce);
  const [numeroDebounce] = useDebounce(numeroBusca, delayDebounce);
  const [nomeCedenteDebounce] = useDebounce(nomeCedenteBusca, delayDebounce);
  const [nomeBancoDebounce] = useDebounce(nomeBancoBusca, delayDebounce);
  const [nomeAgenciaDebounce] = useDebounce(nomeAgenciaBusca, delayDebounce);
  const [numeroContaDebounce] = useDebounce(numeroContaBusca, delayDebounce);
  const [inativoSelecionadoId, setInativoSelecionadoId] = useState<number | null>(null);

  useValidaAcessos([Acessos.CONVENIOS_BANCARIOS], () => {
    setValidouPermissao(true);
  });

  function invalidadeQuery(): Promise<void> {
    return queryClient.invalidateQueries({ queryKey: ["fetchConveniosBancariosPaginado"] });
  }

  useEffect(() => {
    invalidadeQuery().then(() => setInvalidando(false));
  }, []);

  const { data, refetch, isFetching, isError, error } = useQuery({
    queryKey: [
      "fetchConveniosBancariosPaginado",
      currentPage,
      idDebounce,
      numeroDebounce,
      nomeCedenteDebounce,
      nomeBancoDebounce,
      idEntidadeBusca,
      idUnidadeBusca,
      nomeAgenciaDebounce,
      numeroContaDebounce,
      statusBusca,
    ],
    enabled: !invalidando && validouPermissao,
    keepPreviousData: true,
    staleTime: TemposCachePadrao.MODERADO,
    cacheTime: TemposCachePadrao.MODERADO,
    queryFn: () => {
      return fetchConveniosBancariosPaginado(
        {
          page: currentPage - 1,
          pageSize: itemsPerPage,
          id: idDebounce ?? undefined,
          nomeCedente: nomeCedenteDebounce,
          numero: numeroDebounce,
          nomeBanco: nomeBancoDebounce,
          idEntidade: idEntidadeBusca ?? undefined,
          idUnidade: idUnidadeBusca ?? undefined,
          nomeAgencia: nomeAgenciaDebounce,
          numeroConta: numeroContaDebounce,
          status: statusBusca as "ATIVO" | "INATIVO" | undefined,
        },
        axios
      );
    },
  });

  const { mutate: handleConvenioInativoSelecionado } = useMutation(
    (convenioBancario: ConvenioBancarioDTO) => {
      setInativoSelecionadoId(convenioBancario.id); // Adicione esta linha
      return patchAtivarConvenioBancario(
        {
          idConvenioBancario: convenioBancario.id,
        },
        axios
      );
    },
    {
      onSuccess: () => {
        setInativoSelecionadoId(null); // Limpe o estado após o sucesso
        notificacao({
          tipo: "success",
          titulo: "Sucesso",
          message: "Convênio reativado com sucesso!",
        });
        refetch();
      },
      onError: (e) => {
        setInativoSelecionadoId(null); // Limpe o estado após o erro
        notificacao({
          message: getMensagemDeErroOuProcureSuporte(e),
          tipo: "error",
          titulo: "ERRO!",
        });
      },
    }
  );

  const { mutate: deleteConvenio, isLoading: deleteConvenioLoading } = useMutation(
    (idConvenioBancario: number) => {
      return fetchDeleteConvenioBancario({ idConvenioBancario: idConvenioBancario }, axios);
    },
    {
      onSuccess: () => {
        notificacao({ message: "Excluído com sucesso!", tipo: "success", titulo: "SUCESSO!" });
        queryClient
          .invalidateQueries({ queryKey: ["fetchDeleteConvenioBancario"] })
          .then(() => refetch());
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

  function handleDeleteConvenioBancario(): void {
    if (convenioBancario) {
      deleteConvenio(convenioBancario.id);
    }
  }

  function atualizaValorPesquisa<T>(setter: (arg: T) => void, valor: T): void {
    setter(valor);
    setCurrentPage(1);
  }

  return (
    <>
      <CabecalhoPages titulo="Cadastro de Convênios Bancários" />
      <Grid
        w="100%"
        templateColumns={{ base: "¨100%", md: "repeat(12, 1fr)" }}
        mt={4}
        pb={{ base: 4, md: 0 }}
      >
        <InputControl
          label="Código"
          id="id"
          value={idBusca?.toString() || ""}
          onChange={(event) =>
            trataValorInputNumberVazio((val) => atualizaValorPesquisa(setIdBusca, val), event)
          }
          numeroColunasMd={2}
          numeroColunasLg={2}
        />
        <InputControl
          label="Número"
          id="numero"
          value={numeroBusca}
          onChange={(e) => atualizaValorPesquisa(setNumeroBusca, e.target.value)}
          numeroColunasMd={2}
          numeroColunasLg={2}
        />
        <InputControl
          label="Nome Cedente"
          id="nomeCedente"
          value={nomeCedenteBusca}
          onChange={(e) => atualizaValorPesquisa(setNomeCedenteBusca, e.target.value)}
          numeroColunasMd={4}
          numeroColunasLg={4}
        />
        <InputControl
          label="Nome Banco"
          id="nomeBanco"
          value={nomeBancoBusca}
          onChange={(e) => atualizaValorPesquisa(setNomeBancoBusca, e.target.value)}
          numeroColunasMd={4}
          numeroColunasLg={4}
        />
        <FiltroEntidadeUnidade
          width="100%"
          onChange={(arg) => {
            if (arg.tipo === "LIMPAR") {
              setIdEntidadeBusca(null);
              setIdUnidadeBusca(null);
            } else if (arg.tipo === "ENTIDADE") {
              setIdEntidadeBusca(arg.id);
              setIdUnidadeBusca(null);
            } else {
              setIdEntidadeBusca(null);
              setIdUnidadeBusca(arg.id);
            }

            setCurrentPage(1);
          }}
          numeroColunasMd={8}
          numeroColunasLg={4}
        />
        <InputControl
          label="Nome Agência"
          id="nomeAgencia"
          value={nomeAgenciaBusca}
          onChange={(e) => atualizaValorPesquisa(setNomeAgenciaBusca, e.target.value)}
          numeroColunasMd={4}
          numeroColunasLg={4}
        />
        <InputControl
          label="Nº Conta-Dígito"
          id="numeroConta"
          value={numeroContaBusca}
          onChange={(e) => atualizaValorPesquisa(setNumeroContaBusca, e.target.value)}
          numeroColunasMd={4}
          numeroColunasLg={2}
        />
        <SelectControl
          numeroColunasMd={4}
          numeroColunasLg={2}
          label="Status"
          id="status"
          value={statusBusca}
          options={[
            {
              value: "ATIVO",
              label: "Ativo",
            },
            {
              value: "INATIVO",
              label: "Inativo",
            },
          ]}
          onChange={(e) => atualizaValorPesquisa(setStatusBusca, e ?? undefined)}
        />
      </Grid>
      <ButtonControl />
      <TabelaCR5
        cabecalhos={[
          { titulo: "CÓDIGO", dadoBuilder: (item) => item.id.toString() },
          {
            titulo: "CEDENTE",
            dadoBuilder: (item) => item.nomeCedente,
          },
          {
            titulo: "NÚMERO",
            dadoBuilder: (item) => item.numero,
          },
          {
            titulo: "UNIDADE",
            dadoBuilder: (item) => `${item.unidade.nome}-${item.unidade.codigo}`,
          },
          {
            titulo: "NOME BANCO",
            dadoBuilder: (item) => item.contaCorrente.agencia.banco.nome,
          },
          { titulo: "NOME AGÊNCIA", dadoBuilder: (item) => item.contaCorrente.agencia.nome },
          {
            titulo: "NÚMERO-DÍGITO CC",
            dadoBuilder: (item) =>
              item.contaCorrente.numeroConta + "-" + item.contaCorrente.digitoConta,
          },
          {
            titulo: "STATUS",
            dadoBuilder: (item) => (item.dataInativo ? "INATIVO" : "ATIVO"),
          },
        ]}
        detalhesBuilder={{
          type: "Expandir",
          buildDetalhes: (item) => {
            const faixaAtiva = item.faixasNossoNumero.find((f) => f.ativo);

            return [
              [
                { nome: "Data Inclusão", valor: formatarDataBrasil(item.dataInclusao) },
                { nome: "Data Alteracao", valor: formatarDataBrasil(item.dataAlteracao) },
                { nome: "Data Inativo", valor: formatarDataBrasil(item.dataInativo) },
                { nome: "NN Inicial", valor: faixaAtiva?.nossoNumeroInicial ?? "" },
                { nome: "NN Atual", valor: faixaAtiva?.nossoNumeroAtual ?? "" },
                { nome: "NN Final", valor: faixaAtiva?.nossoNumeroFinal ?? "" },
              ],
              [
                { nome: "Carteira", valor: item.carteira },
                { nome: "Sistema Bancário", valor: item.sistemaBancario },
                { nome: "Indice Multa", valor: `${item.indiceMulta.toFixed(2)}%` },
                { nome: "Indice Julta", valor: `${item.indiceJuros.toFixed(3)}%` },
                { nome: "Local Pagamento", valor: item.localPagamento },
                {
                  nome: "Utiliza Un Centralizadora",
                  valor: item.utilizaUnCentralizadora ? "Sim" : "Não",
                },
                { nome: "Tipo Emissao", valor: item.tipoEmissao },
              ],
              [
                { nome: "Observação 1", valor: item.observacao1 },
                { nome: "Observação 2", valor: item.observacao2 },
                { nome: "Observação 3", valor: item.observacao3 },
                { nome: "Observação 4", valor: item.observacao4 },
                { nome: "Observação 5", valor: item.observacao5 },
              ],
            ];
          },
        }}
        customAction={[
          {
            title: "Ativar convênio",
            icon: (item) =>
              item.id === inativoSelecionadoId ? <Spinner size="sm" /> : <FiCheckCircle />,
            action: (item) => {
              if (!item.dataInativo) {
                notificacao({
                  tipo: "warning",
                  titulo: "Convênio já ativo",
                  message: "Caso deseje desativar o convênio, selecione opção de edição.",
                });
              } else {
                handleConvenioInativoSelecionado(item);
              }
            },
          },
        ]}
        data={data?.result}
        keybuilder={(item) => item.id}
        isFetching={isFetching}
        isError={isError}
        error={error}
        onDelete={(item) => {
          setConvenioBancario(item);
          onDeletarOpen();
        }}
        onEdit={(item) => {
          if (item.dataInativo) {
            notificacao({
              tipo: "warning",
              titulo: "Convênio inativo",
              message:
                "Para editar um convênio, você deve ativá-lo.\nObs: fazer isso irá inativar o convênio ativo da unidade se existir.",
            });
            return;
          }
          setConvenioBancario(item);
          navigate("./editar");
        }}
        totalPages={data?.pageTotal ?? 0}
        itemsPerPage={itemsPerPage}
        currentPage={currentPage}
        setCurrentPage={setCurrentPage}
        totalItems={data?.total ?? 0}
      />
      <ModalConfirma
        titulo="Atenção"
        texto={`Deseja realmente excluir convênio da unidade ${convenioBancario?.idUnidade}, de número ${convenioBancario?.numero}?`}
        onClose={onDeletarClose}
        isOpen={isDeletarOpen}
        onConfirm={handleDeleteConvenioBancario}
        isLoading={deleteConvenioLoading}
      />
    </>
  );
}
