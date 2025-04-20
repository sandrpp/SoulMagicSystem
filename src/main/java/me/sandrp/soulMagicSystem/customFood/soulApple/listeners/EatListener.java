package me.sandrp.soulMagicSystem.customFood.soulApple.listeners;

import me.sandrp.soulMagicSystem.Main;
import me.sandrp.soulMagicSystem.customFood.soulApple.SoulApple;
import me.sandrp.soulMagicSystem.utils.CooldownManager;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

public class EatListener implements Listener {
    @EventHandler
    public void onEat(PlayerItemConsumeEvent event) {
        CooldownManager cooldownManager = Main.getCooldownManager();
        ItemStack item = event.getItem();
        if(SoulApple.isSoulApple(item)) {
            Player player = event.getPlayer();
            cooldownManager.addExeption(player);
            BukkitRunnable runnable = new BukkitRunnable() {
                @Override
                public void run() {
                    cooldownManager.removeExeption(player);
                }
            };
            runnable.runTaskTimer(Main.getInstance(), 20*60*5L, 0L);
        }
    }
}
