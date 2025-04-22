package me.sandrp.soulMagicSystem.utils;

import me.sandrp.soulMagicSystem.Main;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class SoundTask extends BukkitRunnable {
    private Sound sound;
    private int duration; // Duration in ticks (1 tick = 0.05 seconds)
    private float volume;
    private float pitch;
    private Player player;

    public SoundTask(Player player, Sound sound, int duration, float volume, float pitch) {
        this.sound = sound;
        this.duration = duration;
        this.volume = volume;
        this.pitch = pitch;
        this.player = player;
    }

    @Override
    public void run() {
        if (duration <= 0) {
            cancel();
            return;
        }
        duration--;
        player.playSound(player, sound, volume, pitch);
    }
}