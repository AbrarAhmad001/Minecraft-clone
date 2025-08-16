package application;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.fxml.FXMLLoader;
import java.io.IOException;
import java.util.Iterator;

public class InventoryController {

    @FXML private TableView<IndustrialItem> inventoryTable;
    @FXML private TableColumn<IndustrialItem, String> nameColumn;
    @FXML private TableColumn<IndustrialItem, Double> wearColumn;
    @FXML private TableColumn<IndustrialItem, String> dateColumn;
    @FXML private TableColumn<IndustrialItem, Integer> maintenanceColumn;
    @FXML private Button maintenanceButton;
    @FXML private Button switchToGeneratorButton;
    @FXML private Button quitButton;

    private static final ObservableList<IndustrialItem> inventory = FXCollections.observableArrayList();
    private Timeline timeline;
    private String username = "admin";

    @FXML
    public void initialize() {
        nameColumn.setCellValueFactory(data -> data.getValue().displayNameProperty());
        wearColumn.setCellValueFactory(data -> data.getValue().wearPercentageProperty().asObject());
        dateColumn.setCellValueFactory(data -> data.getValue().dateAddedProperty());
        maintenanceColumn.setCellValueFactory(data -> data.getValue().timesMaintainedProperty().asObject());

        inventoryTable.setItems(inventory);
        inventoryTable.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

        timeline = new Timeline(new KeyFrame(Duration.seconds(1), e -> updateWear()));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }

    private void updateWear() {
        Iterator<IndustrialItem> iterator = inventory.iterator();
        while (iterator.hasNext()) {
            IndustrialItem item = iterator.next();
            item.increaseWearPerSecond();

            if (item.isCritical()) {
                iterator.remove();
            }
        }
        inventoryTable.refresh();
    }
    
    

    @FXML
    private void performMaintenance() {
        ObservableList<IndustrialItem> selectedItems = inventoryTable.getSelectionModel().getSelectedItems();
        if (selectedItems.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("No Selection");
            alert.setHeaderText("No Item Selected");
            alert.setContentText("Please select an item in the table.");
            alert.showAndWait();
            return;
        }

        try {
            TimeManager.getInstance().pauseTimers();
            
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Maintenance.fxml"));
            Parent root = loader.load();
            
            MaintenanceController controller = loader.getController();
            controller.setItems(selectedItems);
            controller.setUsername(username);
            
            Stage stage = (Stage) inventoryTable.getScene().getWindow();
            stage.setScene(new Scene(root));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void switchTogenerator() throws IOException { // Corrected method name
        Parent generatorRoot = FXMLLoader.load(getClass().getResource("generator.fxml"));
        Stage stage = (Stage) switchToGeneratorButton.getScene().getWindow();
        stage.setScene(new Scene(generatorRoot));
    }

    @FXML
    private void quitApp() {
        timeline.stop();
        // Log final totals when quitting
        RewardLogger.logFinalTotals();
        Stage stage = (Stage) quitButton.getScene().getWindow();
        stage.close();
    }

    public static ObservableList<IndustrialItem> getInventory() {
        return inventory;
    }
}