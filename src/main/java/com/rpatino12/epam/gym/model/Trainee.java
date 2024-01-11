package com.rpatino12.epam.gym.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "TRAINEE")
@Getter
@Setter
public class Trainee implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "TRAINEE_ID")
    private Long traineeId;
    @Column(name = "BIRTHDATE")
    private Date dateOfBirth;
    @Column(name = "ADDRESS")
    private String address;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "USER_ID")
    private User user;

    @Transient
    @ManyToMany
    @JoinTable(name="TRAINEE2TRAINER",
            joinColumns=@JoinColumn(name="TRAINEE_ID"),
            inverseJoinColumns=@JoinColumn(name="TRAINER_ID"))
    private Set<Trainer> trainers = new HashSet<>();

    @Transient
    @OneToMany(mappedBy = "trainee", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Training> trainingsList = new ArrayList<>();

    @Override
    public String toString() {
        return "Trainee{" +
                "traineeId=" + traineeId +
                ", dateOfBirth=" + dateOfBirth +
                ", address='" + address + '\'' +
                ", user=" + user +
                '}';
    }
}
