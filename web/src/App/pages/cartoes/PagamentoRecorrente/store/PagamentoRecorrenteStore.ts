import { create } from "zustand";
import { RecorrenciaDTO } from "../../../../../models/DTOs/Recorrencia/RecorrenciaDTO.ts";

interface RecorrenciaState {
  pagamentoRecorrente: RecorrenciaDTO | null;
  setPagamentoRecorrente: (pagamentoRecorrente: RecorrenciaDTO) => void;
}

export const usePagamentoRecorrenteStore = create<RecorrenciaState>((set) => ({
  pagamentoRecorrente: null,
  setPagamentoRecorrente: (pagamentoRecorrente: RecorrenciaDTO) => set({ pagamentoRecorrente }),
}));
