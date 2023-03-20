package com.example.superherov5.dto;

public class HeroPowerCountDTO {

    private String name;
    private String realName;
    private int num_powers;

    public HeroPowerCountDTO(String name, String realName, int num_powers) {
        this.name = name;
        this.realName = realName;
        this.num_powers = num_powers;
    }

    public String toString() {
        return "{" + this.name + " - " + this.realName + " - " + this.num_powers + "}";
    }

    public String getName() {
        return name;
    }

    public String getRealName() {
        return realName;
    }

    public int getNum_powers() {
        return num_powers;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public void setNum_powers(int num_powers) {
        this.num_powers = num_powers;
    }
}
