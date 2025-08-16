package application;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import java.io.IOException;
import java.util.Random;

public class GeneratorController {

    @FXML private Button switchToInventoryButton;
    @FXML private Button generateEngineButton;
    @FXML private Button generateChemButton;
    @FXML private Button generateElectronicsButton;
    @FXML private Label generatedLabel;

    private final Random rand = new Random();

    @FXML
    private void switchToInventory() throws IOException {
        Parent inventoryRoot = FXMLLoader.load(getClass().getResource("inventory.fxml"));
        Stage stage = (Stage) switchToInventoryButton.getScene().getWindow();
        stage.setScene(new Scene(inventoryRoot));
    }

    @FXML
    private void generateEngineItem() {
        IndustrialItem item = new IndustrialItem("Engine " + rand.nextInt(1000), "Engine");
        InventoryController.getInventory().add(item);
        generatedLabel.setText("Generated: " + item.getDisplayName());
    }

    @FXML
    private void generateChemItem() {
        IndustrialItem item = new IndustrialItem("Chemical " + rand.nextInt(1000), "Chemical");
        InventoryController.getInventory().add(item);
        generatedLabel.setText("Generated: " + item.getDisplayName());
    }

    @FXML
    private void generateElectronicsItem() {
        IndustrialItem item = new IndustrialItem("Electronics " + rand.nextInt(1000), "Electronics");
        InventoryController.getInventory().add(item);
        generatedLabel.setText("Generated: " + item.getDisplayName());
    }

}

