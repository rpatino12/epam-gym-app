package com.rpatino12.epam.gym.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

import java.util.Date;
import java.util.List;

@Entity
@Table(name = "TRAINEE")
public class Trainee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "TRAINEE_ID")
    private Long traineeId;
    @Column(name = "USER_ID")
    private Long userId; // (FK)
    @Column(name = "BIRTHDATE")
    private Date dateOfBirth;
    @Column(name = "ADDRESS")
    private String address;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "USER_ID", nullable = false, insertable = false, updatable = false)
    private User user;

    @OneToMany
    @JoinColumn(name = "TRAINER_ID", nullable = false, insertable = false, updatable = false)
    private List<Trainer> trainers;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "trainee")
    private List<Training> trainings;

    public Trainee() {
    }

    public Trainee(Date dateOfBirth, String address, User user) {
        this.dateOfBirth = dateOfBirth;
        this.address = address;
        this.user = user;
    }

    public Long getTraineeId() {
        return traineeId;
    }

    public void setTraineeId(Long traineeId) {
        this.traineeId = traineeId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<Trainer> getTrainers() {
        return trainers;
    }

    public void setTrainers(List<Trainer> trainers) {
        this.trainers = trainers;
    }

    @Override
    public String toString() {
        return "Trainee{" +
                "traineeId=" + traineeId +
                ", userId=" + userId +
                ", dateOfBirth=" + dateOfBirth +
                ", address='" + address + '\'' +
                '}';
    }
}
