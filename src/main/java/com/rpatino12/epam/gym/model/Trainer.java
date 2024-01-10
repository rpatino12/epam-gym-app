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
import lombok.Data;

import java.util.List;

@Entity
@Table(name = "TRAINER")
@Data
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

    @OneToMany(mappedBy = "traineeId")
    private List<Trainee> trainees;

    @ManyToOne
    @JoinColumn(name = "SPECIALIZATION_ID", insertable = false, updatable = false)
    private TrainingType specialization;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "trainerId")
    private List<Training> trainings;

    public Trainer() {
    }

    public Trainer(Long specializationId, User user) {
        this.specializationId = specializationId;
        this.user = user;
    }
}
