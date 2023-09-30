package xyz.hynse.hyeconomy;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import xyz.hynse.hyeconomy.API.HyeconomyAPI;
import xyz.hynse.hyeconomy.API.Placeholder;
import xyz.hynse.hyeconomy.Process.SendCommandTabCompleter;
import xyz.hynse.hyeconomy.Util.CommandUtil;
import xyz.hynse.hyeconomy.Util.HikariCPUtil;
import xyz.hynse.hyeconomy.Util.MessageUtil;

import java.util.Objects;

public final class Hyeconomy extends JavaPlugin implements HyeconomyAPI {
    public boolean debugMode;
    public static Hyeconomy instance;

    @Override
    public void onEnable() {
        instance = this;
        debugMode = getConfig().getBoolean("debugMode");
        saveDefaultConfig();
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

        Objects.requireNonNull(getCommand("send")).setTabCompleter(new SendCommandTabCompleter());
        if (Bukkit.getPluginManager().getPlugin("PlaceholderAPI") != null) {
            new Placeholder().register();
            getLogger().info("PlaceholderAPI Hooked!");
        } else {
            getLogger().warning("PlaceholderAPI not found! Some features may not work.");
            getLogger().warning("Consider installing PlaceholderAPI as an optional feature.");
        }

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