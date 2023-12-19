package com.rpatino12.epam.gym.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

import java.util.List;

@Entity
@Table(name = "TRAINER")
public class Trainer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "TRAINER_ID")
    private Long trainerId;
    @Column(name = "USER_ID")
    private Long userId; // (FK)
    @Column(name = "SPECIALIZATION_ID")
    private Long specializationId; // trainingTypeId (FK)

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "USER_ID", nullable = false, insertable = false, updatable = false)
    private User user;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "traineeId")
    private List<Trainee> trainees;

    @ManyToOne
    @JoinColumn(name = "TRAINING_TYPE_ID", insertable = false, updatable = false)
    private TrainingType specialization;

    public Trainer() {
    }

    public Trainer(Long specializationId, User user) {
        this.specializationId = specializationId;
        this.user = user;
    }

    public Long getTrainerId() {
        return trainerId;
    }

    public void setTrainerId(Long trainerId) {
        this.trainerId = trainerId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getSpecializationId() {
        return specializationId;
    }

    public void setSpecializationId(Long specializationId) {
        this.specializationId = specializationId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<Trainee> getTrainees() {
        return trainees;
    }

    public void setTrainees(List<Trainee> trainees) {
        this.trainees = trainees;
    }

    public TrainingType getSpecialization() {
        return specialization;
    }

    public void setSpecialization(TrainingType specialization) {
        this.specialization = specialization;
    }

    @Override
    public String toString() {
        return "Trainer{" +
                "trainerId=" + trainerId +
                ", userId=" + userId +
                ", specializationId=" + specializationId +
                '}';
    }
}
