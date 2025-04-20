package me.sandrp.soulMagicSystem;

import me.sandrp.soulMagicSystem.customFood.soulApple.SoulApple;
import me.sandrp.soulMagicSystem.customFood.soulApple.ingredients.SoulBlood;
import me.sandrp.soulMagicSystem.customFood.soulApple.listeners.EatListener;
import me.sandrp.soulMagicSystem.customWeapons.soulSword.SoulAxe;
import me.sandrp.soulMagicSystem.customWeapons.soulSword.ingredients.SoulCrystal;
import me.sandrp.soulMagicSystem.customWeapons.soulSword.listener.DamageKillListener;
import me.sandrp.soulMagicSystem.customWeapons.soulSword.listener.DamageRightClickListener;
import me.sandrp.soulMagicSystem.utils.CooldownManager;
import me.sandrp.soulMagicSystem.customWeapons.luminaSword.LuminaSword;
import me.sandrp.soulMagicSystem.customWeapons.luminaSword.ingredients.LuminaCrystal;
import me.sandrp.soulMagicSystem.customWeapons.luminaSword.listener.HealRightClickListener;
import me.sandrp.soulMagicSystem.customWeapons.luminaSword.listener.HealSneakListener;
import me.sandrp.soulMagicSystem.utils.DatabaseManager;
import me.sandrp.soulMagicSystem.utils.AdminCommand;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public final class Main extends JavaPlugin {

    private static Main instance;
    private static DatabaseManager databaseManager;
    private static CooldownManager cooldownManager;

    @Override
    public void onEnable() {
        // Plugin startup logic
        instance = this;

        // Register custom items
        SoulCrystal.register(this);
        LuminaCrystal.register(this);
        SoulBlood.register(this);

        LuminaSword.register(this);
        LuminaSword.registerCraftingRecipe(this);

        SoulAxe.register(this);
        SoulAxe.registerCraftingRecipe(this);

        SoulApple.register(this);
        SoulApple.registerCraftingRecipe(this);

        // Register listeners
        Bukkit.getPluginManager().registerEvents(new HealRightClickListener(), this);
        Bukkit.getPluginManager().registerEvents(new HealSneakListener(), this);
        Bukkit.getPluginManager().registerEvents(new DamageRightClickListener(), this);
        Bukkit.getPluginManager().registerEvents(new DamageKillListener(), this);
        Bukkit.getPluginManager().registerEvents(new EatListener(), this);

        // Register commands
        Bukkit.getServer().getCommandMap().register("magicitem", "SoulMagicSystem", new AdminCommand("magicitem", this));

        // Initialize database manager
        databaseManager = new DatabaseManager();
        databaseManager.connect();

        cooldownManager = new CooldownManager(61000);
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
}
