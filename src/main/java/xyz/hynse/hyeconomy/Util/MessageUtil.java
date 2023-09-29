package xyz.hynse.hyeconomy.Util;

import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import xyz.hynse.hyeconomy.Hyeconomy;

import java.io.File;

public class MessageUtil {
    private static MiniMessage mm;
    private static FileConfiguration messagesConfig = null;

    public static void initializeMiniMessage() {
        mm = MiniMessage.builder().build();
    }

    public static FileConfiguration loadMessagesConfig() {
        File configFile = new File(Hyeconomy.instance.getDataFolder(), "messages.yml");
        if (!configFile.exists()) {
            Hyeconomy.instance.saveResource("messages.yml", false);
        }
        return YamlConfiguration.loadConfiguration(configFile);
    }

    public static Object getMessage(String key, String... placeholders) {
        String message = messagesConfig.getString("messages." + key, "");
        if (message.isEmpty()) {
            return "";
        }

        for (int i = 0; i < placeholders.length; i += 2) {
            String placeholder = placeholders[i];
            String replacement = placeholders[i + 1];
            message = message.replace(placeholder, replacement);
        }

        return mm.deserialize(message);
    }

    public static void updateMessagesConfig() {
        messagesConfig = loadMessagesConfig();
    }
}
