package com.mroczkowski.map.controller;

import com.mroczkowski.map.model.AppUser;
import com.mroczkowski.map.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;

@Controller
public class UserController {

    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/login")
    public String login(Model model) {
        model.addAttribute("user", new AppUser());
        return "login";
    }

    @GetMapping("/sign-up")
    public String signUp(Model model) {
        model.addAttribute("user", new AppUser());
        return "sign-up";
    }

    @PostMapping("/register")
    public String register(@Valid @ModelAttribute AppUser appUser, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()){
            String defaultMessage = bindingResult.getFieldError().getDefaultMessage();
            model.addAttribute("error", defaultMessage);
            System.out.println("errors: " + bindingResult);
            return "sign-up";
        } else {
            System.out.println(appUser);
            userService.addUser(appUser);
            return "login";
        }

    }
}
