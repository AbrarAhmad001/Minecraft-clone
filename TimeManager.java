package application;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;

public class TimeManager {

    private static TimeManager instance;
    private Timeline timeline;

    private TimeManager() {
        timeline = new Timeline(new KeyFrame(Duration.seconds(1), e -> tick()));
        timeline.setCycleCount(Timeline.INDEFINITE);
    }

    public static TimeManager getInstance() {
        if (instance == null) instance = new TimeManager();
        return instance;
    }

    public void startTimers() {
        timeline.play();
    }

    public void pauseTimers() {
        timeline.pause();
    }

    public void resumeTimers() {
        timeline.play();
    }
    
    private void tick() {
        InventoryController.getInventory().forEach(item -> item.increaseWearPerSecond());
    }
    
    
}
