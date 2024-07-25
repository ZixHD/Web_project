package rs.raf.web_projekat_teodor_jakovljevic_rn9622.repositories.article;

import rs.raf.web_projekat_teodor_jakovljevic_rn9622.entities.Article;
import rs.raf.web_projekat_teodor_jakovljevic_rn9622.entities.User;

import java.util.List;

public interface ArticleRepository {

    public List<Article> allArticles();
    public void incrementVisits(Integer id);
    public List<Article> allArticlePagination(Integer page, Integer pageSize);
    public List<Article> getTotalPages(Integer id);
    public List<Article> getArticlesWithActivity(Integer id, Integer page, Integer pageSize);
    public void joinTableAdd(Integer id, String activities);
    public List<Integer> getActivities(Integer id);
    public List<Article> getArticle(Integer id);
    public Article findArticle(Integer id);
    public User getArticleAuthor(Integer id);
    public Article addArticle(Article article);
    public Article editArticle(Integer id, Article article);
    public void deleteArticle(Integer id);
}
