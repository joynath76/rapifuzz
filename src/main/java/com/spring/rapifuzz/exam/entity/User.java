package com.spring.rapifuzz.exam.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.spring.rapifuzz.exam.validator.ValidInputPassword;
import lombok.Data;
import org.hibernate.annotations.Fetch;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.Base64;
import java.util.Optional;
import java.util.UUID;

@Entity
@Data
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long userId;

    private String userUnqueId;

    @NotNull(message = "User name cannot be null")
    @Column(nullable = false, unique = true)
    private String userName;

    @ValidInputPassword
    @Column(nullable = false)
    private String password;

    @NotNull(message = "Email cannot be null")
    @Email(message = "Email should be valid")
    @Column(nullable = false)
    private String email;

    @NotNull(message = "Phone Number cannot be null")
    @Pattern(regexp = "\\d{10}", message = "Phone number must be 10 digits")
    @Column(nullable = false)
    private String phoneNumber;

    @NotNull(message = "User Type cannot be null")
    @Column(nullable = false)
    private String userType;

    @NotNull(message = "First Name cannot be null")
    @Column(nullable = false)
    private String firstName;

    @NotNull(message = "Last Name cannot be null")
    @Column(nullable = false)
    private String lastName;

    @Column(nullable = false)
    @CreatedDate
    private LocalDateTime createDate;
    private LocalDateTime updateDate;

    @Column(nullable = false, columnDefinition = "CHAR(1) DEFAULT 'A'")
    private String status;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @JoinColumn(name = "address_id", referencedColumnName = "addressId")
    @Valid
    @NotNull
    private Address address;

    // Getters and Setters

    @PrePersist
    public void prePersist() {
        this.createDate = LocalDateTime.now();
        this.status = Optional.ofNullable(this.status).orElse("A");
        this.userUnqueId = UUID.randomUUID().toString();
//        setPreSaveFields();
    }

    @PreUpdate
    public void preUpdate() {
        setPreSaveFields();
    }

    private void setPreSaveFields() {
        this.updateDate = LocalDateTime.now();
        this.status = Optional.ofNullable(this.status).orElse("A");
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String decodedPassword = new String(Base64.getDecoder().decode(this.password.getBytes()), StandardCharsets.UTF_8);
        this.password = encoder.encode(decodedPassword);
    }
}

