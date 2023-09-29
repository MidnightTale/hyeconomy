package xyz.hynse.hyeconomy.Util;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import xyz.hynse.hyeconomy.Command.BalanceCommand;
import xyz.hynse.hyeconomy.Command.DepositCommand;
import xyz.hynse.hyeconomy.Command.SendCommand;
import xyz.hynse.hyeconomy.Command.WithdrawCommand;

public class CommandUtil implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String label, String[] args) {
        if (!(sender instanceof Player player)) {
            sender.sendMessage((Component) MessageUtil.getMessage("playerOnly"));
            return true;
        }

        MiniMessage mm = MiniMessage.builder().build();

        switch (cmd.getName().toLowerCase()) {
            case "balance" -> BalanceCommand.execute(player, mm);
            case "deposit" -> DepositCommand.execute(player, args, mm);
            case "send" -> SendCommand.execute(player, args, mm);
            case "top" -> {
                // Implement top command logic here
            }
            case "withdraw" -> WithdrawCommand.execute(player, args, mm);
            default -> sender.sendMessage((Component) MessageUtil.getMessage("unknownCommand", "%command%", cmd.getName()));
        }
        return true;
    }
}
