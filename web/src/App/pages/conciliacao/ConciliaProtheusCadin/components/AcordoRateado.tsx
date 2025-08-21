import { AbsoluteCenter, Box, Center, Divider, Flex, List, ListItem } from "@chakra-ui/react";
import { AcordoRateadoDTO } from "../../../../../models/DTOs/Cadin/AcordoRateadoDTO";
import { formatarDataHoraBrasil, formatarMoedaBrasil } from "../../../../../utils/mascaras";

interface AcordoRateadoProps {
  dados: AcordoRateadoDTO;
}

export function AcordoRateado({ dados }: AcordoRateadoProps) {
  const parcelas = dados;
  return (
    <>
      <Box mt={5} w="100%" pl={8}>
        <Flex flexDirection="row" p={1} justifyContent="space-around">
          <Flex w="25%">
            <List fontSize="xs">
              <ListItem>
                <strong>Status Acordo:</strong> {parcelas.amortizaBoletoPago.statusAcordo}
              </ListItem>
              <ListItem>
                <strong>Status Objeto Cadin:</strong>{" "}
                {parcelas.amortizaBoletoPago.statusObjetoCadin}
              </ListItem>
              <ListItem>
                <strong>Id Cobrança Cadin:</strong>{" "}
                {parcelas.amortizaBoletoPago.idCobrancaClienteCadin}
              </ListItem>
              <ListItem>
                <strong>Id Cobrança Origem:</strong>{" "}
                {parcelas.amortizaBoletoPago.idCobrancaClienteOrigem}
              </ListItem>
              <ListItem>
                <strong>Número Parcela:</strong> {parcelas.amortizaBoletoPago.numeroParcela}
              </ListItem>
            </List>
          </Flex>

          <Flex w="25%">
            <Center height="100px" mr={5}>
              <Divider orientation="vertical" />
            </Center>
            <List fontSize="xs">
              <ListItem>
                <strong>Código:</strong> {parcelas.amortizaBoletoPago.codigo}
              </ListItem>
              <ListItem>
                <strong>Objeto Inadiplência:</strong>{" "}
                {parcelas.amortizaBoletoPago.objetoDeInadiplencia}
              </ListItem>
              <ListItem>
                <strong>Recno:</strong> {parcelas.amortizaBoletoPago.recno}
              </ListItem>
              <ListItem>
                <strong>Código Entidade:</strong> {parcelas.amortizaBoletoPago.codigoEntidade}
              </ListItem>
              <ListItem>
                <strong>Baixa Parcial:</strong>{" "}
                {parcelas.amortizaBoletoPago.baixaParcial === "1" ? "sim" : "não"}
              </ListItem>
            </List>
          </Flex>

          <Flex w="25%">
            <Center height="100px" mr={5}>
              <Divider orientation="vertical" />
            </Center>
            <List fontSize="xs">
              <ListItem>
                <strong>Valor Princial:</strong>{" "}
                {formatarMoedaBrasil(parcelas.amortizaBoletoPago.valorPrincial.toString())}
              </ListItem>
              <ListItem>
                <strong>Valor Pago:</strong>{" "}
                {formatarMoedaBrasil(parcelas.amortizaBoletoPago.valorPago.toString())}
              </ListItem>{" "}
              <ListItem>
                <strong>Valor Desconto:</strong>{" "}
                {formatarMoedaBrasil(parcelas.amortizaBoletoPago.valorDesconto.toString())}
              </ListItem>
              <ListItem>
                <strong>Valor Juros:</strong>{" "}
                {formatarMoedaBrasil(parcelas.amortizaBoletoPago.valorJuros.toString())}
              </ListItem>
              <ListItem>
                <strong>Valor Multa:</strong>{" "}
                {formatarMoedaBrasil(parcelas.amortizaBoletoPago.valorMulta.toString())}
              </ListItem>
              <ListItem>
                <strong>Valor Custa:</strong>{" "}
                {formatarMoedaBrasil(parcelas.amortizaBoletoPago.valorCusta.toString())}
              </ListItem>
            </List>
          </Flex>

          <Flex w="25%">
            <Center height="100px" mr={5}>
              <Divider orientation="vertical" />
            </Center>
            <List fontSize="xs">
              <ListItem>
                <strong>Data Baixa Protheus:</strong>{" "}
                {formatarDataHoraBrasil(parcelas.amortizaBoletoPago.dataBaixaProtheus)}
              </ListItem>
              <ListItem>
                <strong>Data Baixa Cr5:</strong>{" "}
                {formatarDataHoraBrasil(parcelas.amortizaBoletoPago.dataBaixaCr5)}
              </ListItem>
            </List>
          </Flex>
        </Flex>

        {parcelas.rateios && parcelas.rateios.length > 0 ? (
          <Box>
            <Box position="relative" p={2}>
              <Divider />
              <AbsoluteCenter px="4" fontSize="sm">
                Rateios
              </AbsoluteCenter>
            </Box>
            {parcelas.rateios.map((b) => (
              <Box key={b.id}>
                <Flex flexDirection="row" justifyContent="space-around" mt={2}>
                  <Flex w="33%">
                    <List fontSize="xs">
                      <ListItem>
                        <strong>Data Inclusão:</strong> {formatarDataHoraBrasil(b.dataInclusao)}
                      </ListItem>
                      <ListItem>
                        <strong>Id Cobrança Cadin:</strong> {b.idCobrancaClienteCadin}
                      </ListItem>
                      <ListItem>
                        <strong>Id Cobrança Origem:</strong> {b.idCobrancaClienteOrigem}
                      </ListItem>
                    </List>
                  </Flex>

                  <Flex w="33%">
                    <Center height="100px" mr={5}>
                      <Divider orientation="vertical" />
                    </Center>
                    <List fontSize="xs">
                      <ListItem>
                        <strong>Valor Pago:</strong> {formatarMoedaBrasil(b.valorPago.toString())}
                      </ListItem>
                      <ListItem>
                        <strong>Valor Juros:</strong> {formatarMoedaBrasil(b.juros.toString())}
                      </ListItem>
                      <ListItem>
                        <strong>Valor Multa:</strong> {formatarMoedaBrasil(b.multa.toString())}
                      </ListItem>
                      <ListItem>
                        <strong>Valor Custo:</strong> {formatarMoedaBrasil(b.custo.toString())}
                      </ListItem>
                      <ListItem>
                        <strong>Valor Total Cobrança:</strong>{" "}
                        {formatarMoedaBrasil(b.valorTotalCobranca.toString())}
                      </ListItem>
                    </List>
                  </Flex>

                  <Flex w="33%">
                    <Center height="100px" mr={5}>
                      <Divider orientation="vertical" />
                    </Center>
                    <List fontSize="xs">
                      <ListItem>
                        <strong>Forma Pagamento:</strong> {b.formaPagamento}
                      </ListItem>
                      <ListItem>
                        <strong>Código Amortiza Boleto Pago:</strong> {b.codigoAmortizaBoletoPago}
                      </ListItem>
                      <ListItem>
                        <strong>Data Baixa Protheus:</strong>{" "}
                        {formatarDataHoraBrasil(b.dataBaixaProtheus)}
                      </ListItem>
                    </List>
                  </Flex>
                </Flex>
                {parcelas.rateios.length > 1 ? <Divider /> : null}
              </Box>
            ))}
          </Box>
        ) : null}
      </Box>
    </>
  );
}
