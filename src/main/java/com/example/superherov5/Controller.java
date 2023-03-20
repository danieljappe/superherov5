package com.example.superherov5;

import com.example.superherov5.dto.HeroCityDTO;
import com.example.superherov5.dto.HeroPowerDTO;
import com.example.superherov5.dto.Superhero;
import com.fasterxml.jackson.databind.deser.impl.PropertyValueBuffer;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

@org.springframework.stereotype.Controller
@RequestMapping
public class Controller {
    SuperheroRepository superheroRepository = new SuperheroRepository();

    @GetMapping("/")
    public String getAllSuperheroes(Model model){
        List<SuperheroModel> superheroList = superheroRepository.getAllSuperheroes();
        model.addAttribute("superheroes", superheroList);
        return "index";
    }

    @GetMapping("/superpower/{ID}")
    public String getSuperheroesID(Model model,@PathVariable String ID){
        List<HeroPowerDTO> superheroList = superheroRepository.getSuperheroesWithPowersID(ID);
        model.addAttribute("superheroPower", superheroList);
        return "superheroPower";
    }

    @GetMapping("/createsuperhero")
    public String createSuphero(Model model){
        SuperheroModel superheroModel = new SuperheroModel();
        model.addAttribute("superheromodel", superheroModel);
        List<SuperheroModel> heroCityList = superheroRepository.getCities();
        model.addAttribute("heroCityList", heroCityList);
        List<SuperheroModel> heroPowerList = superheroRepository.getPowers();
        model.addAttribute("heroPowerList", heroPowerList);
        return "superhero_create";
    }

    @PostMapping("/createsuperhero")
    public String submitSuperhero(@ModelAttribute("superheroModel") SuperheroModel superheroModel){
        System.out.println(superheroModel);
        return "superhero_create_succesful";
    }

}
