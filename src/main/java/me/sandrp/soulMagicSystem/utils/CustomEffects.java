package me.sandrp.soulMagicSystem.utils;

import me.sandrp.soulMagicSystem.Main;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import java.util.Random;

public class CustomEffects {

    private static final Random random = new Random();

    public static void spawnWildParticleRays(Player player, int rays, int length) {
        Location center = player.getLocation().add(0, 1, 0);
        World world = player.getWorld();

        for (int i = 0; i < rays; i++) {
            // Zufällige Richtung generieren
            Vector direction = getRandomDirection();

            // Partikel-Strahl spawnen
            spawnParticleRay(world, center, direction, length);
        }
    }

    // Hilfsmethode: Zufällige Richtung generieren
    private static Vector getRandomDirection() {
        double x = random.nextDouble() * 2 - 1; // [-1, 1]
        double y = random.nextDouble() * 2 - 1; // [-1, 1]
        double z = random.nextDouble() * 2 - 1; // [-1, 1]
        return new Vector(x, y, z).normalize();
    }

    // Hilfsmethode: Einzelnen Partikel-Strahl spawnen
    private static void spawnParticleRay(World world, Location start, Vector direction, int length) {
        for (double d = 0; d < length; d += 0.3) { // Schrittweite anpassbar
            Vector offset = direction.clone().multiply(d);
            Location particleLoc = start.clone().add(offset);

            Bukkit.getScheduler().runTaskLater(Main.getInstance(), () -> {
                world.spawnParticle(
                        Particle.SCULK_SOUL,
                        particleLoc,
                        1,
                        0, 0, 0,
                        0
                );
            }, 5);
        }
    }

    public static void particleCircle(Player player, double radius, int particles, Particle particle) {
        Location center = player.getLocation();
        World world = center.getWorld();

        for (int i = 0; i < particles; i++) {
            double angle = 2 * Math.PI * i / particles;
            double x = radius * Math.cos(angle);
            double z = radius * Math.sin(angle);

            Location particleLoc = center.clone().add(x, 0, z);
            world.spawnParticle(particle, particleLoc, 1, 0, 0, 0, 0);
        }
    }
}
