package com.rpatino12.epam.gym.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "TRAINING")
@Getter
@Setter
public class Training implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "TRAINING_ID")
    private Long trainingId;
    @Column(name = "TRAINING_NAME")
    private String trainingName;
    @Column(name = "TRAINING_DATE")
    private Date trainingDate;
    @Column(name = "TRAINING_DURATION")
    private Double trainingDuration;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "TRAINING_TYPE_ID")
    private TrainingType trainingType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "TRAINER_ID")
    private Trainer trainer;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "TRAINEE_ID")
    private Trainee trainee;

    @Override
    public String toString() {
        return "Training{" +
                "trainingId=" + trainingId +
                ", trainingName='" + trainingName + '\'' +
                ", trainingDate=" + trainingDate +
                ", trainingDuration=" + trainingDuration +
                ", trainingType=" + trainingType +
                ", trainer=" + trainer +
                ", trainee=" + trainee +
                '}';
    }
}
