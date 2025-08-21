import { create } from "zustand";
import { ProdutoDadoContabilDTO } from "../../../../../models/DTOs/Produtos/ProdutoDadoContabilDTO";

interface ProdutoDadoContabilState {
  produtoDadoContabil: ProdutoDadoContabilDTO | null;
  setProdutoDadoContabil: (novoProdutoDadoContabil: ProdutoDadoContabilDTO) => void;
}

export const useProdutoDadoContabilStore = create<ProdutoDadoContabilState>((set) => ({
  produtoDadoContabil: null,
  setProdutoDadoContabil: (produtoDadoContabil) => set({ produtoDadoContabil }),
}));
