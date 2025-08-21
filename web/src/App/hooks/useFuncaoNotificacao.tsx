import { useToast, UseToastOptions } from "@chakra-ui/react";
import { ThemeTypings } from "@chakra-ui/styled-system";
import { ReactNode } from "react";
import { IconBaseProps } from "react-icons";
import { FiAlertTriangle, FiCheckCircle, FiInfo, FiSlash } from "react-icons/fi";
import { SimpleObservable } from "../../utils/simpleObservable.ts";
import { ProgressBar } from "../components/ProgressBar/ProgressBar.tsx";
export type TipoNotificacao = "success" | "error" | "warning" | "info";

export interface NotificacaoProps {
  message: string;
  titulo: string;
  tipo: TipoNotificacao;
  mostrarProgresso?: boolean;
}

export function montaParamsNotificacao({
  message,
  titulo,
  tipo,
}: NotificacaoProps): UseToastOptions {
  const [colorScheme, iconFactory, tempoPadrao]: [
    ThemeTypings["colorSchemes"],
    (props: IconBaseProps) => ReactNode,
    number
  ] =
    tipo === "success"
      ? ["green", (props) => FiCheckCircle(props), 4000]
      : tipo === "warning"
      ? ["orange", (props) => FiAlertTriangle(props), 5000]
      : tipo === "error"
      ? ["red", (props) => FiSlash(props), 6000]
      : ["blue", (props) => FiInfo(props), 4000];

  return {
    title: titulo,
    description: message,
    position: "top-right",
    status: tipo,
    duration: Math.floor(Math.max(tempoPadrao, tempoPadrao * (message.length / 300))),
    isClosable: true,
    colorScheme,
    icon: iconFactory({ size: "3rem", style: { marginTop: "4px" } }),
  };
}

export function useCustomToast() {
  const toast = useToast();

  return (props: NotificacaoProps) => {
    const options = montaParamsNotificacao(props);
    const mostrarProgresso = props.mostrarProgresso ?? true;

    if (mostrarProgresso) {
      const mouseEmCima = new SimpleObservable<boolean>();

      options.description = (
        <div
          onMouseEnter={() => mouseEmCima.notify(true)}
          onMouseLeave={() => mouseEmCima.notify(false)}
        >
          <div>{options.description}</div>
          {!!options.duration && (
            <ProgressBar
              duration={options.duration}
              onHoverObservable={mouseEmCima}
              colorScheme={options.colorScheme}
            />
          )}
        </div>
      );
    }

    toast(options);
  };
}

export function useFuncaoNotificacao(): (props: NotificacaoProps) => void {
  const toast = useCustomToast();
  return (props) => {
    toast(props);
  };
}
