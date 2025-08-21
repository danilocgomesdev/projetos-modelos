import { create } from "zustand";
import { VisaoProdutoContabilDTO } from "../../../../../models/DTOs/Visoes/VisaoProdutoContabilDTOs";

interface VisaoProdutoContabilState {
  visaoProdutoContabil: VisaoProdutoContabilDTO | null;
  setVisaoProdutoContabil: (novovisaoProdutoContabil: VisaoProdutoContabilDTO) => void;
}

export const useVisaoProdutoContabilStore = create<VisaoProdutoContabilState>((set) => ({
  visaoProdutoContabil: null,
  setVisaoProdutoContabil: (visaoProdutoContabil) => set({ visaoProdutoContabil }),
}));
