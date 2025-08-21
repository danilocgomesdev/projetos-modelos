import { QueryClient } from "@tanstack/react-query";

const queryClient = new QueryClient({
  defaultOptions: {
    queries: {
      refetchOnWindowFocus: false, // Evita de recarregar a tela toda vez que volta o foco. Pode ser ativado em queries individuais
    },
  },
});

export default queryClient;
