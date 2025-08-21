import React, { useEffect, useMemo, useState } from "react";
import debounce from "../../utils/debounce";

/**
 * Recebe uma variável e retorna uma versão da mesma com "debounce": quando a original muda, a com debounce irá mudar
 * para o mesmo valor depois do deley configurado, porém se ela mudar de novo antes do delay, a contagem é reeiniciada.
 *
 * Extremamente útil para campos de pesquisa reativos, evitando requisições excesivas (a cada tecla digitada).
 *
 * Ex:
 * ```
 * export default function InputComDebounce() {
 *   const [text, setText] = useState("Olá");
 *   const [textDebounce] = useDebounce(text, 1000);
 *
 *   return (
 *     <div>
 *       <input
 *         title="Digite aqui"
 *         defaultValue="Olá"
 *         onChange={(e) => {
 *           setText(e.target.value);
 *         }}
 *       />
 *       <p>Texto: {text}</p>
 *       <p>Texto com debounce: {textDebounce}</p>
 *     </div>
 *   );
 * }
 * ```
 *
 * No exemplo acima, no parágrafo "Texto: ..." vemos o que é digitado em tempo real, mas "Texto com debounce: ..."
 * só é atualizado quando paramos de digitar por 1s (1000ms).
 *
 * @param valor variável que será aplicado o debounce
 * @param delay deley do debounce em ms
 * @returns getter e setter para a variável com debounce. O setter é usado caso se queira contornar o debounce
 */
export default function useDebounce<T>(
  valor: T,
  delay: number
): [T, React.Dispatch<React.SetStateAction<T>>] {
  const [debounceValue, setDebounceValue] = useState(valor);
  const setDebouncedMemoed = useMemo(() => debounce(setDebounceValue, delay), []);

  useEffect(() => {
    setDebouncedMemoed(valor);
  }, [valor]);

  return [debounceValue, setDebounceValue];
}
