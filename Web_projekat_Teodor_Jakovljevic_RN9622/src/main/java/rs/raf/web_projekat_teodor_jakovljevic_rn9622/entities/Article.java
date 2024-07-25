package rs.raf.web_projekat_teodor_jakovljevic_rn9622.entities;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.sql.Array;
import java.util.List;


public class Article {

    private Integer id;
    @NotNull(message = "Name field is required")
    @NotEmpty(message = "Name filed is required")
    private String name;
    @NotNull(message = "Text field is required")
    @NotEmpty(message = "Text filed is required")
    private String text;
    @NotNull(message = "Date field is required")
    @NotEmpty(message = "Date filed is required")
    private String date;
    @NotNull(message = "Visits field is required")
    private Integer visits;
    @NotNull(message = "User_id field is required")
    private Integer user_id;
    @NotNull(message = "Destination_id field is required")
    private Integer destination_id;







    public Article(){}

    public Article(String name, String text, String date, Integer visits, Integer user_id, Integer destination_id) {
        this.name = name;
        this.text = text;
        this.date = date;
        this.visits = visits;
        this.user_id = user_id;
        this.destination_id = destination_id;
    }

    public Article(Integer id, String name, String text, String date, Integer visits, Integer user_id, Integer destination_id) {
        this(name, text, date, visits, user_id, destination_id);
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

    public Integer getVisits() {
        return visits;
    }

    public void setVisits(Integer visits) {
        this.visits = visits;
    }

    public Integer getUser_id() {
        return user_id;
    }

    public void setUser_id(Integer user_id) {
        this.user_id = user_id;
    }

    public Integer getDestination_id() {
        return destination_id;
    }

    public void setDestination_id(Integer destination_id) {
        this.destination_id = destination_id;
    }



}

