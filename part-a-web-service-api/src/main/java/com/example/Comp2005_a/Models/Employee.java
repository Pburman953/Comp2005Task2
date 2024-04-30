package com.example.Comp2005_a.Models;


import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Employee {

    private int id;
    private String forename;
    private String surname;

    @JsonCreator
    public Employee(@JsonProperty("id") int id,
                   @JsonProperty("surname") String surname,
                   @JsonProperty("forename") String forename) {
        this.id = id;
        this.surname = surname;
        this.forename = forename;

    }

        public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getForename() {
        return forename;
    }

    public void setForename(String forename) {
        this.forename = forename;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }
}
