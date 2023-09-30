package xyz.hynse.hyeconomy.Process;

import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;
public class Scheduler {

    private static Boolean IS_FOLIA = null;

    private static boolean tryFolia() {
        try {
            Bukkit.getAsyncScheduler();
            return true;
        } catch (Throwable ignored) {
        }
        return false;
    }

    public static Boolean isFolia() {
        if (IS_FOLIA == null) IS_FOLIA = tryFolia();
        return IS_FOLIA;
    }
    public static void runAsyncSchedulerNow(Plugin plugin, Consumer<Player> playerTask) {
        if (isFolia()) {
            Bukkit.getAsyncScheduler().runNow(plugin, task -> {
                for (Player player : Bukkit.getOnlinePlayers()) {
                    playerTask.accept(player);
                }
            });
        } else {
            Bukkit.getScheduler().runTaskAsynchronously(plugin, () -> {
                for (Player player : Bukkit.getOnlinePlayers()) {
                    playerTask.accept(player);
                }
            });
        }
    }
    public static void runAsyncSchedulerDelay(Plugin plugin, Consumer<Player> playerTask, int initialDelaySeconds) {
        if (isFolia()) {
            Bukkit.getAsyncScheduler().runDelayed(plugin, task -> {
                for (Player player : Bukkit.getOnlinePlayers()) {
                    playerTask.accept(player);
                }
            }, initialDelaySeconds, TimeUnit.SECONDS);
        } else {
            Bukkit.getScheduler().runTaskLaterAsynchronously(plugin, () -> {
                for (Player player : Bukkit.getOnlinePlayers()) {
                    playerTask.accept(player);
                }
            }, initialDelaySeconds * 20L);
        }
    }

    public static void runTaskForEntity(Entity entity, Plugin plugin, Runnable entityTask, long initialDelaySeconds) {
        if (isFolia()) {
            entity.getScheduler().runDelayed(plugin, task -> entityTask.run(), null, initialDelaySeconds);
        } else {
            Bukkit.getScheduler().runTaskLater(plugin, entityTask, initialDelaySeconds);
        }
    }
}