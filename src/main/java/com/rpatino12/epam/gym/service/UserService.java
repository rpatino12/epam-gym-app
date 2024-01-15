package com.rpatino12.epam.gym.service;

import com.rpatino12.epam.gym.dao.UserDAO;
import com.rpatino12.epam.gym.model.User;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    private final UserDAO userDAO;
    private static final Log LOGGER = LogFactory.getLog(UserService.class);

    public UserService(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    public User registerUser(User user){
        String username = generateUsername(user.getFirstName(), user.getLastName());
        String password = generateRandomPassword();

        User newUser = new User();
        newUser.setFirstName(user.getFirstName());
        newUser.setLastName(user.getLastName());
        newUser.setUsername(username);
        newUser.setPassword(password);
        newUser.setIsActive(user.getIsActive());

        return userDAO.save(newUser);
    }

    public Optional<User> getUser(Long userId){
        return userDAO.findById(userId);
    }

    public User updateUser(User newUser, Long userId){
        return userDAO.findById(userId)
                .map(
                        user -> {
                            user.setFirstName(newUser.getFirstName());
                            user.setLastName(newUser.getLastName());
                            user.setUsername(generateUsername(newUser.getFirstName(), newUser.getLastName()));
                            user.setPassword(generateRandomPassword());
                            user.setIsActive(newUser.getIsActive());
                            return userDAO.save(user);
                        }
                ).get();
    }

    private String generateUsername(String firstName, String lastName) {
        String baseUsername = firstName + "." + lastName;
        String username = baseUsername;

        // Check if the username already exists, add a serial number if necessary
        int serialNumber = 1;
        while (userDAO.existsUserByUsername(username)) {
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
}
