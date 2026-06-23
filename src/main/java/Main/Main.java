package Main;

import javax.swing.*;
import java.io.*;
import ui.login.*;

public class Main {
    public static void main(String[] args) {
     try {
            SwingUtilities.invokeLater(() -> {
                new LoginView();
            });

        } catch (Exception e) {

            e.printStackTrace();

            JOptionPane.showMessageDialog(
                    null,
                    e.toString(),
                    "ERROR",
                    JOptionPane.ERROR_MESSAGE);
        }
    }
}