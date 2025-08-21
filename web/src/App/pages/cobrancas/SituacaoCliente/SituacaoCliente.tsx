import { Divider, Grid, useDisclosure } from "@chakra-ui/react";
import { useQuery } from "@tanstack/react-query";
import { useEffect, useState } from "react";
import { FiSearch } from "react-icons/fi";
import { PessoaCr5DTO } from "../../../../models/DTOs/Outros/PessoaCr5DTO.ts";
import { buscaSituacaoCliente } from "../../../../requests/requestsSituacaoCliente.ts";
import queryClient from "../../../../singletons/reactQueryClient.ts";
import Acessos from "../../../../utils/Acessos";
import { trataValorInputNumberVazio } from "../../../../utils/componentUtil.ts";
import { TemposCachePadrao } from "../../../../utils/constantes";
import { formatarDataBrasil_2, formatarMoedaBrasil } from "../../../../utils/mascaras.ts";
import { ButtonControl } from "../../../components/ButoonControl";
import { ButtonPesquisarLimpar } from "../../../components/ButoonControl/ButtonPesquisarLimpar.tsx";
import { CabecalhoPages } from "../../../components/CabecalhoPages";
import { DropBoxMultiOpitions } from "../../../components/DropBoxMultiOpitions/DropBoxMultiOpitions.tsx";
import { DropBoxSistemas } from "../../../components/DropBoxSistemas";
import { DropBoxUnidades } from "../../../components/DropBoxUnidades";
import { InputControl } from "../../../components/InputControl";
import { ModalRelatorio } from "../../../components/ModalRelatorio/ModalRelatorio.tsx";
import { DetalhesLinhaTabela, TabelaCR5 } from "../../../components/Tabelas";
import useCR5Axios from "../../../hooks/useCR5Axios";
import { useFuncaoNotificacao } from "../../../hooks/useFuncaoNotificacao.tsx";
import { useValidaAcessos } from "../../../hooks/useValidaAcessos.ts";
import { ModalPesquisaPessoa } from "../../protheus/ContratoRede/components/ModalPesquisaPessoa.tsx";

export function SituacaoCliente() {
  const { axios } = useCR5Axios();

  const itemsPerPage = 10;

  const {
    onOpen: onOpenModalPesquisaPessoa,
    isOpen: isOpenModalPesquisaPessoa,
    onClose: onCloseModalPesquisaPessoa,
  } = useDisclosure();

  const {
    isOpen: isOpenModalRelatorio,
    onOpen: onOpenModalRelatorio,
    onClose: onCloseModalRelatorio,
  } = useDisclosure();

  const notificacao = useFuncaoNotificacao();
  const [validouPermissao, setValidouPermissao] = useState(false);

  const [invalidando, setInvalidando] = useState(true);
  const [currentPage, setCurrentPage] = useState(1);

  const [idUnidadeBusca, setIdUnidadeBusca] = useState<number | null>(null);
  const [cpfCnpjBusca, setCpfCnpjBusca] = useState("");
  const [contratoBusca, setContratoBusca] = useState<number | null>(null);
  const [parcelaBusca, setParcelaBusca] = useState<number | null>(null);

  const [dtInicioCobrancaBusca, setDtInicioCobrancaBusca] = useState("");
  const [dtFimCobrancaBusca, setDtFimCobrancaBusca] = useState("");

  const [idSistemaBusca, setIdSistemaBusca] = useState<number | null>(null);
  const [nossoNumeroBusca, setNossoNumeroBusca] = useState("");

  const [statusBusca, setStatusBusca] = useState<string[]>([]);

  const [consumidorNomeBusca, setConsumidorNomeBusca] = useState("");
  const [produtoBusca, setProdutoBusca] = useState("");

  const options = [
    { label: "Aberto", value: "ABERTO" },
    { label: "Quitado", value: "PAGO" },
    { label: "Cobrado", value: "COBRADO" },
    { label: "Agrupado", value: "AGRUPADO" },
    { label: "Substituido", value: "SUBSTITUIDO" },
    { label: "Medição", value: "MEDIÇÃO" },
    { label: "Extinguido", value: "EXTINGUIDO" },
  ];

  const [isLimpar, setIsLimpar] = useState(true);

  useValidaAcessos([Acessos.VISUALIZAR_SITUACAO_DO_CLIENTE], () => {
    setValidouPermissao(true);
  });

  function atualizaValorPesquisa<T>(setter: (arg: T) => void, valor: T): void {
    setter(valor);
    setCurrentPage(1);
  }

  const isEnabledCpfCnpj = !!cpfCnpjBusca;

  const { data, isFetching, isError, error, refetch } = useQuery({
    queryKey: [
      "buscaSituacaoCliente",
      currentPage,
      cpfCnpjBusca,
      dtInicioCobrancaBusca,
      dtFimCobrancaBusca,
      idSistemaBusca,
      contratoBusca,
      parcelaBusca,
      nossoNumeroBusca,
      idUnidadeBusca,
      statusBusca,
      consumidorNomeBusca,
      produtoBusca,
    ],
    enabled:
      !invalidando &&
      validouPermissao &&
      isEnabledCpfCnpj &&
      cpfCnpjBusca?.length >= 11 &&
      (contratoBusca === null ||
        (typeof contratoBusca == "number" && contratoBusca.toString().length > 6)) &&
      (nossoNumeroBusca == "" || nossoNumeroBusca.toString().length >= 17) &&
      (produtoBusca == "" || produtoBusca.length >= 4) &&
      (consumidorNomeBusca === "" || consumidorNomeBusca.length >= 4),
    keepPreviousData: true,
    staleTime: TemposCachePadrao.MODERADO,
    cacheTime: TemposCachePadrao.MODERADO,
    queryFn: () => {
      return buscaSituacaoCliente(
        {
          page: currentPage - 1,
          pageSize: itemsPerPage,
          cpfCnpj: cpfCnpjBusca,
          contrato: contratoBusca,
          nossoNumero: nossoNumeroBusca,
          parcela: parcelaBusca,
          idSistema: idSistemaBusca,
          idUnidade: idUnidadeBusca,
          dtInicioCobranca: dtInicioCobrancaBusca ? new Date(dtInicioCobrancaBusca) : null,
          dtFimCobranca: dtFimCobrancaBusca ? new Date(dtFimCobrancaBusca) : null,
          status: statusBusca.join(","),
          consumidorNome: consumidorNomeBusca,
          produto: produtoBusca,
        },
        axios
      );
    },
  });

  function handlePessoaSelecionada(pessoa: PessoaCr5DTO) {
    setCpfCnpjBusca(pessoa.cpfCnpj);

    onCloseModalPesquisaPessoa();
  }

  function validaFiltros(): boolean {
    return cpfCnpjBusca == "";
  }

  useEffect(() => {
    setIsLimpar(validaFiltros());
  }, [cpfCnpjBusca]);

  useEffect(() => {
    setIsLimpar(validaFiltros());
  }, [cpfCnpjBusca]);
  function handleLimparCampo() {
    setCpfCnpjBusca("");
    setContratoBusca(null);
    setNossoNumeroBusca("");
    setProdutoBusca("");
    setConsumidorNomeBusca("");
    queryClient.resetQueries(["buscaSituacaoCliente"]);
    refetch();
  }

  function handlePesquisar() {
    if (validaFiltros()) {
      notificacao({
        tipo: "warning",
        titulo: "Aviso",
        message: "CPF obrigatório !",
      });
    } else {
      setInvalidando(false);

      queryClient.resetQueries(["buscaSituacaoCliente"]);
      refetch();
    }
  }

  const handleOpenModalRelatorio = () => {
    if (!cpfCnpjBusca) {
      notificacao({
        tipo: "warning",
        titulo: "Aviso",
        message: "CPF ou CNPJ obrigatório para listagem.",
      });
      return;
    }

    onOpenModalRelatorio();
  };

  function atualizaValorPesquisaArray<T>(setter: (arg: T[]) => void, valores: T[]): void {
    setter(valores);
    setCurrentPage(1);
  }

  return (
    <>
      <CabecalhoPages titulo="Situação do Cliente" />
      <Grid w="100%" templateColumns={{ base: "¨100%", md: "repeat(12, 1fr)" }} mt={4} p={1}>
        <DropBoxMultiOpitions
          options={options}
          onChange={(valoresSelecionados) =>
            atualizaValorPesquisaArray(setStatusBusca, valoresSelecionados)
          }
          mostrarLabel
          titulo="Status Interface"
          numeroColunasMd={4}
          numeroColunasLg={2}
        />

        <InputControl
          label="Cliente *"
          id="cpfCnpj"
          value={cpfCnpjBusca}
          icon={<FiSearch />}
          onChange={(e) => atualizaValorPesquisa(setCpfCnpjBusca, e.target.value)}
          numeroColunasMd={6}
          numeroColunasLg={2}
          onClick={onOpenModalPesquisaPessoa}
        />
        <InputControl
          label="Data Início Cobrança"
          id="dtInicioCobranca"
          type="date"
          value={dtInicioCobrancaBusca}
          onChange={(e) => setDtInicioCobrancaBusca(e.target.value)}
          numeroColunasMd={6}
          numeroColunasLg={2}
        />
        <InputControl
          label="Data Fim Cobrança"
          id="dtFimCobranca"
          type="date"
          value={dtFimCobrancaBusca}
          onChange={(e) => setDtFimCobrancaBusca(e.target.value)}
          numeroColunasMd={6}
          numeroColunasLg={2}
        />
        <DropBoxSistemas
          onChange={setIdSistemaBusca}
          numeroColunasMd={4}
          numeroColunasLg={2}
          value={idSistemaBusca}
        />

        <InputControl
          label="Contrato *"
          id="contrato"
          value={contratoBusca === null ? "" : contratoBusca.toString()}
          onChange={(event) =>
            trataValorInputNumberVazio((val) => atualizaValorPesquisa(setContratoBusca, val), event)
          }
          numeroColunasMd={4}
          numeroColunasLg={2}
        />

        <InputControl
          label="Parcela Até"
          id="parcela"
          value={parcelaBusca === null ? "" : parcelaBusca.toString()}
          onChange={(event) =>
            trataValorInputNumberVazio((val) => atualizaValorPesquisa(setParcelaBusca, val), event)
          }
          numeroColunasMd={4}
          numeroColunasLg={2}
        />

        <InputControl
          label="Nosso Numero *"
          id="nossoNumero"
          value={nossoNumeroBusca}
          icon={<FiSearch />}
          onChange={(e) => atualizaValorPesquisa(setNossoNumeroBusca, e.target.value)}
          numeroColunasMd={6}
          numeroColunasLg={2}
        />
        <DropBoxUnidades onChange={setIdUnidadeBusca} numeroColunasMd={4} numeroColunasLg={2} />
        <InputControl
          label="Consumidor"
          id="consumidorNome"
          value={consumidorNomeBusca}
          onChange={(e) => atualizaValorPesquisa(setConsumidorNomeBusca, e.target.value)}
          numeroColunasMd={6}
          numeroColunasLg={2}
        />
        <InputControl
          label="Produto"
          id="produto"
          value={produtoBusca}
          onChange={(e) => atualizaValorPesquisa(setProdutoBusca, e.target.value)}
          numeroColunasMd={6}
          numeroColunasLg={2}
        />
        <ButtonPesquisarLimpar
          handlePesquisar={handlePesquisar}
          handleLimparCampo={handleLimparCampo}
          isLimpar={isLimpar}
          numeroColunasMd={1}
          numeroColunasLg={1}
        />
      </Grid>
      <ButtonControl customButtons={[]} onRelatorioClick={handleOpenModalRelatorio} />
      <ModalPesquisaPessoa
        isOpen={isOpenModalPesquisaPessoa}
        onPessoaSelecionada={handlePessoaSelecionada}
        onClose={onCloseModalPesquisaPessoa}
      />

      <ModalRelatorio
        nomeRelatorio="relatorio-situacaoCliente"
        isOpen={isOpenModalRelatorio}
        onClose={onCloseModalRelatorio}
        rota="cliente"
        entidade={2}
        params={{
          page: currentPage - 1,
          pageSize: itemsPerPage,

          cpfCnpj: cpfCnpjBusca,
          contrato: contratoBusca,
          nossoNumero: nossoNumeroBusca,
          parcela: parcelaBusca,
          idSistema: idSistemaBusca,
          idUnidade: idUnidadeBusca,
          dtInicioCobranca: dtInicioCobrancaBusca ? new Date(dtInicioCobrancaBusca) : null,
          dtFimCobranca: dtFimCobrancaBusca ? new Date(dtFimCobrancaBusca) : null,
          status: statusBusca.join(","),
          consumidorNome: consumidorNomeBusca,
          produto: produtoBusca,
        }}
      />

      <TabelaCR5
        cabecalhos={[
          {
            titulo: "Unidade",
            dadoBuilder: (item) => item.codUnidade.toString() + " - " + item.unidadeDescricao,
          },
          {
            titulo: "CPF/CNPJ",
            dadoBuilder: (item) => item.cpfCnpj,
          },
          {
            titulo: "Cliente",
            dadoBuilder: (item) => item.clienteDescricao.toString(),
          },
          {
            titulo: "Data Vigência",
            dadoBuilder: (item) =>
              formatarDataBrasil_2(item.dtInicioCobranca?.toString() || "") +
              " - " +
              formatarDataBrasil_2(item.dtFimCobranca?.toString() || ""),
          },
          {
            titulo: "Status",
            dadoBuilder: (item) => item.statusInterface,
          },
          {
            titulo: "Contrato",
            dadoBuilder: (item) => item.contrato + " - " + item.parcela,
          },
          {
            titulo: "Sistema",
            dadoBuilder: (item) => item.idSistema.toString(),
          },
        ]}
        data={data?.result}
        keybuilder={(item) => item.idCobrancaCliente}
        isFetching={isFetching}
        isError={isError}
        error={error}
        detalhesBuilder={{
          type: "ExpandirCustom",
          buildDetalhes: (item) => (
            <>
              {<Divider />}
              <DetalhesLinhaTabela
                key={item.idCobrancaCliente}
                detalhes={[
                  [
                    { nome: "Nosso Número", valor: item.nossoNumero },
                    { nome: "Valor Cobrança", valor: formatarMoedaBrasil(item.vlCobranca) },
                    {
                      nome: "Valor Pago",
                      valor: formatarMoedaBrasil(item.vlPago?.toString() || "0"),
                    },
                    {
                      nome: "Valor Estorno",
                      valor: formatarMoedaBrasil(item.vlEstorno?.toString() || "0"),
                    },
                    { nome: "Situação", valor: item.cbcSituacao.toString() },
                    { nome: "Rede", valor: item.rede },
                    { nome: "Consumidor", valor: item.consumidorNome },
                  ],
                  [
                    {
                      nome: "Dt. Vencimento",
                      valor: formatarDataBrasil_2(item.dtVencimento?.toString() || ""),
                    },
                    {
                      nome: "Dt. Pagamento",
                      valor: formatarDataBrasil_2(item.dtPagamento?.toString() || ""),
                    },
                    {
                      nome: "Dt. Crédito",
                      valor: formatarDataBrasil_2(item.dtCredito?.toString() || ""),
                    },
                    {
                      nome: "Dt. Estorno",
                      valor: formatarDataBrasil_2(item.dtEstorno?.toString() || ""),
                    },
                    {
                      nome: "Dt. Cancelamento",
                      valor: formatarDataBrasil_2(item.dtCancelamento?.toString() || ""),
                    },
                    { nome: "Objeto", valor: item.objetoContrato },
                  ],
                ]}
              />
            </>
          ),
        }}
        totalPages={data?.pageTotal ?? 0}
        itemsPerPage={itemsPerPage}
        currentPage={currentPage}
        setCurrentPage={setCurrentPage}
        totalItems={data?.total ?? 0}
      />
    </>
  );
}
