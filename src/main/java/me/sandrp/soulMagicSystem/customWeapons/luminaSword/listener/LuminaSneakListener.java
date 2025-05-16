package me.sandrp.soulMagicSystem.customWeapons.luminaSword.listener;

import me.sandrp.soulMagicSystem.Main;
import me.sandrp.soulMagicSystem.Abilities;
import me.sandrp.soulMagicSystem.customWeapons.CooldownManager;
import me.sandrp.soulMagicSystem.customWeapons.luminaSword.LuminaSword;
import me.sandrp.soulMagicSystem.utils.CustomEffects;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.Bukkit;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerToggleSneakEvent;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class LuminaSneakListener implements Listener {

    private final Set<Player> jumpingPlayers = new HashSet<>();
    private final Map<Player, Integer> playerCooldowns = new HashMap<>();
    private final Map<Player, Float> playerJumpPowers = new HashMap<>();
    private final Map<Player, Boolean> playerHasSneaked = new HashMap<>();

    @EventHandler
    public void onSneak(PlayerToggleSneakEvent event) {
        CooldownManager cooldownManager = Main.getCooldownManager();
        Player player = event.getPlayer();

        if (!LuminaSword.isLuminaSword(player.getInventory().getItemInMainHand())) return;
        if (player.isGliding()) return;
        if (cooldownManager.isOnCooldown(player, Abilities.LUMINA_SWORD_JUMP)){
            long remainingTime = cooldownManager.getRemainingTime(player, Abilities.LUMINA_SWORD_JUMP);
            player.sendActionBar(MiniMessage.miniMessage().deserialize("<grey>" + remainingTime + "s" + "</grey>"));
            return;
        }
        if (player.getInventory().getItemInMainHand().getDurability() >= 240) {
            player.getInventory().getItemInMainHand().setAmount(0);
            player.playSound(player.getLocation(), Sound.ENTITY_ITEM_BREAK, 1.0f, 1.0f);
        }

        if (event.isSneaking()) {
            playerHasSneaked.put(player, true);
            playerCooldowns.put(player, 0);
            playerJumpPowers.put(player, 1.1f);
            BukkitRunnable runnable = new BukkitRunnable() {
                @Override
                public void run() {
                    if (!player.isSneaking()) {
                        cancel();
                        return;
                    }

                    if(playerCooldowns.get(player) == 4) {
                        playerJumpPowers.put(player, 1.6f);
                    }
                    if(playerCooldowns.get(player) == 10) {
                        playerJumpPowers.put(player, 1.9f);
                    }

                    playerCooldowns.put(player, playerCooldowns.get(player) + 1);

                    if(playerCooldowns.get(player) <= 4) {
                        CustomEffects.particleCircle(player, 1, 20, Particle.SOUL);
                    }
                    if(playerCooldowns.get(player) >= 4 && playerCooldowns.get(player) <= 10) {
                        CustomEffects.particleCircle(player, 1.4, 20, Particle.SOUL);
                    }
                    if(playerCooldowns.get(player) >= 10) {
                        CustomEffects.particleCircle(player, 1.8, 20, Particle.SOUL);
                    }
                }
            };
            runnable.runTaskTimer(Main.getInstance(), 15L, 10L);
        }

        else if (playerHasSneaked.get(player)) {
            Vector unitVector = new Vector(player.getLocation().getDirection().getX(), 1, player.getLocation().getDirection().getZ());
            unitVector = unitVector.normalize();
            player.setVelocity(unitVector.multiply(playerJumpPowers.get(player)));
            player.playSound(player.getLocation(), Sound.ENTITY_ENDER_DRAGON_FLAP, 0.7f, 0.5f);

            if (player.getInventory().getItemInMainHand().getDurability() >= 240) {
                player.getInventory().getItemInMainHand().setAmount(0);
                player.playSound(player.getLocation(), Sound.ENTITY_ITEM_BREAK, 1.0f, 1.0f);
                return;
            }
            player.getInventory().getItemInMainHand().setDurability((short) (player.getInventory().getItemInMainHand().getDurability() + 10));
            cooldownManager.setCooldown(player, Abilities.LUMINA_SWORD_JUMP);
            jumpingPlayers.add(player);
            playerHasSneaked.put(player, false);

            BukkitRunnable runnable = new BukkitRunnable() {
                @Override
                public void run() {
                    if (player.isOnGround()) {
                        Bukkit.getScheduler().runTaskLater(Main.getInstance(), () -> {
                            jumpingPlayers.remove(player);
                        }, 40L);
                        cancel();
                        return;
                    }
                }
            };
            runnable.runTaskTimer(Main.getInstance(), 0L, 1L);
        }
    }

    @EventHandler
    public void onFallDamage(EntityDamageEvent event) {
        if(!(event.getEntity() instanceof Player player)) {
            return;
        }
        if(!jumpingPlayers.contains(player)) {
            return;
        }
        if (event.getCause() == EntityDamageEvent.DamageCause.FALL) {
            event.setCancelled(true);
        }
    }
}
