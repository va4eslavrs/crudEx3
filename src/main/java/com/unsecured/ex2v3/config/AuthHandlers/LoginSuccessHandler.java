package com.unsecured.ex2v3.config.AuthHandlers;

import com.unsecured.ex2v3.models.repositories.CustomerRepo;
import com.unsecured.ex2v3.security.CustomerDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;

@Component
public class LoginSuccessHandler implements AuthenticationSuccessHandler {
    private final CustomerDetailsService customerDetailsService;

    @Autowired
    public LoginSuccessHandler(CustomerRepo customerRepo, CustomerDetailsService customerDetailsService) {
        this.customerDetailsService = customerDetailsService;
    }

    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {
        response.setStatus(200);
        this.customerDetailsService.setLastSeen(authentication.getName(), LocalDateTime.now().toString());
        response.sendRedirect("/home");
    }
}
