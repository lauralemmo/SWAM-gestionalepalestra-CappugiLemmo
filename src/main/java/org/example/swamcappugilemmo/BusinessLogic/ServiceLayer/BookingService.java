package org.example.swamcappugilemmo.BusinessLogic.ServiceLayer;

import jakarta.inject.Inject;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.PessimisticLockException;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.SecurityContext;
import org.example.swamcappugilemmo.BusinessLogic.ControllerLayer.BookingController;
import org.example.swamcappugilemmo.BusinessLogic.DTO.BookingRequestDTO;
import org.example.swamcappugilemmo.BusinessLogic.DTO.BookingResponseDTO;
import org.example.swamcappugilemmo.BusinessLogic.DTO.ExerciseWorkoutPlanRequestDTO;
import org.example.swamcappugilemmo.BusinessLogic.DTO.ExerciseWorkoutPlanResponseDTO;
import org.example.swamcappugilemmo.Security.Secured;

import java.util.List;

@Path("/bookings")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class BookingService {

    @Inject
    private BookingController bookingController;

//=================================================POST=================================================

    @POST
    @Secured({"ATHLETE"})
    @Path("/register")
    public Response registerBooking(BookingRequestDTO request) {
        try {
            bookingController.createBooking(request);

            return Response.status(Response.Status.CREATED)
                    .entity("Prenotazione registrata con successo")
                    .build();
        } catch (PessimisticLockException e) {
            // Il lock è fallito o è andato in timeout
            return Response.status(Response.Status.CONFLICT)
                    .entity("Il sistema è sovraccarico di prenotazioni. Riprova tra qualche istante.")
                    .build();
        } catch (IllegalArgumentException e) {
            // Gestisce errori di validazione (es. atleta o corso non trovati)
            return Response.status(Response.Status.NOT_FOUND)
                    .entity(e.getMessage())
                    .build();
        } catch (Exception e) {
            // Gestisce errori generici del server
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("Errore nella registrazione della prenotazione: " + e.getMessage())
                    .build();
        }
    }

//=================================================GET=================================================

    @GET
    @Secured ({"ATHLETE", "ADMIN"})
    @Path("/{bookingId}")
    public Response getBooking(@PathParam("bookingId") Long bookingId) {
        try {
            // Delega al controller il recupero della prenotazione
            BookingResponseDTO response = bookingController.getBookingDTOfromId(bookingId);
            return Response.ok(response).build();
        } catch (IllegalArgumentException e) {
            // Se la prenotazione non esiste, restituisce 404 Not Found
            return Response.status(Response.Status.NOT_FOUND)
                    .entity(e.getMessage())
                    .build();
        } catch (Exception e) {
            // Gestisce errori generici del server
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("Errore nel recupero della prenotazione: " + e.getMessage())
                    .build();
        }
    }

    @GET
    @Path("/my-bookings")
    @Secured({"ATHLETE"})
    public Response getMyBookings(@Context SecurityContext securityContext) {
        try {
            // Chi cha fatto la richiesta
            String callerUsername = securityContext.getUserPrincipal().getName();

            // Chiediamo al controller la lista delle prenotazioni
            List<BookingResponseDTO> myBookings = bookingController.getBookingsByAthleteUsername(callerUsername);

            // Restituiamo la lista al frontend
            return Response.ok(myBookings).build();

        } catch (IllegalArgumentException e) {
            return Response.status(Response.Status.NOT_FOUND).entity(e.getMessage()).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Errore nel recupero delle prenotazioni: " + e.getMessage())
                    .build();
        }
    }




    @PUT
    @Path("/{id}")
    public Response updateBooking(@PathParam("id") Long id, BookingRequestDTO request) {
        try{
            BookingResponseDTO response = bookingController.updateBooking(request, id);
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
    @Path("/{bookingId}/cancel")
    @Secured({"ATHLETE"})
    public Response deleteBooking(@PathParam("bookingId") Long bookingId, @Context SecurityContext securityContext) {
        try {
            // Chi ha fatto la richiesta
            String callerUsername = securityContext.getUserPrincipal().getName();
            bookingController.deleteBooking(bookingId, callerUsername);
            return Response.ok("Prenotazione cancellata con successo").build();

        }catch (SecurityException e) {
            return Response.status(Response.Status.FORBIDDEN).entity(e.getMessage()).build();
        } catch (IllegalArgumentException e) {
            return Response.status(Response.Status.NOT_FOUND).entity(e.getMessage()).build();
        } catch (PessimisticLockException e) {
            return Response.status(Response.Status.CONFLICT)
                    .entity("Il sistema è temporaneamente occupato. Riprova tra qualche istante.")
                    .build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Errore durante la cancellazione: " + e.getMessage())
                    .build();
        }

    }


}

