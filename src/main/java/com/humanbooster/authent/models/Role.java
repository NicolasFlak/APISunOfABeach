package com.humanbooster.authent.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "role")
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id_role")
    private Long idRole;

    @NotNull
    @Column(name = "role_name")
    private String roleName;

    @ManyToMany
    @JsonIgnore
    @JoinTable( name = "Users_Roles",
            joinColumns = @JoinColumn( name = "idUser" ),
            inverseJoinColumns = @JoinColumn( name = "idRole" ) )
    private List<User> users = new ArrayList<>();


    public Role() {
    }

    public Role(String roleName) {
        this.roleName = roleName;
    }

    public Role(Long idRole, String roleName, List<User> users) {
        this.idRole = idRole;
        this.roleName = roleName;
        this.users = users;
    }


    public Long getIdRole() {
        return idRole;
    }

    public void setIdRole(Long idRole) {
        this.idRole = idRole;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }
}
