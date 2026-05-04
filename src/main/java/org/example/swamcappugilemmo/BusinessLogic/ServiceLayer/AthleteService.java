package org.example.swamcappugilemmo.BusinessLogic.ServiceLayer;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.SecurityContext;
import org.example.swamcappugilemmo.BusinessLogic.ControllerLayer.AthleteController;
import org.example.swamcappugilemmo.BusinessLogic.ControllerLayer.SubscriptionController;
import org.example.swamcappugilemmo.BusinessLogic.DTO.*;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.example.swamcappugilemmo.Security.JwtUtil;
import org.example.swamcappugilemmo.Security.Secured;

import java.util.List;
import java.util.Map;

@Path("/athletes")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class AthleteService {

    @Inject
    private AthleteController athleteController;
    @Inject
    private SubscriptionController subscriptionController;

    //=================================================POST=================================================
    @POST
    @Path("/register")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response registerAthlete(AthleteRequestDTO request) {
        /*try {
            // Estrai i dati dal tuo DTO e passali al controller
            athleteController.registerNewAthlete(request);

            return Response.status(Response.Status.CREATED).build();
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        }*/
        try{
            AthleteResponseDTO response = athleteController.registerNewAthlete(request);
            return Response.status(Response.Status.CREATED)
                    .entity(response)
                    .build();
        } catch(Exception e){
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(e.getMessage())
                    .build();
        }
    }



    @POST
    @Path("/registerNewSubscription")
    @Secured({"ADMIN"})
    @Consumes(MediaType.APPLICATION_JSON)
    public Response registerNewSubscription(SubscriptionRequestDTO request) {
        try {
            subscriptionController.crateNewSubscription(request);
            return Response.status(Response.Status.OK).build();
        } catch (IllegalArgumentException | IllegalStateException e) { // <-- Aggiunto IllegalStateException
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Errore interno: " + e.getMessage()).build();
        }
    }

    //=================================================GET=================================================
    @GET
    @Secured({"ADMIN"})
    @Path("/all")
    public Response getAllAthletes() {
        try{
            List<AthleteResponseDTO> athletes = athleteController.getAllAthletes();
            return Response.ok(athletes).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Errore interno del server")
                    .build();
        }
    }


    @GET
    @Secured({"ADMIN"})
    @Path("/id/{id}")
    public Response getAthleteById(@PathParam("id") Long id) {
        try {
            AthleteResponseDTO response = athleteController.getAthleteById(id);
            return Response.ok(response).build();
        } catch (IllegalArgumentException e) {
            return Response.status(Response.Status.NOT_FOUND).entity(e.getMessage()).build();
        }
    }

    @GET
    @Secured({"ADMIN"})
    @Path("/{taxCode}")
    public Response getAthlete(@PathParam("taxCode") String taxCode) {
        try {
            // Delega al controller il recupero dell'atleta
            AthleteResponseDTO response = athleteController.getAthleteByTaxCode(taxCode);
            return Response.ok(response).build();
        } catch (IllegalArgumentException e) {
            // Se l'atleta non esiste, restituisce 404 Not Found
            return Response.status(Response.Status.NOT_FOUND).entity(e.getMessage()).build();
        }
    }


    @GET
    @Path("/{taxCode}/activeSubscription")
    @Secured({"ATHLETE", "ADMIN"})
    public Response getActiveSubscription(@PathParam("taxCode") String taxCode) {
        try {
            // Iniezione di SubscriptionController anziché AthleteController
            SubscriptionResponseDTO activeSubscription = subscriptionController.getActiveSubscriptionDTO(taxCode);

            if (activeSubscription == null) {
                return Response.status(Response.Status.NOT_FOUND)
                        .entity("Nessun abbonamento attivo trovato")
                        .build();
            }
            return Response.ok(activeSubscription).build();
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        }
    }

    @DELETE
    @Secured({"ADMIN"})
    @Path("/{id}/delete")
    public Response deleteAthlete(@PathParam("id") Long id) {
        try {
            // Usiamo il metodo corretto che prende il Long id
            athleteController.deleteAthleta(id);
            return Response.status(Response.Status.OK).build();
        } catch (IllegalArgumentException e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
        }
    }

//=================================================PUT=================================================
    /*@POST
    @Path("/registerNewSubscription")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response registerNewSubscription(SubscriptionRequestDTO request) {
        try {
            athleteController.addNewSubscriptionToAthlete(request.getTaxCode(), request.getStartDate(), request.getEndDate());
            return Response.status(Response.Status.OK).build();
        } catch (IllegalArgumentException e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        }
    }*/

    @PUT
    @Path("/{username}/update")
    @Secured({"ATHLETE", "ADMIN"})
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateAthleteUsername(
            @PathParam("username") String username,
            Map<String, String> body,
            @Context SecurityContext securityContext) {

        try {
            // Estraiamo i dati dal contesto e dal body (Lavoro del Service)
            String callerUsername = securityContext.getUserPrincipal().getName();
            boolean isAdmin = securityContext.isUserInRole("ADMIN");
            String newUsername = body.get("newUsername");

            athleteController.updateAthleteUsernameSecurely(username, newUsername, callerUsername, isAdmin);
            return Response.status(Response.Status.OK).build();

        } catch (SecurityException e) {
            // Se il controller blocca l'utente, intercettiamo e restituiamo 403 Forbidden
            return Response.status(Response.Status.FORBIDDEN).entity(e.getMessage()).build();
        } catch (IllegalArgumentException e) {
            // Se c'è un errore nei dati, restituiamo 400 Bad Request
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        } catch (Exception e) {
            // Rete di sicurezza per altri errori
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Errore interno del server").build();
        }
    }

    @PUT
    @Path("/id/{id}/profile")
    @Secured({"ATHLETE", "ADMIN"})
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateAthleteProfile(
            @PathParam("id") Long id,
            AthleteRequestDTO requestDTO,
            @Context SecurityContext securityContext) {

        try {
            // Estraiamo i dati dal contesto
            String callerUsername = securityContext.getUserPrincipal().getName();
            boolean isAdmin = securityContext.isUserInRole("ADMIN");

            AthleteResponseDTO updatedAthlete = athleteController.updateAthleteProfile(id, requestDTO, callerUsername, isAdmin);
            
            return Response.status(Response.Status.OK).entity(updatedAthlete).build();

        } catch (SecurityException e) {
            // Se il controller blocca l'utente, restituiamo 403 Forbidden
            return Response.status(Response.Status.FORBIDDEN).entity(e.getMessage()).build();
        } catch (IllegalArgumentException e) {
            // Se c'è un errore nei dati o l'atleta non esiste, restituiamo 404
            return Response.status(Response.Status.NOT_FOUND).entity(e.getMessage()).build();
        } catch (Exception e) {
            // Rete di sicurezza per altri errori
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Errore interno del server: " + e.getMessage()).build();
        }
    }

}