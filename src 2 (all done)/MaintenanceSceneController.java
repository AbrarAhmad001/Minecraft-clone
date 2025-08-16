package application;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Random;

public class MaintenanceSceneController {

    @FXML
    private Label captchaLabel;

    @FXML
    private TextField inputField;

    @FXML
    private Button submitButton;

    private String currentCaptcha;
    private IndustrialItem selectedItem;

    private Random rand = new Random();

    @FXML
    private void initialize() {
        generateCaptcha();
    }

    private void generateCaptcha() {
        currentCaptcha = randomAlphaNum(5);
        captchaLabel.setText("CAPTCHA: " + currentCaptcha);
    }

    private String randomAlphaNum(int len) {
        String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < len; i++) {
            sb.append(chars.charAt(rand.nextInt(chars.length())));
        }
        return sb.toString();
    }

    @FXML
    private void submitMaintenance() {
        if (inputField.getText().equalsIgnoreCase(currentCaptcha)) {
            selectedItem.reduceWear(20); // Reduce wear by 20%
        } else {
            selectedItem.increaseWear(5); // Increase wear by 5%
        }

        // Resume wear timers
        TimeManager.getInstance().resumeTimers();

        // Go back to inventory scene
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("InventoryScene.fxml"));
            Stage stage = (Stage) submitButton.getScene().getWindow();
            stage.setScene(new Scene(loader.load()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setItem(IndustrialItem item) {
        this.selectedItem = item;
    }
}

