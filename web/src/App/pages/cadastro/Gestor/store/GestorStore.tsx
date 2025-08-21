import { create } from "zustand";
import { GestorDTO } from "../../../../../models/DTOs/Outros/Gestor";

interface GestorState {
  gestor: GestorDTO | null;
  setGestor: (gestor: GestorDTO) => void;
}

export const useGestorStore = create<GestorState>((set) => ({
  gestor: null,
  setGestor: (gestor) => set({ gestor }),
}));
