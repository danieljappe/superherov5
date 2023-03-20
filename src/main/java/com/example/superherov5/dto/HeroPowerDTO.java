package com.example.superherov5.dto;

import java.util.List;

public class HeroPowerDTO {

    private String name;
    private List<String> superpowerList;

    public HeroPowerDTO(String name, List<String> superpowerList){
        this.name = name;
        this.superpowerList = superpowerList;
    }

    public String toString() {
        return "{" + this.name + " - " + this.superpowerList + "}";
    }

    public String getName() {
        return name;
    }

    public List<String> getSuperpowerList() {
        return superpowerList;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSuperpowerList(List<String> superpowerList) {
        this.superpowerList = superpowerList;
    }
}
