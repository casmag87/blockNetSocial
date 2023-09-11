package security;

import com.nimbusds.jose.shaded.gson.Gson;
import com.nimbusds.jose.shaded.gson.GsonBuilder;
import entities.Message;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.websocket.OnClose;
import jakarta.websocket.OnMessage;
import jakarta.websocket.OnOpen;
import jakarta.websocket.Session;
import jakarta.websocket.server.ServerEndpoint;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.SecurityContext;
import jakarta.ws.rs.core.UriInfo;
import service.UserFacade;

import java.io.IOException;
import java.util.Collections;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

// This class represents the WebSocket endpoint for handling chat messages.
// The endpoint URL for this class is "/chat".
@ServerEndpoint("/chat")
public class ChatEndpoint {
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    // A synchronized set to store all active WebSocket sessions.

    private static final Set<Session> sessions = Collections.synchronizedSet(new HashSet<>());



    @Context
    private UriInfo context;
    @Context
    SecurityContext securityContext;
    // This method is called when a new WebSocket connection is established.



    @OnOpen
    public void onOpen(Session session) {
        // Add the new session to the set of active sessions.

        sessions.add(session);

    }

    // This method is called when a new message is received from a WebSocket client.
    @OnMessage
    public void onMessage(String message, Session session) {




        // Loop through all active sessions and send the received message to each of them.
        for (Session s : sessions) {
            try {
                // Send the message to the client represented by this session.
                s.getBasicRemote().sendText(message);

                // Print the message to the server console for debugging purposes.
                System.out.println( message);
            } catch (IOException e) {
                // If an exception occurs while sending the message, print the stack trace for debugging.
                e.printStackTrace();
            }
        }
    }

    // This method is called when a WebSocket connection is closed.
    @OnClose
    public void onClose(Session session) {
        // Remove the closed session from the set of active sessions.
        sessions.remove(session);
    }
}
