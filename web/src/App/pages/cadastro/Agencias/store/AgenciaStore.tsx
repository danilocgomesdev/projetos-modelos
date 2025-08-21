import { create } from "zustand";
import { AgenciaDTO } from "../../../../../models/DTOs/AgenciaBancoConta/AgenciaDTO.ts";

interface AgenciaState {
  agencia: AgenciaDTO | null;
  setAgencia: (novaAgencia: AgenciaDTO) => void;
}

export const useAgenciaStore = create<AgenciaState>((set) => ({
  agencia: null,
  setAgencia: (agencia) => set({ agencia }),
}));
