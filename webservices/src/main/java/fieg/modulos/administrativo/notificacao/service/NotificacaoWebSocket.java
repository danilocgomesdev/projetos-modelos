package fieg.modulos.administrativo.notificacao.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.websocket.OnClose;
import jakarta.websocket.OnMessage;
import jakarta.websocket.OnOpen;
import jakarta.websocket.Session;
import jakarta.websocket.server.PathParam;
import jakarta.websocket.server.ServerEndpoint;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@ServerEndpoint("/notificacoes/{usuario}")
@ApplicationScoped
public class NotificacaoWebSocket {

    private final Map<String, Session> clientes = new ConcurrentHashMap<>();

    @OnOpen
    public void onOpen(Session session, @PathParam("usuario") String usuario) {
        clientes.put(usuario, session);
    }

    @OnMessage
    public void onMessage(String mensagem,  @PathParam("usuario") String usuario) {
        broadcast( mensagem);
    }

    @OnClose
    public void onClose(Session session, @PathParam("usuario") String usuario) {
        clientes.remove(usuario);
    }

    private void broadcast(String mensagem) {
        clientes.values().forEach(s -> s.getAsyncRemote().sendText(mensagem));
    }
}