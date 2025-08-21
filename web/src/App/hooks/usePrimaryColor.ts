import { useColorModeValue } from "@chakra-ui/react";

export function usePrimaryColor() {
  const mode = import.meta.env.MODE;

  const colorMap: Record<string, { light: string; dark: string }> = {
    dev: { light: "#8D3115", dark: "#511E0E" },
    devspace: { light: "#4E087B", dark: "#2C0545" },
    staging: { light: "#761313", dark: "#390B0B" },
    production: { light: "light.primary", dark: "dark.primary" },
  };

  const colors = colorMap[mode] || { light: "#761313", dark: "#390B0B" };

  return useColorModeValue(colors.light, colors.dark);
}
