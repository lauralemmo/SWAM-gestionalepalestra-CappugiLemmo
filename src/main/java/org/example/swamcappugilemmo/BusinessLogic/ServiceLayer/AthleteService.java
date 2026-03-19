package org.example.swamcappugilemmo.BusinessLogic.ServiceLayer;

import org.example.swamcappugilemmo.BusinessLogic.ControllerLayer.AthleteController;
import org.example.swamcappugilemmo.BusinessLogic.DTO.AthleteRegistrationRequest;
import org.example.swamcappugilemmo.BusinessLogic.DTO.AthleteResponse;
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

    @Inject
    private AthleteMapper athleteMapper;

    @GET
    @Path("/{taxCode}")
    public Response getAthlete(@PathParam("taxCode") String taxCode) {
        try {
            // Delega al controller il recupero dell'atleta
            Athlete athlete = athleteController.getAthleteByTaxCode(taxCode);
            AthleteResponse response = athleteMapper.toDto(athlete);
            return Response.ok(response).build();
        } catch (IllegalArgumentException e) {
            // Se l'atleta non esiste, restituisce 404 Not Found
            return Response.status(Response.Status.NOT_FOUND).entity(e.getMessage()).build();
        }
    }

    @POST
    @Path("/register")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response registerAthlete(AthleteRegistrationRequest request) {
        try {
            // Estrai i dati dal tuo DTO e passali al controller
            Athlete newAthlete = athleteMapper.toEntity(request);

            athleteController.registerNewAthlete(newAthlete, request.getSubscriptionType(), request.getStartDate());

            return Response.status(Response.Status.CREATED).build();
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        }
    }
}
