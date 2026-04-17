package org.example.swamcappugilemmo.BusinessLogic.ServiceLayer;

import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.example.swamcappugilemmo.BusinessLogic.ControllerLayer.OccurrenceController;
import org.example.swamcappugilemmo.DomainModel.Occurrence;


@Path("/occurrences")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)

public class OccurrenceService {
    @Inject
    OccurrenceController occurrenceController;

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createOccurrence(Occurrence occurrence) {
        try {
            occurrenceController.addOccurrence(occurrence.getCourse().getId(), occurrence.getDate(), occurrence.getHours());
            return Response.status(Response.Status.CREATED).entity(occurrence).build();
        }
        catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        }

    }

    @GET
    @Path("/{idOccurrence}")
    public Response getOccurrences(@PathParam("idOccurrence") Long idOccurrence) {
        try{
            Occurrence occurrence = occurrenceController.getOccurrenceById(idOccurrence);
            if (occurrence == null) {
                return Response.status(Response.Status.NOT_FOUND).entity("Occurrence not found").build();
            }
            return  Response.status(Response.Status.OK).entity(occurrence).build();
        }
        catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        }
    }


}
