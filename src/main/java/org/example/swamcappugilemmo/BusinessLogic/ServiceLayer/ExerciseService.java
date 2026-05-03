package org.example.swamcappugilemmo.BusinessLogic.ServiceLayer;

import jakarta.inject.Inject;
import jakarta.persistence.EntityNotFoundException;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.example.swamcappugilemmo.BusinessLogic.ControllerLayer.ExerciseController;
import org.example.swamcappugilemmo.BusinessLogic.DTO.ExerciseRequestDTO;
import org.example.swamcappugilemmo.BusinessLogic.DTO.ExerciseResponseDTO;
import org.example.swamcappugilemmo.BusinessLogic.DTO.WorkoutPlanRequestDTO;
import org.example.swamcappugilemmo.BusinessLogic.DTO.WorkoutPlanResponseDTO;

import java.util.List;

@Path("/exercise")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ExerciseService {
    @Inject
    private ExerciseController eController;


    @POST
    public Response createExercise(ExerciseRequestDTO request) {
        try {
            ExerciseResponseDTO response = eController.createExercise(request);
            return Response.status(Response.Status.CREATED)
                    .entity(response)
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


    @GET
    public Response getAllExercises(){
        try{
            List<ExerciseResponseDTO> es = eController.getAllExercises();
            return Response.ok(es).build();
        } catch(Exception e){
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("errore interno del server")
                    .build();
        }
    }


    @GET
    @Path("/{id}")
    public Response getExerciseById(@PathParam("id") Long id) {
        try {
            ExerciseResponseDTO response = eController.getExerciseById(id);
            return Response.ok(response).build();
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


    @PUT
    @Path("/{id}")
    public Response updateExercise(@PathParam("id") Long id, ExerciseRequestDTO request) {
        try {
            ExerciseResponseDTO response = eController.updateExercise(id, request);
            return Response.ok(response).build();
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
    public Response deleteExercise(@PathParam("id") Long id) {
        try {
            eController.deleteExercise(id);
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
