package org.example.swamcappugilemmo.BusinessLogic.ServiceLayer;

import jakarta.inject.Inject;
import jakarta.persistence.EntityNotFoundException;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.example.swamcappugilemmo.BusinessLogic.ControllerLayer.CourseController;
import org.example.swamcappugilemmo.BusinessLogic.DTO.CourseRequestDTO;
import org.example.swamcappugilemmo.BusinessLogic.DTO.CourseResponseDTO;
import org.example.swamcappugilemmo.BusinessLogic.DTO.ExerciseResponseDTO;
import org.example.swamcappugilemmo.Security.Secured;


@Path("/courses")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)

public class CourseService {

    @Inject
    private CourseController courseController;


    @POST
    @Path("/register")
    @Secured({"ADMIN"})
    public Response registerCourse(CourseRequestDTO request){
        try {
            CourseResponseDTO response = courseController.addCourse(request);
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
    @Path("/name")
    //@Secured({"ADMIN", "ATHLETE", "PERSONAL_TRAINER"}) non serve dato che è una info pubblica
    public Response getCourseName(@QueryParam("name") Long idCourse){
        try{
            CourseResponseDTO response = courseController.getCourseById(idCourse);
            return Response.ok(response).build();
        } catch(IllegalArgumentException e){
            return Response.status(Response.Status.NOT_FOUND).entity(e.getMessage()).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
        }
    }


    @PUT
    @Path("/update/{id}")
    @Secured({"ADMIN"})
    public Response updateCourse(@PathParam("id") Long id, CourseRequestDTO dto){
        try{
            CourseResponseDTO response = courseController.updateCourse(id, dto);
            return Response.status(Response.Status.OK)
                    .entity(response)
                    .build();
        } catch (IllegalArgumentException e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
        }
    }

    @DELETE
    @Path("/{id}/delete")
    @Secured({"ADMIN"})
    public Response deleteCourse(@PathParam("id") Long id) {
        try {
            courseController.deleteCourse(id);
            return Response.noContent().build();
        } catch (IllegalArgumentException e) {
            return Response.status(Response.Status.NOT_FOUND).entity(e.getMessage()).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
        }
    }
}