package xyz.hynse.hyeconomy.Util;

import net.kyori.adventure.text.Component;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import xyz.hynse.hyeconomy.Command.Admin.GiveCommand;
import xyz.hynse.hyeconomy.Command.Admin.ResetCommand;
import xyz.hynse.hyeconomy.Command.Admin.SetCommand;
import xyz.hynse.hyeconomy.Command.Admin.TakeCommand;
import xyz.hynse.hyeconomy.Command.*;

public class CommandUtil implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String label, String[] args) {
        if (cmd.getName().equalsIgnoreCase("hyeconomyreload")) {
            ReloadCommand.execute(sender);
            return true;
        }
        if (sender instanceof Player player) {
            switch (cmd.getName().toLowerCase()) {
                case "balance" -> BalanceCommand.execute(sender, args);
                case "deposit" -> DepositCommand.execute(player, args);
                case "send" -> SendCommand.execute(player, args);
                case "top" -> TopCommand.execute(player);
                case "withdraw" -> WithdrawCommand.execute(player, args);
                case "set" -> SetCommand.execute(player, args);
                case "take" -> TakeCommand.execute(player, args);
                case "reset" -> ResetCommand.execute(player, args);
                case "give" -> GiveCommand.execute(player, args);
            default -> sender.sendMessage((Component) MessageUtil.getMessage("general.unknownCommand", "%command%", cmd.getName()));
            }
        } else {
            switch (cmd.getName().toLowerCase()) {
                case "set" -> SetCommand.execute(sender, args);
                case "take" -> TakeCommand.execute(sender, args);
                case "reset" -> ResetCommand.execute(sender, args);
                case "give" -> GiveCommand.execute(sender, args);
            default -> sender.sendMessage((Component) MessageUtil.getMessage("general.unknownCommand", "%command%", cmd.getName()));
            }
        }
        return true;
    }
}
