package rs.raf.web_projekat_teodor_jakovljevic_rn9622.resources;

import rs.raf.web_projekat_teodor_jakovljevic_rn9622.entities.Article;
import rs.raf.web_projekat_teodor_jakovljevic_rn9622.entities.Comment;
import rs.raf.web_projekat_teodor_jakovljevic_rn9622.services.CommentService;

import javax.inject.Inject;
import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;


@Path("/comments")
public class CommentResources {
    @Inject
    private CommentService commentService;

    @GET
    @Path("/{articleId}")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Comment> getComments(@PathParam("articleId") @Valid Integer id){ return this.commentService.getComments(id); }

    @POST
    @Path("/add")
    @Produces(MediaType.APPLICATION_JSON)
    public Comment addComment(@Valid Comment comment){ return this.commentService.addComment(comment); }

    @DELETE
    @Path("/{commentId}/delete")
    @Produces(MediaType.APPLICATION_JSON)
    public void deleteComment(@PathParam("commentId")  Integer id){ this.commentService.deleteComment(id);}




}
