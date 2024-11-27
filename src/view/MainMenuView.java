package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class MainMenuView extends JFrame {
    private JButton randomRollCallButton;
    private JButton manageStudentsButton;
    private JButton switchAccounts;
    private LoginView loginView;

    public MainMenuView(LoginView loginView) {
        setTitle("学生管理系统");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        
        this.loginView = loginView;

        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel titleLabel = new JLabel("学生管理系统", SwingConstants.CENTER);
        titleLabel.setFont(new Font("微软雅黑", Font.BOLD, 24));
        
        JPanel buttonPanel = new JPanel(new GridLayout(3, 1, 10, 10));
        
        randomRollCallButton = createStyledButton("随机点名", new Color(100, 180, 100));
        manageStudentsButton = createStyledButton("学生管理", new Color(70, 130, 180));
        switchAccounts = createStyledButton("切换账号", new Color(180, 100, 100));

        buttonPanel.add(randomRollCallButton);
        buttonPanel.add(manageStudentsButton);
        buttonPanel.add(switchAccounts);

        switchAccounts.addActionListener(e -> {
            loginView.setVisible(true);
            dispose();
        });

        mainPanel.add(titleLabel, BorderLayout.NORTH);
        mainPanel.add(buttonPanel, BorderLayout.CENTER);

        setContentPane(mainPanel);
    }

    private JButton createStyledButton(String text, Color color) {
        JButton button = new JButton(text);
        button.setFont(new Font("微软雅黑", Font.PLAIN, 16));
        button.setBackground(color);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setPreferredSize(new Dimension(200, 40));
        return button;
    }

    public JButton getRandomRollCallButton() {
        return randomRollCallButton;
    }

    public JButton getManageStudentsButton() {
        return manageStudentsButton;
    }

    public JButton getSwitchAccounts() {
        return switchAccounts;
    }
}
