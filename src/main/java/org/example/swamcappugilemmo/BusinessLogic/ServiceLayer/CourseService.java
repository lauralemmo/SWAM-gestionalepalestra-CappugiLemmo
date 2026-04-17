package org.example.swamcappugilemmo.BusinessLogic.ServiceLayer;

import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.example.swamcappugilemmo.BusinessLogic.ControllerLayer.CourseController;
import org.example.swamcappugilemmo.BusinessLogic.DTO.CourseDTO;


@Path("/courses")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)

public class CourseService {

    @Inject
    private CourseController courseController;


    @POST
    @Path("/register")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response registerCourse(CourseDTO dto){
        try{
            courseController.addCourse(dto);

            return Response.status(Response.Status.CREATED).build();
        } catch(Exception e){
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        }
    }

    @GET
    @Path("/name")
    public Response getCourseName(@QueryParam("name") Long idCourse){
        try{
            CourseDTO response = courseController.getCourseById(idCourse);
            return Response.ok(response).build();
        } catch(IllegalArgumentException e){
            return Response.status(Response.Status.NOT_FOUND).entity(e.getMessage()).build();
        }
    }




}
