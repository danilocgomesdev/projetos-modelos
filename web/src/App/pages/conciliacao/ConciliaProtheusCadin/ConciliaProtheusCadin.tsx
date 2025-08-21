import { Grid, useDisclosure } from "@chakra-ui/react";
import { useQuery } from "@tanstack/react-query";
import { useState } from "react";
import { ParcelaDTO } from "../../../../models/DTOs/Cobrancas/ParcelaDTO";
import { integraProtheusValues } from "../../../../models/DTOs/Protheus/IntegraProtheus";
import { fetchParcelasPagasCadinPaginado } from "../../../../requests/requestsCadin";
import Acessos from "../../../../utils/Acessos";
import { trataValorInputNumberVazio } from "../../../../utils/componentUtil";
import { DelaysDebouncePadrao, TemposCachePadrao } from "../../../../utils/constantes";
import {
  formatarDataBrasil,
  formatarDataHoraBrasil,
  formatarMoedaBrasil,
  getDescricaoFormaPagamentoSimplificado,
  getDescricaoIntegraProtheus,
  mascaraCpfCnpj,
} from "../../../../utils/mascaras";
import { CabecalhoPages } from "../../../components/CabecalhoPages";
import { DropBoxFormaPagamentoSimplificada } from "../../../components/DropBoxFormaPagamentoSimplificada";
import { FiltroEntidadeUnidade } from "../../../components/FiltroEntidadeUnidade";
import { InputControl } from "../../../components/InputControl";
import { SelectControl } from "../../../components/SelectControl";
import { TabelaCR5 } from "../../../components/Tabelas";
import useCR5Axios from "../../../hooks/useCR5Axios";
import useDebounce from "../../../hooks/useDebounce";
import { useValidaAcessos } from "../../../hooks/useValidaAcessos";
import { ModalDetalhesCobrancaCadin } from "./components/ModalDetalhesCobrancaCadin";

export function ConciliaProtheusCadin() {
  const itemsPerPage = 15;
  const delayDebounce = DelaysDebouncePadrao.PADRAO;
  const [currentPage, setCurrentPage] = useState(1);
  const { axios } = useCR5Axios();

  const [dataInicialBusca, setDataInicialBusca] = useState("");
  const [dataFinalBusca, setDataFinalBusca] = useState("");
  const [dataVencInicialBusca, setDataVencInicialBusca] = useState("");
  const [dataVencFinalBusca, setDataVencFinalBusca] = useState("");
  const [sacadoNomeBusca, setSacadoNomeBusca] = useState("");
  const [sacadoCpfCnpjBusca, setSacadoCpfCnpjBusca] = useState("");
  const [numeroParcelaBusca, setNumeroParcelaBusca] = useState<number | null>(null);
  const [contIdBusca, setContIdBusca] = useState<number | null>(null);
  const [nossoNumeroBusca, setNossoNumeroBusca] = useState("");
  const [baixaIntegradaBusca, setBaixaIntegradaBusca] = useState<boolean | null>(null);
  const [idUnidadeBusca, setIdUnidadeBusca] = useState<number | null>(null);
  const [idEntidadeBusca, setIdEntidadeBusca] = useState<number | null>(null);
  const [formaPagamentoBusca, setFormaPagamentoBusca] = useState<string | null>("");
  const [integraProtheusBusca, setIntegraProtheusBusca] = useState<string | null>("");

  const [sacadoNomeDebounce] = useDebounce(sacadoNomeBusca, delayDebounce);
  const [sacadoCpfCnpjDebounce] = useDebounce(sacadoCpfCnpjBusca, delayDebounce);
  const [numeroParcelaDebounce] = useDebounce(numeroParcelaBusca, delayDebounce);
  const [contIdDebounce] = useDebounce(contIdBusca, delayDebounce);
  const [nossoNumeroDebounce] = useDebounce(nossoNumeroBusca, delayDebounce);
  const { isOpen, onOpen, onClose } = useDisclosure();

  const [dadosContrato, setDadosContrato] = useState<ParcelaDTO>();
  const [validouPermissao, setValidouPermissao] = useState(false);

  useValidaAcessos([Acessos.CONCILIACAO_CR5_X_PROTHEUS], () => {
    setValidouPermissao(true);
  });

  const isEnabled =
    !!sacadoNomeDebounce ||
    !!sacadoCpfCnpjDebounce ||
    !!contIdDebounce ||
    !!nossoNumeroDebounce ||
    (!!dataInicialBusca && !!dataFinalBusca) ||
    (!!dataVencInicialBusca && !!dataVencFinalBusca);

  const { data, isFetching, isError, error } = useQuery({
    queryKey: [
      "fetchProtheusCadinPaginado",
      currentPage,
      dataInicialBusca,
      dataFinalBusca,
      sacadoNomeDebounce,
      sacadoCpfCnpjDebounce,
      numeroParcelaDebounce,
      contIdDebounce,
      nossoNumeroDebounce,
      baixaIntegradaBusca,
      idUnidadeBusca,
      idEntidadeBusca,
      formaPagamentoBusca,
      integraProtheusBusca,
      dataVencInicialBusca,
      dataVencFinalBusca,
    ],
    enabled: validouPermissao && isEnabled,
    keepPreviousData: true,
    staleTime: TemposCachePadrao.CURTO,
    cacheTime: TemposCachePadrao.CURTO,
    retry: false,
    queryFn: () => {
      return fetchParcelasPagasCadinPaginado(
        {
          page: currentPage - 1,
          pageSize: itemsPerPage,
          dataInicial: dataInicialBusca || null,
          dataFinal: dataFinalBusca || null,
          dataVencimentoInicial: new Date(dataVencInicialBusca),
          dataVencimentoFinal: new Date(dataVencFinalBusca),
          sacadoNome: sacadoNomeDebounce,
          sacadoCpfCnpj: sacadoCpfCnpjDebounce,
          numeroParcela: numeroParcelaDebounce,
          contId: contIdDebounce,
          nossoNumero: nossoNumeroDebounce,
          baixaIntegrada: baixaIntegradaBusca,
          idUnidade: idUnidadeBusca,
          idEntidade: idEntidadeBusca,
          formaPagamento: formaPagamentoBusca || null,
          integraProtheus: integraProtheusBusca || null,
        },
        axios
      );
    },
  });

  function handleChangeDetalhes(dados: ParcelaDTO) {
    setDadosContrato(dados);
    onOpen();
  }

  function atualizaValorPesquisa<T>(setter: (arg: T) => void, valor: T): void {
    setter(valor);
    setCurrentPage(1);
  }

  return (
    <>
      <CabecalhoPages titulo="Concilia Protheus Cadin - Contratos 25" />
      <Grid w="100%" templateColumns={{ base: "100%", md: "repeat(12, 1fr)" }} mt={4}>
        <InputControl
          label="Data Pagamento Inicial"
          id="dataInicial"
          type="date"
          value={dataInicialBusca}
          onChange={(e) => atualizaValorPesquisa(setDataInicialBusca, e.target.value)}
          numeroColunasMd={4}
          numeroColunasLg={2}
        />
        <InputControl
          label="Data Pagamento Final"
          id="dataFinal"
          type="date"
          value={dataFinalBusca}
          onChange={(e) => atualizaValorPesquisa(setDataFinalBusca, e.target.value)}
          numeroColunasMd={4}
          numeroColunasLg={2}
        />
        <InputControl
          label="Contrato"
          id="contId"
          type="number"
          value={contIdBusca?.toString() ?? ""}
          onChange={(e) =>
            trataValorInputNumberVazio((val) => atualizaValorPesquisa(setContIdBusca, val), e)
          }
          numeroColunasMd={4}
          numeroColunasLg={2}
        />
        <InputControl
          label="Nº Parcela"
          id="numeroParcela"
          type="number"
          value={numeroParcelaBusca?.toString() ?? ""}
          onChange={(e) =>
            trataValorInputNumberVazio(
              (val) => atualizaValorPesquisa(setNumeroParcelaBusca, val),
              e
            )
          }
          numeroColunasMd={4}
          numeroColunasLg={2}
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
          label="Data Venc. Inicial"
          id="dataVencInicial"
          type="date"
          value={dataVencInicialBusca}
          onChange={(e) => atualizaValorPesquisa(setDataVencInicialBusca, e.target.value)}
          numeroColunasMd={6}
          numeroColunasLg={2}
        />
        <InputControl
          label="Data Venc. Final"
          id="dataVencFinal"
          type="date"
          value={dataVencFinalBusca}
          onChange={(e) => atualizaValorPesquisa(setDataVencFinalBusca, e.target.value)}
          numeroColunasMd={6}
          numeroColunasLg={2}
        />
        <InputControl
          label="Nosso Número"
          id="nossoNumero"
          type="number"
          value={nossoNumeroBusca}
          onChange={(e) =>
            trataValorInputNumberVazio(
              (val) => atualizaValorPesquisa(setNossoNumeroBusca, val?.toString() || ""),
              e
            )
          }
          numeroColunasMd={6}
          numeroColunasLg={2}
        />
        <SelectControl
          label="Baixa Integrada"
          id="baixaIntegrada"
          options={[
            {
              value: "true",
              label: "Baixados",
            },
            {
              value: "false",
              label: "Não baixados",
            },
          ]}
          onChange={(e) =>
            atualizaValorPesquisa(
              setBaixaIntegradaBusca,
              e === "true" ? true : e === "false" ? false : null
            )
          }
          numeroColunasMd={6}
          numeroColunasLg={2}
        />
        <DropBoxFormaPagamentoSimplificada
          numeroColunasMd={6}
          numeroColunasLg={4}
          onChange={(forma) => atualizaValorPesquisa(setFormaPagamentoBusca, forma)}
        />
        <SelectControl
          label="Integra Protheus"
          id="integraProtheus"
          numeroColunasMd={6}
          numeroColunasLg={4}
          options={[
            {
              value: integraProtheusValues.CONTRATO_PROTHEUS_25.nomeEnum,
              label: integraProtheusValues.CONTRATO_PROTHEUS_25.descricao,
            },
            {
              value: integraProtheusValues.INTEGRACAO_FINANCEIRA.nomeEnum,
              label: integraProtheusValues.INTEGRACAO_FINANCEIRA.descricao,
            },
          ]}
          onChange={(e) => atualizaValorPesquisa(setIntegraProtheusBusca, e)}
        />
        <InputControl
          numeroColunasMd={6}
          numeroColunasLg={4}
          label="Sacado Nome"
          id="sacadoNome"
          type="text"
          value={sacadoNomeBusca}
          onChange={(e) => atualizaValorPesquisa(setSacadoNomeBusca, e.target.value)}
        />
        <InputControl
          numeroColunasMd={6}
          numeroColunasLg={2}
          label="Sacado CPF/CNPJ"
          id="sacadoCpfCnpj"
          type="text"
          value={sacadoCpfCnpjBusca}
          onChange={(e) =>
            atualizaValorPesquisa(setSacadoCpfCnpjBusca, e.target.value.replace(/\D/g, ""))
          }
        />
      </Grid>
      <TabelaCR5
        cabecalhos={[
          { titulo: "CONTRATO", dadoBuilder: (item) => item.contId.toString(), tamanho: "90px" },
          {
            titulo: "PARCELA",
            dadoBuilder: (item) => `${item.numeroParcela}/${item.quantidadeDeParcelas}`,
            tamanho: "120px",
          },
          { titulo: "UNIDADE", dadoBuilder: (item) => item.codigoUnidade, tamanho: "80px" },
          { titulo: "ENTIDADE", dadoBuilder: (item) => item.entidade, tamanho: "100px" },
          {
            titulo: "STATUS CONTRATO",
            dadoBuilder: (item) => item.statusContrato,
            tamanho: "140px",
          },
          {
            titulo: "INTEGRA PROTHEUS",
            dadoBuilder: (item) => getDescricaoIntegraProtheus(item.integraProtheus),
            tamanho: "190px",
          },
          { titulo: "SACADO NOME", dadoBuilder: (item) => item.sacadoNome, tamanho: "350px" },
          {
            titulo: "SACADO CPF/CNPJ",
            dadoBuilder: (item) => mascaraCpfCnpj(item.sacadoCpfCnpj),
            tamanho: "160px",
          },
          {
            titulo: "VALOR CONTRATO",
            dadoBuilder: (item) =>
              formatarMoedaBrasil(item.valorCobranca * item.quantidadeDeParcelas),
            tamanho: "100px",
          },
          {
            titulo: "VALOR COBRADO",
            dadoBuilder: (item) => formatarMoedaBrasil(item.valorCobranca),
            tamanho: "100px",
          },
          {
            titulo: "VALOR PAGO",
            dadoBuilder: (item) => formatarMoedaBrasil(item.valorPago),
            tamanho: "100px",
          },
          {
            titulo: "DT. VENCIMENTO",
            dadoBuilder: (item) => formatarDataBrasil(item.dataVencimento),
            tamanho: "150px",
          },
          {
            titulo: "DT. PAGAMENTO",
            dadoBuilder: (item) => formatarDataHoraBrasil(item.dataPagamento),
            tamanho: "150px",
          },
          {
            titulo: "FORMA PAGAMENTO",
            dadoBuilder: (item) =>
              getDescricaoFormaPagamentoSimplificado(item.formaPagamentoSimplificado),
            tamanho: "150px",
          },
          {
            titulo: "DT. BAIXA PROTHEUS",
            dadoBuilder: (item) => formatarDataHoraBrasil(item.dataAlteracaoProtheus),
            tamanho: "150px",
          },
        ]}
        data={data?.result}
        keybuilder={(item) => item.idCobrancaCliente}
        isError={isError}
        isFetching={isFetching}
        error={error}
        detalhesBuilder={{ type: "OnClick", onClick: handleChangeDetalhes }}
        totalPages={data?.pageTotal ?? 0}
        itemsPerPage={itemsPerPage}
        currentPage={currentPage}
        setCurrentPage={setCurrentPage}
        totalItems={data?.total ?? 0}
      />
      <ModalDetalhesCobrancaCadin
        isOpen={isOpen}
        onClose={onClose}
        idCobrancaCliente={dadosContrato?.idCobrancaCliente || null}
      />
    </>
  );
}
