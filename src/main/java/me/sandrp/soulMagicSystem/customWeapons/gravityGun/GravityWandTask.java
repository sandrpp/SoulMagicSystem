package me.sandrp.soulMagicSystem.customWeapons.gravityGun;

import me.sandrp.soulMagicSystem.Main;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;
import org.jetbrains.annotations.NotNull;

import java.util.Iterator;
import java.util.Map;

public class GravityWandTask extends BukkitRunnable { ;

    @Override
    public void run() {
        Iterator<Map.Entry<Player, Entity>> iterator = Main.getGrabbedEntities().entrySet().iterator();

        while (iterator.hasNext()) {
            Map.Entry<Player, Entity> entry = iterator.next();
            Player player = entry.getKey();
            Entity entity = entry.getValue();

            if (!isValid(player, entity)) {
                iterator.remove();
                continue;
            }

            Location targetLocation = player.getEyeLocation()
                    .add(player.getEyeLocation().getDirection().multiply(3))
                    .add(0, 0.5, 0);

            Vector direction = targetLocation.toVector().subtract(entity.getLocation().toVector());
            entity.setVelocity(direction.multiply(0.35));

            if (entity instanceof LivingEntity livingEntity) {
                livingEntity.setFallDistance(0);
            }
        }
    }

    private boolean isValid(Player player, Entity entity) {
        return entity.isValid()
                && !entity.isDead()
                && player.isOnline()
                && GravityWand.isGravityWand(player.getInventory().getItemInMainHand());
    }
}
