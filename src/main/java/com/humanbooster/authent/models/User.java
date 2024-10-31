package com.humanbooster.authent.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.humanbooster.authent.validations.OnCreate;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id_user")
    private Long idUser;

    @NotBlank (message = "Veuillez renseigner votre adresse mail.")
    @Column(unique = true, nullable = false)
    @Pattern(regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$",
            message = "Le mail doit être au format 'nom@email.domaine'")
    private String email;

    private String firstname;

    private String lastname;

    @NotBlank (message = "Veuillez renseigner votre pseudo.")
    private String nickname;

    @NotBlank (message = "Veuillez renseigner votre mot de passe.")
    @Size(min = 6, message = "Le mot de passe doit contenir au moins {min} caractères")
    @Pattern(regexp = "^(?=.*[0-9])(?=.*[@#$%^&+=!]).{6,}$",
            message = "Le mot de passe doit contenir au moins un chiffre et un caractère spécial.")
    private String password;

    @Transient
    @NotBlank(groups = OnCreate.class, message = "Veuillez confirmer votre mot de passe")
    private String confirmPassword;

    @Column(name="street_number")
    private String streetNumber;

    @Column(name="street_name")
    private String streetName;

    private String zipcode;

    private String city;

    @NotBlank (groups = OnCreate.class, message = "Veuillez renseigner votre pays.")
    private String country;

    @ManyToMany(fetch=FetchType.EAGER)
    @JoinTable(name = "Users_Roles",
            joinColumns = @JoinColumn (name = "idUser"),
            inverseJoinColumns = @JoinColumn(name = "idRole")
    )
    public List<Role> roleList = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    @JsonIgnore
    private List<Command> commandList;


    public User() {
    }

    public User(Long idUser, String email, String firstname, String lastname, String nickname, String password, String streetNumber, String streetName, String zipcode, String city, String country, List<Role> roleList, List<Command> commandList) {
        this.idUser = idUser;
        this.email = email;
        this.firstname = firstname;
        this.lastname = lastname;
        this.nickname = nickname;
        this.password = password;
        this.streetNumber = streetNumber;
        this.streetName = streetName;
        this.zipcode = zipcode;
        this.city = city;
        this.country = country;
        this.roleList = roleList;
        this.commandList = commandList;
    }

    public User(String city, List<Command> commandList, String confirmPassword, String country, String email, String firstname, Long idUser, String lastname, String nickname, String password, List<Role> roleList, String streetName, String streetNumber, String zipcode) {
        this.city = city;
        this.commandList = commandList;
        this.confirmPassword = confirmPassword;
        this.country = country;
        this.email = email;
        this.firstname = firstname;
        this.idUser = idUser;
        this.lastname = lastname;
        this.nickname = nickname;
        this.password = password;
        this.roleList = roleList;
        this.streetName = streetName;
        this.streetNumber = streetNumber;
        this.zipcode = zipcode;
    }

    public Long getIdUser() {
        return idUser;
    }

    public void setIdUser(Long idUser) {
        this.idUser = idUser;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    public String getStreetNumber() {
        return streetNumber;
    }

    public void setStreetNumber(String streetNumber) {
        this.streetNumber = streetNumber;
    }

    public String getStreetName() {
        return streetName;
    }

    public void setStreetName(String streetName) {
        this.streetName = streetName;
    }

    public String getZipcode() {
        return zipcode;
    }

    public void setZipcode(String zipcode) {
        this.zipcode = zipcode;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public List<Role> getRoleList() {
        return roleList;
    }

    public void setRoleList(List<Role> roleList) {
        this.roleList = roleList;
    }

    public List<Command> getCommandList() {
        return commandList;
    }

    public void setCommandList(List<Command> commandList) {
        this.commandList = commandList;
    }


    //Methodes pour creer et supprimer les roles
    public void addRole(Role role){
        this.roleList.add(role);
    }
    public void removeRole(Role role){
        this.roleList.remove(role);
    }

    //Methodes pour creer et supprimer les commandes
    public void addCommand(Command command){
        this.commandList.add(command);
    }
    public void removeCommand(Command command){
        this.commandList.remove(command);
    }
}
