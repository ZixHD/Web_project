package rs.raf.web_projekat_teodor_jakovljevic_rn9622.resources;



import rs.raf.web_projekat_teodor_jakovljevic_rn9622.entities.Article;
import rs.raf.web_projekat_teodor_jakovljevic_rn9622.entities.Destination;
import rs.raf.web_projekat_teodor_jakovljevic_rn9622.entities.User;
import rs.raf.web_projekat_teodor_jakovljevic_rn9622.requests.LoginRequest;
import rs.raf.web_projekat_teodor_jakovljevic_rn9622.services.UserService;

import javax.annotation.Resource;
import javax.inject.Inject;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Path("/users")
public class UserResource {


    @Inject
    private UserService userService;


    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response all(){ return Response.ok(this.userService.allUsers()).build(); }

    @GET
    @Path("/{userId}")
    @Produces(MediaType.APPLICATION_JSON)
    public User getUser(@PathParam("userId") @Valid Integer id){ return this.userService.getUser(id); }

    @GET
    @Path("/pagination")
    @Produces(MediaType.APPLICATION_JSON)
    public List<User> allPagination(@QueryParam("page") @DefaultValue("1") Integer page, @QueryParam("pageSize") @DefaultValue("1") Integer pageSize){
        return this.userService.allUserPagination(page, pageSize);
    }

    @POST
    @Path("/add")
    @Produces({MediaType.APPLICATION_JSON})
    public User create(@Valid User user){ return this.userService.addUser(user); }

    @PUT
    @Path("/{userId}/edit")
    @Produces({MediaType.APPLICATION_JSON})
    public User edit(@PathParam("userId") Integer id, @Valid User user){
        return this.userService.editUser(id, user);
    }

    @PUT
    @Path("/{userId}/activate")
    @Produces({MediaType.APPLICATION_JSON})
    public User activate(@PathParam("userId") Integer id, boolean active){
        return this.userService.activateUser(id, active);
    }



    @POST
    @Path("/login")
    @Produces({MediaType.APPLICATION_JSON})
    public Response login(@Valid LoginRequest loginRequest){
        Map<String, String> response = new HashMap<>();
        System.out.println(loginRequest.getEmail());
        System.out.println(loginRequest.getPassword());
        String jwt = this.userService.login(loginRequest.getEmail(), loginRequest.getPassword());
        if(jwt == null){
            response.put("message", "These credentials do not match our records");
            return Response.status(422, "Unprocessable Entity").entity(response).build();
        }
        response.put("jwt", jwt);

        return Response.ok(response).build();

    }
}

