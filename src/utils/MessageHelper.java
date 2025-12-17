/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;
import javax.swing.JOptionPane;
/**
 *
 * @author ken
 */
public class MessageHelper {
    // Success Message
    public static void showSuccessMessage(String message) {
        JOptionPane.showMessageDialog(null, message, "Success", JOptionPane.INFORMATION_MESSAGE);
    }

    // Error Message
    public static void showErrorMessage(String message) {
        JOptionPane.showMessageDialog(null, message, "Error", JOptionPane.ERROR_MESSAGE);
    }

    // Warning Message
    public static void showWarningMessage(String message) {
        JOptionPane.showMessageDialog(null, message, "Warning", JOptionPane.WARNING_MESSAGE);
    }

    // Information Message
    public static void showInfoMessage(String message) {
        JOptionPane.showMessageDialog(null, message, "Information", JOptionPane.INFORMATION_MESSAGE);
    }

    // Confirmation Dialog (Yes/No)
    public static boolean showConfirmDialog(String message) {
        int result = JOptionPane.showConfirmDialog(null, message, "Confirm", 
                     JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
        return result == JOptionPane.YES_OPTION;
    }

    // Input Dialog
    public static String showInputDialog(String message) {
        return JOptionPane.showInputDialog(null, message, "Input", JOptionPane.QUESTION_MESSAGE);
    }

    // Custom Message with Title
    public static void showMessage(String title, String message, int messageType) {
        JOptionPane.showMessageDialog(null, message, title, messageType);
    }
}

