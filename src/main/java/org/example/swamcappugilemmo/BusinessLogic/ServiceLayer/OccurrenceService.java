package org.example.swamcappugilemmo.BusinessLogic.ServiceLayer;

import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.example.swamcappugilemmo.BusinessLogic.ControllerLayer.OccurrenceController;
import org.example.swamcappugilemmo.DomainModel.Occurrence;
import org.example.swamcappugilemmo.Security.Secured;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.ws.rs.core.Context;


@Path("/occurrences")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)

public class OccurrenceService {
    @Inject
    OccurrenceController occurrenceController;

    @POST
    @Secured({"ADMIN"})
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createOccurrence(Occurrence occurrence) {
        try {
            occurrenceController.addOccurrence(occurrence.getCourse().getIdCourse(), occurrence.getDate(), occurrence.getHours());
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

    @PUT
    @Path("/{idOccurrence}/update")
    @Secured({"ADMIN", "PT"})
    public Response updateOccurrence(
            @PathParam("idOccurrence") Long idOccurrence,
            Occurrence updatedData,
            @Context HttpServletRequest request) {

        try {
            // Leggo chi ha fatto la richiesta
            Long callerId = (Long) request.getAttribute("caller_id");
            String callerRole = (String) request.getAttribute("caller_role");
            occurrenceController.updateOccurrenceSecurely(
                    idOccurrence, updatedData, callerId, callerRole
            );

            return Response.status(Response.Status.OK).entity("Orario aggiornato con successo").build();

        } catch (SecurityException e) {
            // Eccezione di sicurezza se l'utente non ha i permessi
            return Response.status(Response.Status.FORBIDDEN).entity(e.getMessage()).build();
        } catch (IllegalArgumentException e) {
            // Se i dati non si trovano
            return Response.status(Response.Status.NOT_FOUND).entity(e.getMessage()).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Errore: " + e.getMessage()).build();
        }
    }


}
