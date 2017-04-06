package ua.mykytenko.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ua.mykytenko.businesslayer.BankAccount;
import ua.mykytenko.businesslayer.LaptopManager;
import ua.mykytenko.entities.Account;
import ua.mykytenko.entities.Laptop;
import ua.mykytenko.entities.Order;
import ua.mykytenko.service.AccountService;
import ua.mykytenko.service.LaptopService;
import ua.mykytenko.web.ShoppingCart;
import ua.mykytenko.web.impl.ShoppingCartImpl;

@Controller
@RequestMapping(path = "/laptops/shopping_cart")
@SessionAttributes("shoppingCart")
public class ShoppingCartController {

    @Autowired
    public ShoppingCartController(AccountService accountService, LaptopService laptopService, LaptopManager laptopManager) {
        this.laptopService = laptopService;
        this.laptopManager = laptopManager;
        this.accountService = accountService;
    }

    @ModelAttribute("shoppingCart")
    public ShoppingCart getShoppingCart() {
        return new ShoppingCartImpl();
    }

    private final LaptopManager laptopManager;
    private final LaptopService laptopService;
    private final AccountService accountService;

    @RequestMapping(method = RequestMethod.GET)
    public String openCart() {
        return "shopping_cart";
    }

    @RequestMapping(path = "/confirm")
    public String confirmShoppingCart(Model model,
                                      @SessionAttribute("shoppingCart") ShoppingCart shoppingCart) {
        if (!shoppingCart.isReady()) {
            return "shopping_cart";
        }
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = user.getUsername();
        Account account = accountService.getByUsername(username);
        shoppingCart.setAccount(account);
        Order order = shoppingCart.makeOrder();

        if (!laptopManager.confirmStock(order)) {
            model.addAttribute("message", "Some thing changed. Your order is updated");
        }
        shoppingCart.setOrder(order);
        model.addAttribute("order", order);
        return "order_form";
    }

    @RequestMapping(path = "/minus")
    public String minusLaptop(@SessionAttribute("shoppingCart") ShoppingCart shoppingCart,
                              @RequestParam("id") int id) {
        Laptop laptop = laptopService.getById(id);
        shoppingCart.removeItem(laptop);
        return "shopping_cart";
    }

    @RequestMapping(path = "/minusAll")
    public String minusLaptops(@SessionAttribute("shoppingCart") ShoppingCart shoppingCart,
                               @RequestParam("id") int id) {
        Laptop laptop = laptopService.getById(id);
        shoppingCart.removeItemsOfType(laptop);
        return "shopping_cart";
    }

    @RequestMapping(path = "/plus")
    public String plusLaptop(@SessionAttribute("shoppingCart") ShoppingCart shoppingCart,
                             @RequestParam("id") int id) {
        Laptop laptop = laptopService.getById(id);
        shoppingCart.addItem(laptop);
        return "shopping_cart";
    }


    @RequestMapping(path = "/pay", method = RequestMethod.POST)
    public String payTheMoney(@SessionAttribute("shoppingCart") ShoppingCart shoppingCart,
                              @RequestParam("address") String address,
                              @RequestParam("bank_account_id") long bank_id,
                              Model model) {
        Order order = shoppingCart.getOrder();
        order.setAddress(address);
        int orderNum = 0;
        if (laptopManager.confirmStock(order)) {
            orderNum = laptopManager.sellOrder(order, new BankAccount(bank_id));
            model.addAttribute("message", "your order number is " + orderNum);
            shoppingCart.clear();
            return "order_done";
        }else {
            shoppingCart.setOrder(order);
            model.addAttribute("message", "Some thing changed. Your order is updated");
            model.addAttribute("order", order);
            return "order_form";
        }
    }
}
