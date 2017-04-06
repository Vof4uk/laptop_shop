package ua.mykytenko.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ua.mykytenko.entities.Account;
import ua.mykytenko.service.AccountService;
import ua.mykytenko.web.ShoppingCart;
import ua.mykytenko.web.impl.ShoppingCartImpl;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;

@Controller
@RequestMapping(path = "/")
@SessionAttributes("shoppingCart")
public class HomeWebController {

    private final AccountService accounts;
    private final PasswordEncoder passwordEncoder;

    @ModelAttribute("shoppingCart")
    public ShoppingCart getShoppingCart() {
        return new ShoppingCartImpl();
    }

    @Autowired
    public HomeWebController(AccountService accounts, PasswordEncoder passwordEncoder) {
        this.accounts = accounts;
        this.passwordEncoder = passwordEncoder;
    }

    @RequestMapping(path = "login", method = RequestMethod.GET)
    public String login() {
        return "login";
    }

    @RequestMapping(path = "accountInfo", method = RequestMethod.GET)
    public String account(Model model,
                          @SessionAttribute("shoppingCart") ShoppingCart shoppingCart) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = user.getUsername();
        Account account = accounts.getByUsername(username);
        shoppingCart.setAccount(account);
        model.addAttribute("account", account);
        return "profile";
    }

    @RequestMapping(path = "index.html")
    public String index() {
        return home();
    }

    @RequestMapping(path = "home", method = RequestMethod.GET)
    public String home() {
        return "home";
    }

    @RequestMapping(path = "good_bye")
    public String logout(HttpSession session) {
        session.invalidate();
        return "home";
    }

    @RequestMapping
    public String emptyUrl() {
        return home();
    }

}

