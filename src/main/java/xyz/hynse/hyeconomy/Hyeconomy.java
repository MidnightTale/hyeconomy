package xyz.hynse.hyeconomy;

import org.bukkit.plugin.java.JavaPlugin;
import xyz.hynse.hyeconomy.Util.CommandUtil;
import xyz.hynse.hyeconomy.Util.HikariCPUtil;
import xyz.hynse.hyeconomy.Util.MessageUtil;

import java.util.Objects;

public final class Hyeconomy extends JavaPlugin {
    public final boolean debugMode = true;
    public static Hyeconomy instance;
    public MessageUtil messageUtil;

    @Override
    public void onEnable() {
        instance = this;
        messageUtil = new MessageUtil(this);
        saveDefaultConfig();
        reloadConfig();

        HikariCPUtil.initializeDataSource(getConfig());
        HikariCPUtil.downloadMariaDBDriver();
        HikariCPUtil.createTablesIfNotExists();
        getLogger().info("Hyeconomy has been enabled.");
        Objects.requireNonNull(getCommand("balance")).setExecutor(new CommandUtil());
        Objects.requireNonNull(getCommand("deposit")).setExecutor(new CommandUtil());
        Objects.requireNonNull(getCommand("send")).setExecutor(new CommandUtil());
        Objects.requireNonNull(getCommand("top")).setExecutor(new CommandUtil());
        Objects.requireNonNull(getCommand("withdraw")).setExecutor(new CommandUtil());
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
