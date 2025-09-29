package com.example.bankcards.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "Users")
@AllArgsConstructor
@Getter
@Setter
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_user")
    private int id;

    @Column(name = "firstname")
    @NotNull
    private String firstname;

    @Column(name = "midname")
    @NotNull
    private String midname;

    @Column(name = "lastname")
    @NotNull
    private String lastname;

    @Column(name = "login")
    @NotNull
    private String login;

    @Column(name = "password")
    @NotNull
    private String password;

    @Column(name = "roles")
    @NotNull
    private String roles;

    @OneToMany(mappedBy = "userId")
    private List<Card> cards;

    public User() {
    }
}
