package me.sandrp.soulMagicSystem.customWeapons;

import me.sandrp.soulMagicSystem.Abilities;
import org.bukkit.entity.Player;

import java.util.*;

public class CooldownManager {
    private final Map<Map.Entry<UUID, Abilities>, Long> cooldowns = new HashMap<>();
    List<Player> exeptions = new ArrayList<>();
    private final long cooldownTime; // in milliseconds

    public CooldownManager(long cooldownTime) {
        this.cooldownTime = cooldownTime;
    }

    public boolean isOnCooldown(Player player, Abilities weapon) {
        Map.Entry<UUID, Abilities> key = Map.entry(player.getUniqueId(), weapon);
        return cooldowns.containsKey(key) && System.currentTimeMillis() < cooldowns.get(key);
    }

    public void setCooldown(Player player, Abilities weapon) {
        if(exeptions.contains(player)){
            cooldowns.put(Map.entry(player.getUniqueId(), weapon), System.currentTimeMillis() + cooldownTime / 4);
            return;
        }
        cooldowns.put(Map.entry(player.getUniqueId(), weapon), System.currentTimeMillis() + cooldownTime);
    }

    public long getRemainingTime(Player player, Abilities weapon) {
        return (cooldowns.get(Map.entry(player.getUniqueId(), weapon)) - System.currentTimeMillis()) / 1000;
    }

    public void addExeption(Player player){
        exeptions.add(player);
    }

    public void removeExeption(Player player){
        exeptions.remove(player);
    }
}
