package com.humanbooster.authent.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.util.List;

@Entity
@Table(name = "command")
public class Command {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id_command")
    private Long idCommand;

    private String payment;

    @NotNull
    private String remarques;

    @ManyToOne()
    @JoinColumn(name = "idUser", nullable = false)
    @JsonIgnore
    private User user;

    @OneToMany(mappedBy = "command")
    private List<Reservation> reservationList;


    public Command() {
    }

    public Command(Long idCommand, String payment, String remarques, User user, List<Reservation> reservationList) {
        this.idCommand = idCommand;
        this.payment = payment;
        this.remarques = remarques;
        this.user = user;
        this.reservationList = reservationList;
    }

    public Long getIdCommand() {
        return idCommand;
    }

    public void setIdCommand(Long idCommand) {
        this.idCommand = idCommand;
    }

    public String getPayment() {
        return payment;
    }

    public void setPayment(String payment) {
        this.payment = payment;
    }

    public String getRemarques() {
        return remarques;
    }

    public void setRemarques(String remark) {
        this.remarques = remark;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<Reservation> getReservationList() {
        return reservationList;
    }

    public void setReservationList(List<Reservation> reservationList) {
        this.reservationList = reservationList;
    }
}
