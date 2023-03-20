package com.example.superherov5.dto;

public class HeroCityDTO {
    private String cityname;

    public HeroCityDTO(String cityname){
        this.cityname = cityname;
    }

    public String toString() {
        return this.cityname;
    }

    public String getCityname() {
        return cityname;
    }

    public void setCityname(String cityname) {
        this.cityname = cityname;
    }
}
