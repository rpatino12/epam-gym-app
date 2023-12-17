package com.rpatino12.epam.gym.dao;

import com.rpatino12.epam.gym.model.Trainer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TrainerDAO extends JpaRepository<Trainer, Long> {

}
