package com.yuki.tkxdpm_k17_06.RegisterAccountUserWithEmail.Infrastructure;

import jakarta.persistence.*;

@Entity
@Table(name = "users")
public class UserAccountEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String username;

    @Column(unique = true)
    private String email;

    private String passwordHash;


    public UserAccountEntity() {}

    public UserAccountEntity(String name, String username, String email, String passwordHash) {
        this.name = name;
        this.username = username;
        this.email = email;
        this.passwordHash = passwordHash;
    }


    // getters & setters ...
}

