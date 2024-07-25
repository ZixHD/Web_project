package rs.raf.web_projekat_teodor_jakovljevic_rn9622.entities;


import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class Activity {

    private Integer id;
    @NotNull(message = "Text field is required")
    @NotEmpty(message = "Text filed is required")
    private String text;
    @NotNull(message = "Destination_id field is required")
    private Integer destination_id;

    public Activity(){}

    public Activity(String text, Integer destination_id){
        this.text = text;
        this.destination_id = destination_id;
    }

    public Activity(Integer id, String text, Integer destination_id){
        this(text, destination_id);
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Integer getDestination_id() {
        return destination_id;
    }

    public void setDestination_id(Integer destination_id) {
        this.destination_id = destination_id;
    }
}
