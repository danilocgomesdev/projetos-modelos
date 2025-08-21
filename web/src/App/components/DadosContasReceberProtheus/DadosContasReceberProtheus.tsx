import { AbsoluteCenter, Box, Center, Divider, Flex, List, ListItem, Text } from "@chakra-ui/react";
import { ContasAReceberProtheusDTO } from "../../../models/DTOs/Protheus/ContasAReceberProtheusDTO";
import { formatarDataBrasil, formatarMoedaBrasil } from "../../../utils/mascaras";

interface DadosContratoProps {
  dados: ContasAReceberProtheusDTO;
  bgLabelBaixas?: string;
}

export function DadosContasReceberProtheus({ dados, bgLabelBaixas }: DadosContratoProps) {
  const parcelas = dados;
  return (
    <>
      <Box mt={5} w="100%" pl={8}>
        <Flex flexDirection="row" p={1} justifyContent="space-around">
          <Flex w="33%">
            <List fontSize="xs">
              <ListItem>
                <strong>Recno:</strong> {parcelas.recno}
              </ListItem>
              <ListItem>
                <strong>Id Interface:</strong> {parcelas.idInterface}
              </ListItem>
              <ListItem>
                <strong>Título:</strong> {parcelas.titulo}
              </ListItem>
              <ListItem>
                <strong>Parcela:</strong> {parcelas.parcela}
              </ListItem>
              <ListItem>
                <strong>Sistema:</strong> {parcelas.sistema}
              </ListItem>
            </List>
          </Flex>

          <Flex w="33%">
            <Center height="100px" mr={5}>
              <Divider orientation="vertical" />
            </Center>
            <List fontSize="xs">
              <ListItem>
                <strong>Cobrança:</strong> {formatarMoedaBrasil(parcelas.cobranca.toString())}
              </ListItem>
              <ListItem>
                <strong>Valor Liquidado:</strong>{" "}
                {formatarMoedaBrasil(parcelas.valorLiquidado.toString())}
              </ListItem>
              <ListItem>
                <strong>Saldo:</strong> {formatarMoedaBrasil(parcelas.saldo.toString())}
              </ListItem>
              <ListItem>
                <strong>Cliente:</strong> {parcelas.cliente}
              </ListItem>
            </List>
          </Flex>

          <Flex w="33%">
            <Center height="100px" mr={5}>
              <Divider orientation="vertical" />
            </Center>
            <List fontSize="xs">
              <ListItem>
                <strong>Histórico:</strong> {parcelas.historico}
              </ListItem>
              <ListItem>
                <strong>Data Emissão:</strong> {formatarDataBrasil(parcelas.dataEmissao)}
              </ListItem>
              <ListItem>
                <strong>Data Pagamento:</strong> {formatarDataBrasil(parcelas.dataBaixa)}
              </ListItem>
            </List>
          </Flex>
        </Flex>

        <Box>
          <Box position="relative" p={2}>
            <Divider />
            <AbsoluteCenter px="4" fontSize="sm" bg={bgLabelBaixas}>
              Movimentação Bancária - Baixas
            </AbsoluteCenter>
          </Box>
          {parcelas.baixas && parcelas.baixas.length > 0 ? (
            parcelas.baixas.map((b, i) => (
              <>
                <Flex key={b.recno} flexDirection="row" justifyContent="space-around" mt={2}>
                  <Flex w="33%">
                    <List fontSize="xs">
                      <ListItem>
                        <strong>Recno:</strong> {b.recno}
                      </ListItem>
                      <ListItem>
                        <strong>Histórico:</strong> {b.historico}
                      </ListItem>
                      <ListItem>
                        <strong>Motivo Baixa:</strong> {b.motivoBaixa}
                      </ListItem>
                      <ListItem>
                        <strong>Tipo Baixa:</strong> {b.tipoBaixa}
                      </ListItem>
                    </List>
                  </Flex>

                  <Flex w="33%">
                    <Center height="100px" mr={5}>
                      <Divider orientation="vertical" />
                    </Center>
                    <List fontSize="xs">
                      <ListItem>
                        <strong>Valor:</strong> {formatarMoedaBrasil(b.valor.toString())}
                      </ListItem>
                      <ListItem>
                        <strong>Valor Juros:</strong> {formatarMoedaBrasil(b.valorJuros.toString())}
                      </ListItem>
                      <ListItem>
                        <strong>Valor Multa:</strong> {formatarMoedaBrasil(b.valorMulta.toString())}
                      </ListItem>
                      <ListItem>
                        <strong>Valor Desconto:</strong>{" "}
                        {formatarMoedaBrasil(b.valorDesconto.toString())}
                      </ListItem>
                      <ListItem>
                        <strong>Forma Pagamento:</strong> {b.formaPagamento}
                      </ListItem>
                    </List>
                  </Flex>

                  <Flex w="33%">
                    <Center height="100px" mr={5}>
                      <Divider orientation="vertical" />
                    </Center>
                    <List fontSize="xs">
                      <ListItem>
                        <strong>Banco/Agência/Conta:</strong> {b.banco} / {b.agencia} / {b.conta}
                      </ListItem>
                      <ListItem>
                        <strong>Conciliado Gefin:</strong>{" "}
                        {b.conciliacaoGefin.trim().toLowerCase() === "x" ? "sim" : "não"}
                      </ListItem>
                      <ListItem>
                        <strong>Data Pagamento:</strong> {formatarDataBrasil(b.data)}
                      </ListItem>
                      <ListItem>
                        <strong>Excluído:</strong> {b.excluido ? "sim" : "não"}
                      </ListItem>
                    </List>
                  </Flex>
                </Flex>
                {i != parcelas.baixas.length - 1 ? <Divider /> : null}
              </>
            ))
          ) : (
            <Text fontSize="sm">Não Houve Movimentação Bancária!</Text>
          )}
        </Box>
      </Box>
    </>
  );
}
