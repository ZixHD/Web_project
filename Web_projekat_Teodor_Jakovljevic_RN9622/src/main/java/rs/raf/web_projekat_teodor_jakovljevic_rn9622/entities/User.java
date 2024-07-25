package rs.raf.web_projekat_teodor_jakovljevic_rn9622.entities;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;


public class User {

    private Integer id;
    @NotNull(message = "Email field is required")
    @NotEmpty(message = "Email filed is required")
    private String email;
    @NotNull(message = "Name field is required")
    @NotEmpty(message = "Name filed is required")
    private String name;
    @NotNull(message = "Surname field is required")
    @NotEmpty(message = "Surname filed is required")
    private String surname;
    @NotNull(message = "Role field is required")
    @NotEmpty(message = "Role filed is required")
    private String role;
    @NotNull(message = "Active field is required")
    private boolean active;
    @NotNull(message = "Password field is required")
    @NotEmpty(message = "Password filed is required")
    private String password;


    public User(){}

    public User(Integer id, String name, String surname,String email, String role, boolean active, String password){
        this(email, name, surname, role, active, password);
        this.id = id;
    }

    public User(String email, String name, String surname, String role, boolean active, String password) {
        this.email = email;
        this.name = name;
        this.surname = surname;
        this.role = role;
        this.active = active;
        this.password = password;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
