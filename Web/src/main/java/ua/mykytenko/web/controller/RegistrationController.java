package ua.mykytenko.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import ua.mykytenko.entities.Account;
import ua.mykytenko.entities.UserRole;
import ua.mykytenko.service.AccountService;

@Controller
@RequestMapping(path = "/register")
public class RegistrationController {

    private final AccountService accountService;

    private final PasswordEncoder passwordEncoder;

    @Autowired
    public RegistrationController(AccountService accountService, PasswordEncoder passwordEncoder) {
        this.accountService = accountService;
        this.passwordEncoder = passwordEncoder;
    }

    @RequestMapping(method = RequestMethod.GET)
    public String goToRegistrationForm(){
        return "user_form";
    }

    @RequestMapping(method = RequestMethod.POST)
    public String register(@RequestParam("username") String username,
                           @RequestParam("password") String password){
        String encryptedPassword = passwordEncoder.encode(password);
        Account account = new Account(username, encryptedPassword, UserRole.USER);
        accountService.add(account);
        return "/login";
    }
}
