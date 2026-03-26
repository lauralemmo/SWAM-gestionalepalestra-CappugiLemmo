package org.example.swamcappugilemmo.BusinessLogic.ServiceLayer;

import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.example.swamcappugilemmo.BusinessLogic.ControllerLayer.BookingController;
import org.example.swamcappugilemmo.BusinessLogic.DTO.BookingDTO;

@Path("/bookings")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class BookingService {

    @Inject
    private BookingController bookingController;

//=================================================POST=================================================

    @POST
    @Path("/register")
    public Response registerBooking(BookingDTO request) {
        try {
            // Delega la logica di creazione al controller
            bookingController.createBooking(request);

            return Response.status(Response.Status.CREATED)
                    .entity("Prenotazione registrata con successo")
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

}