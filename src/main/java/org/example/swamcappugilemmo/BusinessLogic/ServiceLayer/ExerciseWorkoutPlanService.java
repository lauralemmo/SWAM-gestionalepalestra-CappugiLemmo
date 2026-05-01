package org.example.swamcappugilemmo.BusinessLogic.ServiceLayer;


import jakarta.inject.Inject;
import jakarta.persistence.EntityNotFoundException;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.example.swamcappugilemmo.BusinessLogic.ControllerLayer.ExerciseWorkoutPlanController;
import org.example.swamcappugilemmo.BusinessLogic.DTO.ExerciseWorkoutPlanRequestDTO;
import org.example.swamcappugilemmo.BusinessLogic.DTO.ExerciseWorkoutPlanResponseDTO;
import org.example.swamcappugilemmo.BusinessLogic.DTO.WorkoutPlanRequestDTO;
import org.example.swamcappugilemmo.BusinessLogic.DTO.WorkoutPlanResponseDTO;
import org.example.swamcappugilemmo.DomainModel.ExerciseWorkoutPlan;

@Path("/exerciseWorkoutPlan")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ExerciseWorkoutPlanService {
    @Inject
    private ExerciseWorkoutPlanController ewpController;


    @POST
    public Response createExerciseWorkoutPlan(ExerciseWorkoutPlanRequestDTO request) {
        try{
            ExerciseWorkoutPlanResponseDTO response = ewpController.addExerciseToPlan(request);
            return Response.status(Response.Status.CREATED)
                    .entity(response)
                    .build();
        }catch (EntityNotFoundException e) {
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
    @Path("/{id}")
    public Response getExerciseWorkoutPlanById(@PathParam("id") Long id) {
        try{
            ExerciseWorkoutPlanResponseDTO response = ewpController.getEwpById(id);
            return Response.ok(response).build();
        }catch (EntityNotFoundException e) {
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
    public Response updateExerciseWorkoutPlan(@PathParam("id") Long id, ExerciseWorkoutPlanRequestDTO request) {
        try{
            ExerciseWorkoutPlanResponseDTO response = ewpController.updateExerciseInPlan(id, request);
            return Response.ok(response).build();
        }catch (EntityNotFoundException e) {
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
    public Response deleteExerciseWorkoutPlan(@PathParam("id") Long id) {
        try{
            ewpController.deleteExerciseInPlan(id);
            return Response.noContent().build();
        }catch (EntityNotFoundException e) {
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
