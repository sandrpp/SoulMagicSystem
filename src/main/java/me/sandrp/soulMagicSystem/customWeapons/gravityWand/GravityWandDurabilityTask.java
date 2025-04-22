package me.sandrp.soulMagicSystem.customWeapons.gravityWand;

import me.sandrp.soulMagicSystem.Main;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import java.util.Iterator;
import java.util.Map;

public class GravityWandDurabilityTask extends BukkitRunnable {

    @Override
    public void run() {
        Main.getGrabbedEntities().entrySet().forEach(entry -> {
            Player player = entry.getKey();
            ItemStack item = player.getInventory().getItemInMainHand();
            item.setDurability((short) (item.getDurability() + 1));
            if (item.getDurability() >= 249) {
                player.getInventory().setItemInMainHand(null);
                player.playSound(player.getLocation(), Sound.ENTITY_ITEM_BREAK, 1.0f, 1.0f);
            }
        });
    }
}