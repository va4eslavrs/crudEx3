package com.unsecured.ex2v3.models;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;
import javax.validation.constraints.Pattern;
import java.sql.Timestamp;
import java.time.LocalDateTime;

@Getter
@Setter
@RequiredArgsConstructor
@ToString
@Table(name = "customers")
@Entity
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;
    @Column(name = "name", nullable = false)
    private String name;
    @Column(name = "email", nullable = false, unique = true)
    private String email;
    @Column(name = "registrationDate")
    private Timestamp registrationDate;
    @Column(name = "lastSeen")
    @ColumnDefault("now")
    private String lastSeen;
    @Column(name = "blocked", nullable = false)
    @ColumnDefault("false")
    public boolean blocked;
    @Pattern(regexp = ".+")
    @Column(name = "password")
    private String password;

    @PrePersist
    private void setRegistrationDate() {
        setRegistrationDate(Timestamp.valueOf(LocalDateTime.now()));
    }

}