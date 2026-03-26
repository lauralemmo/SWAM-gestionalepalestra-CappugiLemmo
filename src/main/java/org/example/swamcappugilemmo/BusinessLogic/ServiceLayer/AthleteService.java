package org.example.swamcappugilemmo.BusinessLogic.ServiceLayer;

import org.example.swamcappugilemmo.BusinessLogic.ControllerLayer.AthleteController;
import org.example.swamcappugilemmo.BusinessLogic.DTO.AthleteRegistrationRequestDTO;
import org.example.swamcappugilemmo.BusinessLogic.DTO.AthleteResponseDTO;
import org.example.swamcappugilemmo.BusinessLogic.DTO.SubscriptionDTO;
import org.example.swamcappugilemmo.BusinessLogic.Mapper.AthleteMapper;
import org.example.swamcappugilemmo.DomainModel.Athlete;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/athletes")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class AthleteService {

    @Inject
    private AthleteController athleteController;

    //=================================================POST=================================================
    @POST
    @Path("/register")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response registerAthlete(AthleteRegistrationRequestDTO request) {
        try {
            // Estrai i dati dal tuo DTO e passali al controller
            athleteController.registerNewAthlete(request);

            return Response.status(Response.Status.CREATED).build();
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        }
    }

    //=================================================GET=================================================
    @GET
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
            AthleteResponseDTO athleteResponse = athleteController.getAthleteByTaxCode(taxCode);
            if (athleteResponse.getSubscriptions() == null || athleteResponse.getSubscriptions().isEmpty()) {
                return Response.status(Response.Status.NOT_FOUND).entity("No subscriptions found for this athlete").build();
            }
            // Assuming the active subscription is the one with the latest end date
            SubscriptionDTO activeSubscription = athleteResponse.getSubscriptions().stream()
                    .filter(sub -> sub.getEndDate() == null || sub.getEndDate().isAfter(java.time.LocalDate.now()))
                    .findFirst()
                    .orElse(null);

            if (activeSubscription == null) {
                return Response.status(Response.Status.NOT_FOUND).entity("No active subscription found for this athlete").build();
            }

            return Response.ok(activeSubscription).build();
        } catch (IllegalArgumentException e) {
            return Response.status(Response.Status.NOT_FOUND).entity(e.getMessage()).build();
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
    public Response updateAthleteUsername(@PathParam("taxCode") String taxCode, String request) {
        try {
            athleteController.updateAthleteUsername(taxCode, request);
            return Response.status(Response.Status.OK).build();
        } catch (IllegalArgumentException e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        }

    }
}
