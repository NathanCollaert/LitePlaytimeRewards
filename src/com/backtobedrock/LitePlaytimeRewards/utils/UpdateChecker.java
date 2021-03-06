package com.backtobedrock.LitePlaytimeRewards.utils;

import com.backtobedrock.LitePlaytimeRewards.LitePlaytimeRewards;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Scanner;
import java.util.function.Consumer;
import java.util.logging.Level;

public class UpdateChecker {

    private final LitePlaytimeRewards plugin;
    private final int resourceId;

    public UpdateChecker(int resourceId) {
        this.plugin = JavaPlugin.getPlugin(LitePlaytimeRewards.class);
        this.resourceId = resourceId;
    }

    public void getVersion(final Consumer<String> consumer) {
        Bukkit.getScheduler().runTaskAsynchronously(this.plugin, () -> {
            try (InputStream inputStream = new URL("https://api.spigotmc.org/legacy/update.php?resource=" + this.resourceId).openStream(); Scanner scanner = new Scanner(inputStream)) {
                if (scanner.hasNext()) {
                    consumer.accept(scanner.next());
                }
            } catch (IOException exception) {
                Bukkit.getLogger().log(Level.INFO, "Cannot look for updates: {0}", exception.getMessage());
            }
        });
    }
}
