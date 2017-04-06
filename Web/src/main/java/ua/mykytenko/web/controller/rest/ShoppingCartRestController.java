package ua.mykytenko.web.controller.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ua.mykytenko.service.LaptopService;
import ua.mykytenko.web.ShoppingCart;
import ua.mykytenko.web.impl.ShoppingCartImpl;

@RestController
@RequestMapping(path = "/r/cart")
@SessionAttributes("shoppingCart")
public class ShoppingCartRestController {
    private final LaptopService service;

    @Autowired
    public ShoppingCartRestController(LaptopService service) {
        this.service = service;
    }

    @ModelAttribute(name = "shoppingCart")
    public ShoppingCart createShoppingCart(){
        return new ShoppingCartImpl();
    }

    @RequestMapping(path = "/add/{id}")
    public ResponseEntity<ShoppingCart> addItemToCart(
            @SessionAttribute("shoppingCart") ShoppingCart shoppingCart,
            @PathVariable("id") int id){
        shoppingCart.addItem(service.getById(id));
        return new ResponseEntity<>(shoppingCart, HttpStatus.OK);
    }

    @RequestMapping(path = "/remove/{id}")
    public ResponseEntity<ShoppingCart> removeItemFromCart(
            @SessionAttribute("shoppingCart") ShoppingCart shoppingCart,
            @PathVariable("id") int id){
        shoppingCart.removeItem(service.getById(id));
        return new ResponseEntity<>(shoppingCart, HttpStatus.OK);
    }

    @RequestMapping(path = "/removeAll/{id}")
    public ResponseEntity<ShoppingCart> removeItemsFromCart(
            @SessionAttribute("shoppingCart") ShoppingCart shoppingCart,
            @PathVariable("id") int id){
        shoppingCart.removeItemsOfType(service.getById(id));
        return new ResponseEntity<>(shoppingCart, HttpStatus.OK);
    }

    @RequestMapping(path = "/clear")
    public ResponseEntity<ShoppingCart> clearCart(@SessionAttribute("shoppingCart") ShoppingCart shoppingCart){
                shoppingCart.clear();
                return new ResponseEntity<>(shoppingCart, HttpStatus.OK);
    }
}
