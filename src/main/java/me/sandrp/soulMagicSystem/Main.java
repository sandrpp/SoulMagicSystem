package me.sandrp.soulMagicSystem;

import me.sandrp.soulMagicSystem.customFood.soulMeal.SoulMeal;
import me.sandrp.soulMagicSystem.customFood.soulMeal.ingredients.SoulBlood;
import me.sandrp.soulMagicSystem.customFood.soulMeal.ingredients.listener.WardenKillListener;
import me.sandrp.soulMagicSystem.customFood.soulMeal.listeners.EatListener;
import me.sandrp.soulMagicSystem.customWeapons.gravityGun.GravityWand;
import me.sandrp.soulMagicSystem.customWeapons.gravityGun.GravityWandTask;
import me.sandrp.soulMagicSystem.customWeapons.gravityGun.listener.GravityWandListener;
import me.sandrp.soulMagicSystem.customWeapons.soulAxe.SoulAxe;
import me.sandrp.soulMagicSystem.customWeapons.ingredients.SoulCrystal;
import me.sandrp.soulMagicSystem.customWeapons.soulAxe.listener.DamageKillListener;
import me.sandrp.soulMagicSystem.customWeapons.soulAxe.listener.DamageRightClickListener;
import me.sandrp.soulMagicSystem.utils.CooldownManager;
import me.sandrp.soulMagicSystem.customWeapons.luminaSword.LuminaSword;
import me.sandrp.soulMagicSystem.customWeapons.luminaSword.listener.HealRightClickListener;
import me.sandrp.soulMagicSystem.customWeapons.luminaSword.listener.HealSneakListener;
import me.sandrp.soulMagicSystem.utils.DatabaseManager;
import me.sandrp.soulMagicSystem.utils.AdminCommand;
import org.bukkit.Bukkit;
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

        // Register listeners
        Bukkit.getPluginManager().registerEvents(new HealRightClickListener(), this);
        Bukkit.getPluginManager().registerEvents(new HealSneakListener(), this);
        Bukkit.getPluginManager().registerEvents(new DamageRightClickListener(), this);
        Bukkit.getPluginManager().registerEvents(new DamageKillListener(), this);
        Bukkit.getPluginManager().registerEvents(new EatListener(), this);
        Bukkit.getPluginManager().registerEvents(new GravityWandListener(), this);
        Bukkit.getPluginManager().registerEvents(new WardenKillListener(), this);

        // Register commands
        Bukkit.getServer().getCommandMap().register("magicitem", "SoulMagicSystem", new AdminCommand("magicitem", this));

        // Initialize database manager
        databaseManager = new DatabaseManager();
        databaseManager.connect();

        cooldownManager = new CooldownManager(61000);
        grabbedEntities = new HashMap<>();

        new GravityWandTask().runTaskTimer(this, 0, 1);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
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
