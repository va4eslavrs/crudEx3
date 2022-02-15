package com.unsecured.ex2v3.controllers;

import com.unsecured.ex2v3.models.Customer;
import com.unsecured.ex2v3.models.repositories.CustomerRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;

@Controller
public class RegistrationController {
    private final CustomerRepo customerRepo;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public RegistrationController(CustomerRepo customerRepo, PasswordEncoder passwordEncoder) {
        this.customerRepo = customerRepo;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping(value = "/register")
    public String showRegisterPage(Model model) {
        model.addAttribute("customer", new Customer());
        return "register";
    }

    @GetMapping(value = "register/duplicatedEmail")
    public String showRegisterPageDupEmail(Model model) {
        model.addAttribute("customer", new Customer());
        model.addAttribute("message", "this email already Exists");
        return "register";
    }

    @PostMapping(value = "/user/register")
    public String registerCustomer(@ModelAttribute("customer") @Valid Customer customer) {
        if (this.customerRepo.findByEmail(customer.getEmail()).isPresent()) {
            return "redirect:/register/duplicatedEmail";
        } else {
            customer.setPassword(this.passwordEncoder.encode(customer.getPassword()));
            customer.setLastSeen("Registered");
            this.customerRepo.save(customer);
            return "redirect:/doLogin";
        }
    }
}
