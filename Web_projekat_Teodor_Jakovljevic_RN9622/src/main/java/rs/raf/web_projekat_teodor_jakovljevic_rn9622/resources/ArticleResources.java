package rs.raf.web_projekat_teodor_jakovljevic_rn9622.resources;

import rs.raf.web_projekat_teodor_jakovljevic_rn9622.entities.Activity;
import rs.raf.web_projekat_teodor_jakovljevic_rn9622.entities.Article;
import rs.raf.web_projekat_teodor_jakovljevic_rn9622.entities.Destination;
import rs.raf.web_projekat_teodor_jakovljevic_rn9622.entities.User;
import rs.raf.web_projekat_teodor_jakovljevic_rn9622.services.ActivityService;
import rs.raf.web_projekat_teodor_jakovljevic_rn9622.services.ArticleService;

import javax.inject.Inject;
import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/article")
public class ArticleResources {

    @Inject
    private ArticleService articleService;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response allArticles(@Valid Article article){ return Response.ok(this.articleService.allArticles()).build(); }

    @GET
    @Path("activity/{articleId}")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Article> getArticlesWithActivity(@PathParam("articleId") @Valid Integer id,@QueryParam("page") @DefaultValue("1") Integer page, @QueryParam("pageSize") @DefaultValue("1") Integer pageSize){ return this.articleService.getArticlesWithActivity(id, page, pageSize); }

    @GET
    @Path("pages/{articleId}")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Article> getTotalPages(@PathParam("articleId") @Valid Integer id){ return this.articleService.getTotalPages(id); }

    @GET
    @Path("/pagination")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Article> allPagination(@QueryParam("page") @DefaultValue("1") Integer page, @QueryParam("pageSize") @DefaultValue("1") Integer pageSize){
        return this.articleService.allArticlePagination(page, pageSize);
    }
    @GET
    @Path("/{articleId}")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Article> getArticle(@PathParam("articleId") @Valid Integer id){ return this.articleService.getArticle(id); }

    @GET
    @Path("/id/{articleId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Article findArticle(@PathParam("articleId") @Valid Integer id){ return this.articleService.findArticle(id); }

    @GET
    @Path("/{articleId}/activities")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Integer> getActivities(@PathParam("articleId") @Valid Integer id){ return this.articleService.getActivities(id); }

    @GET
    @Path("/author/{articleId}")
    @Produces(MediaType.APPLICATION_JSON)
    public User getArticleAuthor(@PathParam("articleId") @Valid Integer id){ return this.articleService.getArticleAuthor(id); }

    @POST
    @Path("/add/{articleId}/join")
    @Produces(MediaType.APPLICATION_JSON)
    public void joinTableAdd(@PathParam("articleId") Integer id, @Valid String activities){  this.articleService.joinTableAdd(id, activities); }


    @POST
    @Path("/add")
    @Produces(MediaType.APPLICATION_JSON)
    public Article addArticle(@Valid Article article){ return this.articleService.addArticle(article); }

    @PUT
    @Path("/{articleId}/edit")
    @Produces(MediaType.APPLICATION_JSON)
    public Article editArticle(@PathParam("articleId") Integer id,  Article article){ return this.articleService.editArticle(id, article); }


    @PUT
    @Path("/{articleId}/increment")
    @Produces(MediaType.APPLICATION_JSON)
    public void incrementVisits(@PathParam("articleId") Integer id){ this.articleService.incrementVisits(id); }


    @DELETE
    @Path("/{articleId}/delete")
    @Produces(MediaType.APPLICATION_JSON)
    public void deleteArticle(@PathParam("articleId")  Integer id){ this.articleService.deleteArticle(id);}
}
