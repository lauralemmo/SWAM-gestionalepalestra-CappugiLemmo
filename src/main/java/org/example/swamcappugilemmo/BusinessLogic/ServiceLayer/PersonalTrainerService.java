package org.example.swamcappugilemmo.BusinessLogic.ServiceLayer;


import jakarta.inject.Inject;
import jakarta.persistence.EntityNotFoundException;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.example.swamcappugilemmo.BusinessLogic.ControllerLayer.PersonalTrainerController;
import org.example.swamcappugilemmo.BusinessLogic.DTO.PersonalTrainerRequestDTO;
import org.example.swamcappugilemmo.BusinessLogic.DTO.PersonalTrainerResponseDTO;
import org.example.swamcappugilemmo.Security.JwtUtil;

import java.util.List;
import java.util.Map;


@Path("/personaltrainer")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class PersonalTrainerService {
    @Inject
    private PersonalTrainerController personalTrainerController;


    @GET
    @Path("/{id}")
    public Response getPersonalTrainerById(@PathParam("id") Long id) {
        try{
            PersonalTrainerResponseDTO response = personalTrainerController.getPersonalTrainerById(id);
            return Response.status(Response.Status.OK)
                    .entity(response)
                    .build();
        } catch (EntityNotFoundException e){
            return Response.status(Response.Status.NOT_FOUND)
                    .entity(e.getMessage())
                    .build();
        }catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Errore interno del server")
                    .build();
        }

    }


    @GET
    public Response getAllPersonalTrainers(){
        try{
            List<PersonalTrainerResponseDTO> personalTrainers = personalTrainerController.getAllPersonalTrainers();
            return Response.status(Response.Status.OK)
                    .entity(personalTrainers)
                    .build();
        } catch(Exception e){
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Errore interno del server")
                    .build();
        }
    }


    @POST
    public Response registerPersonalTrainer(PersonalTrainerRequestDTO request) {
        try {
            PersonalTrainerResponseDTO response = personalTrainerController.addPersonalTrainer(request);
            return Response.status(Response.Status.CREATED)
                    .entity(response)
                    .build();
        }
        catch (IllegalArgumentException e){
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(e.getMessage())
                    .build();
        }
    }


    @PUT
    @Path("/{id}")
    public Response updatePersonalTrainer(@PathParam("id") Long id, PersonalTrainerRequestDTO request) {
        try {
            PersonalTrainerResponseDTO response = personalTrainerController.updatePersonalTrainer(id, request);
            return Response.ok(response)
                    .build();
        } catch (EntityNotFoundException e) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity(e.getMessage())
                    .build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Errore interno del server")
                    .build();
        }
    }


    @DELETE
    @Path("/{id}")
    public Response deletePersonalTrainer(@PathParam("id") Long id) {
        try {
            personalTrainerController.deletePersonalTrainer(id);
            return Response.noContent().build();
        } catch (EntityNotFoundException e) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity(e.getMessage())
                    .build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Errore interno del server")
                    .build();
        }
    }


}
