import { Box, Table, Tbody, Td, Th, Thead, Tr } from "@chakra-ui/react";
import { useQuery } from "@tanstack/react-query";
import { fetchClienteResponsavel } from "../../../../../requests/resquestVinculoDependentes";
import { TemposCachePadrao } from "../../../../../utils/constantes";
import { mascaraCpfCnpj } from "../../../../../utils/mascaras";
import { Loading } from "../../../../components/Loading";
import { ModalBase } from "../../../../components/ModalBase";
import useCR5Axios from "../../../../hooks/useCR5Axios";

interface ModalHistoricoVinculoDependenteProps {
  abrirModal: boolean;
  setAbrirModal: (value: boolean) => void;
  cpf: string;
}

export function ModalHistoricoVinculoDependente({
  abrirModal,
  setAbrirModal,
  cpf,
}: ModalHistoricoVinculoDependenteProps) {
  const { axios } = useCR5Axios();

  const { data, isLoading, isRefetching } = useQuery({
    queryKey: ["fetchClienteResponsavelModal", cpf],
    keepPreviousData: true,
    staleTime: TemposCachePadrao.MODERADO,
    cacheTime: TemposCachePadrao.MODERADO,
    queryFn: () => {
      return fetchClienteResponsavel(
        {
          cpf: cpf,
        },
        axios
      );
    },
  });

  return (
    <>
      <ModalBase
        isOpen={abrirModal}
        onClose={() => setAbrirModal(false)}
        titulo="Histórico Vínculo Dependente"
        size="6xl"
        centralizado={true}
      >
        {isLoading || isRefetching ? (
          <Loading mensagem="Carregando os Dados" altura="20vh" />
        ) : (
          <Box p={1} minW={"400px"} overflowX="auto">
            <Table size="sm">
              <Thead>
                <Tr>
                  <Th>ALUNO</Th>
                  <Th minW={"200px"}>CPF</Th>
                  <Th>STATUS ALUNO</Th>
                  <Th>ID VÍNVULO</Th>
                  <Th>STATUS VÍNVULO</Th>
                  <Th>NOME RESPONSÁVEL</Th>
                  <Th minW={"200px"}>CPF RESPONSÁVEL</Th>
                  <Th>SISTEMA</Th>
                </Tr>
              </Thead>
              <Tbody>
                {data?.list.map((item) => (
                  <Tr
                    key={item.cpfAluno}
                    cursor="pointer"
                    _hover={{
                      bg: "hoverColor",
                      color: "white !important",
                    }}
                  >
                    <Td>{item.nomeAluno?.toUpperCase() ?? "-"}</Td>
                    <Td>{mascaraCpfCnpj(item.cpfAluno) ?? "-"}</Td>
                    <Td>{item.statusAluno ?? "-"}</Td>
                    <Td>{item.idResponsavelAluno ?? "-"}</Td>
                    <Td>{item.statusDoVinculo ?? "-"}</Td>
                    <Td>{item.nomeResponsavelFinanceiro?.toUpperCase() ?? "-"}</Td>
                    <Td>{mascaraCpfCnpj(item.cpfResponsavelFinanceiro) ?? "-"}</Td>
                    <Td>{item.sistema?.toUpperCase() ?? "-"}</Td>
                  </Tr>
                ))}
              </Tbody>
            </Table>
          </Box>
        )}
      </ModalBase>
    </>
  );
}
