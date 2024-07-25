package rs.raf.web_projekat_teodor_jakovljevic_rn9622.filter;

import rs.raf.web_projekat_teodor_jakovljevic_rn9622.resources.*;
import rs.raf.web_projekat_teodor_jakovljevic_rn9622.services.*;

import javax.inject.Inject;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;
import java.io.IOException;
import java.util.List;

@Provider
public class AuthFilter implements ContainerRequestFilter {

    @Inject
    UserService userService;

    @Override
    public void filter(ContainerRequestContext requestContext) throws IOException {

//        if (true) return;
        if (!this.isAuthRequired(requestContext)) {
            return;
        }

        try {
            String token = requestContext.getHeaderString("Authorization");
            System.out.println(token);
            if(token != null && token.startsWith("Bearer ")) {
                token = token.replace("Bearer ", "");
            }

            if (!this.userService.isAuthorized(token)) {
                requestContext.abortWith(Response.status(Response.Status.UNAUTHORIZED).build());
            }
            String role = userService.getRole(token);
            if(!isAccessAllowed(requestContext, role)){
                requestContext.abortWith(Response.status(Response.Status.UNAUTHORIZED).build());
            }
        } catch (Exception exception) {
            requestContext.abortWith(Response.status(Response.Status.UNAUTHORIZED).build());
        }
    }

    private boolean isAuthRequired(ContainerRequestContext req) {


        if (req.getUriInfo().getPath().contains("login")) {
            System.out.println("LOGIN");
            return false;
        }


        List<Object> matchedResources = req.getUriInfo().getMatchedResources();
        for (Object matchedResource : matchedResources) {
            if (matchedResource instanceof UserResource || matchedResource instanceof DestinationResources || matchedResource instanceof CommentResources
                    || matchedResource instanceof ActivityResources || matchedResource instanceof ArticleResources) {
                return true;
            }
        }

        return false;
    }

    private boolean isAccessAllowed(ContainerRequestContext req, String role) {

        if(role.equals("admin"))
            return true;

        List<Object> matchedResources = req.getUriInfo().getMatchedResources();
        for (Object matchedResource : matchedResources) {
            if (matchedResource instanceof DestinationResources
                    || matchedResource instanceof CommentResources || matchedResource instanceof ActivityResources
                    || matchedResource instanceof ArticleResources) {
                if(role.equals("editor")) {
                    return true;
                }
            }
        }

        return false;
    }
}
