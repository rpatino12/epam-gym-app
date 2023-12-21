package com.rpatino12.epam.gym.service;

import com.rpatino12.epam.gym.dao.UserDAO;
import com.rpatino12.epam.gym.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    private UserDAO userDAO;

    public User registerUser(String firstName, String lastName, boolean isActive){
        String username = generateUsername(firstName, lastName);
        String password = generateRandomPassword();

        User newUser = new User();
        newUser.setFirstName(firstName);
        newUser.setLastName(lastName);
        newUser.setUsername(username);
        newUser.setPassword(password);
        newUser.setActive(isActive);

        return userDAO.save(newUser);
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
