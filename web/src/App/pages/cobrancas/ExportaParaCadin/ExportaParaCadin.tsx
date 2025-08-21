import { Grid, useDisclosure } from "@chakra-ui/react";
import { useMutation, useQuery } from "@tanstack/react-query";
import { useState } from "react";
import { FiSearch } from "react-icons/fi";
import { PessoaCr5DTO } from "../../../../models/DTOs/Outros/PessoaCr5DTO.ts";
import {
  fetchExportarIdCobrancasClientes,
  fetchExportarParaCadinPaginado,
} from "../../../../requests/requestsExportarParaCadin.ts";
import queryClient from "../../../../singletons/reactQueryClient.ts";
import Acessos from "../../../../utils/Acessos.ts";
import { trataValorInputNumberVazio } from "../../../../utils/componentUtil.ts";
import { DelaysDebouncePadrao, TemposCachePadrao } from "../../../../utils/constantes.ts";
import { getMensagemDeErroOuProcureSuporte } from "../../../../utils/errors.ts";
import {
  formatarDataBrasil,
  formatarDataBrasilTimestamp,
  formatarMoedaBrasil,
  mascaraCpfCnpj,
} from "../../../../utils/mascaras.ts";
import { ButtonPesquisarLimpar } from "../../../components/ButoonControl/ButtonPesquisarLimpar.tsx";
import { CabecalhoPages } from "../../../components/CabecalhoPages/index.tsx";
import { DropBoxSistemas } from "../../../components/DropBoxSistemas/index.ts";
import { InputControl } from "../../../components/InputControl/index.tsx";
import { ModalConfirma } from "../../../components/ModalConfirma/index.tsx";
import { TabelaCR5 } from "../../../components/Tabelas/index.ts";
import useCR5Axios from "../../../hooks/useCR5Axios.ts";
import useDebounce from "../../../hooks/useDebounce.ts";
import { useFuncaoNotificacao } from "../../../hooks/useFuncaoNotificacao.tsx";
import { useValidaAcessos } from "../../../hooks/useValidaAcessos.ts";
import { ModalPesquisaPessoa } from "../../protheus/ContratoRede/components/ModalPesquisaPessoa.tsx";
import { useIdCobrancasClientesStore } from "./store/IdCobrancasClientesStore.tsx";

export function ExportaParaCadin() {
  const { axios } = useCR5Axios();
  const itemsPerPage = 10;
  const delayDebounce = DelaysDebouncePadrao.PADRAO;
  const { isOpen, onOpen, onClose } = useDisclosure();

  const {
    onOpen: onOpenModalPesquisaPessoa,
    isOpen: isOpenModalPesquisaPessoa,
    onClose: onCloseModalPesquisaPessoa,
  } = useDisclosure();

  const [invalidando, setInvalidando] = useState(true);
  const [currentPage, setCurrentPage] = useState(1);

  const [cpfCnpjBusca, setCpfCnpjBusca] = useState("");
  const [nomeBusca, setNomeBusca] = useState("");
  const [dtVencimentoInicioBusca, setDtVencimentoInicioBusca] = useState("");
  const [dtVencimentoFimBusca, setDtVencimentoFimBusca] = useState("");
  const [contratoBusca, setContratoBusca] = useState<number | null>(null);
  const [sistemaBusca, setSistemaBusca] = useState<number | null>(null);
  const [nossoNumeroBusca, setNossoNumeroBusca] = useState("");

  const [cpfCnpjDebounce] = useDebounce(cpfCnpjBusca, delayDebounce);
  const [nomeDebounce] = useDebounce(nomeBusca, delayDebounce);
  const [contratoDebounce] = useDebounce(contratoBusca, delayDebounce);
  const [nossoNumeroDebounce] = useDebounce(nossoNumeroBusca, delayDebounce);
  const [dtVencimentoFimDebounce] = useDebounce(dtVencimentoFimBusca, delayDebounce);
  const [sistemaDebounce] = useDebounce(sistemaBusca, delayDebounce);
  const notificacao = useFuncaoNotificacao();
  const [validouPermissao, setValidouPermissao] = useState(false);

  const { idCobrancasClientes, setIdCobrancasClientes } = useIdCobrancasClientesStore();

  useValidaAcessos([Acessos.EXPORTAR_PARA_CADIN], () => {
    setValidouPermissao(true);
    queryClient
      .invalidateQueries({ queryKey: ["fetchExportarParaCadinPaginado"] })
      .then(() => setInvalidando(false));
  });

  const { data, refetch, error, isError, isFetching } = useQuery({
    queryKey: [
      "fetchExportarParaCadinPaginado",
      currentPage,
      nomeDebounce,
      cpfCnpjDebounce,
      contratoDebounce,
      nossoNumeroDebounce,
      dtVencimentoFimDebounce,
      sistemaDebounce,
    ],
    keepPreviousData: true,
    enabled: !invalidando && validouPermissao,
    staleTime: TemposCachePadrao.CURTO,
    cacheTime: TemposCachePadrao.CURTO,
    queryFn: () => {
      return fetchExportarParaCadinPaginado(
        {
          page: currentPage - 1,
          pageSize: itemsPerPage,
          cpfCnpj: cpfCnpjDebounce,
          nome: nomeDebounce,
          contrato: contratoDebounce,
          nossoNumero: nossoNumeroDebounce,
          dtVencimentoInicio: dtVencimentoInicioBusca,
          dtVencimentoFim: dtVencimentoFimDebounce,
          sistema: sistemaDebounce,
        },
        axios
      );
    },
  });

  function atualizaValorPesquisa<T>(setter: (arg: T) => void, valor: T): void {
    setter(valor);
    setCurrentPage(1);
  }

  const { mutate, isLoading: isLoadingExportarCadin } = useMutation(
    (idCobrancasClientes: number) => {
      return fetchExportarIdCobrancasClientes({ idCobrancasClientes: idCobrancasClientes }, axios);
    },
    {
      onSuccess: () => {
        notificacao({ message: "Exportado com sucesso!", tipo: "success", titulo: "SUCESSO!" });
        queryClient
          .invalidateQueries({ queryKey: ["fetchExportarParaCadinPaginado"] })
          .then(() => refetch());
        onClose();
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

  function handleExporta() {
    if (idCobrancasClientes) {
      mutate(idCobrancasClientes.idCobrancasClientes);
      close();
    }
  }

  function handlePessoaSelecionada(pessoa: PessoaCr5DTO) {
    setNomeBusca(pessoa.descricao);
    setCpfCnpjBusca(pessoa.cpfCnpj);

    onCloseModalPesquisaPessoa();
  }

  function handleLimparCampo() {
    setNomeBusca("");
    setCpfCnpjBusca("");
    setDtVencimentoInicioBusca("");
    setDtVencimentoFimBusca("");
    setContratoBusca(null);
    setSistemaBusca(null);
    setNossoNumeroBusca("");
  }

  return (
    <>
      <CabecalhoPages titulo="Exportar Para Cadin" />
      <Grid w="100%" templateColumns={{ base: "¨100%", md: "repeat(12, 1fr)" }} mt={4} p={1}>
        <InputControl
          label="Cliente"
          id="nome"
          value={nomeBusca}
          icon={<FiSearch />}
          onChange={(e) => atualizaValorPesquisa(setNomeBusca, e.target.value)}
          numeroColunasMd={8}
          numeroColunasLg={4}
          onClick={onOpenModalPesquisaPessoa}
        />
        <InputControl
          label="CPF/CNPJ"
          id="cpfCnpj"
          value={cpfCnpjBusca}
          onChange={(e) => atualizaValorPesquisa(setCpfCnpjBusca, e.target.value)}
          numeroColunasMd={4}
          numeroColunasLg={2}
        />
        <InputControl
          label="Vencimento Início"
          id="dtVencimentoInicio"
          type="date"
          value={dtVencimentoInicioBusca}
          onChange={(e) => atualizaValorPesquisa(setDtVencimentoInicioBusca, e.target.value)}
          numeroColunasMd={4}
          numeroColunasLg={2}
        />
        <InputControl
          label="Vencimento Fim"
          id="dtVencimentoFim"
          type="date"
          value={dtVencimentoFimBusca}
          onChange={(e) => atualizaValorPesquisa(setDtVencimentoFimBusca, e.target.value)}
          numeroColunasMd={4}
          numeroColunasLg={2}
        />
        <InputControl
          label="Contrato"
          id="contrato"
          value={contratoBusca === null ? "" : contratoBusca.toString()}
          onChange={(event) =>
            trataValorInputNumberVazio((val) => atualizaValorPesquisa(setContratoBusca, val), event)
          }
          numeroColunasMd={4}
          numeroColunasLg={2}
        />
        <DropBoxSistemas
          onChange={setSistemaBusca}
          numeroColunasMd={6}
          numeroColunasLg={4}
          value={sistemaBusca}
        />
        <InputControl
          label="Nosso Número"
          id="nossoNumero"
          value={nossoNumeroBusca}
          onChange={(e) => atualizaValorPesquisa(setNossoNumeroBusca, e.target.value)}
          numeroColunasMd={6}
          numeroColunasLg={2}
        />
        <ButtonPesquisarLimpar
          handleLimparCampo={handleLimparCampo}
          numeroColunasMd={6}
          numeroColunasLg={2}
        />
      </Grid>

      <TabelaCR5
        cabecalhos={[
          { titulo: "STATUS", dadoBuilder: (item) => item.status },
          { titulo: "SITUACAO", dadoBuilder: (item) => item.situacao },
          { titulo: "UNIDADE", dadoBuilder: (item) => item.unidade },
          { titulo: "CONTRATO", dadoBuilder: (item) => item.contrato.toString() },
          { titulo: "PARCELA", dadoBuilder: (item) => item.parcela.toString() },
          { titulo: "SISTEMA", dadoBuilder: (item) => item.sistema.toString() },
          {
            titulo: "VENCIMENTO",
            dadoBuilder: (item) => formatarDataBrasil(item.dataVencimento),
          },
          { titulo: "CPF/CNPJ", dadoBuilder: (item) => mascaraCpfCnpj(item.cpfCnpj) },
          { titulo: "NOME", dadoBuilder: (item) => item.nome.toUpperCase() },
          {
            titulo: "VALOR",
            dadoBuilder: (item) => formatarMoedaBrasil(item.valorCobranca.toString()),
          },
          { titulo: "NF", dadoBuilder: (item) => item.numeroNotaFiscal },
          {
            titulo: "Data NF",
            dadoBuilder: (item) => formatarDataBrasilTimestamp(item.dataNotaFiscal),
          },
        ]}
        data={data?.result}
        keybuilder={(item) => item.nossoNumero}
        isFetching={isFetching}
        isError={isError}
        error={error}
        onEdit={(item) => {
          setIdCobrancasClientes(item);
          onOpen();
        }}
        totalPages={data?.pageTotal ?? 0}
        itemsPerPage={itemsPerPage}
        currentPage={currentPage}
        setCurrentPage={setCurrentPage}
        totalItems={data?.total ?? 0}
      />
      <ModalPesquisaPessoa
        isOpen={isOpenModalPesquisaPessoa}
        onPessoaSelecionada={handlePessoaSelecionada}
        onClose={onCloseModalPesquisaPessoa}
      />
      <ModalConfirma
        titulo="Atenção"
        texto={`Deseja Exportar para o Cadin id:  ${idCobrancasClientes?.idCobrancasClientes}?`}
        onClose={onClose}
        isOpen={isOpen}
        onConfirm={handleExporta}
        isLoading={isLoadingExportarCadin}
      />
    </>
  );
}
