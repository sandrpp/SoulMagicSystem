package me.sandrp.soulMagicSystem.utils;

import me.sandrp.soulMagicSystem.Main;
import me.sandrp.soulMagicSystem.customFood.soulApple.ingredients.SoulBlood;
import me.sandrp.soulMagicSystem.customWeapons.soulSword.ingredients.SoulCrystal;
import me.sandrp.soulMagicSystem.customWeapons.luminaSword.ingredients.LuminaCrystal;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class AdminCommand extends Command {

    private final Main plugin;
    public AdminCommand(@NotNull String name, @NotNull Main plugin) {
        super(name);
        this.plugin = plugin;
    }

    @Override
    public boolean execute(@NotNull CommandSender sender, @NotNull String commandLabel, @NotNull String @NotNull [] args) {
        if (!(sender instanceof Player player)){
            return false;
        }
        if (args.length != 1) {
            player.sendMessage("§cUsage: /crystal <type>");
            return false;
        }
        if (!player.hasPermission("soulmagic.admin") && !player.isOp()) {
            player.sendMessage("§cYou do not have permission to use this command.");
            return false;
        }
        switch (args[0]) {
            case "lumina_crystal":
                player.getInventory().addItem(LuminaCrystal.createItem());
                break;
            case "soul_crystal":
                player.getInventory().addItem(SoulCrystal.createItem());
                break;
            case "soul_blood":
                player.getInventory().addItem(SoulBlood.createItem());
                break;
            default:
                player.sendMessage("§cInvalid type. Use 'lumina_crystal', 'soul_crystal' oder 'soul_blood'.");
                return false;
        }
        return true;
    }
}
