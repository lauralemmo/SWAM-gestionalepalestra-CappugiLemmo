package org.example.swamcappugilemmo.BusinessLogic.ServiceLayer;


import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.example.swamcappugilemmo.BusinessLogic.ControllerLayer.PersonalTrainerController;
import org.example.swamcappugilemmo.BusinessLogic.DTO.PersonalTrainerRequestDTO;
import org.example.swamcappugilemmo.BusinessLogic.DTO.PersonalTrainerResponseDTO;

@Path("/personaltrainers")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class PersonalTrainerService {
    @Inject
    private PersonalTrainerController personalTrainerController;

    @GET
    @Path("/{taxCode}")
    public Response getPersonalTrainer(@PathParam("taxCode") String taxCode) {
        try{
            PersonalTrainerResponseDTO response = personalTrainerController.getPersonalTrainerByTaxCode(taxCode);
            return Response.status(Response.Status.OK).entity(response).build();
        } catch (IllegalArgumentException e){
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        }

    }

    @POST
    @Path("/register")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response registerPersonalTrainer(PersonalTrainerRequestDTO personalTrainerDTO) {
        try {
            personalTrainerController.addPersonalTrainer(personalTrainerDTO);
            return Response.status(Response.Status.CREATED).entity(personalTrainerDTO).build();
        }
        catch (IllegalArgumentException e){
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        }
    }
}
