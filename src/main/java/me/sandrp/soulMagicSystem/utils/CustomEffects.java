package me.sandrp.soulMagicSystem.utils;

import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.World;
import org.bukkit.entity.Player;

public class CustomEffects {
    public static void particleCircle(Player player, double radius, int particles, Particle particle) {
        Location center = player.getLocation();
        World world = center.getWorld();

        for (int i = 0; i < particles; i++) {
            // Berechne die Position jedes Partikels im Kreis
            double angle = 2 * Math.PI * i / particles;
            double x = radius * Math.cos(angle);
            double z = radius * Math.sin(angle);

            Location particleLoc = center.clone().add(x, 0, z);
            world.spawnParticle(particle, particleLoc, 1, 0, 0, 0, 0);
        }
    }
}
