package rs.raf.web_projekat_teodor_jakovljevic_rn9622.resources;

import rs.raf.web_projekat_teodor_jakovljevic_rn9622.entities.Activity;
import rs.raf.web_projekat_teodor_jakovljevic_rn9622.entities.Comment;
import rs.raf.web_projekat_teodor_jakovljevic_rn9622.services.ActivityService;

import javax.inject.Inject;
import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("/activity")
public class ActivityResources {

    @Inject
    private ActivityService activityService;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Activity> allActivity(){ return this.activityService.allActivities(); };

    @GET
    @Path("/find/{activityId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Activity findActivity(@PathParam("activityId")  Integer id){ return this.activityService.findActivity(id); };


    @GET
    @Path("/{activityId}")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Activity> getActivity(@PathParam("activityId")  Integer id){ return this.activityService.getActivity(id); };

    @POST
    @Path("/add")
    @Produces(MediaType.APPLICATION_JSON)
    public Activity addActivity(@Valid Activity activity){ return this.activityService.addActivity(activity); }

    @DELETE
    @Path("/{activityId}/delete")
    @Produces(MediaType.APPLICATION_JSON)
    public void deleteActivity(@PathParam("activityId")  Integer id){ this.activityService.deleteActivity(id);}

}
