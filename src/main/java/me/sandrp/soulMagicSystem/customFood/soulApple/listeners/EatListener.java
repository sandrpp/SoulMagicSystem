package me.sandrp.soulMagicSystem.customFood.soulApple.listeners;

import me.sandrp.soulMagicSystem.Main;
import me.sandrp.soulMagicSystem.customFood.soulApple.SoulApple;
import me.sandrp.soulMagicSystem.utils.CooldownManager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.inventory.ItemStack;

public class EatListener implements Listener {
    @EventHandler
    public void onEat(PlayerItemConsumeEvent event) {
        CooldownManager cooldownManager = Main.getCooldownManager();
        ItemStack item = event.getItem();
        if(SoulApple.isSoulApple(item)) {
            event.getPlayer().sendMessage("test");
            cooldownManager.addExeption(event.getPlayer());
        }
    }
}
