import { create } from "zustand";
import { IdCobrancasClientesDTO } from "./IdCobrancasClientesDTO";

interface IdCobrancasClientesState {
  idCobrancasClientes: IdCobrancasClientesDTO | null;
  setIdCobrancasClientes: (idCobrancasClientes: IdCobrancasClientesDTO) => void;
}

export const useIdCobrancasClientesStore = create<IdCobrancasClientesState>((set) => ({
  idCobrancasClientes: null,
  setIdCobrancasClientes: (idCobrancasClientes) => set({ idCobrancasClientes }),
}));
