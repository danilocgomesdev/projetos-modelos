export function removeValoresVazios<T extends object>(obj: T): unknown {
  const result: any = { ...obj }; // Cópia do objeto

  Object.keys(result).forEach((key) => {
    const value = result[key];
    // Para propriedades que podem ser be T | null | undefined
    if (value === null) {
      result[key] = undefined;
    }
    // Para propriedades que podem ser string | undefined
    if (typeof value === "string" && value.trim().length === 0) {
      result[key] = undefined;
    }
  });

  return result;
}