package ua.mykytenko.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(path = "/r")
public class HomeRestController {

    @RequestMapping
    public String restHome(){
        return "rest_home";
    }

    @RequestMapping(path = "/laptops")
    public String restLaptops(){
        return "rest_laptops";
    }

    @RequestMapping(path = "/laptop/edit/{id}")
    public String goToLaptopEditForm(Model model,
                                     @PathVariable("id") int id){
        model.addAttribute("id", id);
        return "rest_laptop_form";
    }
}
