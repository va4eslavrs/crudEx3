package com.unsecured.ex2v3.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class ChatRoom {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    public Integer id;
    @Column(name = "name", unique = true, nullable = false)
    public String name;
    @OneToMany(mappedBy = "id")
    public List<Message> messages;
    @ManyToOne
    @JoinColumn(name = "sender_id")
    public Customer sender;
    @ManyToOne
    @JoinColumn(name = "recipient_id")
    public Customer recipient;

}
