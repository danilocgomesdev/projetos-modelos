/**
 * Recebe uma função e aplica "debounce" a ela. Ou seja, toda vez que ela é chamada, só é executada
 * após o delay, porém se ela for chamada novamente antes do delay, a contagem é reiniciada.
 *
 * Ex:
 * ```
 * const debounced = debounce((i) => console.log(i), 100);
 *
 * for (let i = 0; i < 100; i++) {
 *   debounced(i);
 * }
 * ```
 *
 * No exemplo acima, será impresso somente 99, pois as chamadas anteriores foram "canceladas"
 *
 * @param callback função em que será aplicado o debounce
 * @param delay tempo para aplicar debounce
 * @returns a função de calback que será chamada após o debounce
 */
export default function debounce<T>(callback: (args: T) => void, delay: number): (args: T) => void {
  let timeout: NodeJS.Timeout | undefined;

  return (args: T) => {
    clearTimeout(timeout);
    timeout = setTimeout(() => {
      timeout = undefined;
      callback(args);
    }, delay);
  };
}
