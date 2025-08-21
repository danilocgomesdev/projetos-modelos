import { create } from "zustand";
import { VisaoServicosDTO } from "../../../../models/DTOs/Visoes/VisaoServicosDTO.ts";

interface VisaoServicosStoreState {
  visaoServicos: VisaoServicosDTO | null;
  adicionarVisaoServicos: (novaVisaoServicos: VisaoServicosDTO | null) => void;
}

export const useVisaoServicosStore = create<VisaoServicosStoreState>((set) => ({
  visaoServicos: null,
  adicionarVisaoServicos: (novoVisaoServicos) => set({ visaoServicos: novoVisaoServicos }),
}));
