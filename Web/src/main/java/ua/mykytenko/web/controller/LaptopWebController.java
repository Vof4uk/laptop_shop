package ua.mykytenko.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ua.mykytenko.businesslayer.BankAccount;
import ua.mykytenko.businesslayer.LaptopManager;
import ua.mykytenko.entities.Laptop;
import ua.mykytenko.entities.Order;
import ua.mykytenko.service.LaptopService;
import ua.mykytenko.web.ShoppingCart;
import ua.mykytenko.web.impl.ShoppingCartImpl;

import java.util.List;

@Controller
@SessionAttributes("shoppingCart")
@RequestMapping(path = "/laptops")
public class LaptopWebController {

    @Autowired
    public LaptopWebController(LaptopService laptopService) {
        this.laptopService = laptopService;
    }

    @ModelAttribute("shoppingCart")
    public ShoppingCart getShoppingCart() {
        return new ShoppingCartImpl();
    }

    private final LaptopService laptopService;

    @RequestMapping(path = "/all", method = RequestMethod.GET)
    public String showLaptops(Model model) {
        List<Laptop> laptops = laptopService.getLaptopsInStock();
        model.addAttribute("laptops", laptops);
        return "laptops";
    }

    @RequestMapping(path = "/buy")
    public String doPost(@RequestParam(name = "item_id") int item_id,
                         @SessionAttribute("shoppingCart") ShoppingCart shoppingCart) {
        shoppingCart.addItem(laptopService.getById(item_id));
        return "redirect:/laptops/all";
    }

    @RequestMapping(path = "/show")
    public String showLaptop(Model model,
                             @RequestParam("id") int id) {
        model.addAttribute("laptop", laptopService.getById(id));
        return "laptop_details";
    }
}
