package org.example.swamcappugilemmo.Security;

import io.jsonwebtoken.Claims;
import jakarta.annotation.Priority;
import jakarta.ws.rs.Priorities;
import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.container.ContainerRequestFilter;
import jakarta.ws.rs.container.ResourceInfo;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.HttpHeaders;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.Provider;

import java.lang.reflect.Method;
import java.util.Arrays;

@Secured // Associa questo buttafuori al cartello "Secured"
@Provider // Dice al server JAX-RS che questo è un componente attivo
@Priority(Priorities.AUTHENTICATION) // Deve essere eseguito prima di tutto il resto
public class AuthenticationFilter implements ContainerRequestFilter {
    @Context
    private ResourceInfo resourceInfo;

    @Override
    public void filter(ContainerRequestContext requestContext) {
        // Prendi l'header "Authorization" dalla richiesta HTTP
        String authorizationHeader = requestContext.getHeaderString(HttpHeaders.AUTHORIZATION);

        // Controlla se c'è e se inizia con "Bearer " (standard per i token JWT)
        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
            requestContext.abortWith(Response.status(Response.Status.UNAUTHORIZED).entity("Manca il Token di autorizzazione").build());
            return;
        }

        // Estrai solo la stringa del token, togliendo la parola "Bearer "
        String token = authorizationHeader.substring("Bearer".length()).trim();

        try {
            // Valida il token e ottieni le informazioni (Claims)
            Claims claims = JwtUtil.validateTokenAndGetClaims(token);
            String userRole = claims.get("role", String.class); // Legge il ruolo dal token
            Long id = claims.get("id", Long.class);
            // Salva l'ID e il ruolo dell'utente nel contesto della richiesta per usarli dopo nei servizi
            requestContext.setProperty("caller_id", id);
            requestContext.setProperty("caller_role", userRole);

            // Scopri quale metodo sta cercando di chiamare l'utente
            Method method = resourceInfo.getResourceMethod();
            Secured securedAnnotation = method.getAnnotation(Secured.class);

            if (securedAnnotation != null) {
                String[] allowedRoles = securedAnnotation.value(); // Es: ["ADMIN", "PT"]

                // Se il metodo richiede ruoli specifici, controlla se l'utente ce l'ha!
                if (allowedRoles.length > 0) {
                    boolean hasRole = Arrays.asList(allowedRoles).contains(userRole);
                    if (!hasRole) {
                        // L'utente ha un token valido, ma non è del ruolo giusto
                        requestContext.abortWith(Response.status(Response.Status.FORBIDDEN)
                                .entity("Accesso negato: non hai i permessi per questa operazione").build());
                    }
                }
            }

        } catch (Exception e) {
            requestContext.abortWith(Response.status(Response.Status.UNAUTHORIZED).entity("Token non valido o scaduto").build());
        }
    }
}