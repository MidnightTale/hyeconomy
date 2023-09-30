package xyz.hynse.hyeconomy.Process;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import xyz.hynse.hyeconomy.Hyeconomy;

import java.util.ArrayList;
import java.util.List;

public class SendCommandTabCompleter implements TabCompleter {

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String alias, String[] args) {
        if (cmd.getName().equalsIgnoreCase("send") && args.length == 1) {
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
