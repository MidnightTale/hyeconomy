package xyz.hynse.hyeconomy.Util;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import xyz.hynse.hyeconomy.Command.*;

import java.util.ArrayList;
import java.util.List;

public class CommandUtil implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String label, String[] args) {
        if (!(sender instanceof Player player)) {
            sender.sendMessage((Component) MessageUtil.getMessage("general.playerOnly"));
            return true;
        }

        switch (cmd.getName().toLowerCase()) {
            case "balance" -> BalanceCommand.execute(player);
            case "deposit" -> DepositCommand.execute(player, args);
            case "send" -> SendCommand.execute(player, args);
            case "top" -> {
                // TODO: Implement leaderboard
            }
            case "withdraw" -> WithdrawCommand.execute(player, args);
            case "hyeconomyreload" -> ReloadCommand.execute(player);
            default -> sender.sendMessage((Component) MessageUtil.getMessage("general.unknownCommand", "%command%", cmd.getName()));
        }
        return true;
    }
}
