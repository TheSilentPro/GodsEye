package tsp.godseye.util;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.Plugin;

import java.util.Collections;
import java.util.List;

public class GUtils {


    public final static int METRICS_ID = 7546;
    public final static String PREFIX = "&5&lGodsEye &7| ";
    public final static List<String> DEVELOPERS = Collections.singletonList("Silent");
    public final static String CODENAME = "Godlike";

    public static void sendMessage(CommandSender sender, String message) {
        sender.sendMessage(translate(sender, PREFIX + message));
    }

    public static String translate(CommandSender sender, String message) {
        message = message
                .replace("$sender", sender.getName())
                .replace("$developers", DEVELOPERS.toString())
                .replace("$codename", CODENAME)
                .replace("$version", getInstance().getDescription().getVersion());

        return colorize(message);
    }

    public static String colorize(String message) {
        return ChatColor.translateAlternateColorCodes('&', message);
    }

    public static String replaceBoolean(boolean b) {
        return FileUtils.getString("booleanReplacement." + b);
    }

    public static void log(LogLevel level, String message) {
        Bukkit.getConsoleSender().sendMessage(colorize(PREFIX + getColorByLevel(level) + "[" + level.name() + "] " + message));
    }

    public static void debug(String message) {
        if (FileUtils.getBoolean("debug")) {
            Bukkit.getConsoleSender().sendMessage(colorize(PREFIX + "&9[DEBUG] " + message));
        }
    }

    public static ChatColor getColorByLevel(LogLevel level) {
        switch (level) {
            case INFO:
                return ChatColor.GREEN;
            case WARNING:
                return ChatColor.YELLOW;
            case ERROR:
                return ChatColor.DARK_RED;
            default:
                return ChatColor.WHITE;
        }
    }

    public static boolean isIdBased() {
        if (Bukkit.getBukkitVersion().startsWith("1.9") || Bukkit.getBukkitVersion().startsWith("1.1")) {
            return false;
        }
        return true;
    }

    public static Plugin getInstance() {
        return Bukkit.getPluginManager().getPlugin("GodsEye");
    }

}
