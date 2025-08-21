import {
  Card,
  CardBody,
  CardHeader,
  Heading,
  Modal,
  ModalBody,
  ModalCloseButton,
  ModalContent,
  ModalHeader,
  ModalOverlay,
  Stack,
  StackDivider,
  Text,
} from "@chakra-ui/react";
import { useQuery } from "@tanstack/react-query";
import { fetchVisaoOperadorList } from "../../../requests/requestsVisaoOperador";
import useCR5Axios from "../../hooks/useCR5Axios";
import { Loading } from "../Loading";
import { DetalhesOperador, InfoData } from "./DetalhesOperador";

interface ModalDetalhesOperadorProps {
  abrirModal: boolean;
  setAbrirModal: (value: boolean) => void;
  operadoresABuscar: (OperadorABuscar | null)[];
  tela: string;
  nomeCadastro: string;
}

interface OperadorABuscar {
  titulo: string;
  idOperador: number;
  infoData: InfoData;
}

export function ModalDetalhesOperador({
  abrirModal,
  setAbrirModal,
  operadoresABuscar: operadoresParaBuscar,
  tela,
  nomeCadastro,
}: ModalDetalhesOperadorProps) {
  const { axios } = useCR5Axios();

  const { data, isLoading, isRefetching } = useQuery({
    queryKey: ["fetchVisaoOperadorList", operadoresParaBuscar],
    keepPreviousData: false,
    enabled: abrirModal,
    queryFn: () => {
      return fetchVisaoOperadorList(
        operadoresParaBuscar.filter((it) => !!it).map((it) => it?.idOperador ?? -1),
        axios
      );
    },
  });

  return (
    <Modal closeOnOverlayClick={false} isOpen={abrirModal} onClose={() => setAbrirModal(false)}>
      <ModalOverlay />
      <ModalContent>
        <ModalHeader>Dados Operadores</ModalHeader>
        <ModalCloseButton />
        <ModalBody pb={6}>
          <Card>
            <CardHeader>
              <Heading size="md">{tela}</Heading>
              <Text fontSize="sm">Nome Cadastro: {nomeCadastro}</Text>
            </CardHeader>
            <CardBody>
              {isLoading || isRefetching ? (
                <Loading mensagem="Carregando os Dados" altura="20vh" />
              ) : (
                <Stack divider={<StackDivider />} spacing="4">
                  {data?.map((operador) => {
                    const dadosBusca = operadoresParaBuscar.find(
                      (it) => it?.idOperador === operador.idOperador
                    );
                    if (!dadosBusca) {
                      throw new Error(
                        "Back end retornou operador de id diferente. Não deveria ocorrer"
                      );
                    }

                    return (
                      <DetalhesOperador
                        key={`${dadosBusca.titulo}#${operador.idOperador}`}
                        titulo={dadosBusca.titulo}
                        operador={operador}
                        infoData={dadosBusca.infoData}
                      />
                    );
                  })}
                </Stack>
              )}
            </CardBody>
          </Card>
        </ModalBody>
      </ModalContent>
    </Modal>
  );
}

export function buscaOperadorInclusao(
  idOperador: number,
  dataInclusao: string | number | Date
): OperadorABuscar {
  return {
    titulo: "Operador Inclusão",
    idOperador: idOperador,
    infoData: {
      titulo: "Data Inclusão",
      data: dataInclusao,
    },
  };
}

export function buscaOperadorAlteracao(
  idOperador?: number,
  dataAlteracao?: string | number | Date
): OperadorABuscar | null {
  if (!idOperador || !dataAlteracao) {
    return null;
  }

  return {
    titulo: "Operador Alteração",
    idOperador: idOperador,
    infoData: {
      titulo: "Data Alteração",
      data: dataAlteracao,
    },
  };
}
