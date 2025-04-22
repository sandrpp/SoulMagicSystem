package me.sandrp.soulMagicSystem.customWeapons.gravityWand.listener;

import me.sandrp.soulMagicSystem.Main;
import me.sandrp.soulMagicSystem.customWeapons.gravityWand.GravityWand;
import org.bukkit.Sound;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

import java.util.TreeMap;

public class GravityWandListener implements Listener {
    @EventHandler
    public void onUse(PlayerInteractEvent event) {
        if(event.getHand() == EquipmentSlot.OFF_HAND){
            return;
        }
        if (event.getItem() == null) return;
        if (!GravityWand.isGravityWand(event.getItem())) return;

        Player player = event.getPlayer();

        if (event.getAction().toString().contains("RIGHT_CLICK")) {
            if (Main.getGrabbedEntities().containsKey(player)){
                Main.getGrabbedEntities().remove(player);
                return;
            }
            Entity target = player.getTargetEntity(25);
            if (target == null) return;
            if (target.equals(player)) return;
            if (Main.getGrabbedEntities().containsKey(target) && Main.getGrabbedEntities().get(target).equals(player)) return;

            Main.getGrabbedEntities().put(player, target);
            player.playSound(player.getLocation(), Sound.ENTITY_ENDERMAN_TELEPORT, 1, 2);
            event.setCancelled(true);

        } else if (event.getAction().toString().contains("LEFT_CLICK")) {
            if (!Main.getGrabbedEntities().containsKey(player)) return;
            
            Entity entity = Main.getGrabbedEntities().remove(player);
            Vector direction = player.getLocation().getDirection().multiply(3);
            entity.setVelocity(direction);
            player.playSound(player.getLocation(), Sound.ENTITY_ENDER_DRAGON_FLAP, 1, 1);
            ItemStack item = player.getInventory().getItemInMainHand();
            if (GravityWand.isGravityWand(item)) {
                item.setDurability((short) (item.getDurability() + 10));
                if (item.getDurability() >= 240) {
                    player.getInventory().setItemInMainHand(null);
                    player.playSound(player.getLocation(), Sound.ENTITY_ITEM_BREAK, 1.0f, 1.0f);
                }
            }
            event.setCancelled(true);
        }

    }

    @EventHandler
    public void onDeath(PlayerDeathEvent event) {
        Player player = event.getEntity();
        if (Main.getGrabbedEntities().containsKey(player)) {
            Main.getGrabbedEntities().remove(player, Main.getGrabbedEntities().get(player));
        }
        else if (Main.getGrabbedEntities().containsValue(player)) {
            Main.getGrabbedEntities().remove(player);
        }
    }
}
