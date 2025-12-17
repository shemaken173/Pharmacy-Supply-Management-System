/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;
import dao.UserDao;
import model.User;
import utils.Validator;
import java.util.List;

/**
 *
 * @author ken
 */
public class UserController {

    private UserDao userDao;
    private Validator validator;

    public UserController() {
        this.userDao = new UserDao();
        this.validator = new Validator();
    }

    // Business Logic: Add User with Validations
    public String addUser(User user) {
        // Technical Validations
        if (!validator.isValidEmail(user.getEmail())) {
            return "Invalid email format!";
        }
        if (!validator.isValidPhoneNumber(user.getPhoneNumber())) {
            return "Phone number must be 10 digits!";
        }
        if (!validator.isValidPassword(user.getPassword())) {
            return "Password must be at least 8 characters!";
        }
        if (user.getUsername().trim().isEmpty()) {
            return "Username cannot be empty!";
        }
        if (user.getFullName().trim().isEmpty()) {
            return "Full name cannot be empty!";
        }

        // Business Validations
        if (isUsernameExists(user.getUsername())) {
            return "Username already exists!";
        }
        if (isEmailExists(user.getEmail())) {
            return "Email already exists!";
        }

        // Attempt to add user
        boolean success = userDao.addUser(user);
        return success ? "SUCCESS" : "Failed to add user!";
    }

    // Update User
    public String updateUser(User user) {
        // Technical Validations
        if (!validator.isValidEmail(user.getEmail())) {
            return "Invalid email format!";
        }
        if (!validator.isValidPhoneNumber(user.getPhoneNumber())) {
            return "Phone number must be 10 digits!";
        }
        if (!validator.isValidPassword(user.getPassword())) {
            return "Password must be at least 8 characters!";
        }

        boolean success = userDao.updateUser(user);
        return success ? "SUCCESS" : "Failed to update user!";
    }

    // Delete User
    public String deleteUser(int userId) {
        // Business Validation: Check if user has related records
        // You can add additional checks here if needed
        
        boolean success = userDao.deleteUser(userId);
        return success ? "SUCCESS" : "Failed to delete user! User may have related records.";
    }

    // Get All Users
    public List<User> getAllUsers() {
        return userDao.getAllUsers();
    }

    // Login Authentication
    public User authenticateUser(String username, String password) {
        List<User> users = userDao.getAllUsers();
        for (User user : users) {
            if (user.getUsername().equals(username) && user.getPassword().equals(password)) {
                return user;
            }
        }
        return null;
    }

    // Helper: Check if username exists
    private boolean isUsernameExists(String username) {
        List<User> users = userDao.getAllUsers();
        for (User u : users) {
            if (u.getUsername().equalsIgnoreCase(username)) {
                return true;
            }
        }
        return false;
    }

    // Helper: Check if email exists
    private boolean isEmailExists(String email) {
        List<User> users = userDao.getAllUsers();
        for (User u : users) {
            if (u.getEmail().equalsIgnoreCase(email)) {
                return true;
            }
        }
        return false;
    }
}


