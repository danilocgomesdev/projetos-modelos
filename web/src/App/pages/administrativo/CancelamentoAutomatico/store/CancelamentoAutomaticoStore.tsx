import { create } from "zustand";
import { ConfigCancelamentoAutomaticoDTO } from "../../../../../models/DTOs/Outros/ConfigCancelamentoAutomaticoDTO.ts";

interface ConfigCancelamentoAutomaticoState {
  cancelamentoAutomatico: ConfigCancelamentoAutomaticoDTO | null;
  setCancelamentoAutomatico: (novoCancelamentoAutomatico: ConfigCancelamentoAutomaticoDTO) => void;
}

export const useCancelamentoAutomaticoStore = create<ConfigCancelamentoAutomaticoState>((set) => ({
  cancelamentoAutomatico: null,
  setCancelamentoAutomatico: (cancelamentoAutomatico) => set({ cancelamentoAutomatico }),
}));
