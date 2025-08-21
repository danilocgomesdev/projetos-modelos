import { create } from "zustand";
import { PessoaCr5DTO } from "../../../../../models/DTOs/Outros/PessoaCr5DTO.ts";

interface ClientesState {
  cliente: PessoaCr5DTO | null;
  setCliente: (novoCliente: PessoaCr5DTO) => void;
}

export const useClientesStore = create<ClientesState>((set) => ({
  cliente: null,
  setCliente: (cliente) => set({ cliente }),
}));
