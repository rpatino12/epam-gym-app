package com.rpatino12.epam.gym.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "USERS")
@Data
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "USER_ID")
    private Long id;
    @Column(name = "FIRST_NAME")
    private String firstName;
    @Column(name = "LAST_NAME")
    private String lastName;
    @Column(name = "USERNAME")
    private String username;
    @Column(name = "PASSWORD")
    private String password;
    @Column(name = "IS_ACTIVE")
    private Boolean isActive;

    @OneToOne(mappedBy = "user")
    private Trainee trainee;

    @OneToOne(mappedBy = "user")
    private Trainer trainer;

    public User() {
    }

    public User(String firstName, String lastName, String password, Boolean isActive) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = firstName + "." + lastName;
        this.password = password;
        this.isActive = isActive;
    }
}
