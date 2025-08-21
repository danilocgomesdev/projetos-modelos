

import { create } from "zustand";
import { IdCancelamentoAutomaticoDTO } from "./IdCancelamentoAutomaticoDTO.ts";

interface IdCancelamentoAutomaticoState {
  cancelamentoAutomatico: IdCancelamentoAutomaticoDTO | null;
  setCancelamentoAutomatico: (cancelamentoAutomatico: IdCancelamentoAutomaticoDTO) => void;
}

export const useIdCancelamentoAutomaticoStore = create<IdCancelamentoAutomaticoState>((set) => ({
  cancelamentoAutomatico: null,
  setCancelamentoAutomatico: (cancelamentoAutomatico) => set({ cancelamentoAutomatico }),
}));