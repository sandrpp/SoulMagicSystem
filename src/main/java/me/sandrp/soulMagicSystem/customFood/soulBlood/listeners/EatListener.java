package me.sandrp.soulMagicSystem.customFood.soulBlood.listeners;

import me.sandrp.soulMagicSystem.Main;
import me.sandrp.soulMagicSystem.customFood.soulBlood.SoulBlood;
import me.sandrp.soulMagicSystem.customWeapons.CooldownManager;
import me.sandrp.soulMagicSystem.utils.CustomEffects;
import me.sandrp.soulMagicSystem.utils.SoundTask;
import me.sandrp.soulMagicSystem.utils.database.DatabaseManager;
import org.bukkit.*;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitScheduler;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class EatListener implements Listener {

    int hearts;
    @EventHandler
    public void onEat(PlayerItemConsumeEvent event) {
        hearts = 0;
        ItemStack item = event.getItem();
        if(!SoulBlood.isSoulBlood(item)) return;

        int RADIUS = 100;
        CooldownManager cooldownManager = Main.getCooldownManager();
        DatabaseManager databaseManager = Main.getDatabaseManager();

        Player player = event.getPlayer();
        player.playSound(player.getLocation(), Sound.BLOCK_BEACON_ACTIVATE, 1.5f, 0.5f);
        //CustomEffects.spawnWildParticleRays(player, 10, 4);
        cooldownManager.addExeption(player);
        new SoundTask(player, Sound.BLOCK_BEACON_AMBIENT, 30, 1.5f,0.5f).runTaskTimer(Main.getInstance(), 0L, 100L);

        List<Player> nearbyPlayers;
        if (databaseManager.getTeamID(player.getUniqueId().toString()) != null) {
            List<Player> teamPlayers = databaseManager.getTeamPlayerList(databaseManager.getTeamID(player.getUniqueId().toString()))
                    .stream()
                    .filter(OfflinePlayer::isOnline)
                    .map(offlinePlayer -> (Player) offlinePlayer)
                    .toList();
            nearbyPlayers = player.getNearbyEntities(RADIUS, RADIUS, RADIUS)
                    .stream()
                    .filter(entity -> entity instanceof Player)
                    .map(entity -> (Player) entity)
                    .filter(nearbyPlayer -> !teamPlayers.contains(nearbyPlayer))
                    .toList();
        }
        else {
            nearbyPlayers = player.getNearbyEntities(RADIUS, RADIUS, RADIUS)
                    .stream()
                    .filter(entity -> entity instanceof Player)
                    .map(entity -> (Player) entity)
                    .toList();
        }
        nearbyPlayers.forEach(targetPlayer -> {

            ShulkerBullet shulkerBullet = (ShulkerBullet) player.getWorld().spawnEntity(player.getLocation().add(0, 1, 0), EntityType.SHULKER_BULLET);
            shulkerBullet.setTarget(targetPlayer);
            targetPlayer.addPotionEffect(new PotionEffect(PotionEffectType.GLOWING, 20*30, 0, false, false, false));
            targetPlayer.addPotionEffect(new PotionEffect(PotionEffectType.NAUSEA, 20*8, 1, false, false, false));
            targetPlayer.addPotionEffect(new PotionEffect(PotionEffectType.POISON, 20*14, 0, false, false, false));
            hearts++;
        });

        if(hearts == 1) player.addPotionEffect(new PotionEffect(PotionEffectType.ABSORPTION, 20*30, 0, false, true, true));
        if(hearts == 2) player.addPotionEffect(new PotionEffect(PotionEffectType.ABSORPTION, 20*30, 1, false, true, true));
        if(hearts >= 2) player.addPotionEffect(new PotionEffect(PotionEffectType.ABSORPTION, 20*30, 2, false, true, true));

        player.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 20*30, 1, false, true, true));
        player.addPotionEffect(new PotionEffect(PotionEffectType.RESISTANCE, 20*30, 0, false, true, true));

        Bukkit.getScheduler().runTaskLater(Main.getInstance(), () -> {
            cooldownManager.removeExeption(player);
        }, 30*20L);
    }
}
