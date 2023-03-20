package com.example.superherov5;

import org.springframework.format.annotation.DateTimeFormat;

import java.util.List;

public class SuperheroModel {

    private String name;
    private String realName;
    private int introductionDate;
    private String cityName;
    private List<String> superpowerList;

    public SuperheroModel(){
    }

    public SuperheroModel(String name, String realName, int introductionDate) {
        this.name = name;
        this.realName = realName;
        this.introductionDate = introductionDate;
    }

    public SuperheroModel(String cityname) {
        this.cityName = cityname;
    }

    public SuperheroModel(List<String> superpowerList) {
        this.superpowerList = superpowerList;
    }

    @Override
    public String toString() {
        return super.toString();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public int getIntroductionDate() {
        return introductionDate;
    }

    public void setIntroductionDate(int introductionDate) {
        this.introductionDate = introductionDate;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public List<String> getSuperpowerList() {
        return superpowerList;
    }

    public void setSuperpowerList(List<String> superpowerName) {
        this.superpowerList = superpowerName;
    }
}
