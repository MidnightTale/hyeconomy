package xyz.hynse.hyeconomy;

import org.bukkit.plugin.java.JavaPlugin;
import xyz.hynse.hyeconomy.Util.CommandUtil;
import xyz.hynse.hyeconomy.Util.HikariCPUtil;
import xyz.hynse.hyeconomy.Util.MessageUtil;

import java.util.Objects;

public final class Hyeconomy extends JavaPlugin {
    public boolean debugMode;
    public static Hyeconomy instance;
    @Override
    public void onEnable() {
        instance = this;
        debugMode = getConfig().getBoolean("debugMode");
        saveDefaultConfig();
        reloadConfig();

        // Initialize MiniMessage object here
        MessageUtil.initializeMiniMessage();

        MessageUtil.updateMessagesConfig();

        HikariCPUtil.downloadMariaDBDriver();
        HikariCPUtil.initializeDataSource(getConfig());
        HikariCPUtil.createTablesIfNotExists();
        getLogger().info("Hyeconomy has been enabled.");
        Objects.requireNonNull(getCommand("balance")).setExecutor(new CommandUtil());
        Objects.requireNonNull(getCommand("deposit")).setExecutor(new CommandUtil());
        Objects.requireNonNull(getCommand("send")).setExecutor(new CommandUtil());
        Objects.requireNonNull(getCommand("top")).setExecutor(new CommandUtil());
        Objects.requireNonNull(getCommand("withdraw")).setExecutor(new CommandUtil());
        Objects.requireNonNull(getCommand("hyeconomyreload")).setExecutor(new CommandUtil());
    }

    @Override
    public void onDisable() {
        if (HikariCPUtil.dataSource != null) {
            HikariCPUtil.dataSource.close();
            getLogger().info("Hyeconomy database connection closed.");
        }
        getLogger().info("Hyeconomy has been disabled.");
    }
}
