package com.rpatino12.epam.gym.service;

import com.rpatino12.epam.gym.repo.UserRepository;
import com.rpatino12.epam.gym.model.User;
import jakarta.annotation.PostConstruct;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Slf4j
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Transactional
    public User registerUser(User user){
        String username = generateUsername(user.getFirstName(), user.getLastName());
        String password = generateRandomPassword();

        User newUser = new User();
        newUser.setFirstName(user.getFirstName());
        newUser.setLastName(user.getLastName());
        newUser.setUsername(username);
        newUser.setPassword(password);
        newUser.setIsActive(user.getIsActive());

        log.info("Creating user");
        return userRepository.save(newUser);
    }

    @Transactional
    public Optional<User> getUser(Long userId){
        log.info("Getting user");
        return userRepository.findById(userId);
    }

    @Transactional
    public Optional<User> getUserByUsername(String username){
        log.info("Searching user: " + username);
        return userRepository.findByUsername(username);
    }

    @Transactional
    public User updateUser(User newUser, Long userId){
        log.info("Updating user: Id=" + userId);
        return userRepository.findById(userId)
                .map(
                        user -> {
                            user.setFirstName(newUser.getFirstName());
                            user.setLastName(newUser.getLastName());
                            user.setUsername(generateUsername(newUser.getFirstName(), newUser.getLastName()));
                            user.setPassword(generateRandomPassword());
                            user.setIsActive(newUser.getIsActive());
                            return userRepository.save(user);
                        }
                ).get();
    }

    @Transactional
    public User updateUserPassword(String newPassword, Long userId){
        log.info("Updating user password");
        return userRepository.findById(userId)
                .map(
                        user -> {
                            user.setPassword(newPassword);
                            return userRepository.save(user);
                        }
                ).get();
    }

    @Transactional
    public User updateStatus(boolean isActive, Long userId){
        log.info("Updating user status");
        return userRepository.findById(userId)
                .map(
                        user -> {
                            user.setIsActive(isActive);
                            return userRepository.save(user);
                        }
                ).get();
    }

    @Transactional
    private String generateUsername(String firstName, String lastName) {
        String baseUsername = firstName + "." + lastName;
        String username = baseUsername;

        // Check if the username already exists, add a serial number if necessary
        int serialNumber = 1;
        while (userRepository.existsUserByUsername(username)) {
            username = baseUsername + serialNumber++;
        }
        return username;
    }

    private String generateRandomPassword() {
        String allowedChars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        StringBuilder password = new StringBuilder();
        int passwordLength = 10;

        for (int i = 0; i < passwordLength; i++) {
            int randomIndex = (int) (Math.random() * allowedChars.length());
            password.append(allowedChars.charAt(randomIndex));
        }
        return password.toString();
    }

    @PostConstruct
    public void init(){
        log.info("Starting UserService");
    }
}
