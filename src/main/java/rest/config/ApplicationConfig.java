package rest.config;

import jakarta.websocket.Endpoint;
import jakarta.websocket.server.ServerEndpointConfig;
import jakarta.ws.rs.ApplicationPath;
import jakarta.ws.rs.core.Application;


import java.util.HashSet;
import java.util.Set;


import rest.HelloResource;
import rest.UserResource;
import security.ChatEndpoint;
import security.JWTAuthenticationFilter;
import security.JWTSecurityContext;
import security.RolesAllowedFilter;

import java.util.Set;

@ApplicationPath("/api")
public class ApplicationConfig extends Application {

    @Override
    public Set<Class<?>> getClasses() {
        Set<Class<?>> resources = new java.util.HashSet<>();
        addRestResourceClasses(resources);
        return resources;
    }






    private void addRestResourceClasses(Set<Class<?>> resources) {
        resources.add(cors.CorsFilter.class);
        resources.add(org.glassfish.jersey.server.wadl.internal.WadlResource.class);
        resources.add(HelloResource.class);
        resources.add(UserResource.class);
        resources.add(security.errorhandling.AuthenticationExceptionMapper.class);
        resources.add(security.errorhandling.NotAuthorizedExceptionMapper.class);
        resources.add(security.LoginEndpoint.class);

        resources.add(JWTAuthenticationFilter.class);
        resources.add(JWTSecurityContext.class);
        resources.add(RolesAllowedFilter.class);


    }
}