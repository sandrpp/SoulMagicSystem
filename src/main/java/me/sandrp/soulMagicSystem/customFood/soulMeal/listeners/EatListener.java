package me.sandrp.soulMagicSystem.customFood.soulMeal.listeners;

import me.sandrp.soulMagicSystem.Main;
import me.sandrp.soulMagicSystem.customFood.soulMeal.SoulMeal;
import me.sandrp.soulMagicSystem.customWeapons.CooldownManager;
import me.sandrp.soulMagicSystem.utils.database.DatabaseManager;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.List;

public class EatListener implements Listener {
    @EventHandler
    public void onEat(PlayerItemConsumeEvent event) {
        int RADIUS = 100;
        CooldownManager cooldownManager = Main.getCooldownManager();
        DatabaseManager databaseManager = Main.getDatabaseManager();
        ItemStack item = event.getItem();
        if(SoulMeal.isSoulMeal(item)) {
            Player player = event.getPlayer();
            cooldownManager.addExeption(player);
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
                targetPlayer.addPotionEffect(new PotionEffect(PotionEffectType.GLOWING, 20*30, 0, false, false, false));
                targetPlayer.addPotionEffect(new PotionEffect(PotionEffectType.NAUSEA, 20*8, 1, false, false, false));
                targetPlayer.addPotionEffect(new PotionEffect(PotionEffectType.POISON, 20*14, 0, false, false, false));
            });

            Bukkit.getScheduler().runTaskLater(Main.getInstance(), () -> {
                cooldownManager.removeExeption(player);
            }, 30*20L);
        }

    }
}
