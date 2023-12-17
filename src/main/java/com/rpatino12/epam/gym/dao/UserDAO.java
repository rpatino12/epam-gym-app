package com.rpatino12.epam.gym.dao;

import com.rpatino12.epam.gym.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserDAO extends JpaRepository<User, Long> {

}
