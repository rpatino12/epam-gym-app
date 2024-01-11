package com.rpatino12.epam.gym.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "TRAINING_TYPE")
@Getter
@Setter
public class TrainingType implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "TRAINING_TYPE_ID")
    private Long trainingTypeId;
    @Column(name = "TRAINING_TYPE_NAME")
    private String trainingTypeName;

    @Transient
    @OneToMany(mappedBy = "trainingType")
    private List<Training> trainingsList = new ArrayList<>();

    @Transient
    @OneToMany(mappedBy = "specialization")
    private List<Trainer> trainers = new ArrayList<>();

    @Override
    public String toString() {
        return "TrainingType{" +
                "trainingTypeId=" + trainingTypeId +
                ", trainingTypeName='" + trainingTypeName + '\'' +
                '}';
    }
}
