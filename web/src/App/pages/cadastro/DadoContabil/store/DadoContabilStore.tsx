import { create } from "zustand";
import { DadoContabilDTO } from "../../../../../models/DTOs/Contabil/DadoContabilDTO.ts";

interface DadoContabilState {
  dadoContabil: DadoContabilDTO | null;
  adicionarDadoContabil: (novaDadoContabil: DadoContabilDTO) => void;
}

export const useDadoContabilStore = create<DadoContabilState>((set) => ({
  dadoContabil: null,
  adicionarDadoContabil: (novoDadoContabil) => set({ dadoContabil: novoDadoContabil }),
}));
