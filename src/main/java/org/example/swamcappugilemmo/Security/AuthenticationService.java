package org.example.swamcappugilemmo.Security;

import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.example.swamcappugilemmo.Security.AuthenticationController;
import org.example.swamcappugilemmo.BusinessLogic.DTO.LoginDTO;

import java.util.Map;

@Path("/auth")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class AuthenticationService {

    @Inject
    private AuthenticationController authController;

    @POST
    @Path("/login")
    public Response login(LoginDTO loginRequest) {
        try {
            Map<String, Object> response = authController.login(loginRequest.getUsername(), loginRequest.getPassword());
            return Response.ok(response).build();
        } catch (IllegalArgumentException e) {
            return Response.status(Response.Status.UNAUTHORIZED).entity(e.getMessage()).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Errore interno").build();
        }
    }

}