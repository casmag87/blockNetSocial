package rest.config;

import jakarta.websocket.Endpoint;
import jakarta.websocket.server.ServerApplicationConfig;
import jakarta.websocket.server.ServerEndpointConfig;
import security.ChatEndpoint;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

// This class implements the ServerApplicationConfig interface, which is used to configure
// the WebSocket endpoints for the application.
public class WebSocketConfig implements ServerApplicationConfig {

    // This method is responsible for returning the server endpoint configurations.
    // In this case, we don't need any specific configurations, so we return an empty set.
    @Override
    public Set<ServerEndpointConfig> getEndpointConfigs(Set<Class<? extends Endpoint>> endpointClasses) {
        // Returning an empty set as we don't require any additional endpoint configurations.
        return Collections.emptySet();
    }

    // This method is responsible for returning the annotated endpoint classes.
    // Annotated endpoint classes are automatically discovered by the WebSocket container.
    @Override
    public Set<Class<?>> getAnnotatedEndpointClasses(Set<Class<?>> scanned) {
        // Include the ChatEndpoint class for automatic discovery.
        // When the WebSocket container scans the application, it will find and register the ChatEndpoint class.
        Set<Class<?>> annotatedEndpointClasses = new HashSet<>();
        annotatedEndpointClasses.add(ChatEndpoint.class);
        return annotatedEndpointClasses;
    }
}