import { Box, Center, Divider, Flex, List, ListItem } from "@chakra-ui/react";
import { ParcelaDTO } from "../../../models/DTOs/Cobrancas/ParcelaDTO";
import {
  formatarDataBrasil,
  formatarDataHoraBrasil,
  formatarMoedaBrasil,
  getDescricaoFormaPagamentoSimplificado,
  getDescricaoIntegraProtheus,
  mascaraCpfCnpj,
} from "../../../utils/mascaras";

export function DadosParcela(dados: { parcela: ParcelaDTO }) {
  const parcela = dados.parcela;

  return (
    <Box mt={5} w="100%" pl={8}>
      <Flex flexDirection="row" p={1} justifyContent="space-around">
        <Flex w="25%">
          <List fontSize="xs">
            <ListItem>
              <strong>Contrato:</strong> {parcela.contId}
            </ListItem>
            <ListItem>
              <strong>Sistema:</strong> {parcela.idSistema}
            </ListItem>
            <ListItem>
              <strong>Nº Parcela:</strong>{" "}
              {`${parcela.numeroParcela} / ${parcela.quantidadeDeParcelas}`}
            </ListItem>
            <ListItem>
              <strong>Unidade:</strong> {parcela.codigoUnidade}
            </ListItem>
            <ListItem>
              <strong>Entidade:</strong> {parcela.entidade}
            </ListItem>
          </List>
        </Flex>
        <Flex w="25%">
          <Center height="100px" mr={5}>
            <Divider orientation="vertical" />
          </Center>
          <List fontSize="xs">
            <ListItem>
              <strong>Status Contrato:</strong> {parcela.statusContrato}
            </ListItem>
            <ListItem>
              <strong>Situação Parcela:</strong> {parcela.situacao}
            </ListItem>
            <ListItem>
              <strong>Integra Protheus:</strong>{" "}
              {getDescricaoIntegraProtheus(parcela.integraProtheus)}
            </ListItem>
            <ListItem>
              <strong>Sacado Nome:</strong> {parcela.sacadoNome?.toUpperCase()}
            </ListItem>
            <ListItem>
              <strong>Sacado CPF/CNPJ:</strong> {mascaraCpfCnpj(parcela.sacadoCpfCnpj)}
            </ListItem>
          </List>
        </Flex>
        <Flex w="25%">
          <Center height="100px" mr={5}>
            <Divider orientation="vertical" />
          </Center>
          <List fontSize="xs">
            <ListItem>
              <strong>Valor Cobrança:</strong>{" "}
              {formatarMoedaBrasil(parcela.valorCobranca.toString())}
            </ListItem>
            <ListItem>
              <strong>Valor Pago:</strong> {formatarMoedaBrasil(parcela.valorPago.toString())}
            </ListItem>
            <ListItem>
              <strong>Multa:</strong> {formatarMoedaBrasil(parcela.multa.toString())}
            </ListItem>
            <ListItem>
              <strong>Juros:</strong> {formatarMoedaBrasil(parcela.juros.toString())}
            </ListItem>
            <ListItem>
              <strong>Desconto Vencimento:</strong>{" "}
              {formatarMoedaBrasil(parcela.descontoAteVencimento.toString())}
            </ListItem>
            <ListItem>
              <strong>Desconto Comercial:</strong>{" "}
              {formatarMoedaBrasil(parcela.valorDescontoComercial.toString())}
            </ListItem>
            <ListItem>
              <strong>Forma Pagamento:</strong>{" "}
              {getDescricaoFormaPagamentoSimplificado(parcela.formaPagamentoSimplificado)}
            </ListItem>
          </List>
        </Flex>
        <Flex w="25%">
          <Center height="100px" mr={5}>
            <Divider orientation="vertical" />
          </Center>
          <List fontSize="xs">
            <ListItem>
              <strong>Dt. Geração:</strong> {formatarDataHoraBrasil(parcela.dataGeracao)}
            </ListItem>
            <ListItem>
              <strong>Dt. Vencimento:</strong> {formatarDataBrasil(parcela.dataVencimento)}
            </ListItem>
            <ListItem>
              <strong>Dt. Pagamento:</strong> {formatarDataHoraBrasil(parcela.dataPagamento)}
            </ListItem>
            <ListItem>
              <strong>Integração Baixa:</strong>{" "}
              {formatarDataHoraBrasil(parcela.dataAlteracaoProtheus)}
            </ListItem>
          </List>
        </Flex>
      </Flex>
    </Box>
  );
}
