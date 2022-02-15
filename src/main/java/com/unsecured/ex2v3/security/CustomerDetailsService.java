package com.unsecured.ex2v3.security;


import com.unsecured.ex2v3.models.Customer;
import com.unsecured.ex2v3.models.repositories.CustomerRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CustomerDetailsService implements UserDetailsService {
    private final CustomerRepo customerRepo;

    @Autowired
    public CustomerDetailsService(CustomerRepo customerRepo) {
        this.customerRepo = customerRepo;
    }

    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Customer customer = getCustomerByEmail(email);
        return new User(
                customer.getEmail(), customer.getPassword(),
                !customer.isBlocked(),
                !customer.isBlocked(),
                !customer.isBlocked(),
                !customer.isBlocked(),
                getAuthorities("USER"));
    }

    public List<SimpleGrantedAuthority> getAuthorities(String value) {
        var ret=new ArrayList<SimpleGrantedAuthority>();
        ret.add(new SimpleGrantedAuthority(value));
        return ret;
    }

    public void setLastSeen(String email, String value) {
        Customer customer = getCustomerByEmail(email);
        customer.setLastSeen(value);
        this.customerRepo.save(customer);
    }

    public Customer getCustomerByEmail(String email) {
        return customerRepo.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("no such user"));
    }
}