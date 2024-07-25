package rs.raf.web_projekat_teodor_jakovljevic_rn9622.services;


import rs.raf.web_projekat_teodor_jakovljevic_rn9622.entities.Article;
import rs.raf.web_projekat_teodor_jakovljevic_rn9622.entities.User;
import rs.raf.web_projekat_teodor_jakovljevic_rn9622.repositories.article.ArticleRepository;


import javax.inject.Inject;
import java.util.List;

public class ArticleService {

    @Inject
    private ArticleRepository articleRepository;


    public List<Article> allArticles(){ return this.articleRepository.allArticles(); }
    public List<Article> allArticlePagination(Integer page, Integer pageSize){ return this.articleRepository.allArticlePagination(page, pageSize); }
    public List<Article> getTotalPages(Integer id){ return this.articleRepository.getTotalPages(id); }
    public List<Article> getArticlesWithActivity(Integer id, Integer page, Integer pageSize){ return this.articleRepository.getArticlesWithActivity(id, page, pageSize); }
    public List<Article> getArticle(Integer id){ return this.articleRepository.getArticle(id); }
    public List<Integer> getActivities(Integer id){ return this.articleRepository.getActivities(id); }
    public void incrementVisits(Integer id){ this.articleRepository.incrementVisits(id);}
    public void joinTableAdd(Integer id, String activities){ this.articleRepository.joinTableAdd(id, activities);}
    public Article findArticle(Integer id){ return  this.articleRepository.findArticle(id); }
    public User getArticleAuthor(Integer id){ return this.articleRepository.getArticleAuthor(id); }
    public Article addArticle(Article article){ return this.articleRepository.addArticle(article); }
    public Article editArticle(Integer id, Article article){ return  this.articleRepository.editArticle(id, article);}
    public void deleteArticle(Integer id) { this.articleRepository.deleteArticle(id); }
}
