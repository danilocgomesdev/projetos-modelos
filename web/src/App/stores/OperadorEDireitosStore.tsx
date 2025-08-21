import { create } from "zustand";
import {
  OperadorDireitoDTO,
  OperadorEDireitosDTO,
  PessoaCIDTO,
} from "../../models/DTOs/Acessos/AcessosDTOs";

interface OperadorEDireitosState {
  operador: PessoaCIDTO | null;
  direitos: OperadorDireitoDTO[];
  operadorEDireitos: OperadorEDireitosDTO | null;
  setOperadorEDireitos: (arg: OperadorEDireitosDTO) => void;
}

// Essa store não pode ser guardada no localstorage! Mesmo que validada sempre pelo back, ela deve ser recarregada a cara relaod
export const useOperadorEDireitosStore = create<OperadorEDireitosState>((set) => ({
  operador: null,
  direitos: [],
  operadorEDireitos: null,
  setOperadorEDireitos: (arg) =>
    set(() => ({
      operador: arg.pessoa,
      direitos: arg.direitos,
      operadorEDireitos: arg,
    })),
}));
