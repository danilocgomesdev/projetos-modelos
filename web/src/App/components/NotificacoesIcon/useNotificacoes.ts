import { useEffect, useRef, useState } from "react";
import { useKeycloak } from "../../hooks/useKeyCloak";

interface Notificacao {
  mensagem: string;
  lida: boolean;
}

export const useNotificacoes = () => {
  const [notificacoes, setNotificacoes] = useState<Notificacao[]>([]);
  const notificacoesSet = useRef(new Set<string>()); // Para evitar notificações repetidas
  const url = import.meta.env.VITE_URL;
  const { keycloak } = useKeycloak();
  const socketRef = useRef<WebSocket | null>(null);
  const reconnectTimeout = useRef<NodeJS.Timeout | null>(null);
  const pingInterval = useRef<NodeJS.Timeout | null>(null);

  if (!url) {
    throw new Error("É necessário configurar VITE_URL");
  }

  const baseURL = url;

  useEffect(() => {
    const conectarWebSocket = () => {
      if (!keycloak.sessionId) {
        console.error("Sessão do usuário não encontrada.");
        return;
      }

      if (socketRef.current) {
        console.warn("WebSocket já existe, evitando conexão duplicada.");
        return;
      }

      socketRef.current = new WebSocket(`${baseURL}/notificacoes/${keycloak.sessionId}`);

      socketRef.current.onopen = () => {
        console.log("✅ WebSocket conectado");

        pingInterval.current = setInterval(() => {
          if (socketRef.current?.readyState === WebSocket.OPEN) {
            socketRef.current.send("ping");
          }
        }, 60000);
      };

      socketRef.current.onmessage = (event) => {
        if (event.data === "pong") return;
        if (event.data === "ping") return;

        if (!notificacoesSet.current.has(event.data)) {
          notificacoesSet.current.add(event.data);
          setNotificacoes((prev) => [{ mensagem: event.data, lida: false }, ...prev]);
        }
      };

      socketRef.current.onerror = (error) => {
        console.error("❌ Erro no WebSocket:", error);
      };

      socketRef.current.onclose = () => {
        console.log("🔄 WebSocket fechado. Tentando reconectar em 5 segundos...");
        socketRef.current = null;

        if (pingInterval.current) clearInterval(pingInterval.current);
        if (reconnectTimeout.current) clearTimeout(reconnectTimeout.current);

        reconnectTimeout.current = setTimeout(conectarWebSocket, 5000);
      };
    };

    conectarWebSocket();

    return () => {
      socketRef.current?.close();
      if (pingInterval.current) clearInterval(pingInterval.current);
      if (reconnectTimeout.current) clearTimeout(reconnectTimeout.current);
    };
  }, [baseURL, keycloak.sessionId]);

  // Função para marcar notificação como lida
  const marcarComoLida = (index: number) => {
    setNotificacoes((prev) =>
      prev.map((notificacao, i) => (i === index ? { ...notificacao, lida: true } : notificacao))
    );
  };

  // Função para excluir notificação
  const excluirNotificacao = (index: number) => {
    setNotificacoes((prev) => prev.filter((_, i) => i !== index));
  };

  return { notificacoes, marcarComoLida, excluirNotificacao };
};
