import controller.LoginController;
import javax.swing.SwingUtilities;
import view.LoginView;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            LoginView loginView = new LoginView();
            LoginController loginController = new LoginController(loginView);
            loginView.setVisible(true);
        });
    }
}

