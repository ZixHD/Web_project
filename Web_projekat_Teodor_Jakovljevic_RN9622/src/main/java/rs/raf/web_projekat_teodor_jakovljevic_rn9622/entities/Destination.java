package rs.raf.web_projekat_teodor_jakovljevic_rn9622.entities;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class Destination {

    private Integer id;

    @NotNull(message = "Name field is required")
    @NotEmpty(message = "Name filed is required")
    private String name;
    @NotNull(message = "Description field is required")
    @NotEmpty(message = "Description filed is required")
    private String description;

    public Destination(){}
    public Destination(String name, String description) {
        this.name = name;
        this.description = description;
    }
    public Destination(Integer id, String name, String description){
        this(name, description);
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
