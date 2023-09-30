package xyz.hynse.hyeconomy.Util;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class CommandTabCompleterUtil {

    public static @Nullable List<String> completeOnlinePlayerNames(
            @NotNull CommandSender sender, @NotNull String[] args) {
        if (args.length == 1 && sender instanceof Player) {
            String partialName = args[0].toLowerCase();
            List<String> completions = new ArrayList<>();

            for (Player onlinePlayer : sender.getServer().getOnlinePlayers()) {
                String playerName = onlinePlayer.getName();
                if (playerName.toLowerCase().startsWith(partialName)) {
                    completions.add(playerName);
                }
            }

            return completions;
        }
        return null;
    }
}
