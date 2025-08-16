package application;

import javafx.beans.property.*;

import java.util.Random;

public class IndustrialItem {

    private final SimpleStringProperty displayName;
    private final SimpleDoubleProperty wearPercentage;
    private final SimpleStringProperty dateAdded;
    private final SimpleIntegerProperty timesMaintained;
    private final String type;

    private final Random rand = new Random();

    public IndustrialItem(String name, String type) {
        this.displayName = new SimpleStringProperty(name);
        this.type = type;
        // Start with random wear between 50% - 85%
        this.wearPercentage = new SimpleDoubleProperty(50 + rand.nextInt(36));
        this.dateAdded = new SimpleStringProperty(java.time.LocalDate.now().toString());
        this.timesMaintained = new SimpleIntegerProperty(0);
    }

    public String getDisplayName() { return displayName.get(); }
    public SimpleStringProperty displayNameProperty() { return displayName; }

    public double getWearPercentage() { return wearPercentage.get(); }
    public SimpleDoubleProperty wearPercentageProperty() { return wearPercentage; }

    public String getDateAdded() { return dateAdded.get(); }
    public SimpleStringProperty dateAddedProperty() { return dateAdded; }

    public int getTimesMaintained() { return timesMaintained.get(); }
    public SimpleIntegerProperty timesMaintainedProperty() { return timesMaintained; }

    public String getType() { return type; }

    // Increase wear based on type
    public void increaseWearPerSecond() {
        double increment;
        switch (type) {
            case "Engine": increment = 0.1; break;       // per second
            case "Chemical": increment = 0.15; break;
            case "Electronics": increment = 0.08; break;
            default: increment = 0.1;
        }
        wearPercentage.set(Math.min(100, wearPercentage.get() + increment));
    }

    public void reduceWear(double percent) {
        wearPercentage.set(Math.max(0, wearPercentage.get() - percent));
        timesMaintained.set(timesMaintained.get() + 1);
    }

    public void increaseWear(double percent) {
        wearPercentage.set(Math.min(100, wearPercentage.get() + percent));
    }

    public boolean isCritical() {
        return wearPercentage.get() >= 100;
    }
}
