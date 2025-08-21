import { extendTheme } from "@chakra-ui/react";

export const theme = extendTheme({
  config: {
    initialColorMode: "dark",
    useSystemColorMode: true,
  },
  style: {
    global: {
      body: {
        margin: 0,
        padding: 0,
        fontFamily: "'Roboto', sans-serif",
        fontWeight: 400,
      },
    },
  },
  breakpoints: {
    sm: "360px",
    md: "768px",
    lg: "1200px",
  },

  colors: {
    hoverColor: "#4299E1",

    light: {
      primary: "#2B6CB0",
      background: "#f6f6fa",
      text: "white",
      border: "#90CDF4",
      colorDiscreta: "#A0AEC0",
      cabecalho: "#edf2f7",
      tabela: "#F3F7FB",
      modalBackground: "white",
    },
    dark: {
      primary: "#1d3045",
      background: "#0D1C2E",
      text: "white",
      border: "#1A365D",
      colorDiscreta: "#718096",
      cabecalho: "#2f4054",
      tabela: "#1E2B3D",
      modalBackground: "#2D3748",
    },
  },
  components: {
    FormErrorMessage: {
      baseStyle: {
        minHeight: "24px", // Ajuste este valor para o espaço que deseja reservar
      },
    },
  },
});
