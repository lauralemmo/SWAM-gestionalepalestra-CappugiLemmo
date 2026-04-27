package org.example.swamcappugilemmo.BusinessLogic.ServiceLayer;


import jakarta.inject.Inject;
import jakarta.persistence.EntityNotFoundException;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.example.swamcappugilemmo.BusinessLogic.ControllerLayer.WorkoutPlanController;
import org.example.swamcappugilemmo.BusinessLogic.DTO.PersonalTrainerResponseDTO;
import org.example.swamcappugilemmo.BusinessLogic.DTO.WorkoutPlanRequestDTO;
import org.example.swamcappugilemmo.BusinessLogic.DTO.WorkoutPlanResponseDTO;

import java.util.List;

@Path("/workoutPlan")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class WorkoutPlanService {
    @Inject
    private WorkoutPlanController wpController;


    @GET
    public Response getAllWorkoutPlans(){
        try{
            List<WorkoutPlanResponseDTO> wps = wpController.getAllWorkoutPlans();
            return Response.ok(wps).build();
        } catch(Exception e){
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("errore interno del server")
                    .build();
        }
    }


    @GET
    @Path("/{id}")
    public Response getWorkoutPlanById(@PathParam("id") Long id) {
        try {
            WorkoutPlanResponseDTO response = wpController.getWorkoutPlanById(id);
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


    @POST
    public Response createWorkoutPlan(WorkoutPlanRequestDTO request) {
        try {
            WorkoutPlanResponseDTO response = wpController.createWorkoutPlan(request);
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


    @PUT
    @Path("/{id}")
    public Response updateWorkoutPlan(@PathParam("id") Long id, WorkoutPlanRequestDTO request) {
        try {
            WorkoutPlanResponseDTO response = wpController.updateWorkoutPlan(id, request);
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
    public Response deleteWorkoutPlan(@PathParam("id") Long id) {
        try {
            wpController.deleteWorkoutPlan(id);
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
