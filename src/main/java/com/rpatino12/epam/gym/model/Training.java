package com.rpatino12.epam.gym.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.util.Date;

@Entity
@Table(name = "TRAINING")
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

    public Long getTrainingId() {
        return trainingId;
    }

    public void setTrainingId(Long trainingId) {
        this.trainingId = trainingId;
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

    public String getTrainingName() {
        return trainingName;
    }

    public void setTrainingName(String trainingName) {
        this.trainingName = trainingName;
    }

    public Long getTrainingTypeId() {
        return trainingTypeId;
    }

    public void setTrainingTypeId(Long trainingTypeId) {
        this.trainingTypeId = trainingTypeId;
    }

    public Date getTrainingDate() {
        return trainingDate;
    }

    public void setTrainingDate(Date trainingDate) {
        this.trainingDate = trainingDate;
    }

    public Double getTrainingDuration() {
        return trainingDuration;
    }

    public void setTrainingDuration(Double trainingDuration) {
        this.trainingDuration = trainingDuration;
    }
}
