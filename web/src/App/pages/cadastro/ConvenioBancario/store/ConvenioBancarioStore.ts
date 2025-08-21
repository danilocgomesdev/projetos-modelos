import { create } from "zustand";
import { ConvenioBancarioDTO } from "../../../../../models/DTOs/ConvenioBancario/ConvenioBancarioDTO.ts";

interface ConvenioBancarioStore {
  convenioBancario: ConvenioBancarioDTO | null;
  setConvenioBancario: (banco: ConvenioBancarioDTO) => void;
}

export const useConvenioBancarioStore = create<ConvenioBancarioStore>((set) => ({
  convenioBancario: null,
  setConvenioBancario: (banco) => set({ convenioBancario: banco }),
}));
