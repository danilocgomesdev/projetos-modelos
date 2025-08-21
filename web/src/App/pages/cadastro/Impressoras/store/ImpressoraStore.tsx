import { create } from "zustand";
import { ImpressorasDTO } from "../../../../../models/DTOs/Outros/ImpressorasDTO";

interface ImpressoraState {
  impressora: ImpressorasDTO | null;
  setImpressora: (novaImpressora: ImpressorasDTO) => void;
}

export const useImpressoraStore = create<ImpressoraState>((set) => ({
  impressora: null,
  setImpressora: (impressora) => set({ impressora }),
}));
