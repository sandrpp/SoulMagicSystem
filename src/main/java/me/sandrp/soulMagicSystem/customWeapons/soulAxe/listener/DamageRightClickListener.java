package me.sandrp.soulMagicSystem.customWeapons.soulAxe.listener;

import me.sandrp.soulMagicSystem.Main;
import me.sandrp.soulMagicSystem.Weapon;
import me.sandrp.soulMagicSystem.customWeapons.soulAxe.SoulAxe;
import me.sandrp.soulMagicSystem.utils.CooldownManager;
import me.sandrp.soulMagicSystem.utils.CustomEffects;
import me.sandrp.soulMagicSystem.utils.DatabaseManager;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.List;

public class DamageRightClickListener implements Listener {
    final int RADIUS = 10;
    @EventHandler
    public void onRightClick(PlayerInteractEvent event) {
        CooldownManager cooldownManager = Main.getCooldownManager();
        DatabaseManager databaseManager = Main.getDatabaseManager();
        Player player = event.getPlayer();

        if (!SoulAxe.isSoulAxe(player.getInventory().getItemInMainHand())) return;
        if (!event.getAction().toString().contains("RIGHT")) return;
        if (cooldownManager.isOnCooldown(player, Weapon.SOUL_SWORD_SOUL)) {
            long remainingTime = cooldownManager.getRemainingTime(player, Weapon.SOUL_SWORD_SOUL);
            player.sendActionBar(MiniMessage.miniMessage().deserialize("<grey>" + remainingTime + "s" + "</grey>"));
            return;
        }
        if (player.getInventory().getItemInMainHand().getDurability() >= 28) {
            player.getInventory().getItemInMainHand().setAmount(0);
            player.playSound(player.getLocation(), Sound.ENTITY_ITEM_BREAK, 1.0f, 1.0f);
            return;
        }

        //apply cooldown
        cooldownManager.setCooldown(player, Weapon.SOUL_SWORD_SOUL);

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

        // apply effects to nearby non team players
        nearbyPlayers.forEach(targetPlayer -> {
            targetPlayer.addPotionEffect(new PotionEffect(PotionEffectType.LEVITATION, 300, 0, false, false, false));
        });

        //remove durability
        player.getInventory().getItemInMainHand().setDurability((short) (player.getInventory().getItemInMainHand().getDurability() + 4));

        //animation
        player.swingMainHand();
        Location location = player.getLocation();
        location.setY(location.getY() + 1);
        player.getWorld().spawnParticle(Particle.SWEEP_ATTACK, location.add(location.getDirection().multiply(1.1)), 1);
        CustomEffects.particleCircle(player, RADIUS, 100, Particle.SOUL);
    }
}
