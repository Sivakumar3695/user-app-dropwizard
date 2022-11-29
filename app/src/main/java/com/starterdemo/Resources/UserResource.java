package com.starterdemo.Resources;

import com.starterdemo.model.User;
import com.starterdemo.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.Map;

@Path("/users")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class UserResource {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserResource.class);
    private final UserService userService;
    @Inject
    public UserResource(UserService userService)
    {
        this.userService = userService;
    }

    @GET
    public Response getAllUserDetails()
    {
        List<User> userList = userService.getAllUserDetails();
        return Response
                .ok(Map.of("user_list", userList))
                .build();
    }

    @GET
    @Path("/{user_id}")
    public Response getUserDetails(@PathParam("user_id") String user_id)
    {
        LOGGER.info("Processing a request to fetch a single user details");
        User userList = userService.getUserDetails(String.valueOf(user_id));
        return Response
                .ok(Map.of("user_details", userList))
                .build();
    }

    @POST
    public Response addNewUser(User user) throws Exception
    {
        Object respObj = userService.addNewUser(user);
        return Response.ok()
                .entity(respObj)
                .build();
    }

    @PUT
    @Path("/{user_id}")
    public Response updateUser(@PathParam("user_id") String user_id, User user) throws Exception
    {
        Object respObj = userService.updateUser(user_id, user);
        return Response.ok()
                .entity(respObj)
                .build();
    }

    @DELETE
    @Path("/{user_id}")
    public Response updateUser(@PathParam("user_id") String user_id) throws Exception
    {
        Object respObj = userService.deleteUser(user_id);
        return Response.ok()
                .entity(respObj)
                .build();
    }
}
