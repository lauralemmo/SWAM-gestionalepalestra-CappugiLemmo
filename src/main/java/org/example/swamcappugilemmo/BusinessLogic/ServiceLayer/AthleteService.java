package org.example.swamcappugilemmo.BusinessLogic.ServiceLayer;

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
    @Path("/login")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response login(LoginDTO loginRequest) {
        try {
            AthleteResponseDTO response = athleteController.loginAthlete(
                    loginRequest.getUsername(),
                    loginRequest.getPassword()
            );
            // Se il login è riuscito, genera un token JWT e includilo nella risposta
            String token = JwtUtil.generateToken(response.getUsername());
            return Response.ok(response)
                    .entity(Map.of("token", token, "user", response))
                    .build();
        } catch (IllegalArgumentException e) {
            return Response.status(Response.Status.UNAUTHORIZED)
                    .entity(e.getMessage())
                    .build();
        }
    }

    @POST
    @Path("/registerNewSubscription")
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
    @Secured
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
    @Secured
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
    @Path("/{username}/delete")
    public Response deleteAthlete(@PathParam("username") String username) {
        try {
            athleteController.deleteAthlete(username);
            return Response.status(Response.Status.OK).build();
        } catch (IllegalArgumentException e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
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
    @Path("/{taxCode}/update")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateAthleteUsername(@PathParam("taxCode") String taxCode, Map<String, String> body) {
        try {
            String newUsername = body.get("newUsername");
            athleteController.updateAthleteUsername(taxCode, newUsername);
            return Response.status(Response.Status.OK).build();
        } catch (IllegalArgumentException e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        }
    }

    @PUT
    @Path("/{id}/update")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateAthleteUsername(@PathParam("id") Long id, String request) {
        try {
            athleteController.updateAthleteUsername(id, request);
            return Response.status(Response.Status.OK).build();
        } catch (IllegalArgumentException e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        }
    }
}
