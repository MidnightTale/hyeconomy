package xyz.hynse.hyeconomy.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import xyz.hynse.hyeconomy.PlayerRequest;

import java.util.UUID;

import static xyz.hynse.hyeconomy.command.list.deposit.handleDepositCommand;
import static xyz.hynse.hyeconomy.command.list.send.handleSendCommand;
import static xyz.hynse.hyeconomy.command.list.withdrew.handleWithdrawCommand;

public class handle {
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("Only players can use this command.");
            return true;
        }

        Player player = (Player) sender;
        UUID playerUUID = player.getUniqueId();

        switch (cmd.getName().toLowerCase()) {
            case "balance":
                int balance = PlayerRequest.getPlayerBalance(playerUUID);
                player.sendMessage("Your balance: " + balance + " diamonds.");
                break;
            case "deposit":
                handleDepositCommand(player, args);
                break;
            case "send":
                handleSendCommand(player, args);
                break;
            case "top":
                // Implement top command logic here
                break;
            case "withdraw":
                handleWithdrawCommand(player, args);
                break;
            default:
                player.sendMessage("Unknown command: " + cmd.getName());
        }
        return true;
    }
}
