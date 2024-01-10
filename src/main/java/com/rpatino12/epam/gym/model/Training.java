package com.rpatino12.epam.gym.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

import java.util.Date;

@Entity
@Table(name = "TRAINING")
@Data
public class Training {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "TRAINING_ID")
    private Long trainingId;
    @Column(name = "TRAINEE_ID")
    private Long traineeId; // (FK)
    @Column(name = "TRAINER_ID")
    private Long trainerId; // (FK)
    @Column(name = "TRAINING_NAME")
    private String trainingName;
    @Column(name = "TRAINING_TYPE_ID")
    private Long trainingTypeId; // (FK)
    @Column(name = "TRAINING_DATE")
    private Date trainingDate;
    @Column(name = "TRAINING_DURATION")
    private Double trainingDuration;

    @ManyToOne
    @JoinColumn(name = "TRAINING_TYPE_ID", insertable = false, updatable = false)
    private TrainingType trainingType;

    @ManyToOne
    @JoinColumn(name = "TRAINER_ID", insertable = false, updatable = false)
    private Trainer trainer;

    @ManyToOne
    @JoinColumn(name = "TRAINEE_ID", insertable = false, updatable = false)
    private Trainee trainee;
}
