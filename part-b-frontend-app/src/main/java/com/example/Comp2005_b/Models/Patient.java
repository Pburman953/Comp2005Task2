package com.example.Comp2005_b.Models;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Patient {

    private int id;
    private String surname;
    private String forename;
    private String nhsNumber;

    @JsonCreator
    public Patient(@JsonProperty("id") int id,
                   @JsonProperty("surname") String surname,
                   @JsonProperty("forename") String forename,
                   @JsonProperty("nhsNumber") String nhsNumber) {
        this.id = id;
        this.surname = surname;
        this.forename = forename;
        this.nhsNumber = nhsNumber;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getForename() {
        return forename;
    }

    public void setForename(String forename) {
        this.forename = forename;
    }

    public String getNhsNumber() {
        return nhsNumber;
    }

    public void setNhsNumber(String nhsNumber) {
        this.nhsNumber = nhsNumber;
    }
}
