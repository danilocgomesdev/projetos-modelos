import { create } from "zustand";
import { BancoDTO } from "../../../../../models/DTOs/AgenciaBancoConta/BancoDTO";

interface BancoState {
  banco: BancoDTO | null;
  setBanco: (banco: BancoDTO) => void;
}

export const useBancoStore = create<BancoState>((set) => ({
  banco: null,
  setBanco: (banco) => set({ banco }),
}));
