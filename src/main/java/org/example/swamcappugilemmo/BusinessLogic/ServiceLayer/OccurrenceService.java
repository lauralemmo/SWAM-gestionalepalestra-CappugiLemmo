package org.example.swamcappugilemmo.BusinessLogic.ServiceLayer;

import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.SecurityContext;
import org.example.swamcappugilemmo.BusinessLogic.ControllerLayer.OccurrenceController;
import org.example.swamcappugilemmo.BusinessLogic.DTO.OccurrenceRequestDTO;
import org.example.swamcappugilemmo.BusinessLogic.DTO.OccurrenceResponseDTO;
import org.example.swamcappugilemmo.Security.Secured;
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
    public Response createOccurrence(OccurrenceRequestDTO requestDTO) {
        try {
            OccurrenceResponseDTO savedOccurrence = occurrenceController.addOccurrence(requestDTO);
            return Response.status(Response.Status.CREATED).entity(savedOccurrence).build();
        }
        catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        }
    }

    @GET
    @Secured({"ATHLETE", "PT", "ADMIN"})
    @Path("/{idOccurrence}")
    public Response getOccurrences(@PathParam("idOccurrence") Long idOccurrence) {
        try{
            OccurrenceResponseDTO occurrenceDTO = occurrenceController.getOccurrenceById(idOccurrence);
            if (occurrenceDTO == null) {
                return Response.status(Response.Status.NOT_FOUND).entity("Occurrence not found").build();
            }
            return  Response.status(Response.Status.OK).entity(occurrenceDTO).build();
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
            OccurrenceRequestDTO updatedData,
            @Context SecurityContext securityContext) {

        try {
            // Leggo chi ha fatto la richiesta
            String callerUsername = securityContext.getUserPrincipal().getName();
            boolean isAdmin = securityContext.isUserInRole("ADMIN");

            OccurrenceResponseDTO updatedDTO = occurrenceController.updateOccurrenceSecurely(
                    idOccurrence, updatedData, callerUsername, isAdmin
            );

            return Response.status(Response.Status.OK).entity(updatedDTO).build();

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

    @DELETE
    @Path("/{idOccurrence}/delete")
    @Secured({"ADMIN"})
    public Response deleteOccurrence(@PathParam("idOccurrence") Long idOccurrence) {
        try {
            OccurrenceResponseDTO deletedOccurrence = occurrenceController.deleteOccurrence(idOccurrence);
            return Response.status(Response.Status.OK).entity(deletedOccurrence).build();
        } catch (IllegalArgumentException e) {
            return Response.status(Response.Status.NOT_FOUND).entity(e.getMessage()).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Errore: " + e.getMessage()).build();
        }
    }

}