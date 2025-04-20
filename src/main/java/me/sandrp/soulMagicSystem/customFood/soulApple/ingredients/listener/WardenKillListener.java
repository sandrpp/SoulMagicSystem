package me.sandrp.soulMagicSystem.customFood.soulApple.ingredients.listener;

import me.sandrp.soulMagicSystem.customFood.soulApple.ingredients.SoulBlood;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Warden;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;

public class WardenKillListener implements Listener {
    @EventHandler
    public void onKill(EntityDeathEvent event) {
        if(!(event.getEntity() instanceof Warden)) return;
        Location location = event.getEntity().getLocation();
        if(Math.random() < 0.5) {
            event.getDrops().add(SoulBlood.createItem());
        }
    }
}
