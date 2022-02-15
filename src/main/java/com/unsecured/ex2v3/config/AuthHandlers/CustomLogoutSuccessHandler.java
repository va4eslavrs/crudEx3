package com.unsecured.ex2v3.config.AuthHandlers;


import com.unsecured.ex2v3.security.CustomerDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.logout.SimpleUrlLogoutSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;

@Component
public class CustomLogoutSuccessHandler extends SimpleUrlLogoutSuccessHandler {
    private final CustomerDetailsService customerDetailsService;

    @Autowired
    public CustomLogoutSuccessHandler(CustomerDetailsService customerDetailsService) {
        this.customerDetailsService = customerDetailsService;
    }

    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response,
                                Authentication authentication) throws IOException, ServletException {
        response.setStatus(200);
        try {
            this.customerDetailsService.setLastSeen(authentication.getName(), LocalDateTime.now().toString());
        } catch (UsernameNotFoundException var5) {
            System.out.println("bye");
        }
        response.sendRedirect("/doLogin");
        super.onLogoutSuccess(request, response, authentication);
    }
}

