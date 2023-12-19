package com.rpatino12.epam.gym.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "TRAINEE2TRAINER")
public class TraineeToTrainer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "TRAINEE2TRAINER_ID")
    private Long Id;
    @Column(name = "TRAINEE_ID")
    private Long traineeId; // (FK)
    @Column(name = "TRAINER_ID")
    private Long trainerId; // (FK)

    @ManyToOne
    @JoinColumn(name = "TRAINEE_ID", insertable = false, updatable = false, nullable = false)
    private Trainee trainee;

    @ManyToOne
    @JoinColumn(name = "TRAINER_ID", insertable = false, updatable = false, nullable = false)
    private Trainer trainer;

    public Long getId() {
        return Id;
    }

    public void setId(Long id) {
        Id = id;
    }

    public Long getTraineeId() {
        return traineeId;
    }

    public void setTraineeId(Long traineeId) {
        this.traineeId = traineeId;
    }

    public Long getTrainerId() {
        return trainerId;
    }

    public void setTrainerId(Long trainerId) {
        this.trainerId = trainerId;
    }
}
