import {
  Box,
  BoxProps,
  Center,
  Flex,
  Table,
  Tbody,
  Td,
  Th,
  Thead,
  Tr,
  useColorModeValue,
} from "@chakra-ui/react";

import React, { MouseEvent, ReactNode, useMemo } from "react";

import { FiEdit, FiTrash2, FiZoomIn } from "react-icons/fi";

import { Detalhe, DetalhesLinhaTabela, LinhaDeTabela } from ".";

import { getMensagemDeErroOuProcureSuporte } from "../../../utils/errors";

import { DadosNaoEncontrados } from "../DadosNaoEncontrados";

import { Loading } from "../Loading";

import { Pagination } from "../Pagination";

export interface CabecalhoDeTabela<T> {
  titulo: string;
  alinhamento?: "center" | "left" | "right";
  dadoBuilder: (item: T) => string | JSX.Element;
  tamanho?: string;
}

type DetalhesBuilder<T> =
  | { type: "Expandir"; buildDetalhes: (item: T) => Detalhe[][]; children?: (item: T) => ReactNode }
  | { type: "ExpandirCustom"; buildDetalhes: (item: T) => ReactNode }
  | {
      type: "OnClick";
      onClick: (item: T) => void;
    };

interface CustomAction<T> {
  title: string;
  icon: (item: T) => React.ReactNode;
  action: (item: T) => void;
}

interface TabelaCR5Props<T> {
  cabecalhos: CabecalhoDeTabela<T>[];
  data: T[] | undefined;
  isFetching: boolean;
  isError: boolean;
  error: unknown;
  keybuilder: (item: T) => React.Key;
  onEdit?: (item: T) => void;
  onDelete?: (item: T) => void;
  customAction?: CustomAction<T>[] | undefined;
  detalhesBuilder?: DetalhesBuilder<T>;
  totalPages?: number;
  totalItems?: number;
  itemsPerPage?: number;
  currentPage?: number;
  setCurrentPage?: (page: number) => void;
  onRowClick?: (item: T) => void;
  alturaLoading?: string;
}

export function TabelaCR5<T>({
  cabecalhos,
  data,
  isFetching,
  isError,
  error,
  keybuilder,
  onEdit,
  onDelete,
  customAction,
  detalhesBuilder,
  totalPages,
  totalItems,
  itemsPerPage,
  currentPage,
  setCurrentPage,
  onRowClick,
  alturaLoading,
}: TabelaCR5Props<T>): ReactNode {
  const corTexto = useColorModeValue("black", "white");
  const corCabecalho = useColorModeValue("light.cabecalho", "dark.cabecalho");

  const renderTh = (key: string, titulo: string, tamanho?: string) => (
    <Th key={key} minW={tamanho} maxW={tamanho} p={2} textAlign="center" color={corTexto}>
      {titulo}
    </Th>
  );

  const TabelaHeaders = useMemo(
    () => (
      <Thead>
        <Tr bg={corCabecalho}>
          {detalhesBuilder && renderTh("__vermais__", "VER MAIS")}
          {cabecalhos.map(({ titulo, tamanho }) => renderTh(titulo, titulo, tamanho))}
          {(onEdit || onDelete || customAction) && renderTh("__acoes__", "AÇÕES")}
        </Tr>
      </Thead>
    ),
    [cabecalhos, corCabecalho, detalhesBuilder, onEdit, onDelete, customAction]
  );

  const TabelaBody = useMemo(
    () => (
      <Tbody>
        {data?.map((item, index) => (
          <LinhaDeTabela
            key={keybuilder(item)}
            index={index}
            onClickRow={onRowClick && { type: "linha", onClick: () => onRowClick(item) }}
            componenteExpandido={
              detalhesBuilder &&
              (detalhesBuilder.type === "Expandir"
                ? {
                    type: "Expandido",
                    componente: (
                      <DetalhesLinhaTabela detalhes={detalhesBuilder.buildDetalhes(item)}>
                        {detalhesBuilder.children && detalhesBuilder.children(item)}
                      </DetalhesLinhaTabela>
                    ),
                  }
                : detalhesBuilder.type == "ExpandirCustom"
                ? {
                    type: "Expandido",
                    componente: detalhesBuilder.buildDetalhes(item),
                  }
                : { type: "OnClick", onClick: () => detalhesBuilder.onClick(item) })
            }
          >
            {detalhesBuilder && (
              <Td p={2}>
                <Center>
                  <FiZoomIn />
                </Center>
              </Td>
            )}
            {cabecalhos.map(({ dadoBuilder, titulo, alinhamento }) => (
              <Td key={titulo} textAlign={alinhamento ? alinhamento : "center"}>
                {dadoBuilder(item)}
              </Td>
            ))}
            {(onEdit || onDelete || customAction) && (
              <Td>
                <Center>
                  <Acoes custom={customAction} onEdit={onEdit} onDelete={onDelete} item={item} />
                </Center>
              </Td>
            )}
          </LinhaDeTabela>
        ))}
      </Tbody>
    ),
    [data, cabecalhos, detalhesBuilder, onEdit, onDelete, keybuilder, customAction]
  );

  return (
    <>
      {isFetching ? (
        <Loading mensagem="Carregando dados!" altura={alturaLoading ? alturaLoading : "40vh"} />
      ) : isError ? (
        <DadosNaoEncontrados
          altura="40vh"
          isError
          mensagem={`Erro ao carregar dados: ${getMensagemDeErroOuProcureSuporte(error)}`}
        />
      ) : (
        <>
          <Box
            p={1}
            mt={2}
            overflowX="auto"
            sx={{
              "&::-webkit-scrollbar": {
                height: "10px",
              },
              "&::-webkit-scrollbar-thumb": {
                background: "#A0AEC0", // cinza médio
                borderRadius: "4px",
              },
              "&::-webkit-scrollbar-track": {
                // background: "#A0AEC0", // azul escuro para combinar com o header
                borderRadius: "4px",
              },
            }}
          >
            <Flex direction="column" minWidth="fit-content">
              <Table size="sm" minWidth="full">
                {TabelaHeaders}
                {TabelaBody}
              </Table>
            </Flex>
          </Box>

          {data?.length === 0 ? (
            <DadosNaoEncontrados altura="10vh" mensagem="Nenhum dado encontrado!" isError={false} />
          ) : (
            totalPages &&
            totalItems &&
            itemsPerPage &&
            currentPage &&
            setCurrentPage && (
              <Box mt={4}>
                <Pagination
                  totalPages={totalPages}
                  itemsPerPage={itemsPerPage}
                  currentPage={currentPage}
                  setCurrentPage={setCurrentPage}
                  totalItems={totalItems}
                />
              </Box>
            )
          )}
        </>
      )}
    </>
  );
}

interface AcoesProps<T> {
  onEdit?: (item: T) => void;
  onDelete?: (item: T) => void;
  custom?: CustomAction<T>[];
  item: T;
}

function Acoes<T>({ onEdit, onDelete, custom, item }: AcoesProps<T>) {
  function handleClick(
    event: MouseEvent<HTMLDivElement, globalThis.MouseEvent>,
    action: (item: T) => void
  ): void {
    event.stopPropagation(); // Para a propagação do evento de clique
    action(item);
  }

  return (
    <Flex gap="1px">
      {custom?.map(({ title, icon, action }) => (
        <ActionButton
          key={title}
          icon={icon(item)}
          title={title}
          onClick={(e) => handleClick(e, action)}
          color={useColorModeValue("#2B6CB0", "#63B3ED")}
          hoverColor="#2B6CB0"
        />
      ))}
      {onEdit && (
        <ActionButton
          icon={<FiEdit />}
          title="Editar"
          onClick={(e) => handleClick(e, onEdit)}
          color={useColorModeValue("#2B6CB0", "#63B3ED")}
          hoverColor="#2B6CB0"
        />
      )}
      {onDelete && (
        <ActionButton
          icon={<FiTrash2 />}
          title="Excluir"
          onClick={(e) => handleClick(e, onDelete)}
          color="red"
          hoverColor="red"
        />
      )}
    </Flex>
  );
}

type ActionButtonProps = BoxProps & {
  icon: React.ReactNode;
  hoverColor: string;
};

function ActionButton({ icon, hoverColor, ...rest }: ActionButtonProps) {
  return (
    <Box
      cursor="pointer"
      fontSize="lg"
      p={1}
      borderRadius={3}
      _hover={{
        bg: hoverColor,
        color: "white",
      }}
      {...rest}
    >
      {icon}
    </Box>
  );
}
