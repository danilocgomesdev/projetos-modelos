import {
  Button,
  Divider,
  Grid,
  GridItem,
  Modal,
  ModalBody,
  ModalCloseButton,
  ModalContent,
  ModalFooter,
  ModalHeader,
  ModalOverlay,
  ResponsiveValue,
  Text,
} from "@chakra-ui/react";

import * as CSS from "csstype";

import { ReactNode } from "react";

interface ModalBaseProps {
  titulo: string;
  subtitulo?: string;
  isOpen: boolean;
  onClose: () => void;
  footer?: ReactNode;
  botaoConfirma?: {
    label?: string;
    onClick: () => void;
  };
  size?: string;
  children?: ReactNode;
  centralizado?: boolean;
  overflowX?: ResponsiveValue<CSS.Property.OverflowX>;
  overflowY?: ResponsiveValue<CSS.Property.OverflowY>;
  isLoading?: boolean;
  loadingText?: string;
}

export function ModalBase({
  isOpen,
  onClose,
  size,
  titulo,
  subtitulo,
  children,
  centralizado,
  overflowY,
  overflowX,
  footer,
  botaoConfirma,
  isLoading,
  loadingText,
}: ModalBaseProps) {
  return (
    <Modal
      isCentered={centralizado}
      closeOnOverlayClick={false}
      isOpen={isOpen}
      onClose={onClose}
      size={size}
    >
      <ModalOverlay />
      <ModalContent
        overflowX={overflowX ? overflowX : "hidden"}
        overflowY={overflowY ? overflowY : "hidden"}
      >
        <ModalHeader>{titulo}</ModalHeader>
        {subtitulo && (
          <Text pl={6} mt={-3} pb={2} fontSize={"sm"} fontStyle={"italic"}>
            {subtitulo}
          </Text>
        )}
        <Divider />
        <ModalCloseButton />
        <ModalBody pb={4}>{children}</ModalBody>

        {botaoConfirma && (
          <ModalFooter>
            {footer}
            <Grid w="100%" templateColumns={{ base: "¨100%", md: "repeat(4, 1fr)" }}>
              <GridItem colSpan={{ base: 1, md: 2 }} p={1}>
                <Button
                  colorScheme="blue"
                  w="100%"
                  onClick={botaoConfirma.onClick}
                  isLoading={isLoading}
                  loadingText={loadingText ? loadingText : "Salvando"}
                >
                  {botaoConfirma.label ?? "Ok"}
                </Button>
              </GridItem>
              <GridItem colSpan={{ base: 1, md: 2 }} p={1}>
                <Button variant="outline" colorScheme="blue" w="100%" onClick={onClose}>
                  Cancelar
                </Button>
              </GridItem>
            </Grid>
          </ModalFooter>
        )}
      </ModalContent>
    </Modal>
  );
}
