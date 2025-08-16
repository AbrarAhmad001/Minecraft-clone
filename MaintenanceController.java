package application;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;

import java.io.IOException;
import java.util.List;
import java.util.Random;

public class MaintenanceController {

    @FXML private Label captchaLabel;
    @FXML private TextField inputField;
    @FXML private Button submitButton;
    @FXML private Label messageLabel;

    private List<IndustrialItem> items;
    private String username;
    private String currentCaptcha;
    private final Random rand = new Random();

    public void setItems(List<IndustrialItem> items) {
        this.items = items;
        generateCaptcha();
    }

    public void setUsername(String username) {
        this.username = username;
    }

    private void generateCaptcha() {
        String chars = "ABCDEFGHJKLMNPQRSTUVWXYZ23456789";
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 5; i++) {
            sb.append(chars.charAt(rand.nextInt(chars.length())));
        }
        currentCaptcha = sb.toString();
        captchaLabel.setText(currentCaptcha);
    }

    @FXML
    private void submit() {
        String input = inputField.getText().trim();
        
        if (input.isEmpty()) {
            messageLabel.setText("Please enter the verification code");
            return;
        }
        
        boolean success = input.equalsIgnoreCase(currentCaptcha);
        
        for (IndustrialItem item : items) {
            if (success) {
                item.reduceWear(30);
            } else {
                item.increaseWear(10);
                
                if (item.isCritical()) {
                    InventoryController.getInventory().remove(item);
                }
            }
            // Log each maintenance action
            RewardLogger.logMaintenance(username, item.getDisplayName(), item.getType(), success);
        }
        
        // Resume wear updates
        TimeManager.getInstance().resumeTimers();
        
        // Return to inventory
        Stage stage = (Stage) captchaLabel.getScene().getWindow();
        try {
            stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("inventory.fxml"))));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}