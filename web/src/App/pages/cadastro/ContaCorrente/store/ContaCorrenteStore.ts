import { create } from "zustand";
import { ContaCorrenteDTO } from "../../../../../models/DTOs/AgenciaBancoConta/ContaCorrenteDTO";

interface ContaCorrenteState {
  contaCorrente: ContaCorrenteDTO | null;
  setContaCorrente: (banco: ContaCorrenteDTO) => void;
}

export const useContaCorrenteStore = create<ContaCorrenteState>((set) => ({
  contaCorrente: null,
  setContaCorrente: (banco) => set({ contaCorrente: banco }),
}));
