package xyz.hynse.hyeconomy.Command;

import net.kyori.adventure.text.Component;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import xyz.hynse.hyeconomy.Hyeconomy;
import xyz.hynse.hyeconomy.Process.Scheduler;
import xyz.hynse.hyeconomy.Util.HikariCPUtil;
import xyz.hynse.hyeconomy.Util.MessageUtil;

import static org.bukkit.Bukkit.getServer;

public class ReloadCommand {
    public static void execute(CommandSender sender) {
            if (!(sender instanceof Player) || sender.hasPermission("hyeconomy.reload")) {
                if (HikariCPUtil.dataSource != null) {
                    HikariCPUtil.dataSource.close();
                }
                Hyeconomy.instance.reloadConfig();
                MessageUtil.updateMessagesConfig();
                getServer().getLogger().warning("Hyeconomy database connection closed.");
                HikariCPUtil.initializeDataSource(Hyeconomy.instance.getConfig());
                getServer().getLogger().warning("Hyeconomy reconnect connection database.");
                sender.sendMessage((Component) MessageUtil.getMessage("general.reloadSuccess"));
            } else {
                sender.sendMessage((Component) MessageUtil.getMessage("general.noPermission"));
            }
    }
}
