package org.uresti.pozarreal.dto;

import java.util.List;

import org.uresti.pozarreal.model.House;
import org.uresti.pozarreal.model.Representative;

public class StreetInfo {
    private String id;
    private String name;
    private Representative representative;
    private List<House> houses;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Representative getRepresentative() {
        return representative;
    }

    public void setRepresentative(Representative representative) {
        this.representative = representative;
    }

    public List<House> getHouses() {
        return houses;
    }

    public void setHouses(List<House> houses) {
        this.houses = houses;
    }
}
