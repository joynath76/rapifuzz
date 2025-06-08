package com.spring.rapifuzz.exam.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.Transient;

public class LoginRequest {
    private String username;

    @JsonIgnore
    private String password;

    // Getters and setters
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}

