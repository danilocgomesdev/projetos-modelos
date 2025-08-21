import { create } from "zustand";
import { TerminaisTEFDTO } from "../../../../../models/DTOs/Tef/TerminaisTEFDTO.ts";

interface TerminaisTEFState {
  terminaisTEF: TerminaisTEFDTO | null;
  setTerminaisTEF: (novaTerminaisTEF: TerminaisTEFDTO) => void;
}

export const useTerminaisTEFStore = create<TerminaisTEFState>((set) => ({
  terminaisTEF: null,
  setTerminaisTEF: (terminaisTEF) => set({ terminaisTEF }),
}));
