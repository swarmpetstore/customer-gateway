package org.packt.swarm.petstore.security;

import org.keycloak.KeycloakPrincipal;

import javax.ws.rs.client.ClientRequestContext;
import javax.ws.rs.client.ClientRequestFilter;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.container.ContainerResponseFilter;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.SecurityContext;
import javax.ws.rs.ext.Provider;
import java.io.IOException;

@Provider
public class BearerTokenFilter implements ClientRequestFilter {

    private static final String BEARER = "Bearer";

    @Context
    SecurityContext securityContext;

    @Override
    public void filter(ClientRequestContext requestContext) throws IOException {
        KeycloakPrincipal keycloakPrincipal = (KeycloakPrincipal) securityContext.getUserPrincipal();
        System.out.println("KEYCLOAK PRINCIPAL TO "+keycloakPrincipal);
        if(keycloakPrincipal != null && keycloakPrincipal.getKeycloakSecurityContext()!=null) {
            String token = keycloakPrincipal.getKeycloakSecurityContext().getTokenString();
            if(token != null) {
                requestContext.getHeaders().add(HttpHeaders.AUTHORIZATION, BEARER + " " + token);
                System.out.println("PRZESLALEM DALEJ TOKENA "+token);
            }
        }
    }
}