import { Divider, Grid } from "@chakra-ui/react";
import { useQuery } from "@tanstack/react-query";
import { useState } from "react";
import { buscaPagamentosNaoBaixados } from "../../../../requests/requestsPagamentoNaoBaixado";
import Acessos from "../../../../utils/Acessos";
import { trataValorInputNumberVazio } from "../../../../utils/componentUtil";
import { DelaysDebouncePadrao } from "../../../../utils/constantes";
import { generateRandomKey } from "../../../../utils/keyRandon";
import {
  formatarDataHoraBrasil,
  formatarMoedaBrasil,
  getDescricaoFormaPagamentoSimplificado,
  mascaraCpfCnpj,
} from "../../../../utils/mascaras";
import { CabecalhoPages } from "../../../components/CabecalhoPages";
import { DropBoxUnidades } from "../../../components/DropBoxUnidades";
import { InputControl } from "../../../components/InputControl";
import { DetalhesLinhaTabela, TabelaCR5 } from "../../../components/Tabelas";
import useCR5Axios from "../../../hooks/useCR5Axios";
import useDebounce from "../../../hooks/useDebounce";
import { useValidaAcessos } from "../../../hooks/useValidaAcessos";

// TODO validar permissão
export function PagamentosNaoBaixados() {
  const [validouPermissao, setValidouPermissao] = useState(false);
  const delayDebounce = DelaysDebouncePadrao.PADRAO;
  const { axios } = useCR5Axios();

  useValidaAcessos([Acessos.ADMINISTRAR_FINANCIAMENTO], () => {
    setValidouPermissao(true);
  });

  const hoje = new Date();
  const umMesAtras = new Date(hoje.getTime() - 30 * 24 * 60 * 60 * 1000);

  const [idUnidadeBusca, setIdUnidadeBusca] = useState<number | null>(null);
  const [valorMaximoBusca, setValorMaximoBusca] = useState<number | null>(null);
  const [dataInicialBusca, setDataInicialBusca] = useState(
    umMesAtras.toISOString().substring(0, 10)
  );
  const [dataFinalBusca, setDataFinalBusca] = useState("");

  const [valorMaximoDebounce] = useDebounce(valorMaximoBusca, delayDebounce);

  const { data, isFetching, isError, error } = useQuery({
    queryKey: [
      "buscaPagamentosNaoBaixados",
      idUnidadeBusca,
      valorMaximoDebounce,
      dataInicialBusca,
      dataFinalBusca,
    ],
    enabled: validouPermissao,
    queryFn: () => {
      return buscaPagamentosNaoBaixados(
        {
          idUnidade: idUnidadeBusca,
          valorMaximo: valorMaximoBusca,
          dataInicial: dataInicialBusca ? new Date(dataInicialBusca) : null,
          dataFinal: dataFinalBusca ? new Date(dataFinalBusca) : null,
        },
        axios
      );
    },
  });

  return (
    <>
      <CabecalhoPages titulo="Pagamentos não Baixados" />
      <Grid w="100%" templateColumns={{ base: "¨100%", md: "repeat(12, 1fr)" }} mt={4} p={1}>
        <InputControl
          label="Data Pagto Inicial"
          id="dataInicial"
          type="date"
          value={dataInicialBusca}
          onChange={(e) => setDataInicialBusca(e.target.value)}
          numeroColunasMd={6}
          numeroColunasLg={2}
        />
        <InputControl
          label="Data Pagto Final"
          id="dataFinal"
          type="date"
          value={dataFinalBusca}
          onChange={(e) => setDataFinalBusca(e.target.value)}
          numeroColunasMd={6}
          numeroColunasLg={2}
        />
        <InputControl
          label="Valor máximo"
          id="valorVaximo"
          type="number"
          value={valorMaximoBusca?.toString() ?? ""}
          onChange={(e) => trataValorInputNumberVazio(setValorMaximoBusca, e)}
          numeroColunasMd={6}
          numeroColunasLg={2}
        />
        <DropBoxUnidades onChange={setIdUnidadeBusca} numeroColunasMd={6} numeroColunasLg={6} />
      </Grid>
      <TabelaCR5
        cabecalhos={[
          {
            titulo: "Unidade",
            dadoBuilder: (item) => item.unidade.nome,
          },
          {
            titulo: "Código Unidade",
            dadoBuilder: (item) => item.unidade.codigo.toString(),
          },
          {
            titulo: "Forma de Pagamento",
            dadoBuilder: (item) =>
              getDescricaoFormaPagamentoSimplificado(item.pagamentoNaoBaixado.formaPagamento),
          },
          {
            titulo: "Data Pagamento",
            dadoBuilder: (item) => formatarDataHoraBrasil(item.pagamentoNaoBaixado.dataPagamento),
          },
          {
            titulo: "Valor",
            dadoBuilder: (item) => formatarMoedaBrasil(item.pagamentoNaoBaixado.valorOperacao),
          },
          {
            titulo: "Nome Sacado",
            dadoBuilder: (item) => item.pagamentoNaoBaixado.sacadoNome,
          },
          {
            titulo: "CPF/CNPJ Sacado",
            dadoBuilder: (item) => mascaraCpfCnpj(item.pagamentoNaoBaixado.sacadoCpfCnpj),
          },
          {
            titulo: "Parcelamento",
            dadoBuilder: (item) => item.pagamentoNaoBaixado.parcelamento.toString(),
          },
        ]}
        data={data}
        keybuilder={(item) =>
          generateRandomKey() +
          `${item.pagamentoNaoBaixado.idPedido}@${item.pagamentoNaoBaixado.dataPagamento}`
        }
        isError={isError}
        isFetching={isFetching}
        error={error}
        detalhesBuilder={{
          type: "ExpandirCustom",
          buildDetalhes: (item) => (
            <>
              {item.pagamentoNaoBaixado.cobrancas.map((cobranca, index) => (
                <>
                  {index !== 0 && <Divider />}
                  <DetalhesLinhaTabela
                    key={cobranca.idCobrancaCliente + generateRandomKey()}
                    detalhes={[
                      [
                        { nome: "Contrato", valor: cobranca.contId.toString() },
                        { nome: "Sistema", valor: cobranca.idSistema.toString() },
                        { nome: "Parcela", valor: cobranca.numeroParcela.toString() },
                      ],
                      [
                        {
                          nome: "Valor Cobrança",
                          valor: formatarMoedaBrasil(cobranca.valorCobranca),
                        },
                        { nome: "Situação Parcela", valor: cobranca.situacaoParcela },
                        { nome: "Situação Contrato", valor: cobranca.statusContrato },
                      ],
                    ]}
                  />
                </>
              ))}
            </>
          ),
        }}
      />
    </>
  );
}
