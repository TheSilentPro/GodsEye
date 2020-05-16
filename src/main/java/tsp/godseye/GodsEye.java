package tsp.godseye;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import tsp.godseye.command.Command_godseye;
import tsp.godseye.gui.PagedPaneListener;
import tsp.godseye.util.GUtils;
import tsp.godseye.util.LogLevel;
import tsp.godseye.util.Metrics;

public class GodsEye extends JavaPlugin {

    @Override
    public void onEnable() {
        GUtils.log(LogLevel.INFO, "Loading GodsEye -> Version: " + GUtils.CODENAME + " - " + getDescription().getVersion());
        saveDefaultConfig();

        GUtils.debug("Starting metrics...");
        Metrics metrics = new Metrics(this, GUtils.METRICS_ID);

        GUtils.debug("Registering Commands & Listeners...");
        registerCommands();
        registerListeners();

        GUtils.log(LogLevel.INFO, "Done!");
    }

    @Override
    public void onDisable() {
        GUtils.log(LogLevel.INFO, "GodsEye has been disabled!");
    }

    private void registerCommands() {
        getCommand("godseye").setExecutor(new Command_godseye());
    }

    private void registerListeners() {
        Bukkit.getPluginManager().registerEvents(new PagedPaneListener(), this);
    }

}
