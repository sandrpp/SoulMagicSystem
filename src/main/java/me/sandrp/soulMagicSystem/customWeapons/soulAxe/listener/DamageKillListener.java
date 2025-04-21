package me.sandrp.soulMagicSystem.customWeapons.soulAxe.listener;

import me.sandrp.soulMagicSystem.customWeapons.soulAxe.SoulAxe;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

public class DamageKillListener implements Listener {
    @EventHandler
    public void onKill(PlayerDeathEvent event) {
        if (event.getEntity().getKiller() == null) return;
        if (!(event.getEntity().getKiller() instanceof Player killer)) return;
        if (!SoulAxe.isSoulAxe(killer.getInventory().getItemInMainHand())) return;

        killer.setHealth(Math.min(killer.getHealth() + 5.0, killer.getMaxHealth()));
    }
}
