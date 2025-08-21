import { Progress } from "@chakra-ui/react";
import { ThemeTypings } from "@chakra-ui/styled-system";
import { useEffect, useRef, useState } from "react";
import { SimpleObservable } from "../../../utils/simpleObservable.ts";

export interface ProgressBarProps {
  duration: number;
  colorScheme?: ThemeTypings["colorSchemes"];
  onHoverObservable: SimpleObservable<boolean>;
}

/**
 * Uma barra de progresso que marca uma duração em milisegundos, que pausa ao se passar o mouse em cima (determinado por
 * um evento true ser notificado ao onHover) e reseta ao se tirar o mouse de cima (evento false)
 * @param onHoverObservable {SimpleObservable} observable que avisa quando o mouse entra e sai da região de interesse
 * @param colorScheme {ThemeTypings["colorSchemes"] | undefined} esquema de cores para usar na barra de progresso
 * @constructor
 */
export function ProgressBar({ duration, onHoverObservable, colorScheme }: ProgressBarProps) {
  const updateRate = 10;
  const [value, setValue] = useState(100);
  const pausedRef = useRef(false);

  useEffect(() => {
    const interval = setInterval(() => {
      // Se pausado não atualizamos o valor
      if (pausedRef.current) {
        return;
      }

      setValue((oldValue) => {
        if (oldValue > 0) {
          return oldValue - (100 / duration) * updateRate; // Reduz a barra de progresso
        }

        // Chegou a zero, limpando
        clearInterval(interval);
        onHoverObservable.unsubscribe(observer);
        return 0;
      });
    }, updateRate); // Atualiza

    const observer = onHoverObservable.subscribe((mouseOnTop) => {
      // Se o mouse está em cima, pausamos
      pausedRef.current = mouseOnTop;

      // Se o mouse saiu de cima, reiniciamos
      if (!mouseOnTop) {
        setValue(100);
      }
    });

    // Limpando
    return () => {
      clearInterval(interval);
      onHoverObservable.unsubscribe(observer);
    };
  }, []);

  return (
    <Progress
      value={value}
      size="sm"
      marginTop="10px"
      border="1px"
      borderColor="white"
      colorScheme={colorScheme}
      backgroundColor="white"
      borderRadius="9999px"
    />
  );
}
