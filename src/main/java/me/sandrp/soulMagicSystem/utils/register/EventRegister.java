package me.sandrp.soulMagicSystem.utils.register;

import me.sandrp.soulMagicSystem.customFood.soulBlood.listeners.WardenKillListener;
import me.sandrp.soulMagicSystem.customFood.soulBlood.listeners.EatListener;
import me.sandrp.soulMagicSystem.customWeapons.gravityWand.listener.GravityWandListener;
import me.sandrp.soulMagicSystem.customWeapons.ingredients.ChestListener;
import me.sandrp.soulMagicSystem.customWeapons.luminaSword.listener.LuminaRightClickListener;
import me.sandrp.soulMagicSystem.customWeapons.luminaSword.listener.LuminaSneakListener;
import me.sandrp.soulMagicSystem.customWeapons.soulAxe.listener.SoulAxeKillListener;
import me.sandrp.soulMagicSystem.customWeapons.soulAxe.listener.SoulAxeRightClickListener;
import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;

import org.jetbrains.annotations.NotNull;

import java.util.HashSet;
import java.util.Set;

//This class load all Events
public class EventRegister {

  private static final @NotNull Set<Listener> EVENTS =  new HashSet<>();

  static {
    EVENTS.add(new LuminaRightClickListener());
    EVENTS.add(new LuminaSneakListener());
    EVENTS.add(new SoulAxeRightClickListener());
    EVENTS.add(new SoulAxeKillListener());
    EVENTS.add(new EatListener());
    EVENTS.add(new GravityWandListener());
    EVENTS.add(new WardenKillListener());
    EVENTS.add(new ChestListener());
  }

  public static void registerEvents(@NotNull PluginManager pluginManager, @NotNull Plugin plugin){
    EVENTS.forEach(listener -> pluginManager.registerEvents(listener, plugin));
  }

  public static void registerEvents(@NotNull Plugin plugin){
    EVENTS.forEach(listener -> Bukkit.getPluginManager().registerEvents(listener, plugin));
  }

  public static void registerEvent(@NotNull PluginManager pluginManager, @NotNull Listener listener, @NotNull Plugin plugin){
    EVENTS.add(listener);
    pluginManager.registerEvents(listener, plugin);
  }

  public static void registerEvent(@NotNull Listener listener, @NotNull Plugin plugin){
    Bukkit.getPluginManager().registerEvents(listener, plugin);
  }

}
