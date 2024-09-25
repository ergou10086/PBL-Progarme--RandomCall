package controller;

import view.MainMenuView;
import view.StudentManagementView;
import view.RandomRollCallView;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainMenuController {

    private MainMenuView mainMenuView;


    public MainMenuController(MainMenuView mainMenuView) {
        this.mainMenuView = mainMenuView;
        this.mainMenuView.getRandomRollCallButton().addActionListener(new RandomRollCallListener());
        this.mainMenuView.getManageStudentsButton().addActionListener(new ManageStudentsListener());
    }


    class RandomRollCallListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            mainMenuView.setVisible(true);
            RandomRollCallView randomRollCallView = new RandomRollCallView();
            RandomRollCallController randomRollCallController = new RandomRollCallController(randomRollCallView);
            randomRollCallView.setVisible(true);
        }
    }

    class ManageStudentsListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            mainMenuView.setVisible(true);
            StudentManagementView studentManagementView = new StudentManagementView();
            StudentManagementController studentManagementController = new StudentManagementController(studentManagementView);
            studentManagementView.setVisible(true);
        }
    }
}
