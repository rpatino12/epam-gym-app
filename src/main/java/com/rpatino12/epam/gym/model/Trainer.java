package com.rpatino12.epam.gym.model;

public class Trainer {
    private Long trainerId;
    private Long userId; // (FK)
    private Long specializationId; // trainingTypeId (FK)

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

    @Override
    public String toString() {
        return "Trainer{" +
                "trainerId=" + trainerId +
                ", userId=" + userId +
                ", specializationId=" + specializationId +
                '}';
    }
}
