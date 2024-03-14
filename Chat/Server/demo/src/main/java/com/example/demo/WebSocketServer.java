package com.example.demo;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Component;

import jakarta.websocket.CloseReason;
import jakarta.websocket.OnClose;
import jakarta.websocket.OnError;
import jakarta.websocket.OnMessage;
import jakarta.websocket.OnOpen;
import jakarta.websocket.Session;
import jakarta.websocket.server.ServerEndpoint;

@Component
@ServerEndpoint("/")
public class WebSocketServer {
    // Define a variable to record the client sessions.
    private static final Map<String,Session> clientSessions = new ConcurrentHashMap<>();

    // Define a variable to record the userName.
    String userName;

    // Define a variable to record the message content.
    String messageContent;

    // Define a function to handle the situation when the connection is successful.
    @OnOpen
    public void onServerOpen(Session session){
        clientSessions.put(session.getId(), session);
        System.out.println("A user has joined.");
        unicast(session.getId(),"Server:Welcome!");
    }

    // Define a function to handle the situation when client sends a message to the server
    @OnMessage
    public void onMessage(Session session,String message){
        setInformation(message);
        System.out.println("Received a message from:" + userName + ":" + messageContent);
        broadcast(userName + ":" + messageContent, session.getId());
    }

    // Define a function to handle the situation when the connection is closed.
    @OnClose
    public void onServerClose(Session session,CloseReason closeReason){
        // Remove the client with its id.
        System.out.println("Client:" + userName + " disconnected:" + closeReason);
        clientSessions.remove(session.getId());
    }

    // Define a function to handle the situation when the connection is error.
    @OnError
    public void onError(Session session,Throwable throwable){
        // Remove the client with its id.
        System.out.println("Websocket client error:" + throwable.getMessage());
        clientSessions.remove(session.getId());
    }

    // Define a function to send a message to one client with its id.
    public void unicast(String clientId,String message){
        Session session = clientSessions.get(clientId);
        if (session != null && session.isOpen()) {
            session.getAsyncRemote().sendText(message);
        }
    }

    // Define a function to send a message to all clients.
    public void broadcast(String message,String clientId){
        for (Session session : clientSessions.values()) {
            if (session != null && session.isOpen() && !session.getId().equals(clientId)) {
                session.getAsyncRemote().sendText(message);
            }
        }
    }

    // Define a function to close connection with one client.
    public void closeClientConnection(String clientId){
        Session session = clientSessions.get(clientId);
        if (session != null && session.isOpen()) {
            try {
                session.close(new CloseReason(CloseReason.CloseCodes.NORMAL_CLOSURE, "Closing connection with " + clientId));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    // Define a function to handle the message information
    public void setInformation(String str){
        userName = str.substring(0, str.indexOf(":"));
        messageContent = str.substring(str.indexOf(":") + 1);
    }
}
