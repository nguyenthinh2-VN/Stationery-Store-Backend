package com.yuki.tkxdpm_k17_06;

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

    private String role = "USER";

    public UserAccountEntity(Long id, String name, String username, String email, String passwordHash, String role) {
        this.id = id;
        this.name = name;
        this.username = username;
        this.email = email;
        this.passwordHash = passwordHash;
        this.role = role;
    }

    public UserAccountEntity(String name, String username, String email, String passwordHash) {
        this.id = null;
        this.name = name;
        this.username = username;
        this.email = email;
        this.passwordHash = passwordHash;
    }

    public UserAccountEntity() {

    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public String getRole() {
        return role;
    }

    // Setters for UPDATE operations
    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
