import { EntidadesEnum } from "./constantes";

export function trataValorInputNumberVazio(
  setter: (val: number | null) => void,
  e: React.ChangeEvent<HTMLInputElement>
) {
  const valor = +e.target.value;
  setter(valor === 0 ? null : valor);
}

export const OptionsEntidades = Object.keys(EntidadesEnum).map((key) => ({
  value: EntidadesEnum[key as keyof typeof EntidadesEnum].toString(),
  label: key.toString(),
}));

export function getEntidadeNome(valor: number | null): string {
  for (const [key, value] of Object.entries(EntidadesEnum)) {
    if (value === valor) {
      return key;
    }
  }
  return "";
}