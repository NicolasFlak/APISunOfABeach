package com.humanbooster.authent.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.util.Date;

@Entity
@Table(name = "reservation")
public class Reservation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id_reservation")
    private Long idReservation;

    @NotNull
    private Integer file;

    //    @NotNull
    private Integer col;

    @NotNull
    private Date date;

    //    @NotNull
    private Boolean accepted;

    @NotNull
    private String equipment;

    @ManyToOne
    @JoinColumn(name = "idCommand", nullable = false)
    private Command command;


    public Reservation() {
    }

    public Reservation(Integer file, Integer col, Date date, Boolean accepted, String equipment, Command command) {
        this.file = file;
        this.col = col;
        this.date = date;
        this.accepted = accepted;
        this.equipment = equipment;
        this.command = command;
    }

    public Reservation(Long idReservation, Integer file, Integer col, Date date, Boolean accepted, String equipment, Command command) {
        this.idReservation = idReservation;
        this.file = file;
        this.col = col;
        this.date = date;
        this.accepted = accepted;
        this.equipment = equipment;
        this.command = command;
    }


    public Long getIdReservation() {
        return idReservation;
    }

    public void setIdReservation(Long idReservation) {
        this.idReservation = idReservation;
    }

    public @NotNull Integer getFile() {
        return file;
    }

    public void setFile(@NotNull Integer file) {
        this.file = file;
    }

    public Integer getCol() {
        return col;
    }

    public void setCol(Integer col) {
        this.col = col;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Boolean getAccepted() {
        return accepted;
    }

    public void setAccepted(Boolean accepted) {
        this.accepted = accepted;
    }

    public String getEquipment() {
        return equipment;
    }

    public void setEquipment(String equipment) {
        this.equipment = equipment;
    }

    public Command getCommand() {
        return command;
    }

    public void setCommand(Command command) {
        this.command = command;
    }
}

