/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;
import java.util.regex.Pattern;
/**
 *
 * @author ken
 */
public class Validator {
    // Technical Validation: Email Format
    public boolean isValidEmail(String email) {
        if (email == null || email.trim().isEmpty()) {
            return false;
        }
        String emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$";
        return Pattern.matches(emailRegex, email);
    }

    // Technical Validation: Phone Number (10 digits)
    public boolean isValidPhoneNumber(String phone) {
        if (phone == null || phone.trim().isEmpty()) {
            return false;
        }
        // Remove spaces, dashes, parentheses
        String cleaned = phone.replaceAll("[\\s\\-\\(\\)]", "");
        return cleaned.matches("\\d{10}");
    }

    // Technical Validation: Password Length (minimum 8 characters)
    public boolean isValidPassword(String password) {
        return password != null && password.length() >= 8;
    }

    // Technical Validation: Positive Decimal Number
    public boolean isPositiveDecimal(double value) {
        return value > 0;
    }

    // Technical Validation: Non-Negative Integer
    public boolean isNonNegativeInteger(int value) {
        return value >= 0;
    }

    // Technical Validation: Positive Integer
    public boolean isPositiveInteger(int value) {
        return value > 0;
    }

    // Technical Validation: String Not Empty
    public boolean isNotEmpty(String value) {
        return value != null && !value.trim().isEmpty();
    }

    // Technical Validation: String Length
    public boolean isValidLength(String value, int minLength, int maxLength) {
        if (value == null) return false;
        int length = value.trim().length();
        return length >= minLength && length <= maxLength;
    }

    // Technical Validation: Numeric String
    public boolean isNumeric(String value) {
        if (value == null || value.trim().isEmpty()) {
            return false;
        }
        try {
            Double.parseDouble(value);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    // Technical Validation: Date Format (YYYY-MM-DD)
    public boolean isValidDateFormat(String date) {
        if (date == null || date.trim().isEmpty()) {
            return false;
        }
        String dateRegex = "^\\d{4}-\\d{2}-\\d{2}$";
        return Pattern.matches(dateRegex, date);
    }

    // Technical Validation: DateTime Format (YYYY-MM-DD HH:MM:SS)
    public boolean isValidDateTimeFormat(String dateTime) {
        if (dateTime == null || dateTime.trim().isEmpty()) {
            return false;
        }
        String dateTimeRegex = "^\\d{4}-\\d{2}-\\d{2} \\d{2}:\\d{2}:\\d{2}$";
        return Pattern.matches(dateTimeRegex, dateTime);
    }

    // Business Validation: Check if value is within range
    public boolean isInRange(int value, int min, int max) {
        return value >= min && value <= max;
    }

    // Business Validation: Check if value is within range (double)
    public boolean isInRange(double value, double min, double max) {
        return value >= min && value <= max;
    }

    // Sanitize input (remove leading/trailing spaces)
    public String sanitize(String input) {
        return input == null ? "" : input.trim();
    }

    // Validate username (alphanumeric and underscore only, 3-20 chars)
    public boolean isValidUsername(String username) {
        if (username == null || username.trim().isEmpty()) {
            return false;
        }
        String usernameRegex = "^[A-Za-z0-9_]{3,20}$";
        return Pattern.matches(usernameRegex, username);
    }
}

