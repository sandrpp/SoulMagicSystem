package me.sandrp.soulMagicSystem;

import me.sandrp.soulMagicSystem.customFood.soulMeal.SoulMeal;
import me.sandrp.soulMagicSystem.customFood.soulMeal.ingredients.SoulBlood;
import me.sandrp.soulMagicSystem.customWeapons.gravityWand.GravityWand;
import me.sandrp.soulMagicSystem.customWeapons.gravityWand.GravityWandTask;
import me.sandrp.soulMagicSystem.customWeapons.soulAxe.SoulAxe;
import me.sandrp.soulMagicSystem.customWeapons.ingredients.SoulCrystal;
import me.sandrp.soulMagicSystem.customWeapons.CooldownManager;
import me.sandrp.soulMagicSystem.customWeapons.luminaSword.LuminaSword;
import me.sandrp.soulMagicSystem.utils.database.DatabaseManager;
import me.sandrp.soulMagicSystem.utils.register.CommandRegister;
import me.sandrp.soulMagicSystem.utils.register.EventRegister;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.Map;

public final class Main extends JavaPlugin {

    private static Main instance;
    private static DatabaseManager databaseManager;
    private static CooldownManager cooldownManager;
    private static Map <Player, Entity> grabbedEntities;

    @Override
    public void onEnable() {
        // Plugin startup logic
        instance = this;

        // Register custom items
        SoulCrystal.register(this);
        SoulBlood.register(this);
        LuminaSword.register(this);
        LuminaSword.registerCraftingRecipe(this);
        SoulAxe.register(this);
        SoulAxe.registerCraftingRecipe(this);
        SoulMeal.register(this);
        SoulMeal.registerCraftingRecipe(this);
        GravityWand.register(this);
        GravityWand.registerCraftingRecipe(this);

        // Register
        CommandRegister.registerCommands();
        EventRegister.registerEvents(this);

        // Initialize manager classes
        databaseManager = new DatabaseManager();
        databaseManager.connect();

        cooldownManager = new CooldownManager(61000);
        grabbedEntities = new HashMap<>();

        new GravityWandTask().runTaskTimer(this, 0, 1);
    }

    @Override
    public void onDisable() {
        databaseManager.disconnect();
        CommandRegister.unregisterCommands();
    }

    public static Main getInstance() {
        return instance;
    }

    public static DatabaseManager getDatabaseManager() {
        return databaseManager;
    }

    public static CooldownManager getCooldownManager() {
        return cooldownManager;
    }

    public static Map<Player, Entity> getGrabbedEntities() {
        return grabbedEntities;
    }
}
