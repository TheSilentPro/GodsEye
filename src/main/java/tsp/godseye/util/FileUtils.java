package tsp.godseye.util;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;

public class FileUtils {

    private static File configFile = new File(GUtils.getInstance().getDataFolder(), "config.yml");
    public static FileConfiguration config = YamlConfiguration.loadConfiguration(configFile);

    public static String getString(String identifier) {
        return config.getString(identifier);
    }

    public static boolean getBoolean(String identifier) {
        return config.getBoolean(identifier);
    }

}
