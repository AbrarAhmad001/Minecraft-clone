package application;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import java.io.IOException;

public class LoginController {

    @FXML private TextField usernameField;
    @FXML private PasswordField passwordField;
    @FXML private Label judgementLabel;

    // Dummy username/password
    private final String DUMMY_USERNAME = "admin";
    private final String DUMMY_PASSWORD = "1234";

    @FXML
    private void handleLogin() {
        String username = usernameField.getText();
        String password = passwordField.getText();

        if (username.equals(DUMMY_USERNAME) && password.equals(DUMMY_PASSWORD)) {
            // Login success → go to Inventory scene
            try {
                Parent inventoryRoot = FXMLLoader.load(getClass().getResource("inventory.fxml"));
                Stage stage = (Stage) usernameField.getScene().getWindow();
                stage.setScene(new Scene(inventoryRoot));
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            judgementLabel.setText("Invalid username or password!");
        }
    }
}

