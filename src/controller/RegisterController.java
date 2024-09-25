package controller;

import view.RegisterView;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.HashMap;

public class RegisterController {
    private RegisterView registerView;
    private HashMap<String, String> userDatabase;

    public RegisterController(RegisterView registerView) {
        this.registerView = registerView;
        this.registerView.getRegisterButton().addActionListener(new RegisterListener());
        loadUserDatabase();
    }

    private void loadUserDatabase() {
        userDatabase = new HashMap<>();
        try (BufferedReader br = new BufferedReader(new FileReader("users.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(":");
                if (parts.length == 2) {
                    userDatabase.put(parts[0], parts[1]);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void saveUserDatabase() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter("users.txt"))) {
            for (String username : userDatabase.keySet()) {
                bw.write(username + ":" + userDatabase.get(username));
                bw.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    class RegisterListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String username = registerView.getUsername();
            String password = registerView.getPassword();
            String confirmPassword = registerView.getConfirmPassword();

            if (!username.isEmpty() && !password.isEmpty() && password.equals(confirmPassword)) {
                if (userDatabase.containsKey(username)) {
                    JOptionPane.showMessageDialog(registerView, "Username already exists!");
                } else {
                    userDatabase.put(username, password);
                    saveUserDatabase();
                    JOptionPane.showMessageDialog(registerView, "Registration successful!");
                    registerView.dispose(); // Close registration window
                }
            } else {
                JOptionPane.showMessageDialog(registerView, "Please fill in all fields correctly!");
            }
        }
    }
}
