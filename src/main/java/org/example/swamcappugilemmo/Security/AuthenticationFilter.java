package org.example.swamcappugilemmo.Security;

import jakarta.annotation.Priority;
import jakarta.ws.rs.Priorities;
import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.container.ContainerRequestFilter;
import jakarta.ws.rs.core.HttpHeaders;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.Provider;

@Secured // Associa questo buttafuori al cartello "Secured"
@Provider // Dice al server JAX-RS che questo è un componente attivo
@Priority(Priorities.AUTHENTICATION) // Deve essere eseguito prima di tutto il resto
public class AuthenticationFilter implements ContainerRequestFilter {

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
            // Chiedi a JwtUtil di controllare se il token è valido
            JwtUtil.validateToken(token);

            // Se arriviamo qui, il token è OK!

        } catch (Exception e) {
            // Il token è falso o scaduto: blocca tutto e restituisci Errore 401
            requestContext.abortWith(Response.status(Response.Status.UNAUTHORIZED).entity("Token non valido o scaduto").build());
        }
    }
}