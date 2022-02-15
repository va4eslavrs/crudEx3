package com.unsecured.ex2v3.controllers;

import com.unsecured.ex2v3.models.Customer;
import com.unsecured.ex2v3.models.repositories.CustomerRepo;
import com.unsecured.ex2v3.security.CustomerDetailsService;
import com.unsecured.ex2v3.utility.ArrayWrap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.session.SessionInformation;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Controller
public class HomeController {
    private final CustomerRepo customerRepo;
    private final SessionRegistry sessionRegistry;
    private final CustomerDetailsService customerDetailsService;

    @Autowired
    public HomeController(CustomerRepo customerRepo,
                          SessionRegistry sessionRegistry,
                          CustomerDetailsService customerDetailsService) {
        this.customerRepo = customerRepo;
        this.sessionRegistry = sessionRegistry;
        this.customerDetailsService = customerDetailsService;
    }

    @GetMapping(value = "/home")
    public String customers(Model model) {
        model.addAttribute("customers", this.customerRepo.findAll());
        model.addAttribute("customersActionable", new ArrayWrap());
        model.addAttribute("action", "");
        return "customers2";
    }

    @PostMapping(value = "/wr")
    public String actionOnCustomers(@ModelAttribute("customerActionable") ArrayWrap customerActionable,
                                    @RequestParam("submitButton") String submitButton) {
        if (Objects.equals(submitButton, "unblock"))
            setIsBlockedTo(false, customerActionable.toIntegerList());
        else {
            expireUsersSessions(getSelectedEmails(customerActionable.toIntegerList()));
            setIsBlockedTo(true, customerActionable.toIntegerList());
            if (Objects.equals(submitButton, "delete"))
                this.customerRepo.deleteAllById(customerActionable.toIntegerList());
        }
        return "redirect:/home";
    }

    private List<String> getSelectedEmails(List<Integer> listOfIds) {
        return this.customerRepo.findAllById(listOfIds)
                .stream().map(Customer::getEmail).collect(Collectors.toList());
    }

    private void setIsBlockedTo(boolean value, List<Integer> ids) {
        this.customerRepo.findAllById(ids).forEach((x) -> {
            if (!x.isBlocked() == value) {
                x.setBlocked(value);
                this.customerRepo.save(x);
            }
        });
    }

    private void expireUsersSessions(List<String> emails) {
        emails.stream().map(customerDetailsService::loadUserByUsername).forEach((x) -> expireUserSession((User) x));
    }

    private void expireUserSession(User user) {
        List<SessionInformation> sessions = this.sessionRegistry.getAllSessions(user, false);
        sessions.forEach(SessionInformation::expireNow);
    }
}
