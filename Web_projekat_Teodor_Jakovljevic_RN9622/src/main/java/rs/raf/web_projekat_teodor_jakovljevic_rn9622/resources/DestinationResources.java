package rs.raf.web_projekat_teodor_jakovljevic_rn9622.resources;

import rs.raf.web_projekat_teodor_jakovljevic_rn9622.entities.Activity;
import rs.raf.web_projekat_teodor_jakovljevic_rn9622.entities.Destination;
import rs.raf.web_projekat_teodor_jakovljevic_rn9622.services.DestinationService;

import javax.inject.Inject;
import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/destination")
public class DestinationResources {
    @Inject
    private DestinationService destinationService;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response all(){ return Response.ok(this.destinationService.allDestinations()).build(); }

    @GET
    @Path("/pagination")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Destination> allPagination(@QueryParam("page") @DefaultValue("1") Integer page, @QueryParam("pageSize") @DefaultValue("1") Integer pageSize){
        return this.destinationService.allDestinationPagination(page, pageSize);
    }

    @GET
    @Path("/{destinationId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Destination getDestination(@PathParam("destinationId")  Integer id){ return this.destinationService.getDestination(id); };

    @GET
    @Path("/name/{destinationName}")
    @Produces(MediaType.APPLICATION_JSON)
    public Destination findDestination(@PathParam("destinationName")  String name){ return this.destinationService.findDestination(name); };


    @POST
    @Path("/add")
    @Produces(MediaType.APPLICATION_JSON)
    public Destination addDestination(@Valid Destination destination){ return this.destinationService.addDestination(destination); }

    @PUT
    @Path("{destinationId}/edit")
    @Produces(MediaType.APPLICATION_JSON)
    public Destination editDestination(@PathParam ("destinationId") Integer id,  Destination destination){ return this.destinationService.editDestination(id,destination);}

    @DELETE
    @Path("{destinationId}/delete")
    @Produces(MediaType.APPLICATION_JSON)
    public void deleteDestination(@PathParam("destinationId") Integer id){ this.destinationService.deleteDestination(id);}
}
