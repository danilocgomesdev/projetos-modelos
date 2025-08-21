export function removeCaracteresEspeciais(texto: string) {
  return texto.replace(/[^a-zA-Z0-9\s]/g, "");
};