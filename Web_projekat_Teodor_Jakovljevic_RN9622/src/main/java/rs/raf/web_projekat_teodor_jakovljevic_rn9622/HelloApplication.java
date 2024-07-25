package rs.raf.web_projekat_teodor_jakovljevic_rn9622;

import org.glassfish.jersey.internal.inject.AbstractBinder;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.server.ServerProperties;
import rs.raf.web_projekat_teodor_jakovljevic_rn9622.repositories.activity.ActivityRepository;
import rs.raf.web_projekat_teodor_jakovljevic_rn9622.repositories.activity.MySqlActivityRepository;
import rs.raf.web_projekat_teodor_jakovljevic_rn9622.repositories.article.ArticleRepository;
import rs.raf.web_projekat_teodor_jakovljevic_rn9622.repositories.article.MySqlArticleRepository;
import rs.raf.web_projekat_teodor_jakovljevic_rn9622.repositories.comment.CommentRepository;
import rs.raf.web_projekat_teodor_jakovljevic_rn9622.repositories.comment.MySqlCommentRepository;
import rs.raf.web_projekat_teodor_jakovljevic_rn9622.repositories.destination.DestinationRepository;
import rs.raf.web_projekat_teodor_jakovljevic_rn9622.repositories.destination.MySqlDestinationRepository;
import rs.raf.web_projekat_teodor_jakovljevic_rn9622.repositories.user.MySqlUserRepository;
import rs.raf.web_projekat_teodor_jakovljevic_rn9622.repositories.user.UserRepository;
import rs.raf.web_projekat_teodor_jakovljevic_rn9622.services.*;

import javax.inject.Singleton;
import javax.ws.rs.ApplicationPath;


@ApplicationPath("/api")
public class HelloApplication extends ResourceConfig {

    public HelloApplication(){

        property(ServerProperties.BV_SEND_ERROR_IN_RESPONSE, true);

        AbstractBinder binder = new AbstractBinder() {
            @Override
            protected void configure() {
                this.bind(MySqlUserRepository.class).to(UserRepository.class).in(Singleton.class);
                this.bind(MySqlCommentRepository.class).to(CommentRepository.class).in(Singleton.class);
                this.bind(MySqlDestinationRepository.class).to(DestinationRepository.class).in(Singleton.class);
                this.bind(MySqlActivityRepository.class).to(ActivityRepository.class).in(Singleton.class);
                this.bind(MySqlArticleRepository.class).to(ArticleRepository.class).in(Singleton.class);


                this.bindAsContract(UserService.class);
                this.bindAsContract(CommentService.class);
                this.bindAsContract(DestinationService.class);
                this.bindAsContract(ActivityService.class);
                this.bindAsContract(ArticleService.class);

            }
        };

        register(binder);


        packages("rs.raf.web_projekat_teodor_jakovljevic_rn9622");
    }
}