package me.sandrp.soulMagicSystem.customWeapons.ingredients;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.world.LootGenerateEvent;

public class ChestListener implements Listener {
    @EventHandler
    public void onChestLoot(LootGenerateEvent event){
        if(Math.random() < 0.005){
            event.getLoot().add(SoulCrystal.createItem(1));
        }
        if(Math.random() < 0.008){
            event.getLoot().add(SoulCrystal.createItem(1));
        }
        if(Math.random() < 0.002){
            event.getLoot().add(SoulCrystal.createItem(2));
        }
    }
}
