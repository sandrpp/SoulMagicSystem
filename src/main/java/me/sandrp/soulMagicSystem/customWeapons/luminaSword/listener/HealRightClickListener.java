package me.sandrp.soulMagicSystem.customWeapons.luminaSword.listener;

import me.sandrp.soulMagicSystem.Main;
import me.sandrp.soulMagicSystem.Abilities;
import me.sandrp.soulMagicSystem.customWeapons.CooldownManager;
import me.sandrp.soulMagicSystem.customWeapons.luminaSword.LuminaSword;
import me.sandrp.soulMagicSystem.utils.CustomEffects;
import me.sandrp.soulMagicSystem.utils.database.DatabaseManager;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.List;

public class HealRightClickListener implements Listener {
    final int RADIUS = 10;
    @EventHandler
    public void onRightClick(PlayerInteractEvent event) {

        if(event.getHand() == EquipmentSlot.OFF_HAND){
            return;
        }
        CooldownManager cooldownManager = Main.getCooldownManager();
        DatabaseManager databaseManager = Main.getDatabaseManager();
        Player player = event.getPlayer();

        // Check if the player is right clicking a Soul Sword
        if (!LuminaSword.isLuminaSword(player.getInventory().getItemInMainHand())) return;
        if (!event.getAction().toString().contains("RIGHT")) return;
        if (cooldownManager.isOnCooldown(player, Abilities.LUMINA_SWORD_HEALING)) {
            long remainingTime = cooldownManager.getRemainingTime(player, Abilities.LUMINA_SWORD_HEALING);
            player.sendActionBar(MiniMessage.miniMessage().deserialize("<grey>" + remainingTime + "s" + "</grey>"));
            return;
        }
        if (player.getInventory().getItemInMainHand().getDurability() >= 225) {
            player.getInventory().getItemInMainHand().setAmount(0);
            player.playSound(player.getLocation(), Sound.ENTITY_ITEM_BREAK, 1.0f, 1.0f);
        }

        player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 500*2, 0, false, false, false));
        player.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 100*3, 1, false, false, false));
        cooldownManager.setCooldown(player, Abilities.LUMINA_SWORD_HEALING);

        // Check if the player is in a team
        if (databaseManager.getTeamID(player.getUniqueId().toString()) == null) {
            player.swingMainHand();
            return;
        }

        // heal nearby players in the same team
        List<Player> nearbyPlayers = player.getNearbyEntities(RADIUS, RADIUS, RADIUS)
                .stream()
                .filter(entity -> entity instanceof Player)
                .map(entity -> (Player) entity)
                .toList();

        // Check if the player is in a team
        List<Player> targetPlayers = databaseManager.getTeamPlayerList(databaseManager.getTeamID(player.getUniqueId().toString()))
                .stream()
                .filter(OfflinePlayer::isOnline)
                .map(offlinePlayer -> (Player) offlinePlayer)
                .filter(nearbyPlayers::contains)
                .toList();
        targetPlayers.forEach( targetPlayer -> {
            targetPlayer.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 500*2, 0, false, false, false));
            targetPlayer.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 100*3, 1, false, false, false));
        });

        // summon particles
        player.getInventory().getItemInMainHand().setDurability((short) (player.getInventory().getItemInMainHand().getDurability() + 25));
        player.swingMainHand();
        Location location = player.getLocation();
        location.setY(location.getY() + 1);
        player.getWorld().spawnParticle(Particle.SWEEP_ATTACK, location.add(location.getDirection().multiply(1.1)), 1);
        CustomEffects.particleCircle(player, RADIUS, 100, Particle.SOUL);
    }
}
