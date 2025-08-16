package application;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.atomic.AtomicInteger;

public class RewardLogger {

    private static final String MAINT_FILE = "data/maintenance_log.txt";
    private static final AtomicInteger serialCounter = new AtomicInteger(1);
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    
    // Global counters
    private static int totalSuccessfulMaintenance = 0;
    private static int totalItemsDestroyed = 0;

    static {
        File folder = new File("data");
        if (!folder.exists()) folder.mkdir();
    }

    public static void logMaintenance(String username, String itemName, String itemType, boolean success) {
        int serial = serialCounter.getAndIncrement();
        String timestamp = dateFormat.format(new Date());
        String status = success ? "Success" : "Failed";
        
        try (FileWriter writer = new FileWriter(MAINT_FILE, true)) {
            writer.write(String.format("%d | %s | %s | %s | %s | %s%n", 
                    serial, timestamp, username, itemName, itemType, status));
            
            if (success) {
                totalSuccessfulMaintenance++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void logDestruction(String username, String itemName, String itemType) {
        int serial = serialCounter.getAndIncrement();
        String timestamp = dateFormat.format(new Date());
        
        try (FileWriter writer = new FileWriter(MAINT_FILE, true)) {
            writer.write(String.format("%d | %s | %s | %s | %s | Destroyed%n", 
                    serial, timestamp, username, itemName, itemType));
            
            totalItemsDestroyed++;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void logFinalTotals() {
        try (FileWriter writer = new FileWriter(MAINT_FILE, true)) {
            writer.write("\n--- Final Totals ---\n");
            writer.write("Total Successful Maintenance Actions: " + totalSuccessfulMaintenance + "\n");
            writer.write("Total Items Destroyed: " + totalItemsDestroyed + "\n");
            writer.write("-----------------------------------\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}