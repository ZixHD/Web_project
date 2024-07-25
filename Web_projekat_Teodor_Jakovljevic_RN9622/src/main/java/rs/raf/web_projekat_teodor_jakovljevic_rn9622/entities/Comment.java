package rs.raf.web_projekat_teodor_jakovljevic_rn9622.entities;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class Comment {

    private Integer id;
    @NotNull(message = "Name field is required")
    @NotEmpty(message = "Name filed is required")
    private String name;
    @NotNull(message = "Text field is required")
    @NotEmpty(message = "Text filed is required")
    private String text;
    @NotNull(message = "Date field is required")
    @NotEmpty(message = "Date field is required")
    private String date;
    @NotNull(message = "Article_id field is required")
    private Integer article_id;


    public Comment(){}
    public Comment(String name, String text, String date, Integer article_id) {
        this.name = name;
        this.text = text;
        this.date = date;
        this.article_id = article_id;
    }

    public Comment(Integer id, String name, String text, String date, Integer article_id) {
        this(name, text, date, article_id);
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Integer getArticle_id() {
        return article_id;
    }

    public void setArticle_id(Integer article_id) {
        this.article_id = article_id;
    }
}
