import { create } from "zustand";
import { persist } from "zustand/middleware";

interface SidebarState {
  isMenuOpen: boolean;
  setMenuOpen: (value: boolean) => void;
}

export const useSidebarStore = create(
  persist<SidebarState>(
    (set) => ({
      isMenuOpen: true,
      setMenuOpen: (value) => set({ isMenuOpen: value }),
    }),
    {
      name: "menuStore",
    }
  )
);
