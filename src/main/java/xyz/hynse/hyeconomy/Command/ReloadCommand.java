package xyz.hynse.hyeconomy.Command;

import net.kyori.adventure.text.Component;
import org.bukkit.entity.Player;
import xyz.hynse.hyeconomy.Hyeconomy;
import xyz.hynse.hyeconomy.Util.HikariCPUtil;
import xyz.hynse.hyeconomy.Util.MessageUtil;
public class ReloadCommand {
    public static void execute(Player player) {
       if (player.hasPermission("hyecomoney.reload")) {
           if (HikariCPUtil.dataSource != null) {
               HikariCPUtil.dataSource.close();
           }
            Hyeconomy.instance.reloadConfig();
            MessageUtil.loadMessagesConfig();
            HikariCPUtil.initializeDataSource(Hyeconomy.instance.getConfig());
            player.sendMessage((Component) MessageUtil.getMessage("general.reloadSuccess"));
        } else {
            player.sendMessage((Component) MessageUtil.getMessage("general.noPermission"));
        }
    }
}