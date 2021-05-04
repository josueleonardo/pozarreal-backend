package org.uresti.pozarreal.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "houses")
public class House {
    @Id
    private String id;
    private String street;
    private String number;
    @Column(name = "chips_enabled")
    private boolean chipsEnabled;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public boolean isChipsEnabled() {
        return chipsEnabled;
    }

    public void setChipsEnabled(boolean chipsEnabled) {
        this.chipsEnabled = chipsEnabled;
    }
}
