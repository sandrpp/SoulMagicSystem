package me.sandrp.soulMagicSystem.customWeapons.ingredients;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.world.LootGenerateEvent;

public class ChestListener implements Listener {
    @EventHandler
    public void onChestLoot(LootGenerateEvent event){
        if(!event.getLootTable().getKey().getKey().contains("dungeon")){
            return;
        }
        if(Math.random() < 0.03){
            event.getLoot().add(LuminaCrystal.createItem(1));
        }
        if(Math.random() < 0.05){
            event.getLoot().add(LuminaCrystal.createItem(1));
        }
        if(Math.random() < 0.01){
            event.getLoot().add(LuminaCrystal.createItem(2));
        }
    }
}
