package com.unsecured.ex2v3.config;

import com.unsecured.ex2v3.config.AuthHandlers.CustomLogoutSuccessHandler;
import com.unsecured.ex2v3.config.AuthHandlers.LoginSuccessHandler;
import com.unsecured.ex2v3.security.CustomerDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.session.HttpSessionEventPublisher;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    private final UserDetailsService customerDetailsService;
    private final LoginSuccessHandler loginSuccessHandler;
    private final CustomLogoutSuccessHandler customLogoutSuccessHandler;

    @Autowired
    public WebSecurityConfig(CustomerDetailsService customerDetailsService,
                             LoginSuccessHandler loginSuccessHandler, CustomLogoutSuccessHandler customLogoutSuccessHandler) {
        this.customerDetailsService = customerDetailsService;
        this.loginSuccessHandler = loginSuccessHandler;
        this.customLogoutSuccessHandler = customLogoutSuccessHandler;
    }

    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .authorizeRequests()
                .antMatchers("/register", "/user/register", "/register/duplicatedEmail").permitAll()
                .anyRequest().authenticated()
                .and()
                .formLogin().successHandler(this.loginSuccessHandler)
                .loginPage("/doLogin").permitAll()
                .and()
                .logout()
                .logoutSuccessHandler(this.customLogoutSuccessHandler).invalidateHttpSession(true).permitAll()
                .and()
                .sessionManagement().invalidSessionUrl("/doLogin")
                .enableSessionUrlRewriting(false).maximumSessions(-1).maxSessionsPreventsLogin(false)
                .sessionRegistry(this.sessionRegistry()).expiredUrl("/doLogin");
    }

    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(this.customerDetailsService).passwordEncoder(this.passwordEncoder());
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(12);
    }

    @Bean
    public SessionRegistry sessionRegistry() {
        System.out.println("calling sessionRegistry Bean");
        return new SessionRegistryImpl();
    }

    @Bean
    public HttpSessionEventPublisher httpSessionEventPublisher() {
        return new HttpSessionEventPublisher();
    }
}